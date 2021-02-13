package model;

import java.util.ArrayList;

public class ClassRoom {

    private ArrayList<UserAccount> users;

    public ClassRoom() {
        this.users = new ArrayList<UserAccount>();
    }

    public ArrayList<UserAccount> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserAccount> users) {
        this.users = users;
    }

    public boolean findUserName(String userName) {
        boolean find = false;
        for(int i = 0; i < users.size() && !find; i++) {
            UserAccount user = users.get(i);
            if(user.getUserName().equals(userName)) {
                find = true;
            }
        }
        return find;
    }

    public void addUser(String userName, String passWord, String photo, String gender, String[] career, String birthday, String favBrowser){
       UserAccount user = new UserAccount(userName, passWord, photo, gender, birthday, favBrowser);
        user.setCareers(career);
        users.add(user);
    }

    public int amountUsers(){
        return users.size();
    }

    public boolean validateCredentials(String userName, String password) {
        boolean validate = false;
        for(int i = 0; i < users.size() && !validate; i++) {
            UserAccount user = users.get(i);
            if(user.getUserName().equals(userName) && user.getPassword().equals(password)){
                validate = true;
            }
        }
        return validate;
    }

    public String getUserPhotoPath(String userName) {
        String imgRoute = "";
        for(int i = 0; i < users.size(); i++) {
            UserAccount user = users.get(i);
            if(user.getUserName().equals(userName)) {
                imgRoute = user.getPhoto();
            }
        }
        return imgRoute;
    }

}
