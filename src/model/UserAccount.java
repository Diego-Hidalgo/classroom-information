package model;

public class UserAccount {

    private String userName;
    private String password;
    private String photo;
    private Gender gender;
    private Career[] careers;
    private String birthday;
    private Browser favBrowser;

    public UserAccount(String userName, String password, String photo, String gender, String birthday, String favBrowser) {
        this.userName = userName;
        this.password = password;
        this.photo = photo;
        this.gender = Gender.valueOf(gender);
        careers = new Career[3];
        this.birthday = birthday;
        this.favBrowser = Browser.valueOf(favBrowser);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = Gender.valueOf(gender);
    }

    public Career[] getCareers() {
        return careers;
    }

    public String getCareersInfo() {
        String info = "";
        for(int i = 0; i < careers.length; i++) {
            if(careers[i] != null) {
                info += careers[i] + "  ";
            }
        }
        return info;
    }

    public void setCareers(Career[] careers) {
        this.careers = careers;
    }

    public void setCareers(String[] careers) {
        for(int i = 0; i < careers.length; i ++) {
            if(careers[i] != null) {
                this.careers[i] = Career.valueOf(careers[i]);
            }
        }
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Browser getFavBrowser() {
        return favBrowser;
    }

    public void setFavBrowser(String favBrowser) {
        this.favBrowser = Browser.valueOf(favBrowser);
    }

}
