/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickets;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author kroctus
 */
public interface ITickets {
    
     // Método para obtener todos los registros de la tabla
    List<TicketsVO> getAll() throws SQLException;

    // Méodo para obtener un registro a partir de la PK
    TicketsVO findByPk(int pk) throws SQLException;

    // Método para insertar un registro
    int insertTickets(TicketsVO ticket) throws SQLException;

    // Método para insertar varios registros
    int insertTickets(List<TicketsVO> lista) throws SQLException;

    // Método para borrar una persona
    int deleteTickets(TicketsVO t) throws SQLException;

    // Método para borrar toda la tabla
    int deleteTickets() throws SQLException;

    // Método para modificar una persona. Se modifica a la persona que tenga esa 'pk'
    // con los nuevos datos que traiga la persona 'nuevosDatos'
    int updateTickets(int pk, TicketsVO nuevosDatos) throws SQLException;

    
}
