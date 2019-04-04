/*
 * Gino Tarraga
 * Kaival Patel
 */
package utils;

import javafx.beans.property.SimpleStringProperty;


public class User{
    private final SimpleStringProperty username;

   
    public String getUsername() {
        return username.get();
    }

   
    public SimpleStringProperty usernameProperty() {
        return username;
    }

    
    public void setUsername(String username) {
        this.username.set(username);
    }



    
    public User(){this(null);}

   
    public User(String username){
        this.username = new SimpleStringProperty(username);

    }


}
