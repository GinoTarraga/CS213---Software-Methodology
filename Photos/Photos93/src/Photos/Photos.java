/*
 * Gino Tarraga
 * Kaival Patel
 */
package Photos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Photos extends Application{

  
    public static void main(String[] args) {
        launch(args);
    }

    
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/login_view.fxml"));
        primaryStage.setTitle("Photo Album Login");
        primaryStage.setScene(new Scene(root, 350, 455));
        primaryStage.show();
    }
}
