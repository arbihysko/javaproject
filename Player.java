import com.sun.glass.ui.EventLoop;
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

import java.sql.*;

public class Player {
    private boolean userSigned;
    private int id;
    private String name;
    private String userName;
    private int highScore;

    public Player() {
        this(-1, "", "", -1);
        this.userSigned = false;
    }

    public Player(int id, String name, String userName, int highScore) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.highScore = highScore;
        this.userSigned = true;
    }

    public boolean getUserSigned() {
        return userSigned;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHighScore() {
        return highScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //metoda choose -> krijon pamjen grafike per te zgjedhur lojtaret
    public static Scene choose(Stage stage, Scene menuScene) {
        Scene choosePlayersScene;

        BorderPane choosePlayersLayout = new BorderPane();

        //setting top section layout -> HBox
        VBox top = new VBox();

        HBox btnBox = new HBox();

        //goBack button
        Button goBack = new Button("←");
        goBack.getStyleClass().add("goBackBtn");
        btnBox.getChildren().add(goBack);

        //topLabel
        HBox labelBox = new HBox();
        Label hintLabel = new Label("Zgjidhni numrin e lojtareve!");
        labelBox.getChildren().add(hintLabel);
        labelBox.setAlignment(Pos.CENTER);

        top.getChildren().addAll(btnBox,labelBox);

        if (menuScene!=null) {
            goBack.setOnMouseClicked(e -> stage.setScene(menuScene));
        }
        else {
            goBack.setVisible(false);
        }

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

        //konfigurojme butonat ne VBox
        gameMode.getChildren().addAll(solo, twoPlayer, threePlayer, fourPlayer);
        gameMode.setAlignment(Pos.CENTER);

        //konfigurojme center content ne Layout
        choosePlayersLayout.setCenter(gameMode);
        choosePlayersLayout.setStyle("-fx-background-image: url('public/images/throwingDices.jpg')");

        //setting the scene
        choosePlayersScene = new Scene(choosePlayersLayout, 500, 400);
        choosePlayersScene.getStylesheets().add("public/styles/game.css");

        //kthejme klasen
        return choosePlayersScene;
    }

    //metoda setNames do ktheje nje Scene -> VIEW PER TE VENDOSUR EMRAT
    public static Scene setNames(Stage stage, Scene oldScene, int playersNum) {
        Scene setNamesScene;

        //zgjedhim layout -> BorderPane
        BorderPane setPlayerNames = new BorderPane();

        //setting top section layout -> HBox
        VBox top = new VBox();

        HBox btnBox = new HBox();

        //goBack button
        Button goBack = new Button("←");
        goBack.getStyleClass().add("goBackBtn");
        goBack.setOnMouseClicked(e -> stage.setScene(oldScene));
        btnBox.getChildren().add(goBack);

        //topLabel
        HBox labelBox = new HBox();
        Label hintLabel = new Label("Vendosni emrat e lojtareve!");
        labelBox.getChildren().add(hintLabel);
        labelBox.setAlignment(Pos.CENTER);

        top.getChildren().addAll(btnBox,labelBox);

        //konfigurojme top Section
        setPlayerNames.setTop(top);

        //layout per center section -> VBox
        VBox names = new VBox(10);

        //Label per validation errors
        Label valErrorLabel = new Label();
        valErrorLabel.getStyleClass().add("valErrorLabel");
        names.getChildren().add(valErrorLabel);

        //TextFields
        TextField player1 = new TextField();
        player1.setPromptText("Lojtari i pare");
        //nese kemi lojtar te loguar -> vendos emrin e tij si tekst
        if (User.pl.getUserSigned())
            player1.setText(User.pl.getName());

        TextField player2 = new TextField();
        player2.setPromptText("Lojtari i dyte");

        TextField player3 = new TextField();
        player3.setPromptText("Lojtari i trete");

        TextField player4 = new TextField();
        player4.setPromptText("Lojtari i katert");

        //vendosim player1 ne layout pasi do e kemi ne cdo rast
        names.getChildren().add(player1);

        if (playersNum > 1)//kur zgjedhim me shume se 1 player
            names.getChildren().add(player2);
        if (playersNum > 2)//kur zgjedhim me shume se 2 player
            names.getChildren().add(player3);
        if (playersNum == 4)//kur zgjedhim 4 player
            names.getChildren().add(player4);

        names.setAlignment(Pos.TOP_CENTER);

        //Butoni per te krijuar the actual GAME
        //E vendosim ne nje Hbox per te realizuar right alignment
        HBox lowerBtn = new HBox();
        Button createGame = new Button("→");
        createGame.setOnMouseClicked(e -> {
            Game newGame = null;

            //mbledhim tekstet ne te gjitha text fields
            String[] playerNames = {player1.getText(), player2.getText(), player3.getText(), player4.getText()};

            TextField[] playersInput = {player1, player2, player3, player4};
            if (Main.validateFormNotEmpty(playersNum, playersInput, createGame)) {
                createGame.setDisable(true);
                valErrorLabel.setText("Emri eshte bosh!");
            } else {
                newGame = new Game(playersNum, playerNames, stage);
            }
        });

        //setOnAction e TextField
        player1.setOnKeyPressed(e -> {
            player1.getStyleClass().remove("fieldValError");
            valErrorLabel.setText("");
            createGame.setDisable(false);
        });
        player2.setOnKeyPressed(e -> {
            player2.getStyleClass().remove("fieldValError");
            valErrorLabel.setText("");
            createGame.setDisable(false);
        });
        player3.setOnKeyPressed(e -> {
            player3.getStyleClass().remove("fieldValError");
            valErrorLabel.setText("");
            createGame.setDisable(false);
        });
        player4.setOnKeyPressed(e -> {
            player4.getStyleClass().remove("fieldValError");
            valErrorLabel.setText("");
            createGame.setDisable(false);
        });

        createGame.getStyleClass().add("goToGameBtn");
        lowerBtn.getChildren().add(createGame);
        //RIGHT ALIGNMENT dhe kufizojme width qe te dale ne nje vije me Text fields
        lowerBtn.setAlignment(Pos.BASELINE_RIGHT);
        lowerBtn.setMaxWidth(300.0);

        //vendosim bottom section ne Layout
        names.getChildren().add(lowerBtn);

        //Layout Settings
        names.setPadding(new Insets(50, 0, 0, 0));
        setPlayerNames.setCenter(names);
        setPlayerNames.setStyle("-fx-background-image: url('public/images/throwingDices.jpg')");

        //Scene Settings
        setNamesScene = new Scene(setPlayerNames, 500, 400);
        setNamesScene.getStylesheets().add("public/styles/game.css");
        return setNamesScene;
    }


    public static ResultSet dbAction(String query, int action) {
        String url = "jdbc:mysql://localhost:3306/game";
        String user = "root", pass = "thisgonnabesomepass";
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int res;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
            statement = connect.createStatement();
            if (action==0)
                resultSet = statement.executeQuery(query);
            else if (action==1)
                 res = statement.executeUpdate(query);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static Player signIn(String userName, String password) {
        String query = "SELECT id, name, username, highscore FROM user WHERE username='" + userName + "' AND password='"+password+"'";
        int id;
        String name;
        String username;
        int highscore;
        ResultSet result = dbAction(query, 0);

        try {
            if (result.next()) {
                id = result.getInt("id");
                name = result.getString("name");
                username = result.getString("username");
                highscore = result.getInt("highscore");
                return new Player(id, name, username, highscore);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void signUp(String name, String userName, String password){
        String query = "INSERT INTO user (name, username, password, highscore) VALUES ('"+name+"','"+userName+"','"+password+"',0)";
        ResultSet res = dbAction(query, 1);
    }
}