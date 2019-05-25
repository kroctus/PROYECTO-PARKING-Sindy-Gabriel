/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import clientes.ClienteDAO;
import clientes.ClienteVO;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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

    // Actualiza el estado de la tabla seleccionada por el que se tiene en las carpertas de backup
    public static void recuperar() {

        JOptionPane.showMessageDialog(null, "Bienvenido al menú de recuperación de copias de seguridad");
        int tabla = JOptionPane.showOptionDialog(null, "Selecciona la tabla que deseas recuperar: ",
                "Menú", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                    "Clientes", "Vehiculos", "Plazas", "Reservas", "Tickets"},
                "Turismo");
        
       switch(tabla){
           case 1:
               break;
           case 2:
               break;
           case 3:
               break;
           case 4:
               break;
       }

    }

    public static void main(String[] args) {
//        crearCopiaSeguridad();
        recuperar();
    }

}
