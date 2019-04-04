/*
 * Gino Tarraga
 * Kaival Patel
 */
package utils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


public class Photo{
    private SimpleStringProperty caption = new SimpleStringProperty();
    private ObjectProperty photoArt = new SimpleObjectProperty<>();
    private SimpleStringProperty tag = new SimpleStringProperty();

   
    public Photo(String caption, photo2 photo2, String tag){
        setTag(tag);
        setCaption(caption);
        setPhotoArt(photo2);

    }
    
   
    public String getCaption() {
        return caption.get();
    }
    
    public SimpleStringProperty captionProperty() {
        return caption;
    }

  
    public void setCaption(String caption) {
        this.caption.set(caption);
    }
    
   
    public Object getPhotoArt() {
        return photoArt.get();
    }
    
    
    public ObjectProperty photoArtProperty() {
        return photoArt;
    }

    
    public void setPhotoArt(photo2 photoArt) {
        this.photoArt.set(photoArt);
    }

   
    public String getTag() {
        return tag.get();
    }

   
    public SimpleStringProperty tagProperty() {
        return tag;
    }

    
    public void setTag(String tag) {
        this.tag.set(tag);
    }
}
