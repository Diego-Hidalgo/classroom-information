package ui;

import javafx.event.ActionEvent;
import model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML private BorderPane mainPane;

    //Login controls
    @FXML private Button loginBtn;
    @FXML private TextField userNametxt;
    @FXML private PasswordField passwordtxt;

    //Register controls
    @FXML private TextField newNametxt;
    @FXML private PasswordField newPasswordtxt;
    @FXML private TextField photoPath;
    @FXML private ToggleGroup genderGroup;
    @FXML private CheckBox softwareCheck;
    @FXML private CheckBox telematicCheck;
    @FXML private CheckBox industrialCheck;
    @FXML private DatePicker birthdaytxt;
    @FXML private ChoiceBox<String> browserOptions;

    //AccountList controls
    @FXML private Label accountName;
    @FXML private ImageView accountPhoto;
    @FXML private TableView<UserAccount> contentList;
    @FXML private TableColumn<UserAccount, String> userNameColumn;
    @FXML private TableColumn<UserAccount, Gender> genderColumn;
    @FXML private TableColumn<UserAccount, String> careerColumn;
    @FXML private TableColumn<UserAccount, String> birthdayColumn;
    @FXML private TableColumn<UserAccount, Browser> browserColumn;

    //Class constructor
    public ClassRoomGUI(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    //initialize method
    @FXML
    public void initialize() {}

    //Loads de login.fxml screen
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

    //Action for button login
    @FXML
    public void loginUser(ActionEvent e) throws IOException {
        String userName = userNametxt.getText();
        String password = passwordtxt.getText();
        if(classRoom.validateCredentials(userName, password)) {
            loginSuccessful(userName);
        } else {
            loginError();
        }
    }

    //Loads the account-list.fxml screen and sets the user data (name and photo)
    @FXML
    private void loginSuccessful(String userName) throws IOException {
        String imgRoute = classRoom.getUserPhotoPath(userName);
        File file = new File(imgRoute);
        Image photo = new Image(file.toURI().toString());
        loadAccountList();
        setUserData(userName, photo);
    }

    //Displays an alert if credentials for login are not valid
    @FXML
    private void loginError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("LOGIN ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Please verify your username and/or password");
        ButtonType confirmation = new ButtonType("OK");
        alert.getButtonTypes().setAll(confirmation);
        alert.showAndWait();
    }

    //Loads the register.fxml screen
    @FXML
    public void loadRegister(ActionEvent e) throws IOException {
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

    //Sets the elements displayed in the ChoiceBox
    @FXML
    private void setBrowserElements() {
        browserOptions.getItems().add("CHROME");
        browserOptions.getItems().add("OPERA");
        browserOptions.getItems().add("FIREFOX");
        browserOptions.getItems().add("EDGE");
        browserOptions.getItems().add("TOR");
        browserOptions.getItems().add("SAFARI");
    }

    //Action for Button register
    @FXML
    public void registerUser(ActionEvent e) throws IOException{
        if(!newNametxt.getText().equals("") && !newPasswordtxt.getText().equals("") &&
        !photoPath.getText().equals("") && birthdaytxt.getValue() != null && !browserOptions.getValue().equals("") &&
                (softwareCheck.isSelected() || telematicCheck.isSelected() || industrialCheck.isSelected())) {
            if(!classRoom.findUserName(newNametxt.getText())) {
                String name = newNametxt.getText();
                String password = newPasswordtxt.getText();
                String photo = photoPath.getText();
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

    //Displays an alert
    @FXML
    private void nameNotAvailable() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("THE USERNAME YOU CHOSE IS NOT AVAILABLE");
        alert.setContentText("Please select another username");
        ButtonType confirmation = new ButtonType("OK");
        alert.getButtonTypes().setAll(confirmation);
        alert.showAndWait();
    }

    //Returns the selected gender
    private String getSelectedGender() {
        RadioButton selection = (RadioButton) genderGroup.getSelectedToggle();
        String gender = selection.getText().toUpperCase();
        return gender;
    }

    //returns the selected careers(s)
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

    //Displays the file chooser and lets the user choose a file
    @FXML
    public void browsePhotos(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your profile photo");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG","*.png"));
        File photoSelected = fileChooser.showOpenDialog(null);
        if(photoSelected != null) {
            photoPath.setText(photoSelected.getAbsolutePath());
        }
    }

    //Displays an alert
    @FXML
    private void registerSuccessful() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("The user has been successfully registered");
        ButtonType confirmation = new ButtonType("OK");
        alert.getButtonTypes().setAll(confirmation);
        alert.showAndWait();
        loadLogin();
    }

    //Displays an alert
    @FXML
    private void registerError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("You must fill each field in the form");
        ButtonType confirmation = new ButtonType("OK");
        alert.getButtonTypes().setAll(confirmation);
        alert.showAndWait();
    }

    //Loads the account-list.fxml screen
    @FXML
    public void loadAccountList() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("account-list.fxml"));
        fxmlLoader.setController(this);
        Parent pane = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.setCenter(pane);
        setTableViewContent();
        Stage st = (Stage) pane.getScene().getWindow();
        st.setHeight(550);
        st.setWidth(650);
    }

    //Sets the user data in the account-list screen
    @FXML
    private void setUserData(String userName, Image photo) {
        accountName.setText(userName);
        accountPhoto.setImage(photo);
    }

    //sets the content to be displayed in the TableView
    @FXML
    private void setTableViewContent() {
        ObservableList<UserAccount> content = FXCollections.observableArrayList(classRoom.getUsers());
        contentList.setItems(content);
        userNameColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("userName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, Gender>("gender"));
        careerColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("careersInfo"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("birthday"));
        browserColumn.setCellValueFactory(new PropertyValueFactory<UserAccount, Browser>("favBrowser"));
    }

    //Allows the user to logout the account-list screen an return to login screen
    @FXML
    public void logout(ActionEvent e) throws IOException {
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
