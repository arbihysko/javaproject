import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Player {
    //metoda choose -> krijon pamjen grafike per te zgjedhur lojtaret
    public static Scene choose(Stage stage, Scene menuScene){
        Scene choosePlayersScene;

        BorderPane choosePlayersLayout = new BorderPane();

        //setting pjesen e siperme te layout
        HBox top = new HBox(53);
        //butoni goBack -> Main
        Button goBackMain = new Button("←");
        goBackMain.getStyleClass().add("goBackBtn");
        goBackMain.setOnMouseClicked(e -> stage.setScene(menuScene));

        //topLabel setting
        Label topLabel = new Label("Zgjidhni numrin e lojtareve!");
        topLabel.getStyleClass().add("welcomeLabel");
        topLabel.setPadding(new Insets(7));

        top.getChildren().addAll(goBackMain, topLabel);
        choosePlayersLayout.setTop(top);

        //setting center content
        VBox gameMode = new VBox(10);

        //butonat per zgjedhjen e numrit te lojtareve
        Button solo = new Button("Go Solo");
        solo.setOnMouseClicked(e -> {
            stage.setScene(setNames(stage, choose(stage, menuScene), 1));
        });
        Button twoPlayer = new Button("Dy Lojtare");
        twoPlayer.setOnMouseClicked(e -> {
            stage.setScene(setNames(stage, choose(stage, menuScene), 2));
        });
        Button threePlayer = new Button("Tre Lojtare");
        threePlayer.setOnMouseClicked(e -> {
            stage.setScene(setNames(stage, choose(stage, menuScene), 3));
        });
        Button fourPlayer = new Button("Kater Lojtare");
        fourPlayer.setOnMouseClicked(e -> {
            stage.setScene(setNames(stage, choose(stage, menuScene), 4));
        });

        //vendosim minWidth per cdo buton qe te dalin ne gjatesi uniform
        solo.setMinWidth(120);
        twoPlayer.setMinWidth(120);
        threePlayer.setMinWidth(120);
        fourPlayer.setMinWidth(120);

        //shtojme klasen startBtn per cdo buton
        solo.getStyleClass().add("startBtn");
        twoPlayer.getStyleClass().add("startBtn");
        threePlayer.getStyleClass().add("startBtn");
        fourPlayer.getStyleClass().add("startBtn");

        //konfigurojme butonat ne VBox
        gameMode.getChildren().addAll(solo, twoPlayer, threePlayer, fourPlayer);
        gameMode.setAlignment(Pos.CENTER);

        //konfigurojme center content ne Layout
        choosePlayersLayout.setCenter(gameMode);

        //setting the scene
        choosePlayersScene =  new Scene(choosePlayersLayout, 500, 400);
        choosePlayersScene.getStylesheets().add("public/styles/menu.css");

        //kthejme klasen
        return  choosePlayersScene;
    }

    //metoda setNames do ktheje nje Scene -> VIEW PER TE VENDOSUR EMRAT
    public static Scene setNames(Stage stage, Scene oldScene, int playersNum){
        Scene setNamesScene;

        //zgjedhim layout -> BorderPane
        BorderPane setPlayerNames = new BorderPane();

        //setting top section layout -> HBox
        HBox top = new HBox(57);

        //goBack button
        Button goBack = new Button("←");
        goBack.getStyleClass().add("goBackBtn");
        goBack.setOnMouseClicked(e -> stage.setScene(oldScene));

        //topLabel
        Label hintLabel = new Label("Vendosni emrat e lojtareve!");
        hintLabel.getStyleClass().add("welcomeLabel");
        hintLabel.setPadding(new Insets(7));
        top.getChildren().addAll(goBack, hintLabel);

        //konfigurojme top Section
        setPlayerNames.setTop(top);

        //layout per center section -> VBox
        VBox names = new VBox(10);

        //TextFields
        TextField player1 = new TextField();
        player1.setPromptText("Lojtari i pare");

        TextField player2 = new TextField();
        player2.setPromptText("Lojtari i dyte");

        TextField player3 = new TextField();
        player3.setPromptText("Lojtari i trete");

        TextField player4 = new TextField();
        player4.setPromptText("Lojtari i katert");

        //vendosim player1 ne layout pasi do e kemi ne cdo rast
        names.getChildren().add(player1);

        if (playersNum>1)//kur zgjedhim me shume se 1 player
            names.getChildren().add(player2);
        if (playersNum>2)//kur zgjedhim me shume se 2 player
            names.getChildren().add(player3);
        if (playersNum==4)//kur zgjedhim 4 player
            names.getChildren().add(player4);

        names.setAlignment(Pos.TOP_CENTER);

        //Butoni per te krijuar the actual GAME
        //E vendosim ne nje Hbox per te realizuar right alignment
        HBox lowerBtn = new HBox();
        Button createGame = new Button("→");
        createGame.setOnMouseClicked(e -> {
            //mbledhim tekstet ne te gjitha text fields
            String[] playerNames = {player1.getText(), player2.getText(), player3.getText(), player4.getText()};
            //krijojme GAME
            Game newGame = new Game(playersNum, playerNames, stage);
        });

        createGame.getStyleClass().add("goToGameBtn");
        lowerBtn.getChildren().add(createGame);
        //RIGHT ALIGNMENT dhe kufizojme width qe te dale ne nje vije me Text fields
        lowerBtn.setAlignment(Pos.BASELINE_RIGHT);
        lowerBtn.setMaxWidth(300.0);

        //vendosim bottom section ne Layout
        names.getChildren().add(lowerBtn);

        //Layout Settings
        names.setPadding(new Insets(50, 0, 0 , 0));
        setPlayerNames.setCenter(names);

        //Scene Settings
        setNamesScene = new Scene(setPlayerNames, 500, 400);
        setNamesScene.getStylesheets().add("public/styles/menu.css");
        return setNamesScene;
    }
}
