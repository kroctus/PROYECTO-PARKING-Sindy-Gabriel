/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import java.time.LocalDate;
import javax.swing.JOptionPane;
import static parking.GestionVehiculosAbonados.esNumero;

/**
 *
 * @author gabriel
 */
public class Admin_Gab {

    //Método que pide datos personales al usuario y en base a estos le da de alta como cliente abonado
    //Actualizando la tabla de reservas
    public void alta() {

        JOptionPane.showMessageDialog(null, "Bienvenido a la zona de alta");
        //Pedimos la matricula
        String matricula;
        String[] matricula1;
        do {
            matricula = JOptionPane.showInputDialog("Introduzca su matricula: ");

            while (matricula.length() != 8 || matricula.charAt(4) != '-') {
                matricula = JOptionPane.showInputDialog("La matricula es "
                        + " incorrecta, vuelva a intentarlo: ");
            }
            matricula1 = matricula.split("-");
            System.out.println("Tamaño: " + matricula.length());
        } while (!((GestionVehiculosAbonados.esNumero(matricula1[0]) && !GestionVehiculosAbonados.esNumero(matricula1[1])) && matricula.length() == 8));

        //DNI
        //Pedimo su DNI
        String dni = JOptionPane.showInputDialog("Introduzca el DNI: ");
        System.out.println("DNI: " + dni);
        //Mientras El DNI no tenga el tamaño correcto se volverá a pedir

        String[] arrayDni = new String[9];

        while (esNumero(arrayDni[0])) {

            JOptionPane.showMessageDialog(null, "El DNI introducido no es correcto o esta mal formateado,\n  porfavor introduzca de nuevo");
            dni = JOptionPane.showInputDialog("Introduce nuevamente el DNI: ");
        }

        //Nombre
        String nombre = JOptionPane.showInputDialog("Introduzca el nombre: ");

        //Apellido1 
        String apellido1 = JOptionPane.showInputDialog("Introduzca el primer Apellido: ");

        //Apellido 2
        String apellido2 = JOptionPane.showInputDialog("Introduzca el Segundo apellido: ");

        //Tarjeta de credito
        String tarjeta = JOptionPane.showInputDialog("Numero de tarjeta de credito: ");
        while (tarjeta.length() < 16) {
            tarjeta = JOptionPane.showInputDialog("El numero introducido es incorrecto o esta mal formateado \n por favor introduzcalo de nuevo:");
        }

        //Email
        String email = JOptionPane.showInputDialog("Introduzca el email");
        
        //Tipo de abono
        
        int abono = JOptionPane.showOptionDialog(null, "Selecciona el tipo de abono que deseas tener: ",
                "Abono", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                    "Mensual(25€)", "Trimestral(70€)", "Semestral(130€)", "Anual(200€)"},
                "Turismo");
        
        String tipoAbono;
        final LocalDate feciniAbono=LocalDate.now();
        LocalDate fecFinAbono;
         switch (abono) {
            case 1:
                tipoAbono="Mensual";
                fecFinAbono=feciniAbono.plusMonths(1);
                break;
            case 2:
                tipoAbono="Trimestral";
                fecFinAbono=feciniAbono.plusMonths(3);
                break;
            case 3:
                tipoAbono="Semestral";
                fecFinAbono=feciniAbono.plusMonths(6);
                break;
            case 4:
                tipoAbono="Anual";
                fecFinAbono=feciniAbono.plusYears(1);
                break;
        }
        
    }

}
