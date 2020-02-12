import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


public class User {

    static Player pl = new Player();

    static boolean hasErrors = false;

    public static void logIn(){

        Stage stage = new Stage();

        //nuk lejon interaktim me dritaret e tjera
        stage.initModality(Modality.APPLICATION_MODAL);

        BorderPane loginLayout = new BorderPane();

        VBox loginGroup = new VBox(10);
        Label errValLabel = new Label();
        //errorLabel i shtohet klasa valErrorLabel
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
            if (Main.validateFormNotEmpty(2, inputs, submit)) {
                errValLabel.setText("Username ose passwordi jane bosh!");
            } else {
                try {
                    pl = Player.signIn(userNameField.getText(), encryptPass(passwField.getText()));
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
                if (pl==null) {
                    errValLabel.setText("Usename ose password i pasakte!");
                    userNameField.getStyleClass().add("valErrorLabel");
                    passwField.getStyleClass().add("valErrorLabel");
                    submit.setDisable(true);
                } else
                    stage.close();
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

        nameField.setOnKeyPressed(e -> {
            nameField.getStyleClass().remove("fieldValError");
            errValLabel.setText("");
            submit.setDisable(false);
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
        passwReField.setOnKeyPressed(e -> {
            passwReField.getStyleClass().remove("fieldValError");
            errValLabel.setText("");
            submit.setDisable(false);
        });

        submit.setOnMouseClicked(e -> {
            if (Main.validateFormNotEmpty(4, inputs, submit)){
                errValLabel.setText("Elemente bosh!");
            } else if (!passwField.getText().equals(passwReField.getText())){
                passwReField.getStyleClass().add("fieldValError");
                errValLabel.setText("Passwordet nuk perputhen!");
                submit.setDisable(true);
                hasErrors = true;
            } else if (!validatePass(passwField.getText())){
                passwField.getStyleClass().add("fieldValError");
                errValLabel.setText("Password i dobet!");
                submit.setDisable(true);
            } else {
                try {
                    if (!validateUserName(userNameField.getText())){
                        userNameField.getStyleClass().add("fieldValError");
                        errValLabel.setText("Username eshte i zene!");
                        submit.setDisable(true);
                        hasErrors = true;
                    } else {
                        Player.signUp(nameField.getText(),userNameField.getText(),encryptPass(passwField.getText()));
                        pl = Player.signIn(userNameField.getText(), encryptPass(passwField.getText()));
                        stage.close();
                    }
                } catch (SQLException | NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
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

    public static String encryptPass(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, hash);
        StringBuilder encryptedStr = new StringBuilder(number.toString(16));
        while (encryptedStr.length() < 32)
        {
            encryptedStr.insert(0, '0');
        }
        return encryptedStr.toString();
    }

    public static boolean validatePass(String password){
        if (password.length()<6)
            return false;

        if (!password.matches(".*\\d.*"))
            return false;

        if (password.matches(password.toLowerCase()))
            return false;

        return true;
    }

    public static boolean validateUserName(String username) throws SQLException {
        String query = "SELECT * FROM user WHERE username='"+username+"'";
        ResultSet res = Player.dbAction(query,0);
        if (res.next())
            return false;

        return true;
    }
}
