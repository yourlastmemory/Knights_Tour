package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Main;
import model.solutions.KnightsTour;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private ResourceBundle resources=ResourceBundle.getBundle("lang",new Locale("ru"));

    @FXML
    private URL location;

    @FXML
    private ToggleGroup tgLanguage;

    @FXML
    private ToggleGroup tgFieldSize;

    @FXML
    private Spinner<Integer> spHeight;

    @FXML
    private Spinner<Integer> spWidth;

    @FXML
    private ToggleGroup tgDisplay;

    @FXML
    private ListView<KnightsTour> lvThreads;

    @FXML
    private ListView<?> lvResults;

    @FXML
    private GridPane gpField;

    private volatile ArrayList<ArrayList<Label>> numbersList=new ArrayList<>();

    private void setControlsBehaviour(){
        spHeight.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3,11,1, 1));
        spWidth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3,11,1, 1));
        tgLanguage.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            try {
                String lng = ((RadioMenuItem) newValue).getText();
                System.out.println(lng);
                switch (lng){
                    case "English":{
                        Main.setBundle(ResourceBundle.getBundle("lang", new Locale("en")));
                        break;
                    }
                    case "Deutch":{
                        resources=ResourceBundle.getBundle("lang", new Locale("de"));
                        break;
                    }
                    case "Español":{
                        resources=ResourceBundle.getBundle("lang", new Locale("es"));
                        break;
                    }
                    case "Français":{
                        resources=ResourceBundle.getBundle("lang", new Locale("fr"));
                        break;
                    }
                    case "Italiano":{
                        resources=ResourceBundle.getBundle("lang", new Locale("it"));
                        break;
                    }
                    case "Русский":{
                        resources=ResourceBundle.getBundle("lang", new Locale("ru"));
                        break;
                    }
                }
                initialize();
            } catch (Exception e) {
                System.out.println("some bullshit");
            }
        });
    }


    @FXML
    void btnAdd(ActionEvent event) {

    }

    @FXML
    void btnBuild(ActionEvent event) {
        numbersList.clear();
        gpField.getChildren().clear();
        gpField.getColumnConstraints().clear();
        gpField.getRowConstraints().clear();
        Boolean color=true;
        for (int i = 0; i <spWidth.getValue()*3; i++) {
            numbersList.add(new ArrayList<>());
            gpField.addRow(i);

            for (int j = 0; j <spHeight.getValue()*3; j++) {
                numbersList.get(i).add(createLabel(i,j, color));
                color=!color;
//                numbersList.get(i).add(new Label(String.valueOf (new Random().nextInt(9))));
                gpField.add(numbersList.get(i).get(j),i,j);
            }
        }

    }

    private Label createLabel(int i, int j, Boolean color) {
        Label created=new Label(String.valueOf (new Random().nextInt(9)));
        created.setPrefSize(15,12);
        created.setAlignment(Pos.CENTER);
//        created.getStyle().concat("-fx-width: 12px;\n" +
//                "    -fx-height: 12px;\n" +
//                "    -fx-font-size: 10px;\n" +
//                "    -webkit-text-fill-color: chartreuse;\n");
        created.setStyle("-fx-text-fill: blue;\n");
        if((Math.floor(i/3))%2==0)
            if((Math.floor(j/3))%2==0)
                created.setStyle(created.getStyle().concat("-fx-background-color: black;"));
            else
                created.setStyle(created.getStyle().concat("-fx-background-color: white;"));
            else if((Math.floor(j/3))%2==0)
                created.setStyle(created.getStyle().concat("-fx-background-color: white;"));
            else
                created.setStyle(created.getStyle().concat("-fx-background-color: black;"));

        return created;
    }

    @FXML
    void btnCheck(ActionEvent event) {

    }

    @FXML
    void btnCustom(ActionEvent event) {

    }

    @FXML
    void btnDelete(ActionEvent event) {

    }

    @FXML
    void btnLines(ActionEvent event) {

    }

    @FXML
    void btnNumbers(ActionEvent event) {

    }

    @FXML
    void btnReset(ActionEvent event) {

    }

    @FXML
    void btnStandard(ActionEvent event) {

    }

    @FXML
    void btnStart(ActionEvent event) {

    }

    @FXML
    void btnStop(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert tgLanguage != null : "fx:id=\"tgLanguage\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert tgFieldSize != null : "fx:id=\"tgFieldSize\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert spHeight != null : "fx:id=\"spHeight\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert spWidth != null : "fx:id=\"spWidth\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert tgDisplay != null : "fx:id=\"tgDisplay\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert lvThreads != null : "fx:id=\"lvThreads\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert lvResults != null : "fx:id=\"lvResults\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert gpField != null : "fx:id=\"gpField\" was not injected: check your FXML file 'MainWindow.fxml'.";
        setControlsBehaviour();

    }
}
