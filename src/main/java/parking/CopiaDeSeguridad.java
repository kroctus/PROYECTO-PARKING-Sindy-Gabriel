/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import clientes.ClienteDAO;
import clientes.ClienteVO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
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
 * @author kroctus
 */
public class CopiaDeSeguridad {

    //Método que genera una copia de seguridad de cada una de las tablas de nuestra BD
    public static void crearCopiaSeguridad() {
        PlazaDAO plaza = new PlazaDAO();
        ReservasDAO reservas = new ReservasDAO();
        TicketsDAO ticket = new TicketsDAO();
        ClienteDAO cliente = new ClienteDAO();
        VehiculoDAO vehiculo = new VehiculoDAO();

        //Generamos las listas con los datos de las tablas y agregamos los valores actuales de las tablas
        try {
            ArrayList<PlazaVO> listaPlaza = (ArrayList<PlazaVO>) plaza.getAll();
            ArrayList<ReservasVO> listaReserva = (ArrayList<ReservasVO>) reservas.getAll();
            ArrayList<TicketsVO> listaTicket = (ArrayList<TicketsVO>) ticket.getAll();
            ArrayList<ClienteVO> listaCliente = (ArrayList<ClienteVO>) cliente.getAll();
            ArrayList<VehiculoVO> listaVehiculo = (ArrayList<VehiculoVO>) vehiculo.getAll();

            //imprimimos las listas
            listaPlaza.forEach(System.out::println);
            listaReserva.forEach(System.out::println);
            listaTicket.forEach(System.out::println);
            listaCliente.forEach(System.out::println);
            listaVehiculo.forEach(System.out::println);

            //Creamos los directorios
            LocalDate fecha = LocalDate.now();
            LocalTime horas = LocalTime.now();

            //Fecha
            String anio = Integer.valueOf(fecha.getYear()).toString();
            String mes = Integer.valueOf(fecha.getMonthValue()).toString();
            String dia = Integer.valueOf(fecha.getDayOfMonth()).toString();

            //Hora
            String hora = Integer.valueOf(horas.getHour()).toString();
            String minutos = Integer.valueOf(horas.getMinute()).toString();
            String segundos = Integer.valueOf(horas.getSecond()).toString();

            String cadenaSub = "./backup/" + dia + "-" + mes + "-" + anio + "_" + hora + "-" + minutos + "-" + segundos;
            System.out.println("Cadena Sub: " + cadenaSub);

            Path subCarpeta = Paths.get(cadenaSub);

            Files.createDirectories(subCarpeta);

            String idfichero = "";

            idfichero = subCarpeta + "/Plazas.txt";
            BufferedWriter flujo = new BufferedWriter(new FileWriter(idfichero));
            /*Recorremos el arraylist y escribimos los datos cambiando el nombre para cada archivo y lista*/

 /*PLAZAS*/
            for (int i = 0; i < listaPlaza.size(); i++) {
                flujo.write(listaPlaza.get(i).toString());
                flujo.newLine();
            }
            flujo.flush();

            /*RESERVAS*/
            idfichero = subCarpeta + "/Reservas.txt";
            flujo = new BufferedWriter(new FileWriter(idfichero));
            for (int i = 0; i < listaReserva.size(); i++) {
                flujo.write(listaReserva.get(i).toString());
                flujo.newLine();
            }
            flujo.flush();

            /*TICKETS*/
            idfichero = subCarpeta + "/Tickets.txt";
            flujo = new BufferedWriter(new FileWriter(idfichero));
            for (int i = 0; i < listaTicket.size(); i++) {
                flujo.write(listaTicket.get(i).toString());
                flujo.newLine();
            }
            flujo.flush();

            idfichero = subCarpeta + "/Clientes.txt";
            flujo = new BufferedWriter(new FileWriter(idfichero));
            for (int i = 0; i < listaCliente.size(); i++) {
                flujo.write(listaCliente.get(i).toString());
                flujo.newLine();
            }
            flujo.flush();

            /*VEHICULO*/
            idfichero = subCarpeta + "/Vehiculos.txt";
            flujo = new BufferedWriter(new FileWriter(idfichero));
            for (int i = 0; i < listaVehiculo.size(); i++) {
                flujo.write(listaVehiculo.get(i).toString());
                flujo.newLine();
            }
            flujo.flush();

            JOptionPane.showMessageDialog(null, "Se ha realizado la Copia de seguridad exitosamente");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        } catch (IOException e) {
            System.out.println("Problema creando el directorio.");
            System.out.println(e.toString());

        }

    }


