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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Player;

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
            circle.setOnMouseEntered(event -> {
                circle.setRadius(40.);
            });
            
            circle.setOnMouseExited(event -> {
                circle.setRadius(35.);
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
        String res = "";
        switch(gameState){
            case PLAYING:
                switchTurns(circles);
                setCircleColors(circles);
                return true;
            case PLAYER_ONE_VICTORY:
                res = "¡" + applicationState.getFirstPlayer() + " ha ganado!";
                break;
            case PLAYER_TWO_VICTORY:
                res = "¡" + applicationState.getSecondPlayer() + " ha ganado!";
                break;
            case DRAW:
                res = "¡Empate!";
                break;    
        
        }
        
        //endgame(s);
        restartGame(circles);
        return false;
    }
    
//    private void endGame(String s) {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FXMLFinalState.fxml"));
//        Parent root = null;
//        try {
//            root = (Parent) fxmlLoader.load();
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLGameController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Scene scene = new Scene(root);
//        //FXMLFinalStateController. ;
//        Stage stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setScene(new Scene(root));  
//        stage.show();
//    }
    
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
    
    
    
}
