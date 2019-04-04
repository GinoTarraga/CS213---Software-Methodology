/*
 * Gino Tarraga
 * Kaival Patel
 */
package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.Backend;
import utils.User2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class loginController {
    private static final long serialVersionUID = 1L;
    public String UNAME;
    public Backend be = new Backend();
    private User2 currentUser;

    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Button cancel;
    @FXML
    private Button helpButton;

    private Map<String,User2> users;

    int counter = 0;


   
    @FXML private void loginButtonAction(ActionEvent event) throws IOException{
        UNAME = usernameText.getText();

        Parent parent = null;
        if(UNAME.equals("admin") && passwordText.getText().equals("admin")){
            ((Node) (event.getSource())).getScene().getWindow().hide();
            parent = FXMLLoader.load(getClass().getResource("../views/admin_view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Username: "+UNAME);
            stage.show();

        }else if(usernameText.getText().equals("") || passwordText.getText().equals("")){
            errorLabel.setText("Enter username and password");
        }else{
            if(!(loginCheck(usernameText.getText(),passwordText.getText()))){
                errorLabel.setText("username or password is incorrect");
                refresh();
            }
            else{
                ((Node) (event.getSource())).getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/user_view.fxml"));
                userController userController = new userController(UNAME);
                loader.setController(userController);
                parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setScene(scene);
                stage.setTitle("Username: "+UNAME);
                stage.show();

            }
        }


    }

    
    @FXML private void setCancel(ActionEvent event) throws Exception{
        usernameText.setText("");
        passwordText.setText("");
    }


   
    @FXML private void setHelpButtonAction(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Help!");
        alert.setHeaderText("For Admin: Userid = admin, Pass = admin");
        alert.setContentText("For other user, first create account then login with your new user id and password");
        alert.showAndWait();
    }




   
    public boolean loginCheck(String id, String pass){
        List<String> userIds = be.listUsers();
        if(userIds != null && userIds.contains(id))
        {
            currentUser = be.readUser(id);
            if(currentUser.getPassword().equals(pass))
                return true;
        }
        return false;
    }

   
    public void refresh(){
        usernameText.clear();
        passwordText.clear();
    }

    
    public void writeToFile(String id, String filename){
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename);
            fw.write(id);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

  }


}
