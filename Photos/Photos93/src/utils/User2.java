/*
 * Gino Tarraga
 * Kaival Patel
 */
package utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class User2 implements Serializable{
    //private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private Integer totalAlbums;
    //public ArrayList<String> albums = new ArrayList<String>();
    private Integer totalPhotos;
    public String date;
    private String LastModDate;
    private String oldDate;
    private Map<String, Album2> albumss;
    private Map<String, photo2> photos;
    private Map<String, Integer> photosInAlbum;
    
    
    public String getLastModDate() {
        return LastModDate;
    }

    
    public void setLastModDate(String lastModDate) {
        LastModDate = lastModDate;
    }

   
    public Integer getTotalPhotos() {
        return totalPhotos;
    }

   
    public void setTotalPhotos(Integer totalPhotos) {
        this.totalPhotos = totalPhotos;
    }
    
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    
    public void setUsername(String username) {
        this.username = username;
    }
    
   
    public void setPassword(String password) {
        this.password = password;
    }
    
   
    public void setTotalAlbums(Integer totalAlbums) {
        this.totalAlbums = totalAlbums;
    }
    
    
    public Integer getTotalAlbums() {
        return totalAlbums;

    }
  
    public User2(String username, String password){
        this.username = username;
        this.password = password;
        this.totalAlbums = 0;
        this.totalPhotos = 0;
        this.albumss = new HashMap<String, Album2>();
        this.photos = new HashMap<String, photo2>();
    }

   
    public User2(String username, String password, List<Album2> album2s){
        this.username = username;
        this.password = password;
        this.albumss = new HashMap<String, Album2>();
        this.photos = new HashMap<String, photo2>();
        this.totalAlbums = 0;
        this.totalPhotos = 0;
        Album2 toAdd;
        while(!album2s.isEmpty()) //insert the albums into the map, with their name as their key
        {
            toAdd = album2s.remove(0);
            albumss.put(toAdd.getName(), toAdd);
        }
    }

    
    public User2(String username,String password, Map<String, Album2> albums){
        this.username = username;
        this.password = password;
        this.albumss = albums;
        this.totalAlbums = 0;
        this.photos = new HashMap<String, photo2>();
    }

    
    public String getDate() {
        return date;
    }
    
    
    public void setDate(String date) {
        this.date = date;
    }
    
    
    public Map<String, Album2> getAlbumss() {
        return albumss;
    }
    
    
    public void setAlbumss(Map<String, Album2> albumss) {
        this.albumss = albumss;
    }
    
    
    public Map<String, photo2> getPhotos() {
        return photos;
    }
    
   
    public void setPhotos(Map<String, photo2> photos) {
        this.photos = photos;
    }
    
    
    public boolean addAlbum(Album2 toAdd)
    {

        if(toAdd == null) {
            return false;
        }

        if(this.albumss.containsKey(toAdd.getName())){
            return false;
        }
        else {
            changeDate();
            this.setTotalAlbums(getTotalAlbums()+1);
            this.albumss.put(toAdd.getName(), toAdd);
            return true;
        }

    }
    

    public boolean deleteAlbum(String toDeleteName)
    {
        if(toDeleteName == null || toDeleteName == "") {
            return false;
        }

        if(this.albumss.containsKey(toDeleteName)) {
            changeDate();
            this.setTotalAlbums(getTotalAlbums()-1);
            this.albumss.remove(toDeleteName);
            return true;
        }

        return false;
    }
    
   
    public boolean renameAlbum(String oldName, String newName) {
        if(oldName == null || oldName == "" || newName == null || newName == "") {
            return false;
        }

        if(this.albumss.containsKey(oldName)) {
            if(this.albumss.containsKey(newName)) {
                return false;
            }

            changeDate();
            Album2 toRename = this.albumss.get(oldName);
            this.albumss.remove(oldName);
            toRename.setName(newName);
            this.albumss.put(newName, toRename);
            return true;
        }
        return false;
    }
    
  
    public boolean addPhoto(photo2 toAdd){

        for(String photoFileName : this.photos.keySet()) {
            if(photoFileName.equals(toAdd.getPhotoName())) {
                return false;
            }
        }

        changeDate();
        this.photos.put(toAdd.getPhotoName(), toAdd);

        return true;
    }
    
    
    public void changeDate(){
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy'-'HH:mm:ss");
        Date date = new Date();
        String frmdate = formatter.format(date);
        this.setLastModDate(frmdate);
    }
}
