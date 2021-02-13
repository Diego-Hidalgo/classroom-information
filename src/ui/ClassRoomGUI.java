package ui;

import model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ClassRoomGUI {

    private ClassRoom classRoom;

    //mainPane
    @FXML BorderPane mainPane;

    //Login controls
    @FXML Button loginBtn;
    @FXML TextField userNametxt;
    @FXML PasswordField passwordtxt;

    //Register controls
    @FXML TextField newNametxt;
    @FXML PasswordField newPasswordtxt;
    @FXML TextField photoRoute;
    @FXML ToggleGroup genderGroup;
    @FXML CheckBox softwareCheck;
    @FXML CheckBox telematicCheck;
    @FXML CheckBox industrialCheck;
    @FXML DatePicker birthdaytxt;
    @FXML ChoiceBox<String> browserOptions;

    //AccountList controls
    @FXML TableView<UserAccount> contentList;
    @FXML TableColumn<UserAccount, String> userNameColumn;
    @FXML TableColumn<UserAccount, Gender> genderColumn;
    @FXML TableColumn<UserAccount, String> careerColumn;
    @FXML TableColumn<UserAccount, String> birthdayColumn;
    @FXML TableColumn<UserAccount, Browser> browserColumn;

    public ClassRoomGUI(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    @FXML
    public void initialize() {}

    @FXML
    public void loadLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        fxmlLoader.setController(this);
        Parent pane = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.setCenter(pane);
        if(classRoom.amountUsers() == 0){
            loginBtn.setDisable(true);
        }
        Stage st = (Stage) pane.getScene().getWindow();
        st.setHeight(300);
        st.setWidth(450);
    }

    @FXML
    public void loginUser() throws IOException {
        String userName = userNametxt.getText();
        String password = passwordtxt.getText();
        if(classRoom.validateCredentials(userName, password)) {
            loginSuccessful(userName);
        } else {
            loginError();
        }
    }

    @FXML
    public void loginSuccessful(String userName) throws IOException {

    }

    @FXML
    private void loginError() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("LOGIN ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Please verify your username and/or password");
        alert.showAndWait();
    }

    @FXML
    public void loadRegister() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
        fxmlLoader.setController(this);
        Parent pane = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.setCenter(pane);
        setBrowserElements();
        Stage st = (Stage) pane.getScene().getWindow();
        st.setHeight(550);
        st.setWidth(505);
    }

    @FXML
    private void setBrowserElements() throws IOException {
        browserOptions.getItems().add("CHROME");
        browserOptions.getItems().add("OPERA");
        browserOptions.getItems().add("FIREFOX");
        browserOptions.getItems().add("EDGE");
        browserOptions.getItems().add("TOR");
        browserOptions.getItems().add("SAFARI");
    }

    @FXML
    public void registerUser() throws IOException{
        if(!newNametxt.getText().equals("") && !newPasswordtxt.getText().equals("") &&
        !photoRoute.getText().equals("") && !birthdaytxt.getValue().toString().equals("") && !browserOptions.getValue().equals("")) {
            if(!classRoom.findUserName(newNametxt.getText())) {
                String name = newNametxt.getText();
                String password = newPasswordtxt.getText();
                String photo = photoRoute.getText();
                String gender = getSelectedGender();
                String[] careers = getSelectedCareers();
                String birthday = birthdaytxt.getValue().toString();
                String favBrowser = browserOptions.getValue().toString();
                classRoom.addUser(name, password, photo, gender, careers, birthday, favBrowser);
                registerSuccessful();
            } else {
                nameNotAvailable();
            }
        } else {
            registerError();
        }
    }

    @FXML
    private void nameNotAvailable() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("THE USERNAME YOU CHOSE IS NOT AVAILABLE");
        alert.setContentText("Please select another username");
        alert.showAndWait();
    }

    private String getSelectedGender() {
        RadioButton selection = (RadioButton) genderGroup.getSelectedToggle();
        String gender = selection.getText().toUpperCase();
        return gender;
    }


    private String[] getSelectedCareers() {
        String[] careers = new String[3];
        if(softwareCheck.isSelected()) {
            careers[0] = "SOFTWARE";
        }
        if(telematicCheck.isSelected()) {
            careers[1] = "TELEMATIC";
        }
        if(industrialCheck.isSelected()) {
            careers[2] = "INDUSTRIAL";
        }
        return careers;
    }

    @FXML
    public void browsePhotos() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your profile photo");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG","*.png"));
        File photoSelected = fileChooser.showOpenDialog(null);
        if(photoSelected != null) {
            photoRoute.setText(photoSelected.getAbsolutePath());
        }
    }

    @FXML
    private void registerSuccessful() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("The user has been successfully registered");
        ButtonType confirmation = new ButtonType("OK");
        alert.getButtonTypes().setAll(confirmation);
        alert.showAndWait();
    }

    @FXML
    private void registerError() throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("You must fill each field in the form");
        alert.showAndWait();
    }

    @FXML
    public void loadUserAccount() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("account-list.fxml"));
        fxmlLoader.setController(this);
        Parent pane = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.setCenter(pane);
        setTableViewContent();
        Stage st = (Stage) pane.getScene().getWindow();
        st.setHeight(430);
        st.setWidth(650);
    }

    @FXML
    private void setTableViewContent() throws IOException {
        ObservableList<UserAccount> content = FXCollections.observableArrayList(classRoom.getUsers());
        contentList.setItems(content);
        userNameColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("userName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, Gender>("gender"));
        careerColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("careersInfo"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("birthday"));
        browserColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, Browser>("favBrowser"));
    }

    @FXML
    public void logout() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to log out");
        alert.setContentText("Are you sure?");
        ButtonType confirmation = new ButtonType("CONFIRM");
        ButtonType cancel = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmation, cancel);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == confirmation){
            loadLogin();
        }
    }

}
