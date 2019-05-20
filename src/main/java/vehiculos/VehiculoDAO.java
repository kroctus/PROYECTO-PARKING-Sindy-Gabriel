/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehiculos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import parking.Conexion;

/**
 *
 * @author Sindy Ferreira
 */
public class VehiculoDAO implements IVehiculo{
     private Connection conexion = null;

    public VehiculoDAO() {
        this.conexion = Conexion.getInstance();
    }
    
    @Override
    public List<VehiculoVO> getAll() throws SQLException {
        List<VehiculoVO> lista = new ArrayList<>();

        // Preparamos la consulta de datos mediante un objeto Statement
        // ya que no necesitamos parametrizar la sentencia SQL
        try (Statement st = conexion.createStatement()) {
            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            ResultSet res = st.executeQuery("select * from vehiculos");
            // Ahora construimos la lista, recorriendo el ResultSet y mapeando los datos
            while (res.next()) {
                VehiculoVO v = new VehiculoVO();
                // Recogemos los datos del vehiculo, guardamos en un objeto
                v.setMatricula(res.getString("matricula"));
                v.setTipoVehiculo(VehiculoVO.obtenerTipoVehiculo(res.getString("tipoVehiculo")));
                
                //Añadimos el objeto a la lista
                lista.add(v);
            }
        }

        return lista;
    }

    @Override
    public VehiculoVO findByPk(String matricula) throws SQLException {
        ResultSet res = null;
        VehiculoVO v = new VehiculoVO();
        String sql = "select * from vehiculos where matricula=?";

        try (PreparedStatement prest = conexion.prepareStatement(sql)) {
            // Preparamos la sentencia parametrizada
            prest.setString(1, matricula);

            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            res = prest.executeQuery();

            // Nos posicionamos en el primer registro del Resultset. Sólo debe haber una fila
            // si existe esa pk
            if (res.first()) {
                // Recogemos los datos del vehiculo, guardamos en un objeto
                v.setMatricula(res.getString("matricula"));
                v.setTipoVehiculo(VehiculoVO.obtenerTipoVehiculo(res.getString("tipoVehiculo")));
                return v;

            }
        }

        return null;
    }

    @Override
    public int insertVehiculo(VehiculoVO vehiculo) throws SQLException {
        int numFilas = 0;
        String sql = "insert into vehiculos values (?,?)";
        if (findByPk(vehiculo.getMatricula()) != null) {
            // Existe un registro con esa pk
            // No se hace la inserción
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = conexion.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, vehiculo.getMatricula());
                prest.setString(2, VehiculoVO.obtenerTipoVehiculo(vehiculo));
                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }

    @Override
    public int insertVehiculo(List<VehiculoVO> lista) throws SQLException {
        int numFilas = 0;

        for (VehiculoVO tmp : lista) {
            numFilas += insertVehiculo(tmp);
        }

        return numFilas;

    }

    @Override
    public int deleteVehiculo(VehiculoVO v) throws SQLException {
        int numFilas = 0;

        String sql = "delete from vehiculos where matricula = ?";

        // Sentencia parametrizada
        try (PreparedStatement prest = conexion.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setString(1, v.getMatricula());
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }

    @Override
    public int deleteVehiculo() throws SQLException {
        String sql = "delete from vehiculos";

        int nfilas = 0;

        // Preparamos el borrado de datos  mediante un Statement
        // No hay parámetros en la sentencia SQL
        try (Statement st = conexion.createStatement()) {
            // Ejecución de la sentencia
            nfilas = st.executeUpdate(sql);
        }

        // El borrado se realizó con éxito, devolvemos filas afectadas
        return nfilas;
    }

    @Override
    public int updateVehiculo(String matricula, VehiculoVO nuevosDatos) throws SQLException {
        int numFilas = 0;
        String sql = "update vehiculos set tipoVehiculo = ? "
                + "where matricula=?";

        if (findByPk(matricula) == null) {
            // El vehiculo a actualizar no existe
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = conexion.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, VehiculoVO.obtenerTipoVehiculo(nuevosDatos));
                prest.setString(2, nuevosDatos.getMatricula());
                
                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }
}

