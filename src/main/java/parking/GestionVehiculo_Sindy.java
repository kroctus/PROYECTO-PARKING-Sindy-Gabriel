/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import plazas.PlazaDAO;
import plazas.PlazaVO;
import vehiculos.VehiculoVO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import tickets.TicketsDAO;
import tickets.TicketsVO;
import vehiculos.VehiculoDAO;

/**
 *
 * @author Sindy Ferreira
 */
public class GestionVehiculo_Sindy {

    //Método que solicita al usuario una matricula y un tipo de vehiculo.
    public static VehiculoVO crearVehiculo() {
        String matricula;
        String[] matricula1;
        do {
            matricula = JOptionPane.showInputDialog("Introduzca su matricula: ");
            matricula1 = matricula.split("-");
            System.out.println("Tamaño: " + matricula.length());
        } while (!((esNumero(matricula1[0]) && !esNumero(matricula1[1])) && matricula.length() == 8));

        int tipoVehiculo = JOptionPane.showOptionDialog(null, "Seleccione su tipo de vehiculo",
                "Menú", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                    "Turismo", "Motocicleta", "Caravana"},
                "Turismo");
        return new VehiculoVO(matricula, (tipoVehiculo + 1));

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

    //Método que genera de forma aleatoria un pin de seis digitos numéricos
    private static String generarPin() {
        Random rnd = new Random();
        return Integer.toString(rnd.nextInt(999999 - 100000 + 1) + 100000);
    }

    //Método que a partir de un vehiculo y una plaza genera un ticket
    private static TicketsVO crearTicket(VehiculoVO vehiculo, PlazaVO plaza) {
        TicketsVO ticket = new TicketsVO();
        ticket.setMatricula(vehiculo.getMatricula());
        ticket.setNumplaza(plaza.getNumPlaza());
        ticket.setFecinipin(LocalDate.now());
        ticket.setHoraenticket(LocalTime.now());
        ticket.setFecfinpin(LocalDate.MIN);
        ticket.setHorasalticket(LocalTime.MIN);
        ticket.setPin_desechable(generarPin());
        return ticket;

    }

    //Método para depositar los vehiculos de los clientes no abonados
    //Este método primeramente verifica que hay plazas libre para
    //el tipo de vehiculo que tiene el usuario, si hay plazas libres 
    //entonces inserta los datos del vehiculo en la tabla vehiculos 
    //y general un ticket, en dicho caso devuelve true, 
    //pero si no se encuentran plazas libre devolverá false.    
    public static boolean depositarVehiculo() throws SQLException {
        TicketsDAO daoTicket = new TicketsDAO();
        VehiculoDAO daoVehiculo = new VehiculoDAO();
        PlazaDAO daoPlazas = new PlazaDAO();

        //Creamos un objeto de tipo vehiculo con los datos
        //que introduzca el usuario
        VehiculoVO vehiculo = crearVehiculo();
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
        if (vehiculo.getTipoVehiculo() == 2) {
            //Los turismos se guardaran del 0 al 14, siendo el rango
            //de los posibles identificadores del 100 al 114.
            for (int i = 0; i < 14; i++) {
                //Miramos si la plaza está libre
                if (plazasEstado[i] == 1) {
                    //Si efectivamente la plaza está libre entonces:
                    //Insertamos un nuevo vehiculo
                    daoVehiculo.insertVehiculo(vehiculo);
                    //Insertamos un nuevo ticket
                    daoTicket.insertTickets(crearTicket(vehiculo, listaPlaza.get(i)));
                    PlazaVO plazaModificada = listaPlaza.get(i);
                    plazaModificada.setEstadoPlaza(4);
                    //Cambiamos el estado de la plaza a ocupado
                    daoPlazas.updatePlaza(listaPlaza.get(i).getNumPlaza(), plazaModificada);
                    return true;

                }
            }
        }

        //Si el tipo de vehiculo es un turismo, miramos entre la posición 
        //15 al 29 del array para saber el estado de las plazas de tipo turismo,
        //para ver si encontramos alguna que no esté ocupada.
        if (vehiculo.getTipoVehiculo() == 1) {
            //Los turismos se guardaran del 15 al 29, siendo el rango
            //de los posibles identificadores del 115 al 129.
            for (int i = 15; i < 29; i++) {
                //Comprobamos si la plaza está libre
                if (plazasEstado[i] == 1) {
                    //Si efectivamente la plaza está libre entonces:
                    //Insertamos un nuevo vehiculo
                    daoVehiculo.insertVehiculo(vehiculo);
                    //Insertamos un nuevo ticket
                    daoTicket.insertTickets(crearTicket(vehiculo, listaPlaza.get(i)));
                    PlazaVO plazaModificada = listaPlaza.get(i);
                    plazaModificada.setEstadoPlaza(4);
                    //Cambiamos el estado de la plaza a ocupado
                    daoPlazas.updatePlaza(listaPlaza.get(i).getNumPlaza(), plazaModificada);
                    //Devuelve true si se ha podido insertar correctamente 
                    //el vehiculo en la base de datos y a su vez se ha generado
                    //correctamente el ticket
                    return true;

                }
            }

        }
        //Si el tipo de vehiculo es una caravana, miramos entre la posición 
        //30 al 44 del array para saber el estado de las plazas de tipo caravana,
        //para ver si encontramos alguna que no esté ocupada.
        if (vehiculo.getTipoVehiculo() == 3) {
            //Las caravanas se guardaran del 30 al 44, siendo el rango
            //de los posibles identificadores del 130 al 144.
            for (int i = 30; i < 44; i++) {
                //Comprobamos si la plaza está libre
                if (plazasEstado[i] == 1) {
                    //Si efectivamente la plaza está libre entonces:
                    //Insertamos un nuevo vehiculo
                    daoVehiculo.insertVehiculo(vehiculo);
                    //Insertamos un nuevo ticket
                    daoTicket.insertTickets(crearTicket(vehiculo, listaPlaza.get(i)));
                    PlazaVO plazaModificada = listaPlaza.get(i);
                    plazaModificada.setEstadoPlaza(4);
                    //Cambiamos el estado de la plaza a ocupado
                    daoPlazas.updatePlaza(listaPlaza.get(i).getNumPlaza(), plazaModificada);
                    //Devuelve true si se ha podido insertar correctamente 
                    //el vehiculo en la base de datos y a su vez se ha generado
                    //correctamente el ticket
                    return true;
                }
            }

        }

        //Devuelve false si no se han encontrado plazas libres del tipo
        //de vehiculo que ha introducido el usuario.
        return false;
    }
}
