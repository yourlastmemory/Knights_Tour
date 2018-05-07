package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.Main;
import model.solutions.EulerVandermonde;
import model.solutions.KnightsTour;

import java.net.URL;
import java.util.Locale;
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
    private volatile GridPane gpField;

    private volatile ObservableList<ObservableList<Label>> numbersList=FXCollections.observableArrayList();

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
        if (lvThreads.getItems().size()<9)
        lvThreads.getItems().add(new EulerVandermonde(spWidth.getValue(),spHeight.getValue()));
    }

    @FXML
    void btnBuild(ActionEvent event) {
        numbersList.clear();
        gpField.getChildren().clear();
        gpField.getColumnConstraints().clear();
        gpField.getRowConstraints().clear();
        Boolean color=true;
        for (int i = 0; i <spWidth.getValue()*3; i++) {
            numbersList.add(FXCollections.observableArrayList());
            gpField.addRow(i);

            for (int j = 0; j <spHeight.getValue()*3; j++) {
                numbersList.get(i).add(createLabel(i,j, "GRAY", 0));
                color=!color;
//                numbersList.get(i).add(new Label(String.valueOf (new Random().nextInt(9))));
                gpField.add(numbersList.get(i).get(j),i,j);
            }
        }

    }

    private Label createLabel(int i, int j, String color, int number) {
        Label created=new Label(String.valueOf(number));
        created.setPrefSize(50,15);
        created.setAlignment(Pos.CENTER);
//        created.getStyle().concat("-fx-width: 12px;\n" +
//                "    -fx-height: 12px;\n" +
//                "    -fx-font-size: 10px;\n" +
//                "    -webkit-text-fill-color: chartreuse;\n");
        created.setStyle("-fx-text-fill: "+color+" ;\n");
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
        if ((spWidth.getValue()>3&&spHeight.getValue()>4)||(spWidth.getValue()>4&&spHeight.getValue()>3))
            new Alert(Alert.AlertType.INFORMATION,"для поля заданного размера можно построить маршрут",ButtonType.OK).showAndWait();
        else
            new Alert(Alert.AlertType.INFORMATION,"для поля заданного размера нельзя построить маршрут",ButtonType.OK).showAndWait();

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
        KnightsTour.refreshColors();
        System.out.println("btn start");
        for (int threads = 0; threads <lvThreads.getItems().size(); threads++) {
            KnightsTour kt=lvThreads.getItems().get(threads);
            kt.startFinding();
            final int threadNumber= threads;

            Platform.runLater(() -> {
                System.out.println("color "+(kt.getColor()==null));
                for (int i = 0; i <kt.getPath().size(); i++) {
                    Label changed=numbersList.get(kt.getPath().get(i)[0]*3 +threadNumber%3)
                            .get(kt.getPath().get(i)[1]*3+(int)Math.floor(threadNumber/3));
                    gpField.getChildren().remove(changed);
                    changed=createLabel(kt.getPath().get(i)[0]*3 +threadNumber%3,
                            kt.getPath().get(i)[1]*3+(int)Math.floor(threadNumber/3),kt.getColor(),i+1);
                    gpField.add(changed, kt.getPath().get(i)[0]*3 +threadNumber%3,
                            kt.getPath().get(i)[1]*3+(int)Math.floor(threadNumber/3));

                    try {
                        System.out.println("sleep #"+threadNumber+" i="+i);
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
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
    private String prepareColor(Color myColor) {
        int green = (int) (myColor.getGreen()*255);
        String greenString = Integer.toHexString(green);

        int red = (int) (myColor.getRed()*255);
        String redString = Integer.toHexString(red);

        int blue = (int) (myColor.getBlue()*255);
        String blueString = Integer.toHexString(blue);

        String hexColor = "#"+redString+""+greenString+""+blueString;
        System.out.println(hexColor);
        System.out.println(myColor.toString());
        return hexColor;
    }
}
