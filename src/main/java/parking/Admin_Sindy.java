/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import plazas.PlazaDAO;
import plazas.PlazaVO;
import reservas.ReservasDAO;
import reservas.ReservasVO;

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

    public static void abonosCaducadosMes(int mes) throws SQLException {
        String reserva = "Matricula : Plaza :    Pin      :  Fecha inicio :   Fecha fin \n";
        ReservasDAO reservasDao = new ReservasDAO();
        //Lista de todas las reservas que hay en la bbdd
        ArrayList<ReservasVO> listaReservas = new ArrayList<>();
        //Lista que almacena las reservas que van a caducar en el mes indicado
        ArrayList<ReservasVO> reservasCaducidadP = new ArrayList<>();
        listaReservas = (ArrayList<ReservasVO>) reservasDao.getAll();
        //Recorremos la lista de las reservas que hay en la base de datos
        for (ReservasVO listaReserva : listaReservas) {
            //Si el mes de la fecha de fin de abono coincide con la fecha que indica el usuario y
            //si el año en el que hacemos la consulta coincide con el año del fin de abono entonces añadimos 
            //Esa reserva a la lsita que mostraremos por pantalla
            if (listaReserva.getFecfinabono().getMonthValue() == mes
                    && listaReserva.getFecfinabono().getYear() == LocalDate.now().getYear()) {
                reservasCaducidadP.add(listaReserva);
            }
        }

        if (reservasCaducidadP.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se ha encontrado ninguna reserva que caduque en el mes " + mes);
        } else {
            for (ReservasVO reservasVO : reservasCaducidadP) {

                reserva = reserva + reservasVO.getMatricula()
                        + "  :  " + reservasVO.getNumplaza() + "  :  "
                        + reservasVO.getPin_fijo() + "  :  "
                        + reservasVO.getFeciniabono() + "  :  "
                        + reservasVO.getFecfinabono() + "\n";

            }
            JOptionPane.showMessageDialog(null, "Reservas que caducan en el mes de " + mes + " \n" + reserva);

        }

    }

}
