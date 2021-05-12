/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;
import nav.Navigation;

/**
 *
 * @author 44puk
 */
public class FXMLRankingController extends FXMLBaseController {
    
    
    @FXML
    private TextField searchField;
    
    @FXML
    private ListView playersListView;
    
    private ObservableList<Player> observableList;      

    @Override
    void init() {
        initListCellFactory();
        initListData();
    }
    
    @FXML
    private void navigateToHome() {
        Navigation.navigateToHome((Stage) searchField.getScene().getWindow(), getClass());
    }
    
    @FXML
    private void searchPlayer() {
        FilteredList<Player> filtered = observableList.filtered(player -> {
            String search = searchField.getText().toLowerCase();
            String nick = player.getNickName().toLowerCase();
            return nick.contains(search);
        });
        
        playersListView.setItems(filtered);
        
    }

    private void initListData() {
        ArrayList<Player> players = db.getConnect4Ranking();
        observableList = FXCollections.observableArrayList(players);
        playersListView.setItems(observableList);
    }

    private void initListCellFactory() {
        playersListView.setCellFactory(c -> new PlayerListCell());
    }
    
    class PlayerListCell extends ListCell<Player> {
        
        private ImageView avatar = new ImageView();
              
        @Override
        protected void updateItem(Player item, boolean empty) {
            super.updateItem(item, empty);
            
            if (item == null || empty) {
                setText(null);
                setGraphic(null);
            } else {
                avatar.setImage(item.getAvatar());
                avatar.setFitHeight(45.);
                avatar.setFitWidth(40.);
                setGraphic(avatar);
                setText("    Nombre de usuario: " + item.getNickName() + "     Puntuación: " + item.getPoints());
            }
            
        }
        
        
    }
    
}
