package testing.server;

import java.io.*;
import java.net.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LocalHostChatClientGUI extends Application{
	
	static String nextMessage;
	static Text message = new Text("No message yet");
	static String ip = "localhost";
	static Socket sock;

	public static void main(String[] args) throws IOException {
		sock = new Socket(ip, LocalHostChatServer.port);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		final int WINDOW_HEIGHT = 400;
    	final int WINDOW_WIDTH = 400;
    	
    	Text debugMessage = new Text();
    	debugMessage.setText("CONNECTED");
    	debugMessage.setX(50);
		debugMessage.setY(50);
    	
    	message.setX(150);
		message.setY(100);
    	Group messages = new Group();
    	messages.getChildren().addAll(message, debugMessage);  
    	
    	Scene scene = new Scene(messages, WINDOW_WIDTH, WINDOW_HEIGHT);
    	
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
                    getNewMessage();
                    Platform.runLater(updater);
                    if (message.getText().equals("exit")) {
                    	break;
                    }
                }
                try
				{
					sock.close();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                System.exit(0);
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
						
			InputStream in = sock.getInputStream();
			DataInputStream dIn = new DataInputStream(in);
	        nextMessage = dIn.readUTF();
	        System.out.println(nextMessage);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
    }
	
	private static void updateMessage() {
		message.setText(nextMessage);
	}
}
