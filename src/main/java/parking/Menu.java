/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import javax.swing.JOptionPane;

/**
 *
 * @author sindy
 */
public class Menu {
    
    public static void mostrarMenu() {
        int opcion1;//Seleccionar zona
        int opcion2;//¿Es abonado?
        int opcion3;//Abonado: depositar y retirar 
        int opcion4;//No abonado: depositar y retirar
        int opcion5;//Elegir qué gestionar
        int opcion6;//Facturacion
        int opcion7;//Abonos
        int opcion8;//Copias de seguridad

        opcion1 = JOptionPane.showOptionDialog(null, "Selecciona una zona",
                "Menú", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                    "Zona cliente", "Zona admin"},
                "Zona cliente");
        if (opcion1 != 1) {
            System.out.println("Zona cliente");
            opcion2 = JOptionPane.showOptionDialog(null, "¿Eres abonado?",
                    "Menú", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                        "Si", "No"},
                    "Si");
            if (opcion2 != 1) {
                System.out.println("Es abonado");
                opcion3 = JOptionPane.showOptionDialog(null, "¿Qué quieres hacer?",
                        "Menú", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                            "Depositar vehiculo", "Retirar vehiculo"},
                        "Si");
                if (opcion3 != 1) {
                    System.out.println("Depositar vehiculo");
                } else {
                    System.out.println("Retirar vehiculo");
                }
            } else {
                System.out.println("NO es abonado");
                opcion4 = JOptionPane.showOptionDialog(null, "¿Qué quieres hacer?",
                        "Menú", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                            "Depositar vehiculo", "Retirar vehiculo"},
                        "Si");
                if (opcion4 != 1) {
                    System.out.println("Depositar vehiculo");
                } else {
                    System.out.println("Retirar vehiculo");
                }
            }
        } else {
            System.out.println("Zona admin");
            opcion5 = JOptionPane.showOptionDialog(null, "¿Qué quieres gestionar?",
                    "Menú ", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                        "Estado del parking", "Facturación", "Abonos", "Copias de seguridad"},
                    "Estado del parking");
            switch (opcion5) {
                case 0:
                    System.out.println("Estado del parking");
                    break;
                case 1:
                    System.out.println("Facturación");
                    opcion6 = JOptionPane.showOptionDialog(null, "¿Qué quieres hacer?",
                            "Facturación", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                                "Consultar facturas de clientes abonados", "Consultar facturas de clientes no abonados"},
                            "");
                    if (opcion6 != 1) {
                        System.out.println("Consultar facturas de clientes abonados");
                    } else {
                        System.out.println("Consultar facturas de clientes no abonados");
                    }
                    break;
                case 2:
                    System.out.println("Abonos");
                    opcion7 = JOptionPane.showOptionDialog(null, "¿Qué quieres hacer?",
                            "Abonos ", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                                "Dar de alta", "Modificar abonos", "Dar de baja", "Consultar caducidad"},
                            "Dar de alta");
                    switch (opcion7) {
                        case 0:
                            System.out.println("Dar de alta");
                            break;
                        case 1:
                            System.out.println("Modificar abonos");
                            break;
                        case 2:
                            System.out.println("Dar de baja");
                            break;
                        case 3:
                            System.out.println("Consultar caducidad");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Copias de seguridad");
                    opcion8 = JOptionPane.showOptionDialog(null, "¿Qué quieres hacer?",
                            "Copias de seguridad", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                                "Crear", "Restaurar"},
                            "Crear");
                    if (opcion8 != 1) {
                        System.out.println("Crear copia de seguridad");
                    } else {
                        System.out.println("Restaurar copia de seguridad");
                    }
                    break;
            }

        }

    }
}