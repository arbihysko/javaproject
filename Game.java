import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Timer;

import javax.swing.*;
import java.util.Random;
import java.util.TimerTask;
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
        VBox centerUnit = new VBox();

        HBox tableHeader = new HBox();
        Label roundsLabel = new Label("Raundi");
        Label player1 = new Label(playerNames[0]);

        tableHeader.getChildren().addAll(roundsLabel, player1);

        centerUnit.getChildren().add(tableHeader);

        //center content
        HBox scores = new HBox(20);

        //shfaq raundet
        roundsListView = new ListView<>();
        for (int i = 0; i < 13; i++) {
            roundsListView.getItems().add(Round.rounds[i]);
        }

        //shfaq piket per player1
        playerOnePts = new ListView<>();


        scores.getChildren().addAll(roundsListView, playerOnePts);

        centerUnit.getChildren().add(scores);

        gameLayout.setCenter(centerUnit);

        //Sektori i poshtem
        VBox bottomSection = new VBox();

        //Do te mbaje zarat
        HBox dices = new HBox(20);

        //inizializon zarat me nje vlere random
        dice1 = new CheckBox(""+(rand.nextInt(6)+1));
        dice2 = new CheckBox(""+(rand.nextInt(6)+1));
        dice3 = new CheckBox(""+(rand.nextInt(6)+1));
        dice4 = new CheckBox(""+(rand.nextInt(6)+1));
        dice5 = new CheckBox(""+(rand.nextInt(6)+1));

        dices.getChildren().addAll(dice1, dice2, dice3, dice4, dice5);

        //Do te mbaje butonat e nevojshem per lojen
        HBox buttons = new HBox();

        //Creating the buttons
        Button throwDices = new Button("Hidh Zaret");
        Button skip = new Button("Skip");

        //Timeout function per te hedhur zaret
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

                    //gjenerojme nje numer random per sa here do behet animimi
                    timerTurnMax = rand.nextInt(5)+10;

                    //ne fund te animimit
                    if (++timerTurn>timerTurnMax){
                        diceAnimationDelay.cancel();
                        timerTurn=0;

                        //enable buttons
                        throwDices.setDisable(false);
                        skip.setDisable(false);
                    }
                    //JavaFx hack
                    Platform.runLater(()->{
                        //ndrron numrin e zarave
                        for (int i = 0; i < selectedDices.size(); i++) {
                            selectedDices.get(i).setText(""+(rand.nextInt(6)+1));
                        }
                    });
                }
            },0, 120);

            //Deselect the selected dices
            for (int i = 0; i < selectedDices.size(); i++) {
                selectedDices.get(i).setSelected(false);
            }

            int[] dicesVals = {
                    Integer.parseInt(dice1.getText()),
                    Integer.parseInt(dice2.getText()),
                    Integer.parseInt(dice3.getText()),
                    Integer.parseInt(dice4.getText()),
                    Integer.parseInt(dice5.getText()),
            };

            points[playerAtTurn][raund] = CalcPts.calcPoits(raund, dicesVals);
            this.updatePtsList(raund, points[playerAtTurn][raund]);
            if(++playerAtTurn == playerNumber){
                playerAtTurn = 0;
                raund++;
            }
        });

        //konfigurojme butonat ne layout
        buttons.getChildren().addAll(throwDices, skip);
        bottomSection.getChildren().addAll(buttons, dices);

        gameLayout.setBottom(bottomSection);

        //Scene settings
        gameScene = new Scene(gameLayout, 1024, 600);
        gameScene.getStylesheets().add("public/styles/game.css");
        stage.setScene(gameScene);
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

        //rrit try
        chance++;

        return dices;
    }

    public void updatePtsList(int raund, int pts){

    }

}
