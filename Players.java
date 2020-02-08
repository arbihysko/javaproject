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

public class Players {
    public static Scene choose(Stage stage, Scene menuScene){
        stage.setTitle("Zgjidhni numrin e Lojtareve");

        Scene choosePlayersScene;

        BorderPane choosePlayersLayout = new BorderPane();

        Button goBackMain = new Button("←");
        goBackMain.getStyleClass().add("goBackBtn");
        goBackMain.setOnMouseClicked(e -> stage.setScene(menuScene));
        choosePlayersLayout.setTop(goBackMain);

        VBox gameMode = new VBox(10);

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

        solo.setMinWidth(120);
        twoPlayer.setMinWidth(120);
        threePlayer.setMinWidth(120);
        fourPlayer.setMinWidth(120);

        solo.getStyleClass().add("startBtn");
        twoPlayer.getStyleClass().add("startBtn");
        threePlayer.getStyleClass().add("startBtn");
        fourPlayer.getStyleClass().add("startBtn");

        gameMode.getChildren().addAll(solo, twoPlayer, threePlayer, fourPlayer);
        gameMode.setAlignment(Pos.CENTER);

        choosePlayersLayout.setCenter(gameMode);

        choosePlayersScene =  new Scene(choosePlayersLayout, 500, 400);
        choosePlayersScene.getStylesheets().add("styles.css");

        return  choosePlayersScene;
    }

    public static Scene setNames(Stage stage, Scene oldScene, int playersNum){
        Scene setNamesScene;

        stage.setTitle("Zgjidhni numrin e Lojtareve");

        BorderPane setPlayerNames = new BorderPane();

        Button goBack = new Button("←");
        goBack.getStyleClass().add("goBackBtn");
        goBack.setOnMouseClicked(e -> stage.setScene(oldScene));
        setPlayerNames.setTop(goBack);

        setPlayerNames.setTop(goBack);

        VBox names = new VBox(10);

        TextField player1 = new TextField();
        player1.setPromptText("Lojtari i pare");
        names.getChildren().add(player1);

        if (playersNum>1){
            TextField player2 = new TextField();
            player2.setPromptText("Lojtari i dyte");
            names.getChildren().add(player2);
        }
        if (playersNum>2){
            TextField player3 = new TextField();
            player3.setPromptText("Lojtari i trete");
            names.getChildren().add(player3);
        }
        if (playersNum==4){
            TextField player4 = new TextField();
            player4.setPromptText("Lojtari i katert");
            names.getChildren().add(player4);
        }

        names.setAlignment(Pos.TOP_CENTER);

        HBox lowerBtn = new HBox();
        Button createGame = new Button("→");
        createGame.getStyleClass().add("goToGameBtn");
        lowerBtn.getChildren().add(createGame);
        lowerBtn.setAlignment(Pos.BASELINE_RIGHT);
        lowerBtn.setMaxWidth(300.0);

        names.getChildren().add(lowerBtn);

        names.setPadding(new Insets(50, 0, 0 , 0));
        setPlayerNames.setCenter(names);

        setNamesScene = new Scene(setPlayerNames, 500, 400);
        setNamesScene.getStylesheets().add("styles.css");
        return setNamesScene;
    }
}
