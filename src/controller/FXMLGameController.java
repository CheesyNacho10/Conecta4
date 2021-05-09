/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Game;
import model.GameState;
import java.awt.Paint;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Player;
import nav.Navigation;

/**
 *
 * @author 44puk
 */
public class FXMLGameController extends FXMLBaseController{
    
    @FXML
    private ImageView IVFirstPlayerAvatar;
    
    @FXML
    private Text TFirstPlayerName;
    
    @FXML
    private ImageView IVSecondPlayerAvatar;
    
    @FXML
    private Text TSecondPlayerName;
    
    @FXML
    private GridPane GPField;
    
    @FXML
    private Circle CircleZero;
    @FXML
    private Circle CircleOne;
    @FXML
    private Circle CircleTwo;
    @FXML
    private Circle CircleThree;
    @FXML
    private Circle CircleFour;
    @FXML
    private Circle CircleFive;
    @FXML
    private Circle CircleSix;
    @FXML
    private Circle CircleSeven;
    
    private Game game = new Game();
    private boolean firstPlayerTurn = true;
    private Color color = firstPlayerTurn ? Color.DODGERBLUE : Color.RED;
    
    private List<Circle> addedCircles = new ArrayList();
    
    

    @Override
    void init() {
        initFirstPlayer();
        initSecondPlayer();
        initCircleListeners();
        // 2. dialogs
    }
    
    private void initCircleListeners() {
        Circle[] circles = new Circle[] {CircleZero, CircleOne, CircleTwo, CircleThree, CircleFour, CircleFive, CircleSix, CircleSeven};
        for (Circle circle : circles) {
            circle.setVisible(false);
            
            circle.setOnMouseEntered(event -> {
                circle.setVisible(false);
            });
            
            circle.setOnMouseExited(event -> {
                circle.setVisible(false);
            });
            
            circle.setOnMouseClicked(event -> {
                Integer index = GridPane.getColumnIndex(circle);
                //this is some bug, but when clicked on column 0 index is returned as null, so set it back to 0
                if(index == null) {
                    index = 0;
                }
                System.out.println("circle clicked in column " + index);
                if(game.columnHasSpace(index)){
                    int row = game.addCircle(index, firstPlayerTurn) + 1;
                    addCircle(index, row);
                    
                    if (checkGameState(game.getState(), circles) && applicationState.getSecondPlayer() == null) {
                        index = (int) (Math.random() * 6);
                        while(!game.columnHasSpace(index)) {
                            index = (int) (Math.random() * 6);
                        }
                        row = game.addCircle(index, firstPlayerTurn) + 1;
                        System.out.println("circle clicked in column " + index);
                        addCircle(index, row);
                        checkGameState(game.getState(), circles);
                    }
                    
                } else {
                    System.out.println("column no space" + index);
                }
                
            });
        }
    }
    
    private boolean checkGameState(GameState gameState, Circle[] circles) {
        System.out.println("state: " + gameState.name());
        String message = "";
        switch(gameState){
            case PLAYING:
                switchTurns(circles);
                setCircleColors(circles);
                return true;
            case PLAYER_ONE_VICTORY:
                message = "¡" + applicationState.getFirstPlayer().getNickName() + " ha ganado!";
                break;
            case PLAYER_TWO_VICTORY:
                message = "¡" + (applicationState.getSecondPlayer() != null ? applicationState.getSecondPlayer().getNickName() : "máquina") + " ha ganado!";
                break;
            case DRAW:
                message = "¡Empate!";
                break;    
        
        }
        boolean startNewGame = showDialog(message);
        
        if(startNewGame) {
            restartGame(circles);
        } else {
            Navigation.navigateToHome((Stage) GPField.getScene().getWindow(), getClass());
        }
        
        return false;
    }
    
    private void restartGame(Circle[] circles) {
        game = new Game();
        for(Circle c : addedCircles) {
            c.setVisible(false);
        }
        if(!firstPlayerTurn) { switchTurns(circles); }
    }
    
    private void switchTurns(Circle[] circles) {
        firstPlayerTurn = !firstPlayerTurn;
        color = firstPlayerTurn ? Color.DODGERBLUE : Color.RED;
        setCircleColors(circles);
    }
    
    private void setCircleColors(Circle[] circles){
        for (Circle circle : circles) {
            circle.setFill(color);
        }
    }
    
    private void addCircle(int column, int row) {
        Circle c = new Circle();
        c.setRadius(35.);
        c.setFill(color);
        GPField.add(c, column, row);
        addedCircles.add(c);
    }
    
    private void initFirstPlayer() {
        Player firstPlayer = applicationState.getFirstPlayer();
        IVFirstPlayerAvatar.setImage(firstPlayer.getAvatar());
        TFirstPlayerName.setText(firstPlayer.getNickName());
    }
    
    private void initSecondPlayer() {
        
        Player secondPlayer = applicationState.getSecondPlayer();
        if(secondPlayer != null) {
            IVSecondPlayerAvatar.setVisible(true);
            IVSecondPlayerAvatar.setImage(secondPlayer.getAvatar());
            TSecondPlayerName.setText(secondPlayer.getNickName());
        } else {
            IVSecondPlayerAvatar.setVisible(false);
            TSecondPlayerName.setText("Máquina");
        }
        
    }

    private boolean showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        
        dialog.setTitle("Fin de partida");
        dialog.setHeaderText(message);
        dialog.setContentText("¿Quieres jugar otra partida?");
        
        ButtonType goHomeButton = new ButtonType("Volver a menú");
        ButtonType newGameButton = new ButtonType("Jugar de nuevo");
        dialog.getButtonTypes().setAll(goHomeButton, newGameButton);
        
        Optional<ButtonType> result = dialog.showAndWait();
        
        if(result.isPresent()) {
            if(result.get() == goHomeButton) {
                return false;
            } else {
                return true;
            }
        } else {
          return false;  
        }
    }
    
    
    
}
