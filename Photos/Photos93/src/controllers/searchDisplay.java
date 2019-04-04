/*
 * Gino Tarraga
 * Kaival Patel
 */
package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import utils.Album2;
import utils.Backend;
import utils.User2;
import utils.photo2;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static utils.Backend.writeBackend;



public class searchDisplay {

    @FXML Label captionLabel, tagLabel;
    @FXML
    ScrollPane scrollPaneArea;
    @FXML
    TilePane tilePaneArea;
    public Backend be = new Backend();
    public Controller controller = new Controller();
    public adminController adminController = new adminController();

    private String UNAME;
    private User2 currentuser = null;
    private List<photo2> photos = new ArrayList<photo2>();

    public searchDisplay(String UNAME, List<photo2> photos){
        this.UNAME = UNAME;
        this.photos = photos;
    }

    
    public void initialize(){
        currentuser = be.readUser(UNAME);
        controller.currentUser = currentuser;


        File folder = new File("data/photos/");
        File[] listOfFiles = folder.listFiles();

        for(final File file: listOfFiles){
            for(photo2 p : photos){
                if(file.getName().equals(p.getPhotoName())){
                    ImageView imageView;
                    imageView = createImageView(file);
                    tilePaneArea.getChildren().addAll(imageView);
                }
            }
        }
        scrollPaneArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    
    @FXML private void setBackButton(ActionEvent event){
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/user_view.fxml"));
            userController userController = new userController(UNAME);
            loader.setController(userController);
            parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            ((Node) (event.getSource())).getScene().getWindow().hide();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    @FXML private void setCreatNewAlbumButton(ActionEvent event)throws IOException{
        TextInputDialog dialog = new TextInputDialog("New Album..");
        dialog.setContentText("New Album Name: ");
        dialog.setTitle("New Album");
        dialog.setHeaderText("Creating New Album");
        Optional<String> name = dialog.showAndWait();

        if(name.isPresent()){
            if(!controller.createAlbum(name.get())){
                adminController.alert("Creating Album","Album Already Exists","Enter different album name");
            }else{
                Map<String, Album2> albumHash = currentuser.getAlbumss();
                Album2 targetAlbum = albumHash.get(name.get());
                for(photo2 p : photos){
                    targetAlbum.addPhoto(p);
                    currentuser.addPhoto(p);
                    currentuser.setTotalPhotos(currentuser.getTotalPhotos()+1);
                }
                adminController.alert("album created","new album created :)", "new album is created :)");
                writeBackend(be);
                setBackButton(event);

            }
        }
    }

    
    private ImageView createImageView(final File Imagefile){
        ImageView imageView = null;
        try{
            final Image image = new Image(new FileInputStream(Imagefile),150,0,true,true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setPickOnBounds(true);

            final ImageView finalImageView = imageView;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getButton().equals(MouseButton.PRIMARY)) {
                        if (event.getClickCount() == 2) {

                        }
                        else if(event.getClickCount() ==1){
                            String name = Imagefile.getName();


                        }
                    }

                }
            });
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return imageView;
    }



}
