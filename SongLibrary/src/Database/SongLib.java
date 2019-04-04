/*
 * Gino Tarraga
 * Kaival Patel
 */
package Database;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import songLibGUI.Controller;


public class SongLib extends Application {

	@Override
	public void start(Stage primaryStage)
			throws Exception {
		

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songLibGUI/SongLibGUI.fxml"));
		TitledPane root = (TitledPane) loader.load();
		Controller con = loader.getController();
		con.start(primaryStage);
		Scene scene = new Scene(root, 600, 430);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		
		launch(args);
	}
}