/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.scene.control.ListCell;
import model.Round;

/**
 *
 * @author 44puk
 */
public class RoundListCell extends ListCell<Round> {
              
        @Override
        protected void updateItem(Round item, boolean empty) {
            super.updateItem(item, empty);
            
            if (item == null || empty) {
                setText(null);
            } else {
                setText(getText(item));
            }
            
        }
        
        private String getText(Round round) {
            return "Ganador: " + round.getWinner().getNickName() + 
                    "    Perdedor: " + round.getLoser().getNickName() + 
                    "    Fecha: " + formatDate(round.getTimeStamp());
        }
        
        private String formatDate(LocalDateTime localDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
            return localDateTime.format(formatter);
        }
            
        
    }
