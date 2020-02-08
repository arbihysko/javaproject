import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent> {

    Scene menuScene, choosePlayers;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Welcome");

        // Start game layout
        BorderPane menuLayout = new BorderPane();

        //HBox top = new HBox(20);
        Label topLabel = new Label("WELCOME TO THE GAME!");
        topLabel.getStyleClass().add("welcomeLabel");
        menuLayout.setTop(topLabel);
        //top.getChildren().add(topLabel);
        //top.setAlignment(Pos.CENTER);

        VBox center = new VBox(10);

        Button startGame = new Button("Start Game");
        startGame.getStyleClass().add("startBtn");
        startGame.setOnMouseClicked(e -> stage.setScene(Players.choose(stage, menuScene)));

        Button loadGame = new Button("Load Game");
        loadGame.getStyleClass().add("startBtn");

        center.getChildren().addAll(startGame, loadGame);
        center.setAlignment(Pos.CENTER);
        menuLayout.setCenter(center);

        Button logIn = new Button("Log In");
        logIn.getStyleClass().add("logInBtn");

        menuLayout.setBottom(logIn);


        menuLayout.setAlignment(topLabel, Pos.CENTER);
        menuLayout.setAlignment(center, Pos.CENTER);
        menuLayout.setAlignment(logIn, Pos.CENTER);

        menuLayout.setPadding(new Insets(20, 0, 20, 0));

        menuScene = new Scene(menuLayout, 500, 400);
        menuScene.getStylesheets().add("styles.css");

        stage.setScene(menuScene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
