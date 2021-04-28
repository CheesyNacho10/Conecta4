/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.Connect4DAOException;
import credentials.ApplicationState;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import model.Connect4;

/**
 *
 * @author 44puk
 */
public abstract class FXMLBaseController implements Initializable {
    
    protected Connect4 db;
    protected ApplicationState applicationState;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDB();
        initAppState();
        init();
    }
    
    private void initDB() {
        try {
            db = Connect4.getSingletonConnect4();
        } catch (Connect4DAOException err) {
            System.out.println(err);
        }
    }
    
    private void initAppState() {
        applicationState = ApplicationState.getInstance();
    }
    
    abstract void init();
    
}
