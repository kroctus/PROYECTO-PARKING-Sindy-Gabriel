/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import plazas.PlazaDAO;
import plazas.PlazaVO;
import vehiculos.VehiculoVO;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Sindy Ferreira
 */
public class GestionVehiculo_Sindy {

    //Método que solicita al usuario una matricula y un tipo de vehiculo.
    public static VehiculoVO crearVehiculo() {
        String matricula;
        String[] matricula1;
        do {
            matricula = JOptionPane.showInputDialog("Introduzca su matricula: ");
            matricula1 = matricula.split("-");
            System.out.println("Tamaño: " + matricula.length());
        } while (!((esNumero(matricula1[0]) && !esNumero(matricula1[1])) && matricula.length() == 8));

        int tipoVehiculo = JOptionPane.showOptionDialog(null, "Seleccione su tipo de vehiculo",
                "Menú", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                    "Turismo", "Motocicleta", "Caravana"},
                "Turismo");
        return new VehiculoVO(matricula, (tipoVehiculo + 1));

    }

    //Método que comprueba si un texto es numerico o no,
    //en caso afirmativo devuelve true
    private static boolean esNumero(String tmp) {
        try {
            Integer.parseInt(tmp);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
