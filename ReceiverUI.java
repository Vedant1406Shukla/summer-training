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

import javafx.scene.media.AudioClip;

import java.io.*;
import java.net.*;

public class ReceiverUI extends Application
{
    private Label status;
    private Label timerLabel;

    private Timeline timer;
    private int seconds = 0;

    private AudioClip bell;

    private PrintWriter out;

    private Button acceptBtn;
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
            System.out.println("bell.wav not found");
        }

        Label title =
            new Label(
                "VCCS RECEIVER"
            );

        status =
            new Label(
                "Status : Waiting..."
            );

        timerLabel =
            new Label(
                "00:00"
            );

        acceptBtn =
            new Button(
                "ACCEPT"
            );

        endBtn =
            new Button(
                "END"
            );

        acceptBtn.setPrefWidth(150);
        endBtn.setPrefWidth(150);

        acceptBtn.setOnAction(e ->
        {
            if(out != null)
            {
                out.println("200 OK");
            }

            if(bell != null)
            {
                bell.stop();
            }

            status.setText(
                "Status : Connected"
            );

            acceptBtn.setStyle(
                "-fx-background-color: green; -fx-text-fill: white;"
            );

            startTimer();
        });

        endBtn.setOnAction(e ->
        {
            if(out != null)
            {
                out.println("BYE");
            }

            if(bell != null)
            {
                bell.stop();
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
            acceptBtn,
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
            "Receiver"
        );

        stage.setOnCloseRequest(e ->
        {
            System.exit(0);
        });

        stage.show();

        new Thread(() ->
        {
            startServer();
        }).start();
    }

    private void startServer()
    {
        try
        {
            ServerSocket server =
                new ServerSocket(5000);

            Socket socket =
                server.accept();

            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(
                        socket.getInputStream()
                    )
                );

            out =
                new PrintWriter(
                    socket.getOutputStream(),
                    true
                );

            while(true)
            {
                String msg =
                    in.readLine();

                if(msg == null)
                    break;

                if(msg.equals("INVITE"))
                {
                    Platform.runLater(() ->
                    {
                        status.setText(
                            "Incoming Call..."
                        );

                        if(bell != null)
                        {
                            bell.play();
                        }
                    });
                }

                if(msg.equals("BYE"))
                {
                    Platform.runLater(() ->
                    {
                        if(bell != null)
                        {
                            bell.stop();
                        }

                        stopTimer();

                        status.setText(
                            "Call Ended"
                        );
                    });
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}