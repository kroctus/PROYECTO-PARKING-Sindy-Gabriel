/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import clientes.ClienteDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import plazas.PlazaDAO;
import plazas.PlazaVO;
import vehiculos.VehiculoVO;
import clientes.ClienteVO;
import java.time.LocalDate;
import java.time.Month;
import reservas.ReservasDAO;
import java.util.Random;
import reservas.ReservasVO;

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
        JOptionPane.showMessageDialog(null, "¡Bienvenido Abonado! a continuación te pediremos algunos datos :D");

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
            //     System.out.println("La opcion es yes");
            cliente = new ClienteAbonado(dni, tipoVehiculo, matricula);
            return cliente;

        } else if (opcion == 1) {
            JOptionPane.showConfirmDialog(null, "Se volveran a pedir los datos :D");
            //     System.out.println("La opcion deberia ser no");
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

    //Método GESTION_PLAZAS
    // Este método se encargará de ver si las plazas están o no ocupadas y el tipo de coche que la ocupa
    /*Para ello este método se apoya de un array de 45 posiciones siendo estas: */
    // Del 0-14 Motocicletas
    // El 14-29 Turismos.
    // del 29-44 para Caravanas
    /*Las plazas de los reservados siempre estan ocupadas (false)*/
    public static void gestionPlazas(ClienteAbonado cliente) {
        System.out.println("");
        System.out.println("Estoy en gestión de plazas");
        Integer[] plazasEstado = new Integer[46];
        //Conseguimos las plazas de nuestro parking
//        System.out.println("");
//        System.out.println("Atributos de cliente: " + "\n matricula : " + cliente.getMatricula()
//                + "\n tipoVehiculo: " + cliente.getTipoVehiculo() + " \n DNI " + cliente.getDni());
        PlazaDAO plazas = new PlazaDAO();
        ReservasDAO reservaDAO = new ReservasDAO();
        ArrayList<PlazaVO> listaPlazas = new ArrayList<>();
        int numPlaza;
        try {
            listaPlazas = (ArrayList<PlazaVO>) plazas.getAll();

            System.out.println("Mostramos las plazas: ");
            System.out.println("");
            listaPlazas.forEach(System.out::println);

            for (int i = 0; i < listaPlazas.size(); i++) {
                int estado=listaPlazas.get(i).getEstadoPlaza();
//                System.out.println("El estado en lista1 es : " + estado );
                plazasEstado[i] = estado;
                System.out.println("Estado Plaza: " + i + " : " + plazasEstado[i]);
            }

            //Miramos el estado y el tipo de vehiculo
            //Del 0-14 solo guardaremos las motos

            if (cliente.getTipoVehiculo().equalsIgnoreCase("motocicleta")) {

                System.out.println("Estoy en  motocicleta");
                for (int i = 0; i < 14; i++) {
                    // Plaza libre

                    if (plazasEstado[i] == 1) {
                        numPlaza = plazasEstado[i];
                        System.out.println("Estado Plaza: " + i + " : " + plazasEstado[i]);
                        System.out.println("El numero de la plaza será el : " + (numPlaza + 100));
                        ReservasVO persona = new ReservasVO(cliente.getMatricula(), (numPlaza + 100), generarPin(), LocalDate.now(), LocalDate.of(1, 1, 1));
                        reservaDAO.insertReserva(persona);
                        System.out.println("Hago el insert");
                    }

                }
            }

            //Miramos si es un turismo
            if (cliente.getTipoVehiculo().equalsIgnoreCase("turismo")) {
                System.out.println("Estoy en turismo");
                //Los turismo se guardaran del 15 hasta el 29
                for (int j = 15; j < 29; j++) {
                    //PlazaLibre
                    System.out.println("El estado de la plaza " + j + "es:" + plazasEstado[j]);
                    if (plazasEstado[j] == 1) {
                        numPlaza = plazasEstado[j];
                        System.out.println("El numero de la plaza será el : " + (numPlaza + 100));
                        ReservasVO persona = new ReservasVO(cliente.getMatricula(), (numPlaza + 100), generarPin(), LocalDate.now(), LocalDate.of(1, 1, 1));
                        reservaDAO.insertReserva(persona);
                        System.out.println("Hago el insert");
                    }
                }

            }

            //Miramos si es un caravana
            if (cliente.getTipoVehiculo().equalsIgnoreCase("Caravana")) {
                System.out.println("Estoy rn cara");
                //Las carvaanas se guardaran del 16 al 44
                for (int j = 16; j < 45; j++) {
                    //PlazaLibre
                    System.out.println("El estado de la plaza " + j + "es:" + plazasEstado[j]);
                    if (plazasEstado[j] == 1) {
                        numPlaza = plazasEstado[j];
                        System.out.println("El numero de la plaza será el : " + (numPlaza + 100));
                        ReservasVO persona = new ReservasVO(cliente.getMatricula(), (numPlaza + 100), generarPin(), LocalDate.now(), LocalDate.of(1, 1, 1));
                        reservaDAO.insertReserva(persona);
                        System.out.println("Hago el insert");
                    }
                }

            }

        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        }

    }

    //Método para comprobar si el cliente que se recibe es abonado o no para ello compara las matriculas
    // si son iguales es reservado entonces devuelve true, sino false.
    public static boolean esAbonado(ClienteAbonado clienteAux) {
        ClienteDAO cliente = new ClienteDAO();
        ArrayList<ClienteVO> clientesAbonados = new ArrayList<>();

        try {
            clientesAbonados = (ArrayList<ClienteVO>) cliente.getAll();

            for (ClienteVO clientesAbonado : clientesAbonados) {
                if (clientesAbonado.getMatricula().equalsIgnoreCase(clienteAux.getMatricula())) {
                    return true;
                } else if (!(clientesAbonado.getMatricula().equalsIgnoreCase(clienteAux.getMatricula()))) {
                    return false;
                }
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        }
        return false;
    }

    //Método que se encarga de generar un pin aleatorio para las reservas
    public static String generarPin() {
        int num = 0;
        int[] pin = new int[6];
        String pinS = "";
        Random aleo = new Random();
        for (int i = 0; i < 6; i++) {
            num = aleo.nextInt(9);
            pin[i] = num;
            pinS += String.valueOf(pin[i]);
        }

        System.out.println("El pin final es: " + pinS);
        return pinS;
    }

    public static void main(String[] args) {

        ClienteAbonado aux = GestionVehiculos_gab.IngresarVehiculoAbonado();
        generarPin();
        gestionPlazas(aux);

    }

}
