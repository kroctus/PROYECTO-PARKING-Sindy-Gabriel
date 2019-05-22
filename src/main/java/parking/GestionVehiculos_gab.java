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
import vehiculos.VehiculoVO;

/**
 *
 * @author gabriel
 */
public class GestionVehiculos_gab {

    //Metodo ingresar Vehiculo abonado
    //Este método introducirá el vehiculo del abonado solicitando los datos pertinentes
    /*Matricula del vehiculo y dni del cliente cuyo vehiculo no puede repetirse por cliente*/
    public static ClienteAbonado IngresarVehiculoAbonado() {

        ClienteAbonado cliente = new ClienteAbonado();
        JOptionPane.showMessageDialog(null, "Bienvenido Abonado A continuación te pediremos algunos datos :D");

        String matricula;
        String[] matricula1;
        do {
            matricula = JOptionPane.showInputDialog("Introduzca su matricula: ");
            matricula1 = matricula.split("-");
            System.out.println("Tamaño: " + matricula.length());
        } while (!((esNumero(matricula1[0]) && !esNumero(matricula1[1])) && matricula.length() == 8));

        //Pedimos el Tipo De vehiculo
        JOptionPane.showMessageDialog(null, "A continuación Introduce tu tipo de coche");
        JOptionPane.showMessageDialog(null, "Los valores aceptados son: "
                + "\n Motocicleta \n Turismo \n Caravana");
        String tipoVehiculo = JOptionPane.showInputDialog("Introduce tu tipo de vehiculo: ");

        while (!(tipoVehiculo.equalsIgnoreCase("Motocicleta") || tipoVehiculo.equalsIgnoreCase("Turismo")
                || tipoVehiculo.equalsIgnoreCase("Caravana"))) {

            JOptionPane.showMessageDialog(null, "El tipo introducido " + "*" + tipoVehiculo + "*" + " no es correcto o esta mal formateado, \n por favor introduzcalo nuevamente");
            JOptionPane.showMessageDialog(null, "Los valores aceptados son: "
                    + "\n Motocicleta \n Turismo \n Caravana");
            tipoVehiculo = JOptionPane.showInputDialog("Introduce tu tipo de vehiculo: ");

        }

        //Pedimo su DNI
        String dni = JOptionPane.showInputDialog("Introduzca su DNI: ");
        System.out.println("DNI: " + dni);
        //Mientras El DNI no tenga el tamaño correcto se volverá a pedir

        String[] arrayDni = new String[9];

        while (esNumero(arrayDni[0])) {

            JOptionPane.showMessageDialog(null, "El DNI introducido no es correcto o esta mal formateado,\n  porfavor introduzca de nuevo");
            dni = JOptionPane.showInputDialog("Introduce nuevamente el DNI: ");
        }

        //Confirmación de los campos
        int opcion = JOptionPane.showConfirmDialog(null, "Estos son los datos que has introducido: " + " \n DNI: " + dni + "\n Matricula: " + matricula + "\n Tipo de vehiculo: " + tipoVehiculo);
        // En caso de que los campos no sean los que desea el cliente se volveran a pedir.
        if (opcion == 0) {
            System.out.println("La opcion es yes");
            cliente = new ClienteAbonado(dni, tipoVehiculo, matricula);
            return cliente;

        } else if (opcion == 1) {
            JOptionPane.showConfirmDialog(null, "Se volveran a pedir los datos :D");
            System.out.println("La opcion deberia ser no");
            GestionVehiculos_gab.IngresarVehiculoAbonado();
        }

        return cliente;
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

    //Método ESTADO_PLAZA
    // Este método se encargará de ver si las plazas están o no ocupadas y el tipo de coche que la ocupa
    /*Para ello este método se apoya de un array de 45 posiciones siendo estas: */
    // Del 0-14 Motocicletas
    // El 14-29 Turismos.
    // del 29-44 para Caravanas
    /*Las plazas de los reservados siempre estan ocupadas (false)*/
    public void estadoPlazas(ClienteAbonado cliente) {

        Integer[] plazasEstado = new Integer[45];
        //Conseguimos las plazas de nuestro parking

        PlazaDAO plazas = new PlazaDAO();
        ArrayList<PlazaVO> listaPlazas = new ArrayList<>();

        try {
            listaPlazas = (ArrayList<PlazaVO>) plazas.getAll();

            for (int i = 0; i < listaPlazas.size(); i++) {
                plazasEstado[i] = listaPlazas.get(i).getEstadoPlaza();
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        }

        //Miramos el estado y el tipo de vehiculo
        //Del 0-14 solo guardaremos las motos
        if (cliente.getTipoVehiculo().equalsIgnoreCase("motocicleta")) {

            for (int i = 0; i < 14; i++) {
                // Plaza libre
                if (plazasEstado[i] == 1) {
                    System.out.println("Hago el insert");
                }

                //Miramos si es un turismo
                if (cliente.getTipoVehiculo().equalsIgnoreCase("turismo")) {
                    //Los turismo se guardaran del 15 hasta el 29
                    for (int j = 15; j < 29; j++) {
                        //PlazaLibre
                        if (plazasEstado[i] == 1) {
                            System.out.println("Hago el insert");
                        }
                    }

                }

                //Miramos si es un caravana
                if (cliente.getTipoVehiculo().equalsIgnoreCase("Caravana")) {
                    //Las carvaanas se guardaran del 16 al 44
                    for (int j = 16; j < 44; j++) {
                        //PlazaLibre
                        if (plazasEstado[i] == 1) {
                            System.out.println("Hago el insert");
                        }
                    }

                }
            }
        }

    }

    public static void main(String[] args) {

        GestionVehiculos_gab.IngresarVehiculoAbonado();

    }

}