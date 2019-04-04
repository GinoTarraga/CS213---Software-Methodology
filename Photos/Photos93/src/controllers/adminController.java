/*
 * Gino Tarraga
 * Kaival Patel
 */
package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.Album2;
import utils.Backend;
import utils.User;
import utils.User2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static utils.Backend.writeBackend;



public class adminController{

    Backend be = new Backend();
    @FXML TableView<User> userTableView;
    @FXML TableColumn<User, String> usernameCol;
    @FXML TextField newuserText;
    @FXML PasswordField newpassText;
    @FXML Label newlabelText;
    int usercount = 0;
    Parent parent;
    Stage stage;

    private final ObservableList<User> userData =
            FXCollections.observableArrayList();

    
    public void initialize(){
        usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        setListusersButton();
    }

    
    @FXML private void setListusersButton(){
        userTableView.setItems(userData);
        ObservableList<User> alluser = userTableView.getItems();
        userData.removeAll(alluser);
        List<String> users = be.listUsers();
        usercount = 0;
        if(users != null) {
            for (String u : users) {
                usercount++;
                User u2 = new User(u);
                userData.add(u2);
            }
        }
    }

    
    @FXML private void setAdduserButton(ActionEvent event) throws Exception{
        String newuText = newuserText.getText();
        String newuPass = newpassText.getText();
        boolean iswhitespace = newuText.matches(",");
        if((newuPass.equals("") || newuText.equals(""))){
            newlabelText.setText("Enter Username and Password");
        }else if(iswhitespace){
            alert("Invalid","username should not contain special characters","");
        }
        else if(!addUser(newuText,newuPass)){
            newlabelText.setText("username already exists");
        }
        else{
            newlabelText.setText("account created");
            User user1 = new User(newuText);
            userData.add(user1);
            usercount++;
            writeBackend(be);
        }
        newuserText.clear();
        newpassText.clear();
    }

   
    public boolean addUser(String userID, String password){

        List<Album2> albs = new ArrayList<Album2>();
        User2 user2 = new User2(userID,password);
        return be.addUser(user2);
    }

    
    @FXML private void setDeleteuserButton(ActionEvent event) throws  IOException{
        int index = userTableView.getSelectionModel().getSelectedIndex();
        if(index >=0){
            ObservableList<User> selectedUser, alluser;
            alluser = userTableView.getItems();
            selectedUser = userTableView.getSelectionModel().getSelectedItems();
            User u = userTableView.getSelectionModel().getSelectedItem();
            String name = u.getUsername();
            selectedUser.forEach(alluser::remove);
            usercount--;
            userTableView.requestFocus();
            userTableView.focusModelProperty().get().focus(new TablePosition(userTableView, 0, usernameCol));
            userTableView.getSelectionModel().select(0);
            newlabelText.setText("Account deleted");
            be.deleteUser(name);
            writeBackend(be);

        }else{
            alert("Delete error","Select user before deleting","");
        }
    }



    
    @FXML private void setLogoutButton(ActionEvent event){

        newlabelText.setText("");
        ((Node) (event.getSource())).getScene().getWindow().hide();
        openWindow("../views/login_view.fxml");
        stage.setTitle("Photo Album login");
        stage.show();
    }

    
    private void openWindow(String fxmlName){
        try {
            parent = FXMLLoader.load(getClass().getResource(fxmlName));
            stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   

    @FXML private void setCancel(){
        newuserText.setText("");
        newpassText.setText("");
    }

   
    private void deleteRec(File filename){
        if(filename.isDirectory()){
            for(File child : filename.listFiles())
                deleteRec(child);
        }
        filename.delete();
    }

   
    public void alert(String title, String header, String body){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(body);
        alert.showAndWait();
    }


}
