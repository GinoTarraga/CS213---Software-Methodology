
/*
 * Gino Tarraga
 * Kaival Patel
 */

package utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;



public class album{
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty photos;
    private final SimpleStringProperty date;
    private final SimpleStringProperty dates;
    private final SimpleStringProperty oldestDate;
    private int count = 0;


    public album(){this(null);}

    
    public String getOldestDate() {
        return oldestDate.get();
    }

    
    public SimpleStringProperty oldestDateProperty() {
        return oldestDate;
    }
    
    
    public void setOldestDate(String oldestDate) {
        if(count == 0) {
            this.oldestDate.set(oldestDate);
            count = 1;
        }
    }

    
    public album(String name){
        this.name = new SimpleStringProperty(name);
        this.photos = new SimpleIntegerProperty(0);
        this.date = new SimpleStringProperty("never");
        this.dates = new SimpleStringProperty("no pictures");
        this.oldestDate = new SimpleStringProperty("");

    }

   
    public album(String name,Integer photos,String date, String dates){
        this.name = new SimpleStringProperty(name);
        this.photos = new SimpleIntegerProperty(photos);
        if(getPhotos() == 0){
            this.date = new SimpleStringProperty("never");
        }
        else this.date = new SimpleStringProperty(date);
        if(getPhotos() == 0){
            this.dates = new SimpleStringProperty("no photos");
        }else
            this.dates = new SimpleStringProperty(dates);
        this.oldestDate = new SimpleStringProperty("");
    }

   
    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    
    public void setName(String name) {
        this.name.set(name);
    }

   
    public int getPhotos() {
        return photos.get();
    }

   
    public SimpleIntegerProperty photosProperty() {
        return photos;
    }

    
    public void setPhotos(int photos) {
        this.photos.set(photos);
    }

    
    public String getDate() {
        return date.get();
    }

    
    public SimpleStringProperty dateProperty() {
        return date;
    }

   
    public void setDate(String date) {
        this.date.set(date);
    }

   
    public String getDates() {
        return dates.get();
    }

   
    public SimpleStringProperty datesProperty() {
        return dates;
    }

   
    public void setDates(String dates) {
        this.dates.set(dates);
    }
}
