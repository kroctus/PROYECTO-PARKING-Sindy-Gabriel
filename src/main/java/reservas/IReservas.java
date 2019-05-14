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
    ReservasVO findByPk(int pk) throws SQLException;
    
    // Método para insertar un registro
    int insertPersona (ReservasVO reserva) throws SQLException;
    
    // Método para insertar varios registros
    int insertPersona (List<ReservasVO> lista) throws SQLException;
    
    // Método para borrar una persona
    int deletePersona (ReservasVO r) throws SQLException;
    
    // Método para borrar toda la tabla
    int deletePersona() throws SQLException;
    
    // Método para modificar una persona. Se modifica a la persona que tenga esa 'pk'
    // con los nuevos datos que traiga la persona 'nuevosDatos'
    int updatePersona (int pk, ReservasVO nuevosDatos) throws SQLException;
    
    
}
