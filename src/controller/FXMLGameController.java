/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Paint;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
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
    
    private boolean firstPlayerTurn = true;
    private Color color = firstPlayerTurn ? Color.DODGERBLUE : Color.RED;
    
    

    @Override
    void init() {
        initFirstPlayer();
        initCircleListeners();
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
                addCircle(index, 7);
                switchTurns();
                setCircleColors(circles);
            });
        }
    }
    
    private void switchTurns() {
        firstPlayerTurn = !firstPlayerTurn;
        color = firstPlayerTurn ? Color.DODGERBLUE : Color.RED;
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
