import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;


public class AlertBox {
    public static void display(String title, String msg){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);

        Label msgLabel = new Label(msg);
        Button closeButton = new Button("Close");
        closeButton.setOnMouseClicked(e -> stage.close());

        VBox boxLayout = new VBox(40);
        boxLayout.setAlignment(Pos.CENTER);
        boxLayout.getChildren().addAll(msgLabel, closeButton);

        Scene boxScene = new Scene(boxLayout);
        stage.setScene(boxScene);

        stage.showAndWait();
    }
}
