package application.bankapp;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Do not start app with this, start App.java instead
public class FXApp extends Application {
	private static Stage currentStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		currentStage = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
			// Move window on drag & drop
			root.setOnMousePressed(pressEvent -> {
				root.setOnMouseDragged(dragEvent -> {
					primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
				});
			});

			// Set window style & title
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Bank Management");

			// Create & show the scene
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeScene(String fxml) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(fxml));
		// Move window on drag & drop
		root.setOnMousePressed(pressEvent -> {
			root.setOnMouseDragged(dragEvent -> {
				currentStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
				currentStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
			});
		});
		currentStage.getScene().setRoot(root);
	}

	public static void resizeWindow(int width, int height) {
		currentStage.setWidth(width);
		currentStage.setHeight(height);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
