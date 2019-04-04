/*
 * Gino Tarraga
 * Kaival Patel
 */
package songLibGUI;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

	@FXML Button add;
	@FXML Button edit;
	@FXML Button delete;
	@FXML Button reset;
	@FXML Button confirmEdit;
	
	@FXML ListView<Song> listView;
	
	@FXML TextField name;
	@FXML TextField artist;
	@FXML TextField album;
	@FXML TextField year;
	
	@FXML TextField addName;
	@FXML TextField addAlbum;
	@FXML TextField addArtist;
	@FXML TextField addYear;
	
	ArrayList<Song> songList;
	
	private ObservableList<Song> obsList;
	
	public void editSong(ActionEvent editEvent){
		Button editButton = (Button) editEvent.getSource();
		if(editButton == edit) {
			name.setDisable(false);
			artist.setDisable(false);
			album.setDisable(false);
			year.setDisable(false);
		}
	}
	
	public ArrayList<Song> readFromCatalog(){
		
		ArrayList<Song> songList = new ArrayList<Song>();
		
		try{
			//Using scanner variable to iterate through the text file
			Scanner file = new Scanner(new File("src/Database/SongCatalog.txt"));
			String i;
			while(file.hasNextLine()) {
				i = file.nextLine();
				if(i == null) {
					break;
				}
				String[] songDetail = i.split(",");
				Song tune = new Song(songDetail[0], songDetail[1], songDetail[2], songDetail[3]);
				songList.add(tune);
			}
			file.close();
		}
		catch (IOException io) {
			io.printStackTrace();
		}
		return songList;
	}
	
	public void start(Stage mainStage) {
		//load up the song list from the catalog of the saved songs
		songList = readFromCatalog();
		Collections.sort(songList, new SongComparator());
		obsList = FXCollections.observableArrayList(songList);
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		
		if(0 < songList.size()) {
			addSongSpecifics();
		}
		listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> addSongSpecifics());
	}
	
	public void addSong(ActionEvent addEvent) {
		
		Button action = (Button) addEvent.getSource();
		
		String name = addName.getText();
		String artist = addArtist.getText();
		String album = addAlbum.getText();
		String year = addYear.getText();
		Song addNew;
		
		if(action == add) {
			if(addAlbum.getText().isEmpty()) {
				album = " ";
			}
			if(year.isEmpty()) {
				year = " ";
			}
			if(addName.getText().isEmpty() || addArtist.getText().isEmpty()) {
				Alert emptyEntry = new Alert(AlertType.ERROR);
				emptyEntry.setTitle("Error!");
				emptyEntry.setHeaderText("Name and Artist required:");
				emptyEntry.setContentText("Please enter the song name and artist to add the song");
				emptyEntry.showAndWait().ifPresent(ans-> {
					if(ans.equals(ButtonType.OK)) {	}});
				
			}
			else if (!(name.isEmpty()) && !(artist.isEmpty())) { //If the name and artist fields are not empty, do the following
				 addNew = new Song(name, artist, album, year);
				boolean songMatches = songExists(addNew);
				if(songMatches) {  //If the song is already added, show that alert to user
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setTitle("Error!");
					errorAlert.setHeaderText("Error adding the song");
					errorAlert.setContentText("The song already exists, please try adding a different song!");
					errorAlert.showAndWait().ifPresent(pres -> {
						if(pres == ButtonType.OK) {
							//Do nothing
						}
					});
				}
				else { //Else, if the song doesn't already exists in the list, confirm the adding of the song from the user
					Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
					confirmAlert.setTitle("Confirm");
					confirmAlert.setHeaderText("Add the song?");
					confirmAlert.setContentText("you sure, you want to add the following song?\n" + "Album: " + album + "\n"
							+ "Year: " + year + "\n");
					Optional<ButtonType> yes = confirmAlert.showAndWait();
					
					if(yes.get().equals(ButtonType.OK)) { //If user hits "OK" button, add the song to the list, sort it and add to the listView
						songList.add(addNew); //Add new song to the arrayList 
						Collections.sort(songList, new SongComparator());
						saveToCatalog(songList);
						
						//******* not to self, error exist, fix!
						obsList.add(addNew);
						try{
						obsList = FXCollections.observableArrayList(songList);
						}catch(NullPointerException e){
							
						}
						listView.setItems(obsList);
						int newIndex = 0;
						for(Song newS : songList) {
							newIndex++; //If any song in the list, after the first one, is the newly added, change nothing
							if(newS.getName().equals(name) && newS.getArtist().equals(artist)) {
								break;
							}
						}
						//**
						listView.getSelectionModel().select(newIndex - 1);
					}else {
						
					}
					}
			}
			
		}
		addName.clear();
		addArtist.clear();
		addAlbum.clear();
		addYear.clear();
	}
	
	public void reset(ActionEvent resetEvent) {
		Button cancel = (Button) resetEvent.getSource();
		if(cancel == reset) {//If user clicks reset button, clear all the info added by the user for adding the song
			addName.clear();
			addAlbum.clear();
			addArtist.clear();
			addYear.clear();
		}
	}
	
	public void saveToCatalog(ArrayList<Song> curr){
		try {
			//Creating a text file to save the songs
			PrintWriter file = new PrintWriter("src/Database/SongCatalog.txt");
			for(Song tune : curr) {
				String s = tune.getName() + "," + tune.getArtist()+ "," +tune.getAlbum() + "," +tune.getYear() + ",";
				file.println(s);
			}
			file.close();
		}
		catch (IOException io) {
			io.printStackTrace();
		}	
	}
	
	public void addSongSpecifics(){
		if(songList.size() == 0) {
			name.clear();
			artist.clear();
			album.clear();
			year.clear();
		}else {
			Song selected = listView.getSelectionModel().getSelectedItem();
			if(selected != null) {
				String stringName = selected.getName();
				String stringArtist = selected.getArtist();
				String stringAlbum;
				String stringYear;
				if(selected.getAlbum().isEmpty()) {
					stringAlbum = " ";
				}else {
					stringAlbum = selected.getAlbum();
				}
				if(selected.getYear().isEmpty()) {
					stringYear = " ";
				}else {
					stringYear = selected.getYear();
				}
				
				name.setText(stringName);
				name.setDisable(true);
				artist.setText(stringArtist);
				artist.setDisable(true);
				album.setText(stringAlbum);
				album.setDisable(true);
				year.setText(stringYear);
				year.setDisable(true);
			}
		}
	}
	
	public boolean songExists(Song addNew) {
		for(Song exist : songList) {
			if(exist.getName().equalsIgnoreCase(addNew.getName()) && 
					exist.getArtist().equalsIgnoreCase(addNew.getArtist())) {
				return true;
			}
		}
		return false;
	}
	
	//If the DELETE button is clicked do the following
	public void deleteSong(ActionEvent delEvent) {
		Button deleteButton = (Button) delEvent.getSource();
		if(deleteButton == delete) {
			Song deleteSong = listView.getSelectionModel().getSelectedItem();
			int temp = listView.getSelectionModel().getSelectedIndex();
			Alert deleteAlert = new Alert(AlertType.CONFIRMATION);
			deleteAlert.setTitle("Confirmation:");
			deleteAlert.setHeaderText("Delete song?");
			deleteAlert.setContentText("Are you sure, you want to delete the following song?\n"
					+ "Name: " + deleteSong.getName() + "\n" + "Artsit: "+ deleteSong.getArtist() + "\n"
					+ "Album: " + deleteSong.getAlbum() + "\n" + "Year: " + deleteSong.getYear() + "\n");
			Optional<ButtonType> confirm = deleteAlert.showAndWait();
				if(confirm.get() == ButtonType.OK) {
					int listSize = songList.size();
					if(listSize == 1) {
						songList.remove(temp);
						saveToCatalog(songList);
						obsList.remove(temp);
						obsList = FXCollections.observableArrayList(songList);
						listView.setItems(obsList);
						return;
					}
						songList.remove(temp);
						saveToCatalog(songList);
						obsList.remove(temp);
						obsList = FXCollections.observableArrayList(songList);
						listView.setItems(obsList);
						
						if(temp < (songList.size())) {
							listView.getSelectionModel().select(temp);
						}
						else {
							
							listView.getSelectionModel().select(temp - 1);
						}	
				}
				else {
					//do  nothing
				}
		}
		
	}
	
	public void confirmSongEdit(ActionEvent confirmEvent) {
		Button confirm = (Button) confirmEvent.getSource();
		if(confirm == confirmEdit) {
			Song SongtoEdit = listView.getSelectionModel().getSelectedItem();
			int editIndex = listView.getSelectionModel().getSelectedIndex();
			
			String nameString = name.getText();
			String artistString = artist.getText();
			String albumString;
			String yearString;
			
			if(!nameString.isEmpty() && !artistString.isEmpty()) {
				if (album.getText().isEmpty()) {
					albumString = " ";
				} else {
					albumString = album.getText();
				}
				if (year.getText().isEmpty()) {
					yearString = " ";
				} else {
					yearString = year.getText();
				}
				Song editSong = new Song(name.getText(), artist.getText(), albumString, yearString);
				boolean editMatches = songExists(editSong);
				
				if(editMatches == true) {
					Alert errorEdit = new Alert(AlertType.ERROR);
					errorEdit.setTitle("Error!");
					errorEdit.setHeaderText("Error editing the song");
					errorEdit.setContentText("The song already exists, please try a different song!");
					errorEdit.showAndWait().ifPresent(pres -> {
						if(pres == ButtonType.OK) {
							//Do nothing
						}
					});
				}
				else {
					Alert editAlert = new Alert(AlertType.CONFIRMATION);
					editAlert.setTitle("Confirmation:");
					editAlert.setHeaderText("Edit the song?");
					editAlert.setContentText("Are you sure you want to edit the following song details: \n"
							+ "Name: " + SongtoEdit.getName() + "\n" + "Artsit: "+ SongtoEdit.getArtist() + "\n"
							+ "Album: " + SongtoEdit.getAlbum() + "\n" + "Year: " + SongtoEdit.getYear() + "\n" + " with the new details: \n"
							+ "Name: " + nameString + "\n" + "Artsit: "+ artistString + "\n"
							+ "Album: " + albumString + "\n" + "Year: " + yearString + "\n");
					Optional<ButtonType> ans = editAlert.showAndWait();
					if(ans.get() == ButtonType.OK) {
						//songList.remove(toEdit);
						songList.remove(editIndex);
						songList.add(editSong);
						Collections.sort(songList, new SongComparator());
						saveToCatalog(songList);
						obsList.add(editSong);
						obsList = FXCollections.observableArrayList(songList);
						listView.setItems(obsList);
						int currIndex = 0;
						for (Song g : songList) {
							currIndex++;
							if(g.getName().equalsIgnoreCase(name.getText()) && g.getArtist().equalsIgnoreCase(artist.getText())) {
								break;
							}
						}
						listView.getSelectionModel().select(currIndex - 1);
					}else {
						//If user cancels the edit, do the following
						name.setText(SongtoEdit.getName());
						name.setDisable(true);
						artist.setText(SongtoEdit.getArtist());
						artist.setDisable(true);
						album.setText(SongtoEdit.getAlbum());
						album.setDisable(true);
						year.setText(SongtoEdit.getYear());
						year.setDisable(true);
					}
				}
			}else {
				//Alert user that the name and/or artist of the song for edit is missing
				Alert EmptyInput = new Alert(AlertType.INFORMATION);
				EmptyInput.setTitle("Error");
				EmptyInput.setHeaderText("Message");
				EmptyInput.setContentText("You must enter Song name and Artist to add a song");
				EmptyInput.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
					}
				});
			}
		}	
	}
	
	
}