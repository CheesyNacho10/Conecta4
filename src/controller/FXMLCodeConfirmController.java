/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.Navigation;

/**
 *
 * @author 44puk
 */
public class FXMLCodeConfirmController extends FXMLBaseController {
    
    @FXML
    private TextField TFCode;
    
    @FXML
    private Text TError;

    @Override
    public void init() {
    }
    
    @FXML
    private void validateCode(){
        if(TFCode.getText().equals("111")) {
             Navigation.navigateToResetPass((Stage) TFCode.getScene().getWindow(), getClass());
        } else {
            TError.setVisible(true);
        }
    }
    
}
