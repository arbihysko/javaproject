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

    Scene menuScene; //set the scene

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    // metoda qe ekzekutohet kur run application
    public void start(Stage stage) throws Exception {

        //Set the title of stage
        stage.setTitle("Welcome");

        //Start game layout -> BorderPane
        BorderPane menuLayout = new BorderPane();

        //Vendosim lart 1 Label per welcome
        Label topLabel = new Label("WELCOME TO THE GAME!");
        topLabel.getStyleClass().add("welcomeLabel");
        menuLayout.setTop(topLabel); //konfiguron ne layout

        //Center content -> VBox
        VBox center = new VBox(10);

        //Butoni per te filluar lojen
        Button startGame = new Button("Start Game");
        startGame.getStyleClass().add("startBtn");

        //Set new Scene -> CHOOSE PLAYER
        startGame.setOnMouseClicked(e -> stage.setScene(Player.choose(stage, menuScene)));

        //Load Game nga filet TODO
        Button loadGame = new Button("Load Game");
        loadGame.getStyleClass().add("startBtn");

        center.getChildren().addAll(startGame, loadGame);//konfigurojme ne VBox
        center.setAlignment(Pos.CENTER);//pozicionojme ne qender
        menuLayout.setCenter(center);//konfigurojme VBox -> layout

        //Butoni per tu loguar
        Button logIn = new Button("Log In");
        logIn.getStyleClass().add("logInBtn");

        menuLayout.setBottom(logIn);

        //Pozicionimi per secilen Node
        BorderPane.setAlignment(topLabel, Pos.CENTER);
        BorderPane.setAlignment(center, Pos.CENTER);
        BorderPane.setAlignment(logIn, Pos.CENTER);

        //Hapesira e Layout nga borderat e dritares
        menuLayout.setPadding(new Insets(20, 0, 20, 0));

        //setting the scene
        menuScene = new Scene(menuLayout, 500, 400);
        menuScene.getStylesheets().add("public/styles/menu.css");//aplikimi i stylesheetsave

        //setting the stage
        stage.setScene(menuScene);
//        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
