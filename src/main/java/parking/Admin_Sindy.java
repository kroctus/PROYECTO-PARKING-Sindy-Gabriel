/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import plazas.PlazaDAO;
import plazas.PlazaVO;

/**
 *
 * @author sindy
 */
public class Admin_Sindy {

    public static void mostarEstadoPlazas() throws SQLException {
        PlazaDAO plazaDao = new PlazaDAO();
        ArrayList<PlazaVO> listaPlazas = new ArrayList<>();
        listaPlazas = (ArrayList<PlazaVO>) plazaDao.getAll();
        String plazas = "";
        int contador = 0;
        for (PlazaVO listaPlaza : listaPlazas) {
            if (contador % 2 == 0) {
                plazas = plazas + " " + listaPlaza.getNumPlaza() + ":" + PlazaVO.obtenerEstado(listaPlaza) + "\n";
            } else {
                plazas = plazas + " " + listaPlaza.getNumPlaza() + ":" + PlazaVO.obtenerEstado(listaPlaza);
            }
            contador++;
        }

        JOptionPane.showMessageDialog(null, "Estados de las plazas:\n" + plazas);
    }

}
