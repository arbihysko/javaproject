import javafx.application.Platform;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EndGame {
    public static void multiPlayerEnd(Stage oldStage, int num, int[][] points, String[] playerNames){
        Stage stage = new Stage();
        stage.setTitle("Loja Mbaroi");

        String temp;
        int[] t;
        for (int i = 0; i < num-1; i++) {
            for (int j = i; j < num-i-1; j++) {
                if (points[j][16]<points[j+1][16]){
                    t = points[j];
                    points[j] = points[j+1];
                    points[j+1] = t;
                    temp = playerNames[j];
                    playerNames[j] = playerNames[j+1];
                    playerNames[j+1] = temp;
                }
            }
        }

        //nuk lejon interaktim me dritaret e tjera
        stage.initModality(Modality.APPLICATION_MODAL);

        BorderPane endGameLayout = new BorderPane();

        Label gameOver = new Label("Loja Mbaroi");
        gameOver.setPadding(new Insets(0,0,20,0));
        endGameLayout.setTop(gameOver);
        BorderPane.setAlignment(gameOver, Pos.CENTER);

        HBox listContainer = new HBox();

        ListView<Integer> rendi = new ListView<>();
        ListView<String> player = new ListView<>();
        ListView<Integer> pts = new ListView<>();

        for (int i = 0; i < num; i++) {
            rendi.getItems().add(i+1);
            player.getItems().add(playerNames[i]);
            pts.getItems().add(points[i][16]);
        }

        listContainer.getChildren().addAll(rendi,player,pts);
        endGameLayout.setCenter(listContainer);
        BorderPane.setAlignment(listContainer, Pos.CENTER);

        HBox buttonGroup = new HBox(20);

        Button newGame = new Button("Loje e Re");
        newGame.setOnAction(e->{
            Game.raund=0;
            Game.playerAtTurn=0;
            Game.chance=0;
            oldStage.setMaxWidth(500);
            oldStage.setMaxHeight(400);
            oldStage.setFullScreen(false);
            oldStage.setScene(Player.choose(oldStage, null));
            stage.close();
        });
        Button quit = new Button("Exit");
        quit.setOnAction(e->exit(oldStage, stage));

        buttonGroup.getChildren().addAll(newGame, quit);
        buttonGroup.setAlignment(Pos.CENTER);
        endGameLayout.setBottom(buttonGroup);
        BorderPane.setAlignment(buttonGroup, Pos.CENTER);

        endGameLayout.setPadding(new Insets(20));

        Scene endGameScene = new Scene(endGameLayout, 400,300);
        endGameScene.getStylesheets().add("public/styles/game.css");
        stage.setScene(endGameScene);
        stage.setOnCloseRequest(e->exit(oldStage, stage));

        stage.showAndWait();
    }
    public static void endGameSolo(Stage oldStage, int points){
        Stage stage = new Stage();
        stage.setTitle("Loja Mbaroi");

        //nuk lejon interaktim me dritaret e tjera
        stage.initModality(Modality.APPLICATION_MODAL);

        BorderPane endGameLayout = new BorderPane();

        Label gameOver = new Label("Loja Mbaroi");
        endGameLayout.setTop(gameOver);
        BorderPane.setAlignment(gameOver, Pos.TOP_CENTER);

        Label center = new Label();
        String q = "";
        HBox box = new HBox(16);

        HBox buttonGroup = new HBox(20);

        Button leaderBoard = new Button("LeaderBoard");
        leaderBoard.setOnAction(e-> {
            try {
                openLeaderBoard();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        Button newGame = new Button("Loje e Re");
        newGame.setOnAction(e->{
            Game.raund=0;
            Game.playerAtTurn=0;
            Game.chance=0;
            oldStage.setMaxWidth(500);
            oldStage.setMaxHeight(400);
            oldStage.setFullScreen(false);
            oldStage.setScene(Player.choose(oldStage, null));
            stage.close();
        });
        Button quit = new Button("Exit");
        quit.setOnAction(e->exit(stage, oldStage));

        if (User.pl.getUserSigned()){
            if (points>User.pl.getHighScore()) {
                center.setText("HighScore i ri: " + points);
                q = "UPDATE user SET highscore=" + points + " WHERE id=" + User.pl.getId();
                Player.dbAction(q, 1);
                User.pl.setHighScore(points);
            }
            else {
                center.setText("Ju fituat "+points+", highscore eshe "+User.pl.getHighScore());
            }
            buttonGroup.getChildren().add(leaderBoard);
        } else {
            center.setText("Ju fituat "+points+"pike!");
        }

        endGameLayout.setCenter(center);
        BorderPane.setAlignment(center, Pos.CENTER);

        buttonGroup.getChildren().addAll(newGame, quit);
        buttonGroup.setAlignment(Pos.CENTER);
        endGameLayout.setBottom(buttonGroup);
        BorderPane.setAlignment(buttonGroup, Pos.CENTER);

        endGameLayout.setPadding(new Insets(20));

        Scene endGameScene = new Scene(endGameLayout, 400,300);
        endGameScene.getStylesheets().add("public/styles/game.css");
        stage.setScene(endGameScene);

        stage.setOnCloseRequest(e->exit(oldStage, stage));

        stage.showAndWait();
    }

    public static void openLeaderBoard() throws SQLException {
        Stage stage = new Stage();
        stage.setTitle("LeaderBoard");

        //nuk lejon interaktim me dritaret e tjera
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene leaderBoard = null;
        BorderPane lb = new BorderPane();

        Label gameOver = new Label("LeaderBoard");
        gameOver.setPadding(new Insets(0,0,20,0));
        lb.setTop(gameOver);
        BorderPane.setAlignment(gameOver, Pos.CENTER);

        HBox listContainer = new HBox();

        ListView<String> username = new ListView<>();
        ListView<Integer> points = new ListView<>();

        String query = "SELECT username, highscore FROM user ORDER BY highscore DESC";
        ResultSet res = Player.dbAction(query, 0);

        while (res.next()){
            username.getItems().add(res.getString("username"));
            points.getItems().add(res.getInt("highscore"));
        }

        listContainer.getChildren().addAll(username,points);
        listContainer.setAlignment(Pos.CENTER);

        lb.setCenter(listContainer);

        lb.setPadding(new Insets(20));

        leaderBoard = new Scene(lb, 400, 600);
        leaderBoard.getStylesheets().add("public/styles/game.css");
        stage.setScene(leaderBoard);
        stage.showAndWait();
    }

    public static void exit(Stage old, Stage current){
        Platform.exit();
        old.close();
        current.close();
        System.exit(0);
    }
}
