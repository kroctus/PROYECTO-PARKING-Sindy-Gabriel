/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehiculos;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Sindy Ferreira
 */
public interface IVehiculo {
    // Método para obtener todos los registros de la tabla vehiculos
    public List<VehiculoVO> getAll() throws SQLException;

    // Método para obtener un registro a partir de la matricula (la pk)
    public VehiculoVO findByPk(String matricula) throws SQLException;

    // Método para insertar un vehiculo
    public int insertVehiculo(VehiculoVO plaza) throws SQLException;

    // Método para insertar varios vehiculos
    public int insertVehiculo(List<VehiculoVO> lista) throws SQLException;

    // Método para borrar un vehiculo
    public int deleteVehiculo(VehiculoVO p) throws SQLException;

    // Método para borrar toda la tabla vehiculos
    public int deleteVehiculo() throws SQLException;

    // Método para modificar un vehiculo. Se modifica el vehiculo que tenga 
    //esa matricula con los nuevos datos que traiga el vehiculo 'nuevosDatos'
    public int updateVehiculo(String matricula, VehiculoVO nuevosDatos) throws SQLException;
}
