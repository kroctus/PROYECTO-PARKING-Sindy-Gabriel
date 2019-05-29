/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import clientes.ClienteDAO;
import clientes.ClienteVO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
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

    private static String mesNombre(int mes) {
        switch (mes) {
            case 1:
                return "enero";
            case 2:
                return "febrero";
            case 3:
                return "marzo";
            case 4:
                return "abril";
            case 5:
                return "mayo";
            case 6:
                return "junio";
            case 7:
                return "julio";
            case 8:
                return "agosto";
            case 9:
                return "septiembre";
            case 10:
                return "octubre";
            case 11:
                return "noviembre";
            case 12:
                return "diciembre";
        }
        return "";
    }

    public static void bajaAbono() throws SQLException {
        String matricula = JOptionPane.showInputDialog("Introduce la matricula "
                + "del cliente que quieras dar de baja");

        ClienteDAO daoCliente = new ClienteDAO();
        ArrayList<ClienteVO> listaClientes = new ArrayList<>();
        listaClientes = (ArrayList<ClienteVO>) daoCliente.getAll();
        for (ClienteVO listaCliente : listaClientes) {
            if (listaCliente.getMatricula().equalsIgnoreCase(matricula)) {
                daoCliente.updateCliente(listaCliente.getMatricula(), borrarDatos(listaCliente));
            }
        }

    }

    private static ClienteVO borrarDatos(ClienteVO cliente) {

        cliente.setApellido1("");
        cliente.setApellido2("");
        cliente.setDni("");
        cliente.setEmail("");
        cliente.setNombre("");
        cliente.setNumTarjeta("");
        cliente.setTipoAbono(1);

        return cliente;

    }

    public static void abonosCaducadosMes(int mes) throws SQLException {
        String nombreMes = mesNombre(mes);
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
            JOptionPane.showMessageDialog(null, "No se ha encontrado ninguna reserva que caduque en el mes " + nombreMes);
        } else {
            for (ReservasVO reservasVO : reservasCaducidadP) {

                reserva = reserva + reservasVO.getMatricula()
                        + "  :  " + reservasVO.getNumplaza() + "  :  "
                        + reservasVO.getPin_fijo() + "  :  "
                        + reservasVO.getFeciniabono() + "  :  "
                        + reservasVO.getFecfinabono() + "\n";

            }
            JOptionPane.showMessageDialog(null, "Reservas que caducan en el mes de " + nombreMes + " \n" + reserva);

        }

    }

    public static void abonosCaducados10dias() throws SQLException {
        String reserva = "Matricula : Plaza :    Pin      :  Fecha inicio :   Fecha fin \n";
        ReservasDAO reservasDao = new ReservasDAO();
        //Lista de todas las reservas que hay en la bbdd
        ArrayList<ReservasVO> listaReservas = new ArrayList<>();
        //Lista que almacena las reservas que van a caducar en los próximos 10 días
        ArrayList<ReservasVO> reservasCaducidadP = new ArrayList<>();
        listaReservas = (ArrayList<ReservasVO>) reservasDao.getAll();
        LocalDate hoy = LocalDate.now();
        LocalDate _10dias = LocalDate.now().plusDays(10);

        for (ReservasVO reservasVO : listaReservas) {

            if (reservasVO.getFecfinabono().isAfter(hoy)
                    && reservasVO.getFecfinabono().isBefore(_10dias)) {

                reservasCaducidadP.add(reservasVO);
            }

        }

        if (reservasCaducidadP.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay abonos que caducan en los próximos 10 días");
        } else {
            for (ReservasVO reservasVO : reservasCaducidadP) {

                reserva = reserva + reservasVO.getMatricula()
                        + "  :  " + reservasVO.getNumplaza() + "  :  "
                        + reservasVO.getPin_fijo() + "  :  "
                        + reservasVO.getFeciniabono() + "  :  "
                        + reservasVO.getFecfinabono() + "\n";

            }
            JOptionPane.showMessageDialog(null, "Reservas que caducan en los próximos 10 días \n" + reserva);

        }

    }

    public static void recuperarCopiaSeguridad() throws FileNotFoundException, SQLException {
        ClienteDAO daoCliente = new ClienteDAO();
        PlazaDAO daoPlaza = new PlazaDAO();
        VehiculoDAO daoVehiculo = new VehiculoDAO();
        ReservasDAO daoReservas = new ReservasDAO();
        TicketsDAO daoTickets = new TicketsDAO();
        String respuesta;
        String archivos = "";
        boolean encontrado = false;
        //Mostramos las copias de seguridad que tenemos guardadas
        File carpeta = new File("./backup");
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
        } else {

            for (String listado1 : listado) {
                archivos = archivos + listado1 + "\n";
            }
            do {
                respuesta = JOptionPane.showInputDialog("Elige la copia de seguridad que quieres restaurar:\n " + archivos);

                for (String lista : listado) {
                    if (respuesta.equalsIgnoreCase(lista)) {
                        encontrado = true;
                    }

                }
            } while (!encontrado);

            /*ELIMINO EL CONTENIDO DE TODAS LAS TABLAS DE LA BASE DE DATOS*/
            daoReservas.deletereserva();
            daoTickets.deleteTickets();
            daoVehiculo.deleteVehiculo();
            daoCliente.deleteCliente();
            daoPlaza.deletePlaza();

            /*VEHICULOS*/
            ArrayList<VehiculoVO> listaVehiculo = new ArrayList<>();
            //Variable que lleva la cuenta de las líneas que se han leido.
            try (Scanner datosFichero = new Scanner(new FileInputStream("./backup/" + respuesta + "/Vehiculos.txt"), "ISO-8859-1")) {
                String[] tokens;
                String linea;
                while (datosFichero.hasNextLine()) {

                    linea = datosFichero.nextLine();
                    // Se guarda en el array de String cada elemento de la
                    // línea en función del carácter separador |
                    tokens = linea.split(" : ");

                    listaVehiculo.add(new VehiculoVO(tokens[0], Integer.valueOf(tokens[1])));
                }
                for (VehiculoVO vehiculoVO : listaVehiculo) {
                    System.out.println(vehiculoVO);
                }

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            //Restauramos la tabla vehiculos
            daoVehiculo.insertVehiculo(listaVehiculo);

            /*PLAZAS*/
            ArrayList<PlazaVO> listaPlazas = new ArrayList<>();
            //Variable que lleva la cuenta de las líneas que se han leido.
            try (Scanner datosFichero = new Scanner(new FileInputStream("./backup/" + respuesta + "/Plazas.txt"), "ISO-8859-1")) {
                String[] tokens;
                String linea;
                while (datosFichero.hasNextLine()) {

                    linea = datosFichero.nextLine();

                    tokens = linea.split(" : ");

                    listaPlazas.add(new PlazaVO(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2]), Double.valueOf(tokens[3])));
                }
                for (PlazaVO plazas : listaPlazas) {
                    System.out.println(plazas);
                }

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            //Restauramos la tabla de plazas
            daoPlaza.insertPlaza(listaPlazas);

            /*Clientes*/
            ArrayList<ClienteVO> listaClientes = new ArrayList<>();
            //Variable que lleva la cuenta de las líneas que se han leido.
            try (Scanner datosFichero = new Scanner(new FileInputStream("./backup/" + respuesta + "/Clientes.txt"), "ISO-8859-1")) {
                String[] tokens;
                String linea;
                while (datosFichero.hasNextLine()) {

                    linea = datosFichero.nextLine();

                    tokens = linea.split(" : ");
                    listaClientes.add(new ClienteVO(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], Integer.valueOf(tokens[6]), tokens[7]));
                }
                for (ClienteVO clientes : listaClientes) {
                    System.out.println(clientes);
                }

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            //Restauramos la tabla de clientes
            daoCliente.insertCliente(listaClientes);

            /*RESERVAS*/
            ArrayList<ReservasVO> listaReservas = new ArrayList<>();
            //Variable que lleva la cuenta de las líneas que se han leido.
            try (Scanner datosFichero = new Scanner(new FileInputStream("./backup/" + respuesta + "/Reservas.txt"), "ISO-8859-1")) {
                String[] tokens;
                String linea;
                while (datosFichero.hasNextLine()) {

                    linea = datosFichero.nextLine();
                    tokens = linea.split(" : ");
                    String i = tokens[3].trim();
                    String[] fecha1 = i.split("-");
                    LocalDate fechaIni = LocalDate.of(Integer.valueOf(fecha1[1]), Integer.valueOf(fecha1[1]), Integer.valueOf(fecha1[2]));

                    String f = tokens[4].trim();
                    String[] fecha2 = f.split("-");
                    LocalDate fechafin = LocalDate.of(Integer.valueOf(fecha2[0]), Integer.valueOf(fecha2[1]), Integer.valueOf(fecha2[2]));

                    listaReservas.add(new ReservasVO(tokens[0], Integer.valueOf(tokens[1]), tokens[2], fechaIni, fechafin));
                }
                for (ReservasVO reservas : listaReservas) {
                    System.out.println(reservas);
                }

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            //Restauramos la tabla reservas
            daoReservas.insertReserva(listaReservas);

            /*TICKETS*/
            ArrayList<TicketsVO> listaTickets = new ArrayList<>();
            //Variable que lleva la cuenta de las líneas que se han leido.
            try (Scanner datosFichero = new Scanner(new FileInputStream("./backup/" + respuesta + "/Tickets.txt"), "ISO-8859-1")) {
                String[] tokens;
                String linea;
                while (datosFichero.hasNextLine()) {

                    linea = datosFichero.nextLine();

                    tokens = linea.split(" : ");
                    String i = tokens[3].trim();
                    String[] fecha1 = i.split("-");
                    LocalDate fechaini = LocalDate.of(Integer.valueOf(fecha1[0]), Integer.valueOf(fecha1[1]), Integer.valueOf(fecha1[2]));

                    String f = tokens[4].trim();
                    String[] fecha2 = f.split("-");
                    LocalDate fechafin = LocalDate.of(Integer.valueOf(fecha2[0]), Integer.valueOf(fecha2[1]), Integer.valueOf(fecha2[2]));

                    String e = tokens[5].trim();
                    String[] hora1 = e.split(":");
                    LocalTime horaini = LocalTime.of(Integer.valueOf(hora1[0]), Integer.valueOf(hora1[1]), Integer.valueOf(hora1[2]));

                    String s = tokens[6].trim();
                    String[] hora2 = s.split(":");
                    LocalTime horafin = LocalTime.of(Integer.valueOf(hora2[0]), Integer.valueOf(hora2[1]), Integer.valueOf(hora2[2]));

                    listaTickets.add(new TicketsVO(Integer.valueOf(tokens[0]), tokens[1], tokens[2], fechaini, fechafin, horaini, horafin, Double.valueOf(tokens[7])));
                }
                for (TicketsVO tickets : listaTickets) {
                    System.out.println(tickets);
                }

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            daoTickets.insertTickets(listaTickets);

        }

    }

    public static void mostrarAbonadosAnio() throws SQLException {
        ReservasDAO daoReserva = new ReservasDAO();
        ArrayList<ReservasVO> listaReservas = new ArrayList<>();
        ArrayList<ReservasVO> listaReservasAnio = new ArrayList<>();
        System.out.println("ANTES");
        listaReservas = (ArrayList<ReservasVO>) daoReserva.getAll();
        System.out.println("DESPUES");
        int contadorReservas = 0;
        String reservas = "";
        double precioTotal = 0;
        for (ReservasVO listaReserva : listaReservas) {
            if (listaReserva.getFecfinabono().getYear()== LocalDate.now().getYear()) {
                System.out.println("HOLA");
                contadorReservas++;
                listaReservasAnio.add(listaReserva);
                precioTotal = precioTotal + listaReserva.getPrecio();
            }
        }

        if (contadorReservas != 0) {
            System.out.println("Numero de reservas que se han realizado en el año "
                    + LocalDate.now().getYear() + "\n es igual a: " + contadorReservas);
            for (ReservasVO reservasVO : listaReservasAnio) {
                reservas = reservas + reservasVO + "\n";
            }

            JOptionPane.showMessageDialog(null, "Numero de reservas que se han realizado en el año "
                    + LocalDate.now().getYear() + " es igual a: "
                    + contadorReservas + "\n" + reservas + "\n Ganacias de este año: "
                    + precioTotal);
        }
    }

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        try {
            mostrarAbonadosAnio();

        } catch (SQLException e) {
        }
    }

}
