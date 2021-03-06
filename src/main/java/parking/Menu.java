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

/**
 *
 * @author sindy
 */
public class Menu {

    public static void mostrarMenu() throws SQLException, ParseException, FileNotFoundException {
        int opcion1;//Seleccionar zona
        int opcion2;//¿Es abonado?
        int opcion3;//Abonado: depositar y retirar 
        int opcion4;//No abonado: depositar y retirar
        int opcion5;//Elegir qué gestionar
        int opcion6;//Facturacion
        int opcion7;//Abonos
        int opcion8;//Caducidad
        int opcion9;//Copias de seguridad
        String mes;

        opcion1 = JOptionPane.showOptionDialog(null, "Selecciona una zona",
                "Menú", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                    "Zona cliente", "Zona admin"},
                " ");
        if (opcion1 != 1) {
            opcion2 = JOptionPane.showOptionDialog(null, "¿Eres abonado?",
                    "Menú", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                        "Si", "No"},
                    " ");
            if (opcion2 != 1) {
                opcion3 = JOptionPane.showOptionDialog(null, "¿Qué quieres hacer?",
                        "Menú", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                            "Depositar vehiculo", "Retirar vehiculo"},
                        " ");
                if (opcion3 != 1) {
                    GestionVehiculosAbonados.depositarVehiculoAbonado();
                } else {
                    GestionVehiculosAbonados.retirarAbonados();
                }
            } else {
                opcion4 = JOptionPane.showOptionDialog(null, "¿Qué quieres hacer?",
                        "Menú", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                            "Depositar vehiculo", "Retirar vehiculo"},
                        " ");
                if (opcion4 != 1) {
                    Admin_Sindy.mostarEstadoPlazas();
                    GestionVehiculos.depositarVehiculo();
                } else {
                    GestionVehiculos.retirarVehiculo();
                }
            }
        } else {
            opcion5 = JOptionPane.showOptionDialog(null, "¿Qué quieres gestionar?",
                    "Menú ", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                        "Estado del parking", "Facturación", "Abonos", "Copias de seguridad"},
                    " ");
            switch (opcion5) {
                case 0:
                    Admin_Sindy.mostarEstadoPlazas();
                    break;
                case 1:
                    opcion6 = JOptionPane.showOptionDialog(null, "¿Qué quieres hacer?",
                            "Facturación", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                                "Consultar facturas de clientes abonados", "Consultar facturas de clientes no abonados"},
                            "");
                    if (opcion6 != 1) {
                        Admin_Sindy.mostrarAbonadosAnuales();
                    } else {
                        Admin_Sindy.mostrarFacturacionNoAbonado();
                    }
                    break;
                case 2:
                    opcion7 = JOptionPane.showOptionDialog(null, "¿Qué quieres hacer?",
                            "Abonos ", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                                "Dar de alta", "Modificar abonos", "Dar de baja", "Consultar caducidad"},
                            " ");
                    switch (opcion7) {
                        case 0:
                            Admin_Gab.alta();
                            break;
                        case 1:
                            Admin_Gab.modificarAbonado();
                            break;
                        case 2:
                            Admin_Sindy.bajaAbono();
                            break;
                        case 3:
                            opcion8 = JOptionPane.showOptionDialog(null, "¿Qué quieres consultar?",
                                    "Abonos ", JOptionPane.YES_NO_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                                        "Abonos que caducan en un mes concreto", "Abonos que caducan en los siguientes 10 días"},
                                    " ");
                            switch (opcion8) {
                                case 0:
                                    do {
                                        mes = JOptionPane.showInputDialog("Indica el "
                                                + "numero del mes para obtener un listado\n"
                                                + " de los abonos que caducan en ese mes");
                                    } while (Integer.valueOf(mes) > 12 || Integer.valueOf(mes) < 0);
                                    Admin_Sindy.abonosCaducadosMes(Integer.valueOf(mes));
                                    break;
                                case 1:
                                    Admin_Sindy.abonosCaducados10dias();
                                    break;
                            }
                            break;
                    }
                    break;
                case 3:
                    opcion9 = JOptionPane.showOptionDialog(null, "¿Qué quieres hacer?",
                            "Copias de seguridad", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                                "Crear", "Restaurar"},
                            " ");
                    if (opcion9 != 1) {
                        CopiaDeSeguridad.crearCopiaSeguridad();
                    } else {
                        Admin_Sindy.recuperarCopiaSeguridad();
                    }
                    break;
            }

        }

    }
}
