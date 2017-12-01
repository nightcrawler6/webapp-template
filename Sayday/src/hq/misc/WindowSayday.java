package hq.misc;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WindowSayday extends Application {

    @Override
    public void start(Stage primaryStage) {
        String initialURL = "http://10.0.0.13:8000/home" ;
        BorderPane root = new BorderPane();
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();

        engine.load(initialURL);

        root.setCenter(webView);

        // Get full width and height of page when it's loaded:
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
           if (newState == Worker.State.SUCCEEDED) {
               int width = (Integer) engine.executeScript("document.body.scrollWidth");
               int height = (Integer) engine.executeScript("document.body.scrollHeight");
           }
        }); 
        Scene scene = new Scene(root, 600, 400);

        engine.load(initialURL);

        primaryStage.setScene(scene);
        primaryStage.setTitle("SOExpress - Your Answers, Any Time!");
        primaryStage.show();

		final KeyCombination keyComb1 = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
			@Override
			public void handle(Event event) {
				if (keyComb1.match((KeyEvent) event)) {
					System.out.println("Ctrl+R pressed");
					primaryStage.setIconified(!primaryStage.isIconified());
				}
			}
		});
    }

    public static void main(String[] args) {
        launch(args);
    }
}