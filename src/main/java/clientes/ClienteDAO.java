/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

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
public class ClienteDAO implements ICliente {

    private Connection conexion = null;

    public ClienteDAO() {
        this.conexion = Conexion.getInstance();
    }

    @Override
    public List<ClienteVO> getAll() throws SQLException {
        List<ClienteVO> lista = new ArrayList<>();

        // Preparamos la consulta de datos mediante un objeto Statement
        // ya que no necesitamos parametrizar la sentencia SQL
        try (Statement st = conexion.createStatement()) {
            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            ResultSet res = st.executeQuery("select * from clientes");
            // Ahora construimos la lista, recorriendo el ResultSet y mapeando los datos
            while (res.next()) {
                ClienteVO c = new ClienteVO();
                // Recogemos los datos del cliente, guardamos en un objeto
                c.setMatricula(res.getString("matricula"));
                c.setNombre(res.getString("nombre"));
                c.setApellido1(res.getString("apellido"));
                c.setApellido2(res.getString("apellido2"));
                c.setDni(res.getString("dni"));
                c.setEmail(res.getString("email"));
                c.setNumTarjeta(res.getString("numTarjetaCredito"));
                c.setTipoAbono(ClienteVO.obtenerTipoAbono(res.getString("tipoAbono")));
                //Añadimos el objeto a la lista
                lista.add(c);
            }
        }

        return lista;
    }

    @Override
    public ClienteVO findByPk(String matricula) throws SQLException {
        ResultSet res = null;
        ClienteVO c = new ClienteVO();
        String sql = "select * from clientes where matricula=?";

        try (PreparedStatement prest = conexion.prepareStatement(sql)) {
            // Preparamos la sentencia parametrizada
            prest.setString(1, matricula);

            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            res = prest.executeQuery();

            // Nos posicionamos en el primer registro del Resultset. Sólo debe haber una fila
            // si existe esa pk
            if (res.first()) {
                // Recogemos los datos del cliente, guardamos en un objeto
                c.setMatricula(res.getString("matricula"));
                c.setNombre(res.getString("nombre"));
                c.setApellido1(res.getString("apellido"));
                c.setApellido2(res.getString("apellido2"));
                c.setDni(res.getString("dni"));
                c.setEmail(res.getString("email"));
                c.setNumTarjeta(res.getString("numTarjetaCredito"));
                c.setTipoAbono(ClienteVO.obtenerTipoAbono(res.getString("tipoAbono")));
                return c;
            }

            return null;
        }
    }

    @Override
    public int insertCliente(ClienteVO cliente) throws SQLException {
        int numFilas = 0;
        String sql = "insert into clientes values (?,?,?,?,?,?,?,?)";
        if (findByPk(cliente.getMatricula()) != null) {
            // Existe un registro con esa pk
            // No se hace la inserción
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = conexion.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, cliente.getMatricula());
                prest.setString(2, cliente.getDni());
                prest.setString(3, cliente.getNombre());
                prest.setString(4, cliente.getApellido1());
                prest.setString(5, cliente.getApellido2());
                prest.setString(6, cliente.getNumTarjeta());
                prest.setString(7, ClienteVO.obtenerTipoAbono(cliente));
                prest.setString(8, cliente.getEmail());
                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }

    @Override
    public int insertCliente(List<ClienteVO> lista) throws SQLException {
        int numFilas = 0;

        for (ClienteVO tmp : lista) {
            numFilas += insertCliente(tmp);
        }

        return numFilas;

    }

    @Override
    public int deleteCliente(ClienteVO c) throws SQLException {
        int numFilas = 0;

        String sql = "delete from clientes where matricula = ?";

        // Sentencia parametrizada
        try (PreparedStatement prest = conexion.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setString(1, c.getMatricula());
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }

    @Override
    public int deleteCliente() throws SQLException {
        String sql = "delete from clientes";

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
    public int updateCliente(String matricula, ClienteVO nuevosDatos) throws SQLException {
        int numFilas = 0;
        String sql = "update clientes set dni = ?, nombre = ?, apellido = ?, "
                + "apellido2 = ?, numTarjetaCredito = ?, tipoAbono = ?, "
                + "email = ? where matricula=?";

        if (findByPk(matricula) == null) {
            // El cliente a actualizar no existe
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = conexion.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(8, nuevosDatos.getMatricula());
                prest.setString(1, nuevosDatos.getDni());
                prest.setString(2, nuevosDatos.getNombre());
                prest.setString(3, nuevosDatos.getApellido1());
                prest.setString(4, nuevosDatos.getApellido2());
                prest.setString(5, nuevosDatos.getNumTarjeta());
                prest.setString(6, ClienteVO.obtenerTipoAbono(nuevosDatos));
                prest.setString(7, nuevosDatos.getEmail());

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }
}
