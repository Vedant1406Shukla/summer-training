import javafx.application.Application;
import javafx.application.Platform;

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
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class SenderUI extends Application
{
    private Label status;
    private Label timerLabel;

    private Timeline timer;
    private int seconds = 0;

    private PrintWriter out;

    private Button callBtn;
    private Button endBtn;

    private void startTimer()
    {
        seconds = 0;

        timer = new Timeline(
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

        timer.setCycleCount(
            Timeline.INDEFINITE
        );

        timer.play();
    }

    private void stopTimer()
    {
        if(timer != null)
        {
            timer.stop();
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
            System.out.println("icon.jpg not found");
        }

        Label title =
            new Label(
                "VCCS SENDER"
            );

        status =
            new Label(
                "Status : Connecting..."
            );

        timerLabel =
            new Label(
                "00:00"
            );

        callBtn =
            new Button(
                "CALL"
            );

        endBtn =
            new Button(
                "END"
            );

        callBtn.setPrefWidth(150);
        endBtn.setPrefWidth(150);

        callBtn.setOnAction(e ->
        {
            if(out != null)
            {
                out.println("INVITE");

                status.setText(
                    "Status : Calling..."
                );

                callBtn.setStyle(
                    "-fx-background-color: orange;"
                );
            }
        });

        endBtn.setOnAction(e ->
        {
            if(out != null)
            {
                out.println("BYE");
            }

            stopTimer();

            status.setText(
                "Status : Call Ended"
            );

            endBtn.setStyle(
                "-fx-background-color: red; -fx-text-fill: white;"
            );
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
            endBtn
        );

        Scene scene =
            new Scene(
                root,
                450,
                550
            );

        stage.setScene(scene);

        stage.setTitle(
            "Sender"
        );

        stage.setOnCloseRequest(e ->
        {
            System.exit(0);
        });

        stage.show();

        new Thread(() ->
        {
            connect();
        }).start();
    }

    private void connect()
    {
        try
        {
            Socket socket =
                new Socket(
                    "127.0.0.1",
                    5000
                );

            out =
                new PrintWriter(
                    socket.getOutputStream(),
                    true
                );

            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(
                        socket.getInputStream()
                    )
                );

            Platform.runLater(() ->
            {
                status.setText(
                    "Status : Ready"
                );
            });

            while(true)
            {
                String msg =
                    in.readLine();

                if(msg == null)
                    break;

                if(msg.equals("200 OK"))
                {
                    Platform.runLater(() ->
                    {
                        status.setText(
                            "Status : Connected"
                        );

                        startTimer();
                    });
                }

                if(msg.equals("BYE"))
                {
                    Platform.runLater(() ->
                    {
                        stopTimer();

                        status.setText(
                            "Status : Call Ended"
                        );
                    });
                }
            }
        }
        catch(Exception e)
        {
            Platform.runLater(() ->
            {
                status.setText(
                    "Status : Receiver Offline"
                );
            });

            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}