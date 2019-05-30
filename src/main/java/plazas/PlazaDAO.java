/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plazas;

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
public class PlazaDAO implements IPlaza {

    private Connection conexion = null;

    public PlazaDAO() {
        this.conexion = Conexion.getInstance();
    }

    @Override
    public List<PlazaVO> getAll() throws SQLException {
        List<PlazaVO> lista = new ArrayList<>();

        // Preparamos la consulta de datos mediante un objeto Statement
        // ya que no necesitamos parametrizar la sentencia SQL
        try (Statement st = conexion.createStatement()) {
            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            ResultSet res = st.executeQuery("select * from plazas");
            // Ahora construimos la lista, recorriendo el ResultSet y mapeando los datos
            while (res.next()) {
                PlazaVO p = new PlazaVO();
                // Recogemos los datos de la plaza, guardamos en un objeto
                p.setNumPlaza(res.getInt("numplaza"));
                p.setTarifa(res.getDouble("tarifa"));
                p.setEstadoPlaza(Plaza.obtenerEstado(res.getString("estadoplaza")));
                p.setTipoPlaza(Plaza.obtenerTipoPlaza(res.getString("tipoPlaza")));

                //Añadimos el objeto a la lista
                lista.add(p);
            }
        }

        return lista;
    }

    @Override
    public PlazaVO findByPk(int numPlaza) throws SQLException {
        ResultSet res = null;
        PlazaVO p = new PlazaVO();
        String sql = "select * from plazas where numplaza=?";

        try (PreparedStatement prest = conexion.prepareStatement(sql)) {
            // Preparamos la sentencia parametrizada
            prest.setInt(1, numPlaza);

            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            res = prest.executeQuery();

            // Nos posicionamos en el primer registro del Resultset. Sólo debe haber una fila
            // si existe esa pk
            if (res.first()) {
                // Recogemos los datos de la plaza, guardamos en un objeto
                p.setNumPlaza(res.getInt("numplaza"));
                p.setTarifa(res.getDouble("tarifa"));
                p.setEstadoPlaza(Plaza.obtenerEstado(res.getString("estadoplaza")));
                p.setTipoPlaza(Plaza.obtenerTipoPlaza(res.getString("tipoPlaza")));
                return p;

            }
        }

        return null;
    }

    @Override
    public int insertPlaza(PlazaVO plaza) throws SQLException {
        int numFilas = 0;
        String sql = "insert into plazas values (?,?,?,?)";
        if (findByPk(plaza.getNumPlaza()) != null) {
            // Existe un registro con esa pk
            // No se hace la inserción
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = conexion.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setInt(1, plaza.getNumPlaza());
                prest.setString(2, Plaza.obtenerTipoPlaza(plaza));
                prest.setString(3, Plaza.obtenerEstado(plaza));
                prest.setDouble(4, plaza.getTarifa());
                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }

    @Override
    public int insertPlaza(List<PlazaVO> lista) throws SQLException {
        int numFilas = 0;

        for (PlazaVO tmp : lista) {
            numFilas += insertPlaza(tmp);
        }

        return numFilas;

    }

    @Override
    public int deletePlaza(PlazaVO p) throws SQLException {
        int numFilas = 0;

        String sql = "delete from plazas where numplaza = ?";

        // Sentencia parametrizada
        try (PreparedStatement prest = conexion.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setInt(1, p.getNumPlaza());
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }

    @Override
    public int deletePlaza() throws SQLException {
        String sql = "delete from plazas";

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
    public int updatePlaza(int numPLaza, PlazaVO nuevosDatos) throws SQLException {
        int numFilas = 0;
        String sql = "update plazas set tipoPlaza = ?, estadoplaza = ?, tarifa = ? where numplaza=?";

        if (findByPk(numPLaza) == null) {
            // La plaza a actualizar no existe
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = conexion.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setInt(4, nuevosDatos.getNumPlaza());
                prest.setString(1, Plaza.obtenerTipoPlaza(nuevosDatos));
                prest.setString(2, Plaza.obtenerEstado(nuevosDatos));
                prest.setDouble(3, nuevosDatos.getTarifa());
                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }
}

