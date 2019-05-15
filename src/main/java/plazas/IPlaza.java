/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plazas;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Sindy Ferreira
 */
public interface IPlaza {
    
    // Método para obtener todos los registros de la tabla plazas
    public List<PlazaVO> getAll() throws SQLException;

    // Método para obtener un registro a partir del numero de plaza (la pk)
    public PlazaVO findByPk(int numPlaza) throws SQLException;

    // Método para insertar una plaza
    public int insertPlaza(PlazaVO plaza) throws SQLException;

    // Método para insertar varias plazas
    public int insertPlaza(List<PlazaVO> lista) throws SQLException;

    // Método para borrar uns plaza
    public int deletePlaza(PlazaVO p) throws SQLException;

    // Método para borrar toda la tabla plazas
    public int deletePlaza() throws SQLException;

    // Método para modificar una plaza. Se modifica la plaza que tenga ese numero 
    //de plaza con los nuevos datos que traiga la plaza 'nuevosDatos'
    public int updatePlaza(int numPlaza, PlazaVO nuevosDatos) throws SQLException;
}