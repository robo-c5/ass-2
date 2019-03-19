package testing.server;
	
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//not code I have written - found on 
//https://riptutorial.com/javafx/example/7291/updating-the-ui-using-platform-runlater
//as a learning tool to try to understand how to update a javafx gui
//Please remember to delete this later - especially before submit on friday!!!
public class ConsoleToGUI extends Application {

    private String message;
    private final Text text = new Text("Not yet set");
    Scanner in;

    private void updateMessage() {
    	text.setText(message);
    }

    @Override
    public void start(Stage primaryStage) {
    	in = new Scanner(System.in);
        StackPane root = new StackPane();
        text.setX(50);
        text.setY(50);
        text.setFont(Font.font("null", FontWeight.BOLD, 36));
        
        root.getChildren().add(text);

        Scene scene = new Scene(root, 200, 200);

        // longrunning operation runs on different thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        updateMessage();
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                    System.out.println("Enter a message");
                    message = in.nextLine();

                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }

        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}