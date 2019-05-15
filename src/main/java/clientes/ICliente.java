/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sindy
 */
public interface ICliente {

    // Método para obtener todos los registros de la tabla clientes
    public List<ClienteVO> getAll() throws SQLException;

    // Método para obtener un registro a partir de la matricula (la pk)
    public ClienteVO findByPk(String matricula) throws SQLException;

    // Método para insertar un cliente
    public int insertCliente(ClienteVO cliente) throws SQLException;

    // Método para insertar varios clientes
    public int insertCliente(List<ClienteVO> lista) throws SQLException;

    // Método para borrar un cliente
    public int deleteCliente(ClienteVO c) throws SQLException;

    // Método para borrar toda la tabla clientes
    public int deleteCliente() throws SQLException;

    // Método para modificar un cliente. Se modifica al cliente que tenga esa 
    //matrícula con los nuevos datos que traiga la persona 'nuevosDatos'
    public int updateCliente(String matricula, ClienteVO nuevosDatos) throws SQLException;
}
