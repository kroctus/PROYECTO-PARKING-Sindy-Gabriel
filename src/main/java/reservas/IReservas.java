/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservas;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author gabriel
 */
public interface IReservas {
    
    // Método para obtener todos los registros de la tabla
    List<ReservasVO> getAll() throws SQLException;
    
    // Méodo para obtener un registro a partir de la PK
    ReservasVO findByPk(String matricula, int numplaza) throws SQLException;
    
    // Método para insertar un registro
    int insertReserva (ReservasVO reserva) throws SQLException;
    
    // Método para insertar varios registros
    int insertReserva (List<ReservasVO> lista) throws SQLException;
    
    // Método para borrar una reserva
    int deleteReserva (ReservasVO r) throws SQLException;
    
    // Método para borrar toda la tabla
    int deletereserva() throws SQLException;
    
    // Método para modificar una reserva. Se modifica a la reserva que tenga esa 'pk'
    // con los nuevos datos que traiga la reserva 'nuevosDatos'
    int updatereserva (String matricula, int numplaza, ReservasVO nuevosDatos) throws SQLException;
    
    
}
