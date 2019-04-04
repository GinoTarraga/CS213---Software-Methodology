/*
 * Gino Tarraga
 * Kaival Patel
 */
package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static utils.Backend.writeBackend;

public class photoController{
    public static int a=0;
    String ANAME = null; //current album name
    String UNAME = null; //current user name
    @FXML
    Button backButton,searchButton,slideshowButton,moveButton,newPhotoButton,editCaptionButton,deletePhotoButton, logoutButton, addTagButton, removeTagButton;
    @FXML Label rangeOfDatesLabel, albumNameLabel,totalphotosLabel,dateLabel,datesLabel,captionLabel;
    @FXML ScrollPane scrollPaneArea;
    @FXML TilePane tilePaneArea;
    @FXML ImageView displayImageView;
    @FXML Button backDisplayButton;
    @FXML Label displayCaptionLabel;
    @FXML Label displayTagLabel;
    @FXML TableView<Photo> photoTableView;
    @FXML TableColumn<Photo, photo2> photoCol;
    @FXML TableColumn<Photo,String> tagCol;

    protected ObservableList<Photo> data = FXCollections.observableArrayList(
        new Photo("123",null,null)
    );

    public Controller controller = new Controller();
    public Backend be = new Backend();
    public User2 currentuser= null;
    List<String> photolist = new ArrayList<String>();
    List<String> photoFile = new ArrayList<>();
    int total = 0;
    ImageView imagev = null;
    String currentPhotoSelected = null;
    File file = null;

