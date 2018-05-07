package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
import java.util.ArrayList;
import java.util.List;
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
    private Spinner<Integer> spDelay;

    @FXML
    private ToggleGroup tgDisplay;

    @FXML
    private ListView<KnightsTour> lvThreads;

    @FXML
    private ListView<?> lvResults;

    @FXML
    private volatile GridPane gpField;

    private  ObservableList<ObservableList<Label>> numbersList=FXCollections.observableArrayList();

    private void setControlsBehaviour(){
        spDelay.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(100,2000,100,100));
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

    private List<Label> illegalTajics=new ArrayList<>();
    private List<Service<Void>> backgroundThreads=new ArrayList<>();


    @FXML
    void btnAdd() {
        if (lvThreads.getItems().size()<9)
        lvThreads.getItems().add(new EulerVandermonde(spWidth.getValue(),spHeight.getValue()));
        lvThreads.getItems().get(lvThreads.getItems().size()-1).setDelay(spDelay.getValue());
    }

    @FXML
    void btnBuild() {
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
    void btnCheck() {
        if ((spWidth.getValue()>3&&spHeight.getValue()>4)||(spWidth.getValue()>4&&spHeight.getValue()>3))
            new Alert(Alert.AlertType.INFORMATION,"для поля заданного размера можно построить маршрут",ButtonType.OK).showAndWait();
        else
            new Alert(Alert.AlertType.INFORMATION,"для поля заданного размера нельзя построить маршрут",ButtonType.OK).showAndWait();

    }

    @FXML
    void btnCustom() {

    }

    @FXML
    void btnDelete() {

    }

    @FXML
    void btnLines() {

    }

    @FXML
    void btnNumbers() {

    }

    @FXML
    void btnReset() {
        btnBuild();
        lvThreads.getItems().clear();
    }

    @FXML
    void btnStandard() {

    }

    @FXML
    void btnStart() {
        illegalTajics.clear();
        backgroundThreads.clear();
        KnightsTour.refreshColors();
        System.out.println("btn start");
        for (int threads = 0; threads < lvThreads.getItems().size(); threads++) {
            KnightsTour kt = lvThreads.getItems().get(threads);
            kt.startFinding();
            final int threadNumber = threads;

//            RUN LATER
//            Platform.runLater(() -> {
//                System.out.println("color "+(kt.getColor()==null));
//                for (int i = 0; i <kt.getPath().size(); i++) {
//                    Label changed=numbersList.get(kt.getPath().get(i)[0]*3 +threadNumber%3)
//                            .get(kt.getPath().get(i)[1]*3+(int)Math.floor(threadNumber/3));
//                    gpField.getChildren().remove(changed);
//                    changed=createLabel(kt.getPath().get(i)[0]*3 +threadNumber%3,
//                            kt.getPath().get(i)[1]*3+(int)Math.floor(threadNumber/3),kt.getColor(),i+1);
//                    gpField.add(changed, kt.getPath().get(i)[0]*3 +threadNumber%3,
//                            kt.getPath().get(i)[1]*3+(int)Math.floor(threadNumber/3));
//
//                    try {
//                        System.out.println("sleep #"+threadNumber+" i="+i);
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

//            WITH TASK
            backgroundThreads.add(new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<>() {
                        @Override
                        protected Void call() {
                            for (int i = 0; i < kt.getPath().size(); i++) {
                                try {
                                    updateMessage(kt.getColor() + ',' +
                                            i + ',' +
                                            (kt.getPath().get(i)[0] * 3 + threadNumber % 3) + ',' +
                                            (kt.getPath().get(i)[1] * 3 + (int) Math.floor(threadNumber / 3)) + ',');

                                    System.out.println("sleep #" + threadNumber + " i=" + i);
                                    Thread.sleep(kt.getDelay());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            return null;
                        }
                    };
                }
            });
            Label listener=new Label();
            listener.textProperty().bind(backgroundThreads.get(backgroundThreads.size()-1).messageProperty());
            listener.textProperty().addListener((observable, oldValue, newValue) -> {
                changeCell(newValue);
            });
            illegalTajics.add(listener);
        }
        for (Service<Void> backgroundThread : backgroundThreads) {
            backgroundThread.restart();
        }
    }

    private void changeCell(String newValue) {
        StringBuilder strColor=new StringBuilder("");
        StringBuilder strStep=new StringBuilder("");
        StringBuilder strI=new StringBuilder("");
        StringBuilder strJ=new StringBuilder("");
        int x=0;
        while (newValue.toCharArray()[x]!=','){
            strColor.append(newValue.toCharArray()[x]);
            x++;
        }
        x++;
        while (newValue.toCharArray()[x]!=','){
            strStep.append(newValue.toCharArray()[x]);
            x++;
        }
        x++;
        while (newValue.toCharArray()[x]!=','){
            strI.append(newValue.toCharArray()[x]);
            x++;
        }
        x++;
        while (newValue.toCharArray()[x]!=','){
            strJ.append(newValue.toCharArray()[x]);
            x++;
        }

                Label changed=numbersList.get(Integer.valueOf(strI.toString()))
                        .get(Integer.valueOf(strJ.toString()));
                gpField.getChildren().remove(changed);
                changed=createLabel(
                        Integer.valueOf(strI.toString()),
                        Integer.valueOf(strJ.toString()),
                        strColor.toString(),
                        Integer.valueOf(strStep.toString())+1);
                gpField.add(changed,
                        Integer.valueOf(strI.toString()),
                        Integer.valueOf(strJ.toString())
                );
    }

    @FXML
    void btnStop() {
        for (Service<Void> thr:backgroundThreads) {
            thr.reset();
        }
    }

    @FXML
    void initialize() {
        assert tgLanguage != null : "fx:id=\"tgLanguage\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert tgFieldSize != null : "fx:id=\"tgFieldSize\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert spHeight != null : "fx:id=\"spHeight\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert spWidth != null : "fx:id=\"spWidth\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert spDelay != null : "fx:id=\"spDelay\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert tgDisplay != null : "fx:id=\"tgDisplay\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert lvThreads != null : "fx:id=\"lvThreads\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert lvResults != null : "fx:id=\"lvResults\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert gpField != null : "fx:id=\"gpField\" was not injected: check your FXML file 'MainWindow.fxml'.";
        setControlsBehaviour();

    }
}
