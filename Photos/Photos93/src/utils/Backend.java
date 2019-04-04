/*
 * Gino Tarraga
 * Kaival Patel
 */
package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Backend implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String storeDir = "data";
    private static final String storeFile = "users.dat";
    private Map<String, User2> users;

   
    public Backend() {
        Backend b = null;
        try {
            b = readBackend();
        }
        catch(Exception e) {}
        if(b == null) {
            users = new HashMap<String, User2>();
        }
        else {
            users = b.users;
        }
    }

   
    public boolean addUser(User2 user) {
        boolean retVal = false;

        if(user == null) {
            return false;
        }

        if(!(users.containsKey(user.getUsername()))) {
            users.put(user.getUsername(), user);
            return true;
        }

        return false;
    }

    
    public User2 readUser(String id) {
        User2 retUser = null;

        if(id == null || id == "") {
            return null;
        }

        if(users.containsKey(id)) {
            return users.get(id);
        }

        return null;
    }

  
    public boolean writeUser(User2 user) {
        boolean retVal = false;

        if(user == null)
            return false;

        if(users.containsKey(user.getUsername())) {
            users.remove(user.getUsername());
            users.put(user.getUsername(), user);
            retVal = true;
        }


        return retVal;

    }
    
   
    public boolean deleteUser(String id) {
        boolean retVal = false;

        if(id == null || id.equals("")) {
            return false;
        }

        if(users.containsKey(id)) {
            users.remove(id);
            retVal = true;
        }

        return retVal;
    }

   
    public List<String> listUsers() {

        if(users.isEmpty()) {
            return null;
        }

        List<String> retList = new ArrayList<String>();

        for (String key : users.keySet()) {
            retList.add(key);
        }

        return retList;
    }


    
    public static void writeBackend(Backend backend) throws IOException {
        File dir = new File(storeDir);
        dir.mkdir();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
        oos.writeObject(backend);
        oos.close();
    }

    
    public static Backend readBackend() throws IOException, ClassNotFoundException {
        File dir = new File(storeDir);
        dir.mkdir();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
        Backend backend = (Backend)ois.readObject();
        ois.close();
        return backend;
    }
}
