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
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import static parking.GestionVehiculos.crearVehiculo;
import static parking.GestionVehiculosAbonados.esNumero;
import static parking.GestionVehiculosAbonados.generarTarifa;
import plazas.PlazaDAO;
import plazas.PlazaVO;
import reservas.ReservasDAO;
import reservas.ReservasVO;
import tickets.TicketsDAO;
import tickets.TicketsVO;
import vehiculos.VehiculoDAO;
import vehiculos.VehiculoVO;

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

        int tipoVehiculo = JOptionPane.showOptionDialog(null, "Tipo Vehiculo: ",
                "Vehiculo", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                    "Motocicleta", "Turismo", "Carava"},
                "Turismo");

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
            case 0:
                tipoAbono = 1;
                fecFinAbono = feciniAbono.plusMonths(1);
                break;
            case 1:
                tipoAbono = 2;
                fecFinAbono = feciniAbono.plusMonths(3);
                break;
            case 2:
                tipoAbono = 3;
                fecFinAbono = feciniAbono.plusMonths(6);
                break;
            case 3:
                tipoAbono = 4;
                fecFinAbono = feciniAbono.plusMonths(12);
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

//        ClienteAbonado clienteA = new ClienteAbonado(dni, tipoVehiculo, matricula);
        //Agregamos ese cliente a la BD
        try {
            System.out.println("Insertamos el cliente");
            clienteDAO.insertCliente(clienteVO);
            System.out.println("Se ha incertado el cliente");

            System.out.println("Hacemos la reserva");
            IngresarAbonado(matricula, fecFinAbono, tipoAbono, tipoVehiculo);
            JOptionPane.showMessageDialog(null, "EL nuevo abonado se ah registrado satisfacoriamente");

        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        }

    }

    //Método que recbe el tipo de abono y devuelve el precio por tipo de abono
    public static int generarPrecioAbono(int tipoAbono) {
        switch (tipoAbono) {
            case 1:
                return 25;
            case 2:
                return 70;
            case 3:
                return 130;
            case 4:
                return 200;

        }

        return 25;
    }

    public static boolean IngresarAbonado(String matricula, LocalDate fecFinAbono, int tipoAbono, int tipoVehiculo) throws SQLException {
        TicketsDAO daoTicket = new TicketsDAO();
        VehiculoDAO daoVehiculo = new VehiculoDAO();
        PlazaDAO daoPlazas = new PlazaDAO();

        //Creamos un objeto de tipo vehiculo con los datos
        //que introduzca el usuario
        //Creamos un array con el número de parking que tenemos, es decir, 45.
        Integer[] plazasEstado = new Integer[45];
        ArrayList<PlazaVO> listaPlaza = new ArrayList<>();

        try {
            //Obtenemos de la base de datos la información de las plazas almacenadas
            listaPlaza = (ArrayList<PlazaVO>) daoPlazas.getAll();
            for (int i = 0; i < listaPlaza.size(); i++) {
                //Guardamos en el array el estado de las plazas
                plazasEstado[i] = listaPlaza.get(i).getEstadoPlaza();
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        }

        //Si el tipo de vehiculo es una motocicleta, miramos entre la posición 
        //0 al 14 del array para saber el estado de las plazas de tipo motocicleta,
        //para ver si encontramos alguna que no esté ocupada.
        if (tipoVehiculo == 0) {
            //Los turismos se guardaran del 0 al 14, siendo el rango
            //de los posibles identificadores del 100 al 114.
            for (int i = 0; i < 14; i++) {
                //Miramos si la plaza está libre
                if (plazasEstado[i] == 1) {
                    //Si efectivamente la plaza está libre entonces:
                    PlazaVO plazaModificada = listaPlaza.get(i);
                    plazaModificada.setEstadoPlaza(2);
                    daoPlazas.updatePlaza(listaPlaza.get(i).getNumPlaza(), plazaModificada);
                    //Añadimos La reserva
                    ReservasVO reserva = new ReservasVO(matricula, listaPlaza.get(i).getNumPlaza(), GestionVehiculosAbonados.generarPin(), LocalDate.now(), fecFinAbono, generarPrecioAbono(tipoAbono));
                    ReservasDAO r = new ReservasDAO();
                    r.insertReserva(reserva);
                    return true;

                }
            }
        }

        //Si el tipo de vehiculo es un turismo, miramos entre la posición 
        //15 al 29 del array para saber el estado de las plazas de tipo turismo,
        //para ver si encontramos alguna que no esté ocupada.
        if (tipoVehiculo == 1) {
            //Los turismos se guardaran del 15 al 29, siendo el rango
            //de los posibles identificadores del 115 al 129.
            for (int i = 15; i < 29; i++) {
                //Comprobamos si la plaza está libre
                if (plazasEstado[i] == 1) {
                    //Si efectivamente la plaza está libre entonces:
                    PlazaVO plazaModificada = listaPlaza.get(i);
                    plazaModificada.setEstadoPlaza(2);
                    //Mostramos por pantalla los datos del ticket necesarios                    
                    daoPlazas.updatePlaza(listaPlaza.get(i).getNumPlaza(), plazaModificada);
                    //Añadimos La reserva
                    ReservasVO reserva = new ReservasVO(matricula, listaPlaza.get(i).getNumPlaza(), GestionVehiculosAbonados.generarPin(), LocalDate.now(), fecFinAbono, generarPrecioAbono(tipoAbono));
                    ReservasDAO r = new ReservasDAO();
                    r.insertReserva(reserva);
                    return true;

                }
            }

        }
        //Si el tipo de vehiculo es una caravana, miramos entre la posición 
        //30 al 44 del array para saber el estado de las plazas de tipo caravana,
        //para ver si encontramos alguna que no esté ocupada.
        if (tipoVehiculo == 2) {
            //Las caravanas se guardaran del 30 al 44, siendo el rango
            //de los posibles identificadores del 130 al 144.
            for (int i = 30; i < 44; i++) {
                //Comprobamos si la plaza está libre
                if (plazasEstado[i] == 1) {
                    //Si efectivamente la plaza está libre entonces:

                    PlazaVO plazaModificada = listaPlaza.get(i);
                    plazaModificada.setEstadoPlaza(2);
                    //Mostramos por pantalla los datos del ticket necesarios 

                    //Cambiamos el estado de la plaza a ocupado
                    daoPlazas.updatePlaza(listaPlaza.get(i).getNumPlaza(), plazaModificada);
                    //Devuelve true si se ha podido insertar correctamente 
                    //Añadimos La reserva
                    ReservasVO reserva = new ReservasVO(matricula, listaPlaza.get(i).getNumPlaza(), GestionVehiculosAbonados.generarPin(), LocalDate.now(), fecFinAbono, generarPrecioAbono(tipoAbono));
                    ReservasDAO r = new ReservasDAO();
                    r.insertReserva(reserva);
                    return true;
                }
            }

        }

        //Devuelve false si no se han encontrado plazas libres del tipo
        //de vehiculo que ha introducido el usuario.
        return false;
    }

    /*Método que modifica los datos personales del abonado y la fecha de cancelación*/
    public static void modificarAbonado() {

        JOptionPane.showMessageDialog(null, "Entrando a modificación de abonado.....");
        int opcion = JOptionPane.showOptionDialog(null, "¿Qué datos deseas modificar? ",
                "Datos", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                    "Datos Personales", "Fecha de cancelación de abono"},
                "Datos");
        ClienteDAO clienteD = new ClienteDAO();
        ArrayList<ClienteVO> listaClientes = new ArrayList<>();
        ClienteVO cliente = new ClienteVO();
        ReservasDAO reservaD = new ReservasDAO();
        ReservasDAO reservaD2 = new ReservasDAO();
        ArrayList<ReservasVO> listaReservas = new ArrayList<>();
        ReservasVO reserva = new ReservasVO();

        try {

            listaClientes = (ArrayList<ClienteVO>) clienteD.getAll();
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        }

        String matricula = JOptionPane.showInputDialog(null, "Introduce la matricula del abonado");
        for (int i = 0; i < listaClientes.size(); i++) {

            ArrayList<ClienteVO> listaAux = new ArrayList<>();

            if (listaClientes.get(i).getMatricula().equalsIgnoreCase(matricula)) {
                listaAux.add(listaClientes.get(i));
            }

            if (listaAux.isEmpty() == true) {
                if (!((listaClientes.get(i).getMatricula().equalsIgnoreCase(matricula)) && listaClientes.size() == listaClientes.size())) {
                    int salir = JOptionPane.showOptionDialog(null, "La matricula introducida no coincide con ninguna de la base de datos \n¿Desea volver a introducirla? ",
                            "ERROR", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                                "Si", "No"}, "Error");

                    switch (salir) {
                        case 0:
                            modificarAbonado();
                            break;
                        case 1:
                            JOptionPane.showMessageDialog(null, "Saliendo del menú de modificación....");
                            return;
                    }
                }
            }

            if (listaClientes.get(i).getMatricula().equalsIgnoreCase(matricula)) {
                try {
                    //Clonamos a el cliente con esa matricula para realizar las moficiaciones sobre este
                    cliente = clienteD.findByPk(matricula);
                    ClienteVO clienteAux = cliente;
                    String fecha;//Fecha que introduce el usuario 
                    String[] fechaA; //Array que almacena año, mes y dia de la fecha
                    boolean fechaCorrecta = false;//Comprueba si la fecha es correcta
                    switch (opcion) {
                        //Datos personales
                        case 0:
                            String dni = JOptionPane.showInputDialog(null, "Introduce el nuevo DNI: ");
                            String nombre = JOptionPane.showInputDialog(null, "Introduce el nuevo nombre: ");
                            String apellido = JOptionPane.showInputDialog(null, "Introduce el nuevo primer apellido: ");
                            String apellido2 = JOptionPane.showInputDialog(null, "Introduce el nuevo segundo apellido: ");
                            String tarjeta = JOptionPane.showInputDialog(null, "Introduce la nueva tarjeta de credito: ");
                            String email = JOptionPane.showInputDialog(null, "Introduce el nuevo Email: ");

                            //Actualizamos los datos del cliente
                            cliente = new ClienteVO(matricula, dni, nombre, apellido, apellido2, tarjeta, clienteAux.getTipoAbono(), email);
                            clienteD.updateCliente(matricula, cliente);
                            JOptionPane.showMessageDialog(null, "Datos actualizados satisfactoriamente");
                            System.out.println("Los datos anteriores eran: " + clienteAux.toString());
                            System.out.println("----");
                            System.out.println("Los nuevos datos son: " + cliente.toString());
                            break;
                        //Fecha de caducidad
                        case 1:
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
                                case 0:
                                    tipoAbono = 1;
                                    fecFinAbono = feciniAbono.plusMonths(1);
                                    break;
                                case 1:
                                    tipoAbono = 2;
                                    fecFinAbono = feciniAbono.plusMonths(3);
                                    break;
                                case 2:
                                    tipoAbono = 3;
                                    fecFinAbono = feciniAbono.plusMonths(6);
                                    break;
                                case 4:
                                    tipoAbono = 4;
                                    fecFinAbono = feciniAbono.plusMonths(12);
                                    break;
                            }

                            //Modificaciones en la BD
                            //Modificamos el cliente
                            System.out.println("Vamos a obtener el cliente");
                            ClienteVO cliente2 = clienteD.findByPk(matricula);
                            cliente2.toString();
                            clienteD.updateCliente(matricula, new ClienteVO(cliente2.getMatricula(), cliente2.getDni(), cliente2.getNombre(), cliente2.getApellido1(), cliente2.getApellido2(),
                                    cliente2.getNumTarjeta(), tipoAbono, cliente2.getEmail()));
                            System.out.println("Modificamos el cliente");

                            //Modificamos reserva
                            System.out.println("Pasamos a reservas");
                            int numPlaza = reservaD.findPlaza(matricula);
                            ReservasVO aux = reservaD.findByPk(matricula, numPlaza);
                            reservaD.updatereserva(matricula, numPlaza, new ReservasVO(
                                    matricula, numPlaza, aux.getPin_fijo(), feciniAbono, fecFinAbono, aux.getPrecio()));
                            JOptionPane.showMessageDialog(null, "Se han cambiado los datos satisfacotiramente");
                            break;

                    }

                } catch (SQLException sqle) {
                    System.out.println("No se ha podido realizar la operación:");
                    System.out.println(sqle.getMessage());
                }
            }

        }

    }

    public static void main(String[] args) {
//        alta();
        modificarAbonado();
    }

}
