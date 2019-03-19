package server;

import java.io.*;
import java.net.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LocalHostChatClientGUI extends Application{
	
	static Text message = new Text();
	static String ip = "localhost";
	static Socket sock;
	static String debug;

	public static void main(String[] args) throws IOException {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		final int WINDOW_HEIGHT = 400;
    	final int WINDOW_WIDTH = 400;
    	
    	Text debugMessage = new Text();
    	debugMessage.setText(debug);
    	debugMessage.setX(50);
		debugMessage.setY(50);
    	
    	message.setX(100);
		message.setY(200);
    	Group messages = new Group();
    	messages.getChildren().addAll(message, debugMessage);  
    	
    	Scene scene = new Scene(messages, WINDOW_WIDTH, WINDOW_HEIGHT);
    	
    	Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        getNewMessage();
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }

                    Platform.runLater(updater);
                    if (message.getText().equals("exit")) {
                    	break;
                    }
                }
            }

        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();
        
        primaryStage.setTitle("Testing client-server communication displayed on a gui");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	private static void getNewMessage() {
		try {
			sock = new Socket(ip, LocalHostChatServer.port);
			
			InputStream in = sock.getInputStream();
			DataInputStream dIn = new DataInputStream(in);
	        message.setText(dIn.readUTF());
	        System.out.println(message.getText());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				sock.close();
			} catch (IOException ioe) {
			}			
		}
    }
}
