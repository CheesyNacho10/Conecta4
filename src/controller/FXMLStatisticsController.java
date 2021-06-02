/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.DayRank;
import nav.Navigation;

/**
 * FXML Controller class
 *
 * @author 44puk
 */
public class FXMLStatisticsController extends FXMLBaseController {
    
    @FXML
    private BarChart<String, Number> barChart;
    
    @FXML
    private LineChart<String, Number> lineChart;
    
    @FXML
    private Button clearButton;
        
        
    @Override
    void init() {
        barChart.managedProperty().bind(barChart.visibleProperty());
        lineChart.managedProperty().bind(lineChart.visibleProperty());
        initData();
        
    }

    @FXML
    void clearSelection() {
        if(!clearButton.isDisable()) {
            initData();
            clearButton.setDisable(true);
        }
    }

    @FXML
    void navigateToHome() {
        Navigation.navigateToHome((Stage) lineChart.getScene().getWindow(), getClass());
    }

    @FXML
    void selectDates() {
        showDatePickerDialog();
        initData();
    }

    @FXML
    void selectPlayer() {
        
        applicationState.setPlayerToShowHistory(null);
        
        showPlayerPickerDialog();
        
        if(applicationState.getPlayerToShowHistory() != null) {
            initDataWithPlayer();
            clearButton.setDisable(false);
        }
    }
    
    private void showDatePickerDialog() {
        // Create the custom dialog.
        Dialog<Pair<LocalDate, LocalDate>> dialog = new Dialog<>();
        dialog.setTitle("Elegir fechas");
        dialog.setHeaderText("Elija el periodo de tiempo para mostrar las partidas");
        
        ButtonType okButton = ButtonType.OK;
        
        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        DatePicker start = new DatePicker(LocalDate.now());
        DatePicker end = new DatePicker(LocalDate.now());

        grid.add(new Label("Desde:"), 0, 0);
        grid.add(start, 1, 0);
        grid.add(new Label("Hasta:"), 0, 1);
        grid.add(end, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                return new Pair<>(start.getValue(), end.getValue());
            }
            return null;
        });

        DialogPane dialogP = dialog.getDialogPane();
        String style = Navigation.isDark ? "/view/styleB.css" : "/view/style.css";
        dialogP.getStylesheets().add(getClass().getResource(style).toExternalForm());
        
        Optional<Pair<LocalDate, LocalDate>> result = dialog.showAndWait();

        result.ifPresent(startAndEndDates -> {
            applicationState.setStartDate(startAndEndDates.getKey());
            applicationState.setEndDate(startAndEndDates.getValue());
        });
    }

    private void initData() {
        TreeMap<LocalDate, Integer> map = db.getRoundCountsPerDay();
        Map<LocalDate, Integer> selection = map.subMap(applicationState.getStartDate(), true, applicationState.getEndDate(), true);
        
        List<XYChart.Data<String, Number>> list = new ArrayList();
        
        for(Entry<LocalDate, Integer> entry : selection.entrySet()) {
            
            list.add(new XYChart.Data(entry.getKey().toString(), entry.getValue()));
        }
        
        XYChart.Series serie = new XYChart.Series(FXCollections.observableArrayList(list));
        
        serie.setName("Total");
        
        lineChart.getData().clear();
        lineChart.setTitle("Todas las partidas");
        lineChart.getData().add(serie);
        
        lineChart.setVisible(true);
        barChart.setVisible(false);
        
    }
    
    private void showPlayerPickerDialog() {
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Elegir fechas y usuario");
        dialog.setHeaderText("Elija el periodo de tiempo y el usuario para mostrar las partidas");
        
        ButtonType okButton = ButtonType.OK;
        
        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField textField = new TextField();
        textField.setPromptText("Entra el nombre del jugador");
        
        Text error = new Text();
        error.setText("Jugador no existe");
        error.setFill(Color.RED);
        
        grid.add(new Label("Jugador:"), 0, 0);
        grid.add(textField, 1, 0);
        grid.add(error, 2, 0);

        dialog.getDialogPane().setContent(grid);
        
        Node buttonNode = dialog.getDialogPane().lookupButton(okButton);
        
        buttonNode.setDisable(true);
        error.setVisible(false);
        
        textField.setOnKeyReleased(e -> {
            
            boolean userExists = db.exitsNickName(textField.getText());
            
            buttonNode.setDisable(!userExists);
            error.setVisible(!userExists);
            
        });
        
        dialog.setResultConverter(dialogButton -> {
            
            if (dialogButton == okButton) {
                
                return textField.getText();
            }
            return null;
        });
        
        DialogPane dialogP = dialog.getDialogPane();
        String style = Navigation.isDark ? "/view/styleB.css" : "/view/style.css";
        dialogP.getStylesheets().add(getClass().getResource(style).toExternalForm());

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nickName -> {
            applicationState.setPlayerToShowHistory(nickName);
        });
    }

    private void initDataWithPlayer() {
        TreeMap<LocalDate, DayRank> map = db.getDayRanksPlayer(db.getPlayer(applicationState.getPlayerToShowHistory()));
        Map<LocalDate, DayRank> selection = map.subMap(applicationState.getStartDate(), applicationState.getEndDate());
        
        List<XYChart.Data<String, Number>> winned = new ArrayList();
        List<XYChart.Data<String, Number>> lost = new ArrayList();
        List<XYChart.Data<String, Number>> oponents = new ArrayList();
        
        for(Entry<LocalDate, DayRank> entry : selection.entrySet()) {
            
            winned.add(new XYChart.Data(entry.getKey().toString(), entry.getValue().getWinnedGames()));
            lost.add(new XYChart.Data(entry.getKey().toString(), entry.getValue().getLostGames()));
            oponents.add(new XYChart.Data(entry.getKey().toString(), entry.getValue().getOponents()));
        }
        
        XYChart.Series serieW = new XYChart.Series(FXCollections.observableArrayList(winned));
        
        serieW.setName("Ganadas");
        
        XYChart.Series serieL = new XYChart.Series(FXCollections.observableArrayList(lost));
        
        serieL.setName("Perdidas");
        
        XYChart.Series serieOp = new XYChart.Series(FXCollections.observableArrayList(oponents));
        
        serieOp.setName("Oponentes");
        
        barChart.getData().clear();
        barChart.setTitle("Todas las partidas");
        barChart.getData().add(serieW);
        barChart.getData().add(serieL);
        barChart.getData().add(serieOp);
        
        barChart.setVisible(true);
        lineChart.setVisible(false);
    }
    
}
