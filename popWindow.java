import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;


public class popWindow {
    public static void logIn(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);

        BorderPane loginLayout = new BorderPane();

        VBox loginGroup = new VBox(10);
        Label errValLabel = new Label();
        errValLabel.getStyleClass().add("valErrorLabel");

        loginLayout.setTop(errValLabel);
        loginLayout.setAlignment(errValLabel, Pos.TOP_CENTER);

        Label loginLabel = new Label("Login:");
        loginLabel.setStyle("-fx-alignment: baseline-left;-fx-text-fill:#41EAD4");
        TextField userNameField = new TextField();
        userNameField.setPromptText("Username");
        PasswordField passwField = new PasswordField();
        passwField.setPromptText("Password");

        HBox btnBox = new HBox();

        TextField[] inputs = {userNameField, passwField};

        Button submit = new Button("Log In");
        submit.getStyleClass().add("submitBtn");
        submit.setOnMouseClicked(e -> {
            if (Main.validateFormNotEmpty(2, inputs, submit)){

            } else {
                errValLabel.setText("Username ose passwordi jane bosh!");
            }
        });

        userNameField.setOnKeyPressed(e -> {
            userNameField.getStyleClass().remove("fieldValError");
            errValLabel.setText("");
            submit.setDisable(false);
        });
        passwField.setOnKeyPressed(e -> {
            passwField.getStyleClass().remove("fieldValError");
            errValLabel.setText("");
            submit.setDisable(false);
        });

        loginLabel.setPrefWidth(300);
        loginLabel.setAlignment(Pos.BASELINE_LEFT);
        btnBox.getChildren().add(submit);
        btnBox.setMaxWidth(300);
        btnBox.setAlignment(Pos.BASELINE_RIGHT);

        loginGroup.getChildren().addAll(loginLabel,userNameField,passwField,btnBox);
        loginGroup.setPadding(new Insets(20,0,0,0));
        loginGroup.setAlignment(Pos.TOP_CENTER);

        loginLayout.setCenter(loginGroup);

        Button signUp = new Button("Rregjistrohu");
        signUp.getStyleClass().add("signUpBtn");

        loginLayout.setBottom(signUp);
        loginLayout.setAlignment(signUp, Pos.CENTER);
        loginLayout.setPadding(new Insets(20));

        Scene loginScene = new Scene(loginLayout, 400, 400);
        signUp.setOnMouseClicked(e -> signUp(stage, loginScene));
        loginScene.getStylesheets().add("public/styles/game.css");
        stage.setScene(loginScene);
        stage.showAndWait();
    }

    public static void signUp(Stage stage, Scene signIn){
        BorderPane loginLayout = new BorderPane();

        VBox loginGroup = new VBox(10);
        Label errValLabel = new Label();
        errValLabel.getStyleClass().add("valErrorLabel");

        loginLayout.setTop(errValLabel);
        loginLayout.setAlignment(errValLabel, Pos.TOP_CENTER);

        Label loginLabel = new Label("Rregjistrohu:");
        loginLabel.setStyle("-fx-alignment: baseline-left;-fx-text-fill:#41EAD4");
        TextField nameField = new TextField();
        nameField.setPromptText("Emri");
        TextField userNameField = new TextField();
        userNameField.setPromptText("Username");
        PasswordField passwField = new PasswordField();
        passwField.setPromptText("Password");
        PasswordField passwReField = new PasswordField();
        passwReField.setPromptText("Password Retype");


        HBox btnBox = new HBox();

        TextField[] inputs = {nameField, userNameField, passwField, passwReField};

        Button submit = new Button("Rregjistrohu");
        submit.getStyleClass().add("submitBtn");
        submit.setOnMouseClicked(e -> {
            if (Main.validateFormNotEmpty(4, inputs, submit)){

            } else {
                errValLabel.setText("Elemente bosh!");
            }
        });

        userNameField.setOnKeyPressed(e -> {
            userNameField.getStyleClass().remove("fieldValError");
            errValLabel.setText("");
            submit.setDisable(false);
        });
        passwField.setOnKeyPressed(e -> {
            passwField.getStyleClass().remove("fieldValError");
            errValLabel.setText("");
            submit.setDisable(false);
        });

        loginLabel.setPrefWidth(300);
        loginLabel.setAlignment(Pos.BASELINE_LEFT);
        btnBox.getChildren().add(submit);
        btnBox.setMaxWidth(300);
        btnBox.setAlignment(Pos.BASELINE_RIGHT);

        loginGroup.getChildren().addAll(loginLabel,nameField,userNameField,passwField,passwReField,btnBox);
        loginGroup.setPadding(new Insets(20,0,0,0));
        loginGroup.setAlignment(Pos.TOP_CENTER);

        loginLayout.setCenter(loginGroup);

        Button logInBtn = new Button("Log In");
        logInBtn.getStyleClass().add("signUpBtn");
        logInBtn.setOnMouseClicked(e -> stage.setScene(signIn));

        loginLayout.setBottom(logInBtn);
        loginLayout.setAlignment(logInBtn, Pos.CENTER);
        loginLayout.setPadding(new Insets(20));

        Scene loginScene = new Scene(loginLayout, 400, 400);
        loginScene.getStylesheets().add("public/styles/game.css");
        stage.setScene(loginScene);
    }
}
