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

    //random object
    Random rand = new Random();

    //deklarimi i ListViews qe do te perdoren per te shfaqur piket
    ListView<String> roundsListView;
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
        playerNames = names;
        points = new int[num][13];
        this.generateGame();
    }

    //gjeneron pamjen per lojen
    public void generateGame(){
        //setting the layout -> BORDERPANE
        BorderPane gameLayout = new BorderPane();

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
        roundsListView = new ListView<>();
        roundsListView.setMinWidth(300);
        roundsListView.getStyleClass().add("roundsList");
        for (int i = 0; i < 13; i++) {
            roundsListView.getItems().add(Round.rounds[i]);
        }

        //shfaq piket per player1
        playerOnePts = new ListView<>();
        scores.getChildren().addAll(roundsListView, playerOnePts);

        if (playerNumber>1){
            Label player2 = new Label(playerNames[1]);
            tableHeader.getChildren().add(player2);
            playerTwoPts = new ListView<>();
            scores.getChildren().add(playerTwoPts);
        }

        if (playerNumber>2){
            Label player3 = new Label(playerNames[2]);
            tableHeader.getChildren().add(player3);
            playerThreePts = new ListView<>();
            scores.getChildren().add(playerThreePts);
        }

        if(playerNumber==4){
            Label player4 = new Label(playerNames[3]);
            tableHeader.getChildren().add(player4);
            playerFourPts = new ListView<>();
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

        //inizializon zarat me nje vlere random
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

        skip.setOnMouseClicked(e -> {
            playerAtTurn++;
            chance = 0;
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

        System.out.println(timerTurnMax);

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

            int[] dicesVals = new int[5];

            setTimeout.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {

                        System.out.println("Chance: "+ chance + "\nRond: "+raund+"\n");

                        //Deselect the selected dices
                        for (CheckBox selectedDice : selectedDices) {
                            selectedDice.setSelected(false);
                        }

                        dicesVals[0] = Integer.parseInt(dice1.getText());
                        dicesVals[1] = Integer.parseInt(dice2.getText());
                        dicesVals[2] = Integer.parseInt(dice3.getText());
                        dicesVals[3] = Integer.parseInt(dice4.getText());
                        dicesVals[4] = Integer.parseInt(dice5.getText());

                        System.out.println(Arrays.toString(dicesVals));

                        if (playerAtTurn == playerNumber){
                            playerAtTurn = 0;
                            raund++;
                        }

                        points[playerAtTurn][raund] = CalcPts.calcPoits(raund, dicesVals, points, playerAtTurn);
                        updatePtsList(points[playerAtTurn][raund], playerNumber, playerOnePts, playerTwoPts, playerThreePts, playerFourPts);
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

    public static void updatePtsList(int pts, int playerNum, ListView<Integer> playerOnePts, ListView<Integer> playerTwoPts, ListView<Integer> playerThreePts, ListView<Integer> playerFourPts){
        if (playerAtTurn==0) {
            if (chance <= 3) {
                try {
                    playerOnePts.getItems().set(raund, pts);
                } catch (Exception ex) {
                    playerOnePts.getItems().add(pts);
                }
            } if (chance==3) {
                playerAtTurn++;
                chance = 0;
            }
        } else if (playerAtTurn==1) {
            if (chance <= 3) {
                try {
                    playerTwoPts.getItems().set(raund, pts);
                } catch (Exception ex) {
                    playerTwoPts.getItems().add(pts);
                }
            } if (chance==3) {
                playerAtTurn++;
                chance = 0;
            }
        } else if (playerAtTurn==2) {
            if (chance <= 3) {
                try {
                    playerThreePts.getItems().set(raund, pts);
                } catch (Exception ex) {
                    playerThreePts.getItems().add(pts);
                }
            } if (chance==3) {
                playerAtTurn++;
                chance = 0;
            }
        } else if (playerAtTurn==3) {
            if (chance <= 3) {
                try {
                    playerFourPts.getItems().set(raund, pts);
                } catch (Exception ex) {
                    playerFourPts.getItems().add(pts);
                }
            } if (chance==3){
                playerAtTurn++;
                chance = 0;
            }
        }
    }

}
