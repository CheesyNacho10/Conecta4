/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Player;
import model.Round;
import nav.Navigation;
import util.RoundListCell;

/**
 *
 * @author 44puk
 */
public class FXMLTotalHistoryController extends FXMLBaseController {
    
    @FXML
    private ListView gamesListView;

    @Override
    void init() {
        initListCellFactory();
        loadGames();
    }
    
    @FXML
    private void navigateToHistory() {
        Navigation.navigateToHistory((Stage)  gamesListView.getScene().getWindow(), getClass());
    }

    private void loadGames() {
        TreeMap<LocalDate, List<Round>> treeMap = db.getRoundsPerDay();
        
        NavigableMap<LocalDate, List<Round>> selectionMap = treeMap.subMap(applicationState.getStartDate(), true, applicationState.getEndDate(), true);
        
        Collection<List<Round>> selectedDaysWithRounds  = selectionMap.values();
        
        List<Round> allRounds = new ArrayList();
        
        selectedDaysWithRounds.forEach(day -> {
            allRounds.addAll(day);
        });
        
        Collections.reverse(allRounds);
        
        ObservableList<Round> observableList = FXCollections.observableArrayList(allRounds);
        
        gamesListView.setItems(observableList);
    }

    private void initListCellFactory() {
        gamesListView.setCellFactory(c -> new RoundListCell());
    }
}
