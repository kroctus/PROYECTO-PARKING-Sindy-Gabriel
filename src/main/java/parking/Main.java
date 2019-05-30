/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;
import static parking.Menu.mostrarMenu;

/**
 *
 * @author sindy
 */
public class Main {
    public static void main(String[] args) throws SQLException, ParseException, FileNotFoundException {
        try {
            int respuesta;
            do {
                mostrarMenu();
                respuesta = JOptionPane.showOptionDialog(null, "¿Quieres salir del menú?",
                "Menú", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                    "Si", "No"},
                " ");
            } while (respuesta == 1);

        } catch (SQLException | ParseException | FileNotFoundException sql) {
            System.out.println("Ha ocurrido un error");
            System.out.println(sql.getMessage());
        }
    }
}
