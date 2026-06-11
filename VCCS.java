import javafx.application.Application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.File;

public class VCCSUI extends Application
{
    private AudioClip bell;

    private Timeline timerTimeline;

    private int seconds = 0;

    private Label timerLabel;

    private void startTimer()
    {
        seconds = 0;

        timerTimeline = new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                e ->
                {
                    seconds++;

                    int mins = seconds / 60;
                    int secs = seconds % 60;

                    timerLabel.setText(
                        String.format(
                            "%02d:%02d",
                            mins,
                            secs
                        )
                    );
                }
            )
        );

        timerTimeline.setCycleCount(
            Timeline.INDEFINITE
        );

        timerTimeline.play();
    }

    private void stopTimer()
    {
        if(timerTimeline != null)
        {
            timerTimeline.stop();
        }

        timerLabel.setText("00:00");
    }

    @Override
    public void start(Stage stage)
    {
        ImageView logoView = null;

        try
        {
            Image logo =
                new Image(
                    new File("icon.jpg")
                    .toURI()
                    .toString()
                );

            logoView =
                new ImageView(logo);

            logoView.setFitWidth(120);
            logoView.setFitHeight(120);
            logoView.setPreserveRatio(true);
        }
        catch(Exception ex)
        {
            System.out.println(
                "icon.jpg not found"
            );
        }

        Label title =
            new Label(
                "VCCS Calling Application"
            );

        title.setStyle(
            "-fx-font-size: 22px;" +
            "-fx-font-weight: bold;"
        );

        Label status =
            new Label(
                "Status : Idle"
            );

        status.setStyle(
            "-fx-font-size: 16px;"
        );

        timerLabel =
            new Label("00:00");

        timerLabel.setStyle(
            "-fx-font-size: 30px;" +
            "-fx-font-weight: bold;"
        );

        Button callBtn =
            new Button("CALL");

        Button acceptBtn =
            new Button("ACCEPT");

        Button endBtn =
            new Button("END");

        callBtn.setPrefSize(140,50);
        acceptBtn.setPrefSize(140,50);
        endBtn.setPrefSize(140,50);

        try
        {
            bell =
                new AudioClip(
                    new File("bell.wav")
                    .toURI()
                    .toString()
                );

            bell.setCycleCount(
                AudioClip.INDEFINITE
            );
        }
        catch(Exception ex)
        {
            System.out.println(
                "bell.wav not found"
            );
        }

        callBtn.setOnAction(e ->
        {
            status.setText(
                "Status : Calling..."
            );

            callBtn.setStyle(
                "-fx-background-color: orange;"
            );

            if(bell != null)
            {
                bell.play();
            }
        });

        acceptBtn.setOnAction(e ->
        {
            status.setText(
                "Status : Connected"
            );

            acceptBtn.setStyle(
                "-fx-background-color: green;" +
                "-fx-text-fill: white;"
            );

            if(bell != null)
            {
                bell.stop();
            }

            startTimer();
        });

        endBtn.setOnAction(e ->
        {
            status.setText(
                "Status : Call Ended"
            );

            endBtn.setStyle(
                "-fx-background-color: red;" +
                "-fx-text-fill: white;"
            );

            if(bell != null)
            {
                bell.stop();
            }

            stopTimer();
        });

        VBox root =
            new VBox(15);

        root.setAlignment(
            Pos.CENTER
        );

        if(logoView != null)
        {
            root.getChildren().add(
                logoView
            );
        }

        root.getChildren().addAll(
            title,
            status,
            timerLabel,
            callBtn,
            acceptBtn,
            endBtn
        );

        Scene scene =
            new Scene(
                root,
                500,
                600
            );

        stage.setTitle("VCCS");

        stage.setScene(scene);

        stage.setOnCloseRequest(e ->
        {
            System.exit(0);
        });

        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