    public photoController(){}
    public photoController(String UNAME, String ANAME){
        this.UNAME = UNAME;
        this.ANAME = ANAME;
    }


   
    public void initialize(){
        currentPhotoSelected = null;
        imagev = null;
        User2 user = null;
            currentuser = be.readUser(UNAME);
            controller.currentUser = currentuser;
            albumNameLabel.setText("Album: " + ANAME);

        Map<String, Album2> albumHash = currentuser.getAlbumss();
        Album2 targetAlbum = albumHash.get(ANAME);
        List<photo2> targetPhotos = targetAlbum.getPhoto2List();


        File folder = new File("data/photos/");
        File[] listOfFiles = folder.listFiles();
        for(final File file: listOfFiles){
            for(photo2 p : targetPhotos){
                if(file.getName().equals(p.getPhotoName()) && !photolist.contains(p.getPhotoName())){
                    photolist.add(p.getPhotoName());
                    ImageView imageView;
                    imageView = createImageView(file);

                    Label label = new Label(p.getCaption(),imageView);
                    label.setContentDisplay(ContentDisplay.TOP);

                    tilePaneArea.getChildren().addAll(imageView);
                    //tilePaneArea.getChildren().addAll(label);
                    total++;
                }
            }
        }
        captionLabel.setText("");
        displayTagLabel.setText("");
        scrollPaneArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    @FXML private void setBackButton(ActionEvent event){
        currentPhotoSelected = null;
        imagev = null;
        captionLabel.setText("");
        displayTagLabel.setText("");
        Stage stage;
        Parent parent;
        stage = (Stage) deletePhotoButton.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/user_view.fxml"));
            userController userController = new userController(UNAME);
            loader.setController(userController);
            parent = loader.load();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    @FXML private void setSearchButton(ActionEvent event){
        currentPhotoSelected = null;
        imagev = null;
        captionLabel.setText("");
        displayTagLabel.setText("");

    }

   
    @FXML private void setSlideshowButtonButton(ActionEvent event){
        currentPhotoSelected = null;
        imagev = null;
        captionLabel.setText("");
        displayTagLabel.setText("");

        User2 user = be.readUser(UNAME);
        Map<String, Album2> userAlbums = user.getAlbumss();
        Album2 selectedAlbum = userAlbums.get(ANAME);
        List<photo2> selectedPhotos = selectedAlbum.getPhoto2List();

        if(selectedPhotos.size() <= 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Slide show");
            alert.setHeaderText("There are no photos in the album");
            alert.showAndWait();
            return;
        }

        Parent parent;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/slideshow_view.fxml"));
        slidshowController slidshowController = new slidshowController(UNAME,ANAME,0);
        loader.setController(slidshowController);
        try {
            parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Slide Show");
            ((Node) (event.getSource())).getScene().getWindow().hide();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    
    @FXML private void setMoveButton(ActionEvent event)throws IOException{
        if(currentPhotoSelected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Move photo");
            alert.setHeaderText("Select a photo before moving it");
            alert.showAndWait();
            return;
        }

        Map<String, Album2> albumHash = currentuser.getAlbumss();
        List<String> choices = new ArrayList<String>();

        for(String name : albumHash.keySet()){
            if(name.equals(ANAME))
                continue;
            choices.add(name);
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("",choices);
        dialog.setTitle("Move Image");
        dialog.setHeaderText("Choose an album you want to move the photo to");
        dialog.setContentText("Choose Album");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            if(!result.get().equals("")){
                if(!controller.movePhoto(currentPhotoSelected,ANAME,result.get())){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Move photo Error");
                    alert.setHeaderText("Error occured");
                    alert.showAndWait();
                }else{
                    writeBackend(be);
                    tilePaneArea.getChildren().remove(imagev);
                    total--;
                }
            }
        }


        if(result.isPresent())
        currentPhotoSelected = null;
        imagev = null;
        captionLabel.setText("");
        displayTagLabel.setText("");


    }

  
    @FXML private void setNewPhotoButton(ActionEvent event)throws IOException{
        a++;
        currentPhotoSelected = null;
        imagev = null;
        captionLabel.setText("");
        displayTagLabel.setText("");
        Stage currentStage = (Stage) newPhotoButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif","*.jpeg"));
        File f = new File("photos/");
        fileChooser.setInitialDirectory(f);
        File selectedFile = fileChooser.showOpenDialog(currentStage);
        if(selectedFile != null){

            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Add Caption");
            dialog.setHeaderText("Add Caption");
            dialog.setContentText("Caption: ");
            Optional<String> caption = dialog.showAndWait();


            int result = -1111;
            if (caption.isPresent()){
                result = controller.addPhoto(a+selectedFile.getName(),caption.get(),ANAME);
            }
            else result = controller.addPhoto(a+selectedFile.getName(),a+selectedFile.getName(),ANAME);

            if(result == 0 || result == 1){
                adminController adminController = new adminController();
                adminController.alert("Adding error","Photo already exists","choose a different photo");
            }
            else {
                writeBackend(be);
            }


            Path movefrom = FileSystems.getDefault().getPath(selectedFile.getPath());
            Path target = FileSystems.getDefault().getPath("data/photos/"+a+selectedFile.getName());
            Path targetDir = FileSystems.getDefault().getPath("data/photos");
            try{
                Files.copy(movefrom,target,REPLACE_EXISTING);
            }catch (IOException e){}
        }
        initialize();


    }


    @FXML private void setDeletePhotoButton(ActionEvent event)throws IOException{
        if(currentPhotoSelected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete");
            alert.setHeaderText("Select a photo before deleting");
            alert.showAndWait();
            return;
        }


        if(controller.removePhoto(currentPhotoSelected,ANAME)){
            photolist.remove(currentPhotoSelected);
            writeBackend(be);
        }

        tilePaneArea.getChildren().remove(imagev);
        total--;
        currentPhotoSelected = null;
        imagev = null;
        captionLabel.setText("");
        displayTagLabel.setText("");
        //initialize();
    }

   
    @FXML private void setLogoutButton(ActionEvent event){
        userController userController = new userController();
        userController.setLogoutButtonAction(event);

    }

    
    private ImageView createImageView(final File Imagefile){
        ImageView imageView = null;
        try{
            final Image image = new Image(new FileInputStream(Imagefile),150,0,true,true);
            photoFile.add(Imagefile.getPath());
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setPickOnBounds(true);

            final ImageView finalImageView = imageView;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getButton().equals(MouseButton.PRIMARY)) {
                        if (event.getClickCount() == 2) {
                            try {
                                Map<String, Album2> albumHash = currentuser.getAlbumss();
                                Album2 targetAlbum = albumHash.get(ANAME);
                                List<photo2> targetPhotos = targetAlbum.getPhoto2List();
                                String caption = "",tag = "",date = "";
                                Calendar calendar = null;
                                for(photo2 p : targetPhotos){
                                    if(Imagefile.getName().equals(p.getPhotoName())){
                                        caption = p.getCaption();
                                        calendar = p.getDateAndTime();
                                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                                        date = format1.format(calendar.getTime());
                                        List<Tag> s = p.getTags();
                                        for(int i = 0; i < s.size(); i++){
                                            if(i == 0) {
                                                tag += s.get(i).getTagType();
                                            }else{
                                                tag += ", " + s.get(i).getTagType();
                                            }
                                        }
                                    }
                                }


                                Parent parent;
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/display_view.fxml"));
                                displayController displayController = new displayController(Imagefile,caption,tag,date);
                                loader.setController(displayController);
                                parent = loader.load();
                                Scene scene = new Scene(parent);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else if(event.getClickCount() ==1){

                            String name = Imagefile.getName();
                            captionLabel.setText(name);
                            currentPhotoSelected = name;
                            imagev = finalImageView;
                            setLabel(name,captionLabel,displayTagLabel);

                        }
                    }

                }
            });
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return imageView;
    }
    
   
    private void setLabel(String name, Label label1, Label label2){
        Map<String, Album2> albumHash = currentuser.getAlbumss();
        Album2 targetAlbum = albumHash.get(ANAME);
        List<photo2> targetPhotos = targetAlbum.getPhoto2List();
        for(photo2 p : targetPhotos){
            if(name.equals(p.getPhotoName())){
                label1.setText(p.getCaption());
                List<Tag> s = p.getTags();
                String ss = "";
                for(int i = 0; i < s.size(); i++){
                    if(i == 0) {
                        ss += s.get(i).getTagValue();
                    }else{
                        ss += ", " + s.get(i).getTagValue();
                    }
                }
                label2.setText(ss);
            }
        }
    }

  
    @FXML private void setAddTagButton(ActionEvent event) throws IOException{
        if(currentPhotoSelected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Add Tag");
            alert.setHeaderText("Select a photo before Adding tag");
            alert.showAndWait();
            return;
        }else{
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Add Tag");
            dialog.setHeaderText("Enter only one tag at a time:\n (for location and people name, just enter the location or the person's name.)");
            dialog.setContentText("Tag: ");
            Optional<String> tag = dialog.showAndWait();

            if(tag.isPresent()){
                List<String> photos = controller.listPhotos(ANAME);


                if(!controller.addTag(currentPhotoSelected,tag.get())){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Add Tag Error");
                    alert.setHeaderText("Tag is already exists");
                    alert.showAndWait();
                }else{
                    Map<String, Album2> albumHash = currentuser.getAlbumss();
                    Album2 targetAlbum = albumHash.get(ANAME);
                    targetAlbum.changeDate();
                    writeBackend(be);
                }
            }
        }
        currentPhotoSelected = null;
        imagev = null;
        captionLabel.setText("");
        displayTagLabel.setText("");
    }
    
   
    @FXML private void setRemoveTagButton(ActionEvent event) throws IOException{
        if(currentPhotoSelected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Remove Tag");
            alert.setHeaderText("Select a photo before Removing tag");
            alert.showAndWait();
            return;
        }else{
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Remove Tag");
            dialog.setHeaderText("Enter a tag to be removed");
            dialog.setContentText("Tag: ");
            Optional<String> tag = dialog.showAndWait();

            if(tag.isPresent()){
                if(!controller.deleteTag(currentPhotoSelected,tag.get())){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Delete Tag Error");
                    alert.setHeaderText("Tag does not exists");
                    alert.showAndWait();
                }else{
                    Map<String, Album2> albumHash = currentuser.getAlbumss();
                    Album2 targetAlbum = albumHash.get(ANAME);
                    targetAlbum.changeDate();
                    writeBackend(be);
                }
            }
        }
        currentPhotoSelected = null;
        imagev = null;
        captionLabel.setText("");
        displayTagLabel.setText("");
    }
    
  
    @FXML private void setEditCaptionButton(ActionEvent event) throws IOException{
        if(currentPhotoSelected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Re caption");
            alert.setHeaderText("Select a photo before re captioning");
            alert.showAndWait();
            return;
        }

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("re caption");
        dialog.setHeaderText("Enter New Caption");
        dialog.setContentText("New Caption: ");
        Optional<String> caption = dialog.showAndWait();

        if(caption.isPresent()){
            List<String> photos = controller.listPhotos(ANAME);

            if(!controller.recaptionPhoto(currentPhotoSelected,caption.get())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Re Caption Error");
                alert.setHeaderText("Error while Re Captioning");
                alert.showAndWait();
            }else{
                Map<String, Album2> albumHash = currentuser.getAlbumss();
                Album2 targetAlbum = albumHash.get(ANAME);
                targetAlbum.changeDate();
                writeBackend(be);
            }
        }

        currentPhotoSelected = null;
        imagev = null;
        captionLabel.setText("");
        displayTagLabel.setText("");
    }
}
