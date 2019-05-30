/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservas;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import parking.Conexion;

/**
 *
 * @author gabriel
 */
public class ReservasDAO implements IReservas {

    private Connection con = null;

    public ReservasDAO() {
        con = Conexion.getInstance();
    }

    @Override
    public List<ReservasVO> getAll() throws SQLException {

        List<ReservasVO> lista = new ArrayList<>();

        // Preparamos la consulta de datos mediante un objeto Statement
        // ya que no necesitamos parametrizar la sentencia SQL
        try (Statement st = con.createStatement()) {
            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            ResultSet res = st.executeQuery("select * from reservas");
            // Ahora construimos la lista, recorriendo el ResultSet y mapeando los datos
            while (res.next()) {
                ReservasVO p = new ReservasVO();
                // Recogemos los datos de la reserva, guardamos en un objeto
                p.setMatricula(res.getString("matricula"));
                p.setNumplaza(res.getInt("numplaza"));
                p.setPin_fijo(res.getString("pin_fijo"));
                p.setFeciniabono(res.getDate("feciniabono").toLocalDate());
                p.setFecfinabono(res.getDate("fecfinabono").toLocalDate());
                p.setPrecio(res.getDouble("precio"));

                //Añadimos el objeto a la lista
                lista.add(p);
            }
        }

        return lista;
    }

    @Override
    public ReservasVO findByPk(String matricula, int numplaza) throws SQLException {
        ResultSet res = null;
        ReservasVO p = new ReservasVO();

        String sql = "select * from reservas where matricula=? and numplaza=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {
            // Preparamos la sentencia parametrizada
//            prest.setInt(1, pk);
            prest.setString(1, matricula);
            prest.setInt(2, numplaza);

            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            res = prest.executeQuery();

            // Nos posicionamos en el primer registro del Resultset. Sólo debe haber una fila
            // si existe esa pk
            if (res.first()) {
                // Recogemos los datos de la reserva, guardamos en un objeto
                p.setMatricula(res.getString("matricula"));
                p.setNumplaza(res.getInt("numplaza"));
                p.setPin_fijo(res.getString("pin_fijo"));
                p.setFeciniabono(res.getDate("feciniabono").toLocalDate());
                p.setFecfinabono(res.getDate("fecfinabono").toLocalDate());
                p.setPrecio(res.getDouble("precio"));
                return p;
            }

            return null;
        }
        

    }

    @Override
    public int insertReserva(ReservasVO reserva) throws SQLException {
        int numFilas = 0;
        String sql = "insert into reservas values (?,?,?,?,?,?)";

        if (findByPk(reserva.getMatricula(), reserva.getNumplaza()) != null) {
            // Existe un registro con esa pk
            // No se hace la inserción
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, reserva.getMatricula());
                prest.setInt(2, reserva.getNumplaza());
                prest.setString(3, reserva.getPin_fijo());
                prest.setDate(4, Date.valueOf(reserva.getFeciniabono()));
                prest.setDate(5, Date.valueOf(reserva.getFecfinabono()));
                prest.setDouble(6, ReservasVO.obtenerPrecio(reserva.getMatricula()));

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }

    }

    @Override
    public int insertReserva(List<ReservasVO> lista) throws SQLException {
        int numFilas = 0;

        for (ReservasVO tmp : lista) {
            numFilas += insertReserva(tmp);
        }

        return numFilas;
    }

    @Override
    public int deleteReserva(ReservasVO r) throws SQLException {
        int numFilas = 0;

        String sql = "delete from reservas where matricula=? and numplaza?";

        // Sentencia parametrizada
        try (PreparedStatement prest = con.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setString(1, r.getMatricula());
            prest.setInt(2, r.getNumplaza());
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }

    @Override
    public int deletereserva() throws SQLException {

        String sql = "delete from reservas";

        int nfilas = 0;

        // Preparamos el borrado de datos  mediante un Statement
        // No hay parámetros en la sentencia SQL
        try (Statement st = con.createStatement()) {
            // Ejecución de la sentencia
            nfilas = st.executeUpdate(sql);
        }

        // El borrado se realizó con éxito, devolvemos filas afectadas
        return nfilas;
    }

    @Override
    public int updatereserva(String matricula, int numplaza, ReservasVO nuevosDatos) throws SQLException {
        int numFilas = 0;
        String sql = "update reservas set pin_fijo=?, feciniabono=?, fecfinabono=? , precio= ? where matricula=? and numplaza=?";

        if (findByPk(matricula, numplaza) == null) {
            // La reserva a actualizar no existe
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(5, nuevosDatos.getMatricula());
                prest.setInt(6, nuevosDatos.getNumplaza());
                prest.setString(1, nuevosDatos.getPin_fijo());
                prest.setDate(2, Date.valueOf(nuevosDatos.getFeciniabono()));
                prest.setDate(3, Date.valueOf(nuevosDatos.getFecfinabono()));
                prest.setDouble(4, nuevosDatos.getPrecio());
                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }

    @Override
    public int findPlaza(String matricula) throws SQLException {
        ResultSet res = null;
        ReservasVO p = new ReservasVO();
        int numero = 222;

        String sql = "select reservas.numplaza from reservas join clientes on reservas.matricula= clientes.matricula where clientes.matricula=?";
//        String sql2 = "select * from reservas where matricula=? and numplaza=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {
            // Preparamos la sentencia parametrizada
//            prest.setInt(1, pk);
            prest.setString(1, matricula);

            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            res = prest.executeQuery();

            // Nos posicionamos en el primer registro del Resultset. Sólo debe haber una fila
            // si existe esa pk
            if (res.first()) {
                // Recogemos los datos de la reserva, guardamos en un objeto

                int numPlaza = res.getInt("numplaza");
                return numPlaza;
            }

            return numero;
        }

    }
}
