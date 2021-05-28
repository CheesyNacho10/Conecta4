/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package credentials;

import java.time.LocalDate;
import model.Player;

/**
 * Singleton class for save logins and manage logouts from the players
 * @author 44puk
 */
public class ApplicationState {
    
    /**
     * Private constructor
     */
    private ApplicationState(){};
    
    /**
     * Saves the state of the players logs
     */
    private static ApplicationState INSTANCE;
    
    /**
     * Saves players info
     */
    private Player firstPlayer;
    private Player secondPlayer;
    
    private String forgotPassNickName;
    
    private boolean blackStyle;
    
    private LocalDate startDate;
    private LocalDate endDate;
         
    private String playerToShowHistory;
    
    /**
     * Makes (if necessary) a new instance
     * @return New or existent ApllicationState
     */
    public static synchronized ApplicationState getInstance() {
        // Create singleton object if isn't created already
        if(INSTANCE == null) {
            INSTANCE = new ApplicationState();
        }
        return INSTANCE;
    }
    
    /**
     * Logs a player into de the ApplicationState as firts if there is no one
     * and as a second in other case
     * @param p Player to be loged in
     * @return True if everything went okay or false if the player is already logged in
     */
    public boolean logInPlayer(Player p){
        if(firstPlayer == null) {
            firstPlayer = p;
        } else if(firstPlayer.getNickName().equals(p.getNickName())) { // Check that we don't log in the same player twice
            return false;
        }
        else {
            secondPlayer = p;
        }
        return true;
        
    }
    
    /**
     * Returns the logged firts player
     * @return Firts player
     */
    public Player getFirstPlayer(){
        return firstPlayer;
    }
    
    /**
     * Returns the logged second player
     * @return Second player
     */
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
     * Logs out the firts player
     * @return False if there is no second player, true if second player is authorized
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
    
    /**
     * Logs out the second player
     */
    public void logOutSecondPlayer() {
        secondPlayer = null;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPlayerToShowHistory() {
        return playerToShowHistory;
    }

    public void setPlayerToShowHistory(String playerToShowHistory) {
        this.playerToShowHistory = playerToShowHistory;
    }
    
    
    public boolean getStyle() {
        return blackStyle;
    }
    
    public void setStyle(boolean s) {
        blackStyle = s;
    }
}
