/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.Connect4DAOException;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Player;
import nav.Navigation;

/**
 * FXML Controller class
 *
 * @author Nacho
 */
public class FXMLSignUpController extends FXMLBaseController {

    @FXML
    private TextField TFUser;
    @FXML
    private TextField TFEmail;
    @FXML
    private Text TEEmail;
    @FXML
    private PasswordField PFPass;
    @FXML
    private Text TEPass;
    @FXML
    private DatePicker DPBirthDate;
    @FXML
    private Text TEBirthDate;
    @FXML
    private Button BSignUp;
    @FXML
    private ImageView IVAvatar;
    @FXML
    private Button BChooseAvatar;    
    @FXML
    private Text TEUser;
    @FXML
    private Text TError;

    private String nick;
    private String email;
    private String pass;
    private Image img;
    private LocalDate birth;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void init() {
        // TODO
        
    }    

    @FXML
    private void signUp(ActionEvent event) throws Connect4DAOException {
        refreshData();
        
        if(checkCredentials(nick, email, pass, birth)) {
            TError.setVisible(false);
            db.registerPlayer(nick, email, pass, img, birth, 0);
            
            Alert output = new Alert(AlertType.INFORMATION);
            output.setTitle("Registro");
            output.setHeaderText("¡Bienvenido/a " + nick + " !");
            output.setContentText("Registro realizado con éxito.");
            output.getButtonTypes().setAll(new ButtonType("¡Perfecto!", ButtonBar.ButtonData.OK_DONE));
            
            DialogPane dialogP = output.getDialogPane();
            dialogP.getStylesheets().add(getClass().getResource("/view/style.css").toExternalForm());
            
            output.showAndWait();
            
            Navigation.navigateToSignIn((Stage) BSignUp.getScene().getWindow(), getClass());
       }
        else {
            TError.setVisible(true);
            
            Alert output = new Alert(AlertType.ERROR);
            output.setTitle("Registro");
            output.setHeaderText("Error");
            output.setContentText("Uno o más campos incorrectos.");
            output.getButtonTypes().setAll(new ButtonType("Volver", ButtonBar.ButtonData.OK_DONE));
            
            DialogPane dialogP = output.getDialogPane();
            dialogP.getStylesheets().add(getClass().getResource("/view/style.css").toExternalForm());
            
            output.showAndWait();
        }
    }

    @FXML
    private void chooseAvatar(ActionEvent event) {
        // TBI
    }
    
//    Was already a method that does this
//    Crying
//    public boolean checkPass(String s) {
//        return s.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&()-+=])(?=\\S+$).{8,21}$");
//    }

    private boolean checkCredentials(String n, String e, String p, LocalDate b) {
        boolean res = true;
        
        if (Player.checkNickName(n)) TEUser.setVisible(false);
        else { TEUser.setVisible(true); res = false; }
        
        if (Player.checkEmail(e)) TEEmail.setVisible(false);
        else { TEEmail.setVisible(true); res = false; }
        
        if (Player.checkPassword(p)) TEPass.setVisible(false);
        else { TEPass.setVisible(true); res = false; }
        
        if (checkAge(b)) TEBirthDate.setVisible(false);
        else { TEBirthDate.setVisible(true); res = false; }
        
        return res;
    }
    
    public boolean checkAge(LocalDate dP) {
        return dP.isBefore(LocalDate.now().minus((long) 12, ChronoUnit.YEARS));
    }

    private void refreshData() {
        nick = TFUser.getText();
        email = TFEmail.getText();
        pass = PFPass.getText();
        img = IVAvatar.getImage();
        birth = DPBirthDate.getValue();
    }
}
