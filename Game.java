import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Array;
import java.util.*;

import javax.swing.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Game {
    Stage stage;
    Scene gameScene;

    //gameSpecs
    private int playerNumber;
    private String[] playerNames;
    private int[][] points;

    //kjo variabel do te mbaje numrin per try
    static int chance;
    static int raund;
    static int playerAtTurn;

    public static String[] raunds = {
            "Njesha",
            "Dysha",
            "Tresha",
            "Katra",
            "Pesa",
            "Gjashta",
            "Piket e siperme",
            "Bonus",
            "Tre me nje vlere",
            "Kater me nje vlere",
            "Tre dhe Dy",
            "Kater te njepasnjeshme",
            "Pese te njepasnjeshme",
            "E njejta vlere",
            "Cdo Rast",
            "Piket e siperme",
            "Total"
    };

    //random object
    Random rand = new Random(System.currentTimeMillis());

    //deklarimi i ListViews qe do te perdoren per te shfaqur piket
    ListView<String> raundsListView;
    ListView<Integer> playerOnePts;
    ListView<Integer> playerTwoPts;
    ListView<Integer> playerThreePts;
    ListView<Integer> playerFourPts;

    //deklarimi -> ZARAT
    private CheckBox dice1;
    private CheckBox dice2;
    private CheckBox dice3;
    private CheckBox dice4;
    private CheckBox dice5;

    //perdoren te Timer
    static Timer setTimeout = new Timer();
    static int timerTurnMax;
    static int timerTurn = 0;


    //constuctor me numrin e players dhe emrat
    public Game(int num, String[] names, Stage stage){
        this.stage = stage;
        playerNumber = num;
        for (int i = 0; i < num; i++) {
            names[i] = Character.toUpperCase(names[i].charAt(0)) + names[i].substring(1,names[i].length()).toLowerCase();
        }
        playerNames = names;
        points = new int[num][17];
        this.generateGame();
    }

    //gjeneron pamjen per lojen
    public void generateGame(){
        //setting the layout -> BORDERPANE
        BorderPane gameLayout = new BorderPane();

        HBox gameIndicator = new HBox(50);
        Label raundIndicator = new Label("Raundi 1");
        Label playerIndicator = new Label(playerNames[0]);
        Label tryIndicator = new Label("Shanci 1");

        gameIndicator.getChildren().addAll(playerIndicator,raundIndicator,tryIndicator);
        gameIndicator.setAlignment(Pos.CENTER);
        gameIndicator.setPadding(new Insets(0,0,20,0));
        gameLayout.setTop(gameIndicator);
        BorderPane.setAlignment(gameIndicator, Pos.CENTER);

        //tableTop
        VBox centerUnit = new VBox(10);

        HBox tableHeader = new HBox(20);
        Label roundsLabel = new Label("Raundi");
        roundsLabel.getStyleClass().add("roundsLabel");
        Label player1 = new Label(playerNames[0]);

        tableHeader.getChildren().addAll(roundsLabel, player1);
        tableHeader.setAlignment(Pos.CENTER);


        //center content
        HBox scores = new HBox(20);

        //shfaq raundet
        raundsListView = new ListView<>();
        raundsListView.setMinWidth(300);
        raundsListView.getStyleClass().add("roundsList");
        for (int i = 0; i < 17; i++) {
            raundsListView.getItems().add(raunds[i]);
        }

        //shfaq piket per player1
        playerOnePts = new ListView<>();
        playerOnePts.getStyleClass().add("bigList");
        scores.getChildren().addAll(raundsListView, playerOnePts);

        if (playerNumber>1){
            Label player2 = new Label(playerNames[1]);
            tableHeader.getChildren().add(player2);
            playerTwoPts = new ListView<>();
            playerTwoPts.getStyleClass().add("bigList");
            scores.getChildren().add(playerTwoPts);
        }

        if (playerNumber>2){
            Label player3 = new Label(playerNames[2]);
            tableHeader.getChildren().add(player3);
            playerThreePts = new ListView<>();
            playerThreePts.getStyleClass().add("bigList");
            scores.getChildren().add(playerThreePts);
        }

        if(playerNumber==4){
            Label player4 = new Label(playerNames[3]);
            tableHeader.getChildren().add(player4);
            playerFourPts = new ListView<>();
            playerFourPts.getStyleClass().add("bigList");
            scores.getChildren().add(playerFourPts);
        }

        scores.setAlignment(Pos.CENTER);

        centerUnit.getChildren().add(tableHeader);
        centerUnit.getChildren().add(scores);

        gameLayout.setCenter(centerUnit);

        //Sektori i poshtem
        VBox bottomSection = new VBox(20);

        //Do te mbaje zarat
        HBox dices = new HBox(20);

        //inizializon zarat me nje vlere random dhe vendosim figurat
        int randomNr = rand.nextInt(6)+1;
        dice1 = new CheckBox(""+randomNr);
        dice1.setStyle("-fx-background-image: url('public/images/zari-"+randomNr+".png')");
        randomNr = rand.nextInt(6)+1;
        dice2 = new CheckBox("" + (randomNr));
        dice2.setStyle("-fx-background-image: url('public/images/zari-"+randomNr+".png')");
        randomNr = rand.nextInt(6)+1;
        dice3 = new CheckBox("" + (randomNr));
        dice3.setStyle("-fx-background-image: url('public/images/zari-"+randomNr+".png')");
        randomNr = rand.nextInt(6)+1;
        dice4 = new CheckBox(""+(randomNr));
        dice4.setStyle("-fx-background-image: url('public/images/zari-"+randomNr+".png')");
        randomNr = rand.nextInt(6)+1;
        dice5 = new CheckBox(""+(randomNr));
        dice5.setStyle("-fx-background-image: url('public/images/zari-"+randomNr+".png')");

        dices.getChildren().addAll(dice1, dice2, dice3, dice4, dice5);
        dices.setAlignment(Pos.CENTER);

        //Do te mbaje butonat e nevojshem per lojen
        HBox buttons = new HBox(20);

        //Creating the buttons
        Button throwDices = new Button("Hidh Zaret");
        Button skip = new Button("Skip");

        throwDices.getStyleClass().add("btn-blue");
        skip.getStyleClass().add("btn-red");

        int[] dicesVals = new int[5];

        skip.setOnMouseClicked(e -> {
            if ((raund==5||raund==14)&&playerAtTurn==playerNumber-1)
                updateSpecial(stage, playerNumber, points, dicesVals, playerOnePts, playerTwoPts, playerThreePts, playerFourPts, playerNames);
            else {
                playerAtTurn++;
                chance = 0;
            }

            int thisRaund=raund+1;
            if (playerAtTurn==playerNumber)
                thisRaund=raund+2;

            playerIndicator.setText(playerNames[(playerAtTurn==playerNumber)?0:playerAtTurn]);
            raundIndicator.setText("Raundi " + thisRaund);
            tryIndicator.setText("Shanci 1");

            throwDices.setText("Hidh Zaret");
            throwDices.setDisable(false);
            skip.setDisable(true);

        });

        skip.setDisable(true);

        dice1.setOnAction(e -> throwDices.setDisable(false));
        dice2.setOnAction(e -> throwDices.setDisable(false));
        dice3.setOnAction(e -> throwDices.setDisable(false));
        dice4.setOnAction(e -> throwDices.setDisable(false));
        dice5.setOnAction(e -> throwDices.setDisable(false));

        //gjenerojme nje numer random per sa here do behet animimi
        timerTurnMax = rand.nextInt(5)+10;
//        timerTurnMax = 2;
        throwDices.setOnAction(event -> {


            //marrim zarat e selektuar.. nese jemi ne try e pare do na ktheje te gjithe zarat
            ArrayList<CheckBox> selectedDices = getSelectedDices(dice1,dice2,dice3,dice4,dice5);

            //Timer per Animimin e zareve
            Timer diceAnimationDelay = new Timer();
            //funksioni qe do te thirret periodikisht
            diceAnimationDelay.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    //disable the buttons
                    throwDices.setDisable(true);
                    skip.setDisable(true);

                    //JavaFx hack
                    Platform.runLater(()-> {

                        //ne fund te animimit
                        if (++timerTurn > timerTurnMax) {
                            diceAnimationDelay.cancel();
                            timerTurn = 0;

                            //enable buttons
                            if (chance == 3) {
                                throwDices.setDisable(false);
                                throwDices.setText("Hidh Zaret");
                            } else {
                                skip.setDisable(false);
                                throwDices.setText("Provo Perseri");
                            }
                        }

                        //ndrron numrin e zarave
                        for (CheckBox selectedDice : selectedDices) {
                            int randomNr = rand.nextInt(6) + 1;
                            selectedDice.setText("" + randomNr);
                            selectedDice.setStyle("-fx-background-image: url('public/images/zari-" + randomNr + ".png')");
                        }
                    });
                }
            },0, 120);

            setTimeout.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {

                        //Deselect the selected dices
                        for (CheckBox selectedDice : selectedDices) {
                            selectedDice.setSelected(false);
                        }

                        dicesVals[0] = Integer.parseInt(dice1.getText());
                        dicesVals[1] = Integer.parseInt(dice2.getText());
                        dicesVals[2] = Integer.parseInt(dice3.getText());
                        dicesVals[3] = Integer.parseInt(dice4.getText());
                        dicesVals[4] = Integer.parseInt(dice5.getText());


                        if (playerAtTurn == playerNumber){
                            playerAtTurn = 0;
                            raund++;
                        }

                        points[playerAtTurn][raund] = CalcPts.calcPoits(raund, dicesVals, points, playerAtTurn);
                        updatePtsList(points[playerAtTurn][raund], playerOnePts, playerTwoPts, playerThreePts, playerFourPts);

//                        System.out.println("Chance: "+ chance + "\nRaund: "+raund+"\nTurn: "+playerAtTurn+"\n");

                        if ((raund==5||raund==14)&&chance==3&&(playerAtTurn==(playerNumber-1))) {
                            updateSpecial(stage, playerNumber, points, dicesVals, playerOnePts, playerTwoPts, playerThreePts, playerFourPts, playerNames);
                        }

                        if (chance==3) {
                            playerAtTurn++;
                            chance = 0;
                        }

                        tryIndicator.setText("Shanci "+(chance+1));
                        playerIndicator.setText(playerNames[(playerAtTurn==playerNumber)?0:playerAtTurn]);
                        raundIndicator.setText("Raundi "+(raund+1));

                    });

                }
            }, ((120*timerTurnMax)+200));

        });

        //konfigurojme butonat ne layout
        buttons.getChildren().addAll(throwDices, skip);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setMaxWidth(350);
        bottomSection.getChildren().addAll(buttons, dices);
        bottomSection.setAlignment(Pos.CENTER);

        gameLayout.setBottom(bottomSection);

        gameLayout.setPadding(new Insets(50));

        //Scene settings
        gameScene = new Scene(gameLayout, 1024, 600);
        gameScene.getStylesheets().add("public/styles/game.css");
        stage.setScene(gameScene);
        stage.setMaximized(true);
        stage.setOnCloseRequest(e -> {
            setTimeout.cancel();
        });
    }

    //ky funksion do te ktheje zarat e selektuar
    static ArrayList<CheckBox> getSelectedDices(CheckBox dice1, CheckBox dice2, CheckBox dice3, CheckBox dice4, CheckBox dice5){
        ArrayList<CheckBox> dices = new ArrayList<>();

        //nese jemi ne try e pare kthe te gjithe zarat
        if (chance == 0){
            dices.add(dice1);
            dices.add(dice2);
            dices.add(dice3);
            dices.add(dice4);
            dices.add(dice5);
        } else {
            //gjej zarat e selektuar
            if (dice1.isSelected())
                dices.add(dice1);
            if (dice2.isSelected())
                dices.add(dice2);
            if (dice3.isSelected())
                dices.add(dice3);
            if (dice4.isSelected())
                dices.add(dice4);
            if (dice5.isSelected())
                dices.add(dice5);
        }
        chance ++;

        return dices;
    }

    public static void updatePtsList(int pts, ListView<Integer> playerOnePts, ListView<Integer> playerTwoPts, ListView<Integer> playerThreePts, ListView<Integer> playerFourPts){
        if (playerAtTurn==0) {
            if (chance <= 3) {
                try {
                    playerOnePts.getItems().set(raund, pts);
                } catch (Exception ex) {
                    playerOnePts.getItems().add(pts);
                }
            }
        } else if (playerAtTurn==1) {
            if (chance <= 3) {
                try {
                    playerTwoPts.getItems().set(raund, pts);
                } catch (Exception ex) {
                        playerTwoPts.getItems().add(pts);

                }
            }
        } else if (playerAtTurn==2) {
            if (chance <= 3) {
                try {
                    playerThreePts.getItems().set(raund, pts);
                } catch (Exception ex) {
                    playerThreePts.getItems().add(pts);
                }
            }
        } else if (playerAtTurn==3) {
            if (chance <= 3) {
                try {
                    playerFourPts.getItems().set(raund, pts);
                } catch (Exception ex) {
                    playerFourPts.getItems().add(pts);
                }
            }
        }
    }

    static void updateSpecial(Stage stage, int playerNumber, int[][] points, int[] dicesVals, ListView<Integer> playerOnePts, ListView<Integer> playerTwoPts, ListView<Integer> playerThreePts, ListView<Integer> playerFourPts, String[] playerNames) {
        if (raund==5) {
            raund++;
            for (int i = 0; i < playerNumber; i++) {
                playerAtTurn = i;
                points[i][6] = CalcPts.calcPoits(6, dicesVals, points, playerAtTurn);
                updatePtsList(points[i][6], playerOnePts, playerTwoPts, playerThreePts, playerFourPts);
            }
            raund++;
            for (int i = 0; i < playerNumber; i++) {
                playerAtTurn = i;
                points[i][7] = CalcPts.calcPoits(7, dicesVals, points, playerAtTurn);
                updatePtsList(points[i][6], playerOnePts, playerTwoPts, playerThreePts, playerFourPts);
            }
            raund++;
            chance = 0;
            playerAtTurn = 0;
        } else {
            raund++;
            for (int i = 0; i < playerNumber; i++) {
                playerAtTurn = i;
                points[i][15] = CalcPts.calcPoits(15, dicesVals, points, playerAtTurn);
                updatePtsList(points[i][15], playerOnePts, playerTwoPts, playerThreePts, playerFourPts);
            }
            raund++;
            for (int i = 0; i < playerNumber; i++) {
                playerAtTurn = i;
                points[i][16] = CalcPts.calcPoits(16, dicesVals, points, playerAtTurn);
                updatePtsList(points[i][16], playerOnePts, playerTwoPts, playerThreePts, playerFourPts);
            }
//            game over
            if (playerNumber==1)
                EndGame.endGameSolo(stage, points[0][16]);
            else
                EndGame.multiPlayerEnd(stage, playerNumber,points,playerNames);
        }
    }

}
