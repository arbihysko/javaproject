import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        VBox menuLayout = new VBox(20); // krijon nje VBox ku elementet jane spaced 20 px

        Label topLabel = new Label("WELCOME TO THE GAME!");
        Button startGame = new Button("Start Game");
        startGame.setOnMouseClicked(e -> stage.setScene(choosePlayers));
        menuLayout.getChildren().addAll(topLabel, startGame);
        menuLayout.setAlignment(Pos.CENTER);

        menuScene = new Scene(menuLayout, 400, 400);

        // choose players layout
        VBox choosePlayersLayout = new VBox(20);

        Label howManyPlayers = new Label("Zgjidhni sa lojtare jane!");
        Button onePlayer = new Button("Luaj Solo");
        Button twoPlayer = new Button("Luaj Dyshe");
        Button threePlayer = new Button("Luaj Treshe");
        Button fourPlayer = new Button("Luaj Katershe");

        Button goBackMain = new Button("Back");
        goBackMain.setOnMouseClicked(e -> stage.setScene(menuScene));

        choosePlayersLayout.getChildren().addAll(howManyPlayers, onePlayer, twoPlayer, threePlayer, fourPlayer, goBackMain);
        choosePlayersLayout.setAlignment(Pos.CENTER);

        choosePlayers = new Scene(choosePlayersLayout, 400, 400);

        stage.setScene(menuScene);
        stage.show();
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
