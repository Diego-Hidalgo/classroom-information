package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ClassRoom;

public class Main extends Application {

    private ClassRoom classRoom;
    private ClassRoomGUI classRoomGUI;

    public Main(){
        classRoom = new ClassRoom();
        classRoomGUI = new ClassRoomGUI(classRoom);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-pane.fxml"));
        fxmlLoader.setController(classRoomGUI);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Classroom");
        stage.show();
        classRoomGUI.loadLogin();
    }

    public static void main(String[] args) {launch(args);}

}
