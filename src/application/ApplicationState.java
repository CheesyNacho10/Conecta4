/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import model.Player;

/**
 *
 * @author 44puk
 */
public class ApplicationState {
    
    private ApplicationState(){};
    
    private static ApplicationState INSTANCE;
    
    private Player firstPlayer;
    
    private Player secondPlayer;
    
    private String forgotPassNickName;
    
    public static synchronized ApplicationState getInstance() {
        // create singleton object
        if(INSTANCE == null) {
            INSTANCE = new ApplicationState();
        }
        return INSTANCE;
    }
    
    public boolean logInPlayer(Player p){
        if(firstPlayer == null) {
            firstPlayer = p;
        } else if(firstPlayer.getNickName().equals(p.getNickName())) { // check that we don't log in the same player twice
            return false;
        }
        else {
            secondPlayer = p;
        }
        return true;
        
    }
    
    public Player getFirstPlayer(){
        return firstPlayer;
    }
    
    public Player getSecondPlayer(){
        return secondPlayer;
    }

    public String getForgotPassNickName() {
        return forgotPassNickName;
    }

    public void setForgotPassNickName(String forgotPassNickName) {
        this.forgotPassNickName = forgotPassNickName;
    }
    
    /**
     * 
     * @return false if there is no second player, true if second player is authorized
     */
    public boolean logOutFirstPlayer() {
        if(secondPlayer == null) {
            firstPlayer = null;
            return false;
        } else {
            firstPlayer = secondPlayer;
            secondPlayer = null;
            return true;
        }
    }
    
    public void logOutSecondPlayer() {
        secondPlayer = null;
    }
    
 
}
