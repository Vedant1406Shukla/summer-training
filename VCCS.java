import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class VCCSUI extends Application
{
    @Override
    public void start(Stage stage)
    {
        Label status = new Label("Status : Idle");

        Button callBtn = new Button("CALL");
        Button acceptBtn = new Button("ACCEPT");
        Button endBtn = new Button("END");

        callBtn.setPrefWidth(120);
        acceptBtn.setPrefWidth(120);
        endBtn.setPrefWidth(120);

        callBtn.setOnAction(e -> {
            status.setText("Status : Calling...");
            callBtn.setStyle("-fx-background-color: orange;");
        });

        acceptBtn.setOnAction(e -> {
            status.setText("Status : Connected");
            acceptBtn.setStyle(
                "-fx-background-color: green; -fx-text-fill: white;"
            );
        });

        endBtn.setOnAction(e -> {
            status.setText("Status : Call Ended");
            endBtn.setStyle(
                "-fx-background-color: red; -fx-text-fill: white;"
            );
        });

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
            status,
            callBtn,
            acceptBtn,
            endBtn
        );

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("VCCS");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
