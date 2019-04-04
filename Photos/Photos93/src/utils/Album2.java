/*
 * Gino Tarraga
 * Kaival Patel
 */
package utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Album2 implements Serializable {
    private String name;
    private int numPhotos;
    private String lastModDate;
    public String firstDate = null;
    public String lastDate = null;
    public List<photo2> photo2List;
    public List<String> dates = new ArrayList<String>();
    public int count = 0;

    
    public String getLastModDate() {
        return lastModDate;
    }

    
    public void setLastModDate(String lastModDate) {
        this.lastModDate = lastModDate;
    }


    
    public Album2(){}

    
    public Album2(String name){
        this.name = name;
        this.numPhotos = 0;
    }

    
    public Album2(String name, List<photo2> photos){
        this.name = name;
        if(photos == null){
            photos = new ArrayList<photo2>();
        }
        this.photo2List = photos;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public int getNumPhotos() {
        return numPhotos;
    }

    
    public void setNumPhotos(int numPhotos) {
        this.numPhotos = numPhotos;
    }

    
    public int getCount() {
        return count;
    }

   
    public void setCount(int count) {
        this.count = count;
    }

    
    public List<photo2> getPhoto2List() {

        return photo2List;
    }

   
    public void setPhoto2List(List<photo2> photo2List) {
        this.photo2List = photo2List;
    }

    public photo2 getPhoto(String photoName){
        for(photo2 photo2 : photo2List){
            if(photo2.getPhotoName().equalsIgnoreCase(photoName))
                return photo2;
        }
        return null;
    }

   
    public boolean deletePhoto(photo2 toDelete) {

        if(toDelete == null) {
            return false;
        }

        for(photo2 p : this.photo2List) {


            if(p == toDelete) {
                List<photo2> tempPhoto = new ArrayList<photo2>();


                for(photo2 photo2 : this.photo2List){
                    if(photo2.getPhotoName().equals(toDelete.getPhotoName()))
                        continue;
                    tempPhoto.add(photo2);
                }

                changeDate();
                this.numPhotos = getNumPhotos() -1;
                this.photo2List = tempPhoto;

                return true;
            }
        }
        return false;

    }
    
    
    public String getLastDate() {
        return lastDate;
    }
    
   
    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

   
    public boolean addPhoto(photo2 toAdd) {

        if(toAdd == null) {
            return false;
        }

        for(photo2 p : this.photo2List) {
            if (p.getPhotoName().equals(toAdd.getPhotoName())) {
                return false;
            }
        }
        if(firstDate == null){
            this.firstDate = changeDate("");
            this.lastDate = changeDate("");
        }else{
            this.lastDate = changeDate("");
        }

        changeDate();
        this.numPhotos = getNumPhotos()+1;
        this.photo2List.add(toAdd);

        return true;
    }

    
    public boolean equals(Object o) {
        boolean retVal = false;

        if(o == null || !(o instanceof Album2)) {
            return false;
        }

        Album2 toCompare = (Album2)o;
        if(name.equals(toCompare))
            return true;


        return false;
    }

   
    public void changeDate(){
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy'-'HH:mm:ss");
        Date date = new Date();
        String frmdate = formatter.format(date);
        this.setLastModDate(frmdate);
    }

    
    public String changeDate(String date1){
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy'-'HH:mm:ss");
        Date date = new Date();
        String frmdate = formatter.format(date);
        return frmdate;
    }


}