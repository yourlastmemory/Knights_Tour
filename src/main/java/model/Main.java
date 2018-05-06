package model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.solutions.EulerVandermonde;
import model.solutions.PolygnacQuarters;

import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    private static ResourceBundle bundle;

    public static void main(String[] args) {

//        EulerVandermonde eulerVandermonde=new EulerVandermonde(20,20);
//        eulerVandermonde.startFinding();
        PolygnacQuarters polygnacQuarters=new PolygnacQuarters(8,8);
        polygnacQuarters.startFinding();
//        System.out.println("finish");
//        eulerVandermonde.printPath();
        polygnacQuarters.printPath();
        Platform.exit();
//        launch(args);
    }

    public static void setBundle(ResourceBundle res) {
        bundle = res;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(new Locale("ru"));
        URL url = new File("src/main/java/view/MainWindow.fxml").toURL();
        bundle = ResourceBundle.getBundle("lang", new Locale("ru"));
        Parent root= FXMLLoader.load(url, bundle);
        Scene value = new Scene(root, 1100, 700);
//        value.getStylesheets().add(getClass().getResource("FieldStyle.css").toExternalForm());
        primaryStage.setScene(value);
        primaryStage.setResizable(false);
        primaryStage.setTitle(bundle.getString("menu_help_knights_tour"));
        primaryStage.show();
    }
}
