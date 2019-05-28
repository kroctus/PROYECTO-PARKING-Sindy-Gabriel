/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickets;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author kroctus
 */
public interface ITickets {
    
     // Método para obtener todos los registros de la tabla
    List<TicketsVO> getAll() throws SQLException;

    // Méodo para obtener un registro a partir de la PK
    TicketsVO findByPk(int numplaza,String matricula, LocalDate fecinipin) throws SQLException;

    // Método para insertar un registro
    int insertTickets(TicketsVO ticket) throws SQLException;

    // Método para insertar varios registros
    int insertTickets(List<TicketsVO> lista) throws SQLException;

    // Método para borrar una persona
    int deleteTickets(TicketsVO t) throws SQLException;

    // Método para borrar toda la tabla
    int deleteTickets() throws SQLException;

    // Método para modificar un ticket. Se modifica el ticket que tenga esa 'pk'
    // con los nuevos datos que traiga el ticket 'nuevosDatos'
    int updateTickets(int numplaza,String matricula,LocalDate fecinipin, LocalTime horaenticket, TicketsVO nuevosDatos) throws SQLException;

    
    
    
    
}