    //métodos que leen  cada uno de los archivos de backups y extraen sus datos para actualizar las tablas
    public static void leerClinetes(String nombre) throws FileNotFoundException, IOException {

        ClienteDAO dao = new ClienteDAO();
        ArrayList<ClienteVO> clientes = new ArrayList<>();// lista donde guardaremos todos los empleados creados con los tokens
        // del texto y que posteriormente devolveremos en el método.
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nombre), "ISO-8859-1"))) {

            // Se crea el objeto FileReader
            FileReader fr = new FileReader(nombre);

            // Mientras el método readLine() no devuelva null existen datos por leer
            String[] tokens;
            String linea;

            /*   this.matricula = matricula;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.numTarjeta = numTarjeta;
        this.tipoAbono = tipoAbono;
        this.email = email;*/
            while ((linea = br.readLine()) != null) {

                tokens = linea.split(" : ");
                String matricula = tokens[0];
                String dni = tokens[1];
                String nombreC = tokens[2];
                String apellido1 = tokens[3];
                String apellido2 = tokens[4];
                String numTarjeta = tokens[5];
                String tipoAbono = tokens[6];
                String email = tokens[7];

                clientes.add(new ClienteVO(matricula, dni, nombreC, apellido1, apellido2, numTarjeta, Integer.valueOf(tipoAbono), email));
                dao.updateCliente(matricula, new ClienteVO(matricula, dni, nombreC, apellido1, apellido2, numTarjeta, Integer.valueOf(tipoAbono), email));

            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());

        }

        clientes.forEach(System.out::println);
    }

    /*PLAZAS*/
    public static void leerPlazas(String nombre) throws FileNotFoundException, IOException {

        PlazaDAO dao = new PlazaDAO();
        ArrayList<PlazaVO> plaza = new ArrayList<>();// lista donde guardaremos todos los empleados creados con los tokens
        // del texto y que posteriormente devolveremos en el método.
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nombre), "ISO-8859-1"))) {

            // Se crea el objeto FileReader
            FileReader fr = new FileReader(nombre);

            // Mientras el método readLine() no devuelva null existen datos por leer
            String[] tokens;
            String linea;

            /*  private int numPlaza; //Clave primaria en la tabla Plazas
    private int tipoPlaza;
    private int estadoPlaza;
    private double tarifa;*/
            while ((linea = br.readLine()) != null) {

                tokens = linea.split(" : ");
                String numPlaza = tokens[0];
                String tipoPlaza = tokens[1];
                String estadoPlaza = tokens[2];
                String tarifa = tokens[3];

                plaza.add(new PlazaVO(Integer.valueOf(numPlaza), Integer.valueOf(tipoPlaza), Integer.valueOf(estadoPlaza), Double.valueOf(tarifa)));
                dao.updatePlaza(Integer.valueOf(numPlaza), new PlazaVO(Integer.valueOf(numPlaza), Integer.valueOf(tipoPlaza), Integer.valueOf(estadoPlaza), Double.valueOf(tarifa)));

            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());

        }

        plaza.forEach(System.out::println);
    }

    /*VEHICULOS*/
    public static void leerVehiculos(String nombre) throws FileNotFoundException, IOException {

        VehiculoDAO dao = new VehiculoDAO();
        ArrayList<VehiculoVO> vehiculos = new ArrayList<>();// lista donde guardaremos todos los empleados creados con los tokens
        // del texto y que posteriormente devolveremos en el método.
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nombre), "ISO-8859-1"))) {

            // Se crea el objeto FileReader
            FileReader fr = new FileReader(nombre);

            // Mientras el método readLine() no devuelva null existen datos por leer
            String[] tokens;
            String linea;

            /* private String matricula;
                 private int tipoVehiculo;*/
            while ((linea = br.readLine()) != null) {

                tokens = linea.split(" : ");
                String matricula = tokens[0];
                String tipoVehiculo = tokens[1];

                vehiculos.add(new VehiculoVO(matricula, Integer.valueOf(tipoVehiculo)));
                dao.updateVehiculo(matricula, new VehiculoVO(matricula, Integer.valueOf(tipoVehiculo)));

            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());

        }

        vehiculos.forEach(System.out::println);
    }

    /*RESERVAS*/
    public static void leerReservas(String nombre) throws FileNotFoundException, IOException {

        ReservasDAO dao = new ReservasDAO();
        ArrayList<ReservasVO> reserva = new ArrayList<>();// lista donde guardaremos todos los empleados creados con los tokens
        // del texto y que posteriormente devolveremos en el método.
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nombre), "ISO-8859-1"))) {

            // Se crea el objeto FileReader
            FileReader fr = new FileReader(nombre);

            // Mientras el método readLine() no devuelva null existen datos por leer
            String[] tokens;
            String linea;

            /*  private String matricula;
                  private int numplaza;
                 private String pin_fijo;
                 private LocalDate feciniabono;
                  private LocalDate fecfinabono;*/
            while ((linea = br.readLine()) != null) {

                tokens = linea.split(" : ");
                String matricula = tokens[0];
                String numPlaza = tokens[1];
                String pinFijo = tokens[2];
                String feciniabono = tokens[3];
                String fecfinabono = tokens[4];
                String anio = feciniabono.substring(4);
                String mes = feciniabono.substring(5, 5);
                String dia = feciniabono.substring(6, 7);

                String anio2 = fecfinabono.substring(4);
                String mes2 = fecfinabono.substring(5, 5);
                String dia2 = fecfinabono.substring(6, 7);

                reserva.add(new ReservasVO(matricula, Integer.valueOf(numPlaza), pinFijo,
                        LocalDate.of(Integer.valueOf(anio), Integer.valueOf(mes), Integer.valueOf(dia)),
                        LocalDate.of(Integer.valueOf(anio2), Integer.valueOf(mes2), Integer.valueOf(dia2))));

                dao.updatereserva(matricula, Integer.valueOf(numPlaza), new ReservasVO(matricula, Integer.valueOf(numPlaza), pinFijo,
                        LocalDate.of(Integer.valueOf(anio), Integer.valueOf(mes), Integer.valueOf(dia)),
                        LocalDate.of(Integer.valueOf(anio2), Integer.valueOf(mes2), Integer.valueOf(dia2))));

            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());

        }

        reserva.forEach(System.out::println);
    }


}
