/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Player;
import model.Round;
import nav.Navigation;
import util.RoundListCell;

/**
 *
 * @author 44puk
 */
public class FXMLPlayerHistoryController extends FXMLBaseController {
    
    @FXML
    private ListView gamesListView;
    
    private Player player;
    @FXML
    private Button showAllGamesB;
    @FXML
    private Button showLostGamesB;
    @FXML
    private Button showWonGamesB;

    @Override
    void init() {
        
        initPlayer();
        initListCellFactory();
        showAllGames();
        
        showAllGamesB.setStyle(".relevant");
    }
    
    @FXML
    private void navigateToHistory() {
        Navigation.navigateToHistory((Stage)  gamesListView.getScene().getWindow(), getClass());
    }
    
    @FXML
    private void showAllGames() {
        setListData(Collections.EMPTY_LIST);
        List<Round> rounds = db.getRoundsPlayer(player);
        rounds = filterDates(rounds);
        if(rounds != null) setListData(rounds);
    }
    
    @FXML
    private void showLostGames() {
        setListData(Collections.EMPTY_LIST);
        List<Round> rounds = db.getLostRoundsPlayer(player);
        rounds = filterDates(rounds);
        if(rounds != null) setListData(rounds);
    }
    
    @FXML
    private void showWonGames() {
        setListData(Collections.EMPTY_LIST);
        List<Round> rounds = db.getWinnedRoundsPlayer(player);
        rounds = filterDates(rounds);
        if(rounds != null) setListData(rounds);
    }
    
    private void initListCellFactory() {
        gamesListView.setCellFactory(c -> new RoundListCell());
    }
    
    private void initPlayer() {
        player = db.getPlayer(applicationState.getPlayerToShowHistory());
    }
    
    private void setListData(List<Round> rounds) {
       
        Collections.reverse(rounds);
        
        ObservableList<Round> observableList = FXCollections.observableArrayList(rounds);
        
        gamesListView.setItems(observableList);
    }
    
    
    private List<Round> filterDates(List<Round> rounds) {
        Round start = null;
        Round end = null;
        
        for (Round round : rounds) {
            
            if(start == null && (round.getLocalDate().compareTo(applicationState.getStartDate()) >= 0)) {
               
                start = round;
            }
            
            if(round.getLocalDate().compareTo(applicationState.getEndDate()) >= 1) {
               
                end = round;
            }
        }
        
        if(start != null) {
            
            if(end != null) {
                return rounds.subList(rounds.indexOf(start), rounds.indexOf(end));
            
            } else {
                return rounds.subList(rounds.indexOf(start), rounds.size());
            }
        }
        return null;
    }
}
