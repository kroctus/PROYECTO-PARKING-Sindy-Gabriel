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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;
import tickets.TicketsDAO;
import tickets.TicketsVO;
import vehiculos.VehiculoDAO;

/**
 *
 * @author Sindy Ferreira
 */
public class GestionVehiculos {

    //Método que solicita al usuario una matricula y un tipo de vehiculo.
    public static VehiculoVO crearVehiculo() {
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
    public static boolean esNumero(String tmp) {
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
        ticket.setFecfinpin(LocalDate.of(1, 1, 1));
        ticket.setHorasalticket(LocalTime.MIN);
        ticket.setPin_desechable(generarPin());
        ticket.setPrecio(0);
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
                    TicketsVO ticketUsuario = crearTicket(vehiculo, listaPlaza.get(i));
                    daoTicket.insertTickets(ticketUsuario);
                    PlazaVO plazaModificada = listaPlaza.get(i);
                    plazaModificada.setEstadoPlaza(4);
                    //Mostramos por pantalla los datos del ticket necesarios 
                    //para retirar el vehiculo
                    JOptionPane.showMessageDialog(null, "Ticket\n Matricula:"
                            + ticketUsuario.getMatricula() + "\nPlaza:"
                            + ticketUsuario.getNumplaza() + "\nPin:"
                            + ticketUsuario.getPin_desechable());
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
                    TicketsVO ticketUsuario = crearTicket(vehiculo, listaPlaza.get(i));
                    daoTicket.insertTickets(ticketUsuario);
                    PlazaVO plazaModificada = listaPlaza.get(i);
                    plazaModificada.setEstadoPlaza(4);
                    //Mostramos por pantalla los datos del ticket necesarios 
                    //para retirar el vehiculo
                    JOptionPane.showMessageDialog(null, "Ticket\n Matricula:"
                            + ticketUsuario.getMatricula() + "\nPlaza:"
                            + ticketUsuario.getNumplaza() + "\nPin:"
                            + ticketUsuario.getPin_desechable());
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
                    TicketsVO ticketUsuario = crearTicket(vehiculo, listaPlaza.get(i));
                    daoTicket.insertTickets(ticketUsuario);
                    PlazaVO plazaModificada = listaPlaza.get(i);
                    plazaModificada.setEstadoPlaza(4);
                    //Mostramos por pantalla los datos del ticket necesarios 
                    //para retirar el vehiculo
                    JOptionPane.showMessageDialog(null, "Ticket\n Matricula: "
                            + ticketUsuario.getMatricula() + "\nPlaza: "
                            + ticketUsuario.getNumplaza() + "\nPin: "
                            + ticketUsuario.getPin_desechable());
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

    public static boolean retirarVehiculo() throws SQLException, ParseException {
        System.out.println(LocalDate.now());
        System.out.println(LocalTime.now());
        //Cambiamos el formato del double para que solo muestre dos decimales.
        DecimalFormat formatoDecimal = new DecimalFormat("#.00");
        TicketsDAO daoTicket = new TicketsDAO();
        PlazaDAO daoPlaza = new PlazaDAO();
        ArrayList<TicketsVO> listaTicket = new ArrayList<>();
        ArrayList<PlazaVO> listaPlaza = new ArrayList<>();
        int minutos;
        String matricula, pin, numeroPlaza;
        double tarifa = 0.0;
        double precioTicket;
        String[] matricula1;
        PlazaVO plazaModificada = new PlazaVO();
        TicketsVO ticketModificado = new TicketsVO();
        do {
            matricula = JOptionPane.showInputDialog("Introduzca su matricula: ");
            matricula1 = matricula.split("-");
            System.out.println("Tamaño: " + matricula.length());
        } while (!((esNumero(matricula1[0]) && !esNumero(matricula1[1])) && matricula.length() == 8));

        do {
            pin = JOptionPane.showInputDialog("Introduzca su pin: ");
        } while (!(esNumero(pin) && pin.length() == 6));

        do {
            numeroPlaza = JOptionPane.showInputDialog("Introduzca su número de plaza: ");
        } while (!(esNumero(numeroPlaza) && (Integer.parseInt(numeroPlaza) >= 100 && Integer.parseInt(numeroPlaza) <= 145)));
        listaPlaza = (ArrayList<PlazaVO>) daoPlaza.getAll();
        listaTicket = (ArrayList<TicketsVO>) daoTicket.getAll();
        for (TicketsVO ticket : listaTicket) {
            if (ticket.getMatricula().equalsIgnoreCase(matricula)
                    && ticket.getPin_desechable().equals(pin)
                    && ticket.getNumplaza() == Integer.parseInt(numeroPlaza)) {
                //Obtenemos los minutos que el vehiculo ha pasado en el parking
                minutos = calcularMinutos(ticket.getFecinipin(), LocalDate.now().minusDays(1), ticket.getHoraenticket(), LocalTime.now());
                //Obtenemos mediante el numero de plaza la tarifa por minuto 
                //que se la va a aplicar al vehiculo que dependerá del tipo de vehiculo.
                for (PlazaVO plazaVO : listaPlaza) {
                    if (plazaVO.getNumPlaza() == ticket.getNumplaza()) {
                        tarifa = plazaVO.getTarifa();
                        plazaModificada = plazaVO;
                    }
                }
                //Calculamos el precio multiplicando los minutos que ha estado
                //el vehiculo en el parking por la tarifa del parking,
                //que depende del tipo de vehiculo
                precioTicket = (minutos * tarifa);
                System.out.println(precioTicket);
                //Cambiamos el estado a libre
                plazaModificada.setEstadoPlaza(1);
                daoPlaza.updatePlaza(plazaModificada.getNumPlaza(), plazaModificada);
                //Cambiamos la fecha y la hora de salida 
                ticketModificado = ticket;
                ticketModificado.setHorasalticket(LocalTime.now());
                ticketModificado.setFecfinpin(LocalDate.now().minusDays(1));
                ticketModificado.setPrecio(precioTicket);
                daoTicket.deleteTickets(ticket);
                daoTicket.insertTickets(ticketModificado);
                //Mostramos por pantalla los datos del ticket
                if (precioTicket==0) {
                    JOptionPane.showMessageDialog(null, "Ticket\n Matricula: "
                        + ticketModificado.getMatricula() + "\nPlaza: "
                        + ticketModificado.getNumplaza() + "\nPin: "
                        + ticketModificado.getPin_desechable() + "\nFecha de entrada: "
                        + ticketModificado.getFecinipin() + "\nHora de entrada: "
                        + ticketModificado.getHoraenticket() + "\nFecha de salida: "
                        + ticketModificado.getFecfinpin() + "\nHora de salida: "
                        + ticketModificado.getHorasalticket() + "\nImporte: "
                        + precioTicket);
                }else{
                    JOptionPane.showMessageDialog(null, "Ticket\n Matricula: "
                        + ticketModificado.getMatricula() + "\nPlaza: "
                        + ticketModificado.getNumplaza() + "\nPin: "
                        + ticketModificado.getPin_desechable() + "\nFecha de entrada: "
                        + ticketModificado.getFecinipin() + "\nHora de entrada: "
                        + ticketModificado.getHoraenticket() + "\nFecha de salida: "
                        + ticketModificado.getFecfinpin() + "\nHora de salida: "
                        + ticketModificado.getHorasalticket() + "\nImporte: "
                        + formatoDecimal.format(precioTicket));
                }
                
                return true;
            }
        }

        return false;

    }

    public static int calcularMinutos(LocalDate inicioF, LocalDate finF, LocalTime inicioH, LocalTime finH) throws ParseException {
        //Minutos totales que ha pasado un vehiculo en el parking
        int minutosTotales;
        //Pasamos los parametros de entrada a string, para luego poder compararlos
        String inicio, fin;
        inicio = inicioF.getYear() + "-" + inicioF.getMonthValue() + "-"
                + inicioF.getDayOfMonth() + " " + inicioH.getHour() + ":"
                + inicioH.getMinute() + ":" + inicioH.getSecond();
        fin = finF.getYear() + "-" + finF.getMonthValue() + "-"
                + finF.getDayOfMonth() + " " + finH.getHour() + ":"
                + finH.getMinute() + ":" + finH.getSecond();

        //Establecemos el formato que tendrá los Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        Date fechaInicial = dateFormat.parse(inicio);
        Date fechaFinal = dateFormat.parse(fin);

        //Calculamos los segundos que hay entre una fecha y otra
        int diferencia = (int) ((fechaFinal.getTime() - fechaInicial.getTime()) / 1000);

        int dias = 0;

        int horas = 0;

        int minutos = 0;

        //Si hay mas de 86400 segundos, entendemos que ha pasado un día o más
        if (diferencia > 86400) {
            //Calculamos cuantos dias han pasado
            dias = (int) Math.floor(diferencia / 86400);

            diferencia = diferencia - (dias * 86400);

        }

        //Si hay más de 3600 segundos entendemos que ha pasado una hora o más
        if (diferencia > 3600) {
            //Calculamos cuantas horas han pasado
            horas = (int) Math.floor(diferencia / 3600);

            diferencia = diferencia - (horas * 3600);

        }

        //Si hay más de 60 segundos entendemos que ha pasado un minuto o más
        if (diferencia > 60) {
            //Calculamos cuantas minutos han pasado
            minutos = (int) Math.floor(diferencia / 60);

            diferencia = diferencia - (minutos * 60);

        }

        //Pasamos todo a minutos, tanto los dias como las horas, para saber 
        //cuántos minutos ha estado el vehiculo en el parking ya que la 
        //tarifa se cobra por minuto.
        minutosTotales = dias * 1440 + horas * 60 + minutos;
        return minutosTotales;
    }
}
