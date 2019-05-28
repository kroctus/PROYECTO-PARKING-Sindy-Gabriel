/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import clientes.ClienteDAO;
import clientes.ClienteVO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import static parking.GestionVehiculosAbonados.esNumero;
import static parking.GestionVehiculosAbonados.generarTarifa;
import plazas.PlazaDAO;
import plazas.PlazaVO;
import reservas.ReservasDAO;
import reservas.ReservasVO;

/**
 *
 * @author gabriel
 */
public class Admin_Gab {

    //Método que pide datos personales al usuario y en base a estos le da de alta como cliente abonado
    //Actualizando la tabla de reservas
    public static void alta() {

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

        //TIPO_VEHICULO
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
        System.out.println("Tipo de abono: " + abono);

        int tipoAbono = 0;
        final LocalDate feciniAbono = LocalDate.now();
        LocalDate fecFinAbono = LocalDate.of(1, Month.MARCH, 1);
        switch (abono) {
            case 1:
                tipoAbono = 1;
                fecFinAbono = feciniAbono.plusMonths(1);
                break;
            case 2:
                tipoAbono = 2;
                fecFinAbono = feciniAbono.plusMonths(3);
                break;
            case 3:
                tipoAbono = 3;
                fecFinAbono = feciniAbono.plusMonths(6);
                break;
            case 4:
                tipoAbono = 4;
                fecFinAbono = feciniAbono.plusYears(1);
                break;
        }

        //Modificaciones en la BD
        System.out.println("Cliente dao");
        ClienteDAO clienteDAO = new ClienteDAO();
        System.out.println("Reservas DAO");
        ReservasDAO reservaDAO = new ReservasDAO();

        //Nos creamos un cliente con los datos introducidos
        System.out.println("Creamos el cliente");
        ClienteVO clienteVO = new ClienteVO(matricula, dni, nombre, apellido1, apellido2, tarjeta, tipoAbono, email);
        System.out.println("Se ha creado el cliente");

        ClienteAbonado clienteA = new ClienteAbonado(dni, tipoVehiculo, matricula);

        //Creamos una reserva con los datos recogidos
        System.out.println("Vamos a hcer el alta de las reservas");
        gestionReservasAlta(clienteA, feciniAbono, fecFinAbono);
        System.out.println("se ha hecho la reserva");

        //Agregamos ese cliente a la BD
        try {
            System.out.println("Insertamos el cliente");
            clienteDAO.insertCliente(clienteVO);
            System.out.println("Se ha incertado el cliente");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        }

    }

    //Método que hace la insersión en la BD de la nuev reserva del cliente el cual se ha dado de alta
    //Mediante los datos del cliente que recibe y la fecha de inicio y de fin del abono
    public static void gestionReservasAlta(ClienteAbonado cliente, LocalDate feciniAbono, LocalDate fecFinAbono) {
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

            //Comprobamos que el pin no sea igual
            ReservasDAO r = new ReservasDAO();
            ArrayList<ReservasVO> listaR = new ArrayList<>();
            listaR = (ArrayList<ReservasVO>) r.getAll();

            /*Para ello comparamos el pin a generar con cada uno de los pin de la tabla reservas
            y hasta que no se generé uno diferente no se asigna el pin*/
             
            cliente.setPin(GestionVehiculosAbonados.generarPin());

            for (int i = 0; i < listaR.size(); i++) {
               while (cliente.getPin().equals(listaR.get(i).getPin_fijo())) {
                    System.out.println("El pin es igual se cambiara");
                    cliente.setPin(GestionVehiculosAbonados.generarPin());
                }
            }

            listaPlazas = (ArrayList<PlazaVO>) plazas.getAll();

            System.out.println("Mostramos las plazas: ");
            System.out.println("");
            listaPlazas.forEach(System.out::println);

            for (int i = 0; i < listaPlazas.size(); i++) {
                int estado = listaPlazas.get(i).getEstadoPlaza();
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
                        ReservasVO persona = new ReservasVO(cliente.getMatricula(), (numPlaza + 100), cliente.getPin(), feciniAbono, fecFinAbono);
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
                        ReservasVO persona = new ReservasVO(cliente.getMatricula(), (numPlaza + 100), cliente.getPin(), feciniAbono, fecFinAbono);
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
                        ReservasVO persona = new ReservasVO(cliente.getMatricula(), (numPlaza + 100), cliente.getPin(), feciniAbono, fecFinAbono);
                        reservaDAO.insertReserva(persona);

                        System.out.println("Hago el insert");

                    }
                }

            }

            JOptionPane.showMessageDialog(null, "Se ha ingresado el Cliente");

        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        }
    }

    public static void main(String[] args) {
        alta();
    }
}
