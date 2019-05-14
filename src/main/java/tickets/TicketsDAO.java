/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickets;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import parking.Conexion;
import reservas.ReservasVO;

/**
 *
 * @author gabriel
 */
public class TicketsDAO implements ITickets {

    private Connection con = null;

    public TicketsDAO() {
        con = Conexion.getInstance();
    }

    @Override
    public List<TicketsVO> getAll() throws SQLException {

        List<TicketsVO> lista = new ArrayList<>();

        // Preparamos la consulta de datos mediante un objeto Statement
        // ya que no necesitamos parametrizar la sentencia SQL
        try (Statement st = con.createStatement()) {
            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            ResultSet res = st.executeQuery("select * from tickets");
            // Ahora construimos la lista, recorriendo el ResultSet y mapeando los datos
            while (res.next()) {
                TicketsVO p = new TicketsVO();
                // Recogemos los datos de la persona, guardamos en un objeto

                p.setNumplaza(res.getInt("numplaza"));
                p.setMatricula(res.getString("matricula"));
                p.setPin_desechable(res.getString("pin_desechable"));
                p.setFecinipin(res.getDate("fecinipin").toLocalDate());
                p.setFecfinpin(res.getDate("fecfinpin").toLocalDate());

                //Añadimos el objeto a la lista
                lista.add(p);
            }
        }

        return lista;
    }

    @Override
    public TicketsVO findByPk(int pk) throws SQLException {
        ResultSet res = null;
        TicketsVO p = new TicketsVO();

        String sql = "select * from tickets where pk=?";

        try (PreparedStatement prest = con.prepareStatement(sql)) {
            // Preparamos la sentencia parametrizada
            prest.setInt(1, pk);

            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            res = prest.executeQuery();

            // Nos posicionamos en el primer registro del Resultset. Sólo debe haber una fila
            // si existe esa pk
            if (res.first()) {
                // Recogemos los datos de la persona, guardamos en un objeto

                p.setNumplaza(res.getInt("numplaza"));
                p.setMatricula(res.getString("matricula"));
                p.setPin_desechable(res.getString("pin_desechable"));
                p.setFecinipin(res.getDate("fecinipin").toLocalDate());
                p.setFecfinpin(res.getDate("fecfinpin").toLocalDate());
                return p;
            }

            return null;
        }
    }

    @Override
    public int insertTickets(TicketsVO ticket) throws SQLException {
        int numFilas = 0;
        String sql = "insert into tickets values (?,?,?,?,?)";

        if (findByPk(ticket.getNumplaza()) != null) {
            // Existe un registro con esa pk
            // No se hace la inserción
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setInt(1, ticket.getNumplaza());
                prest.setString(2, ticket.getMatricula());
                prest.setString(3, ticket.getPin_desechable());
                prest.setDate(4, Date.valueOf(ticket.getFecinipin()));
                prest.setDate(5, Date.valueOf(ticket.getFecfinpin()));

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }

    }

    @Override
    public int insertTickets(List<TicketsVO> lista) throws SQLException {
        int numFilas = 0;

        for (TicketsVO tmp : lista) {
            numFilas += insertTickets(tmp);
        }

        return numFilas;

    }

    @Override
    public int deleteTickets(TicketsVO t) throws SQLException {

        int numFilas = 0;

        String sql = "delete from tickets where pk = ?";

        ///PROBLEMA AQUÍ LA CLAVE PRIMARIA ES COMPUESTA
        // Sentencia parametrizada
        try (PreparedStatement prest = con.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setInt(1, t.getNumplaza());
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }

    @Override
    public int deleteTickets() throws SQLException {

        String sql = "delete from tickets";

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
    public int updateTickets(int pk, TicketsVO nuevosDatos) throws SQLException {
        int numFilas = 0;
        String sql = "update reservas set numplaza = ?, matricula = ? , pin_desechable=?, fecinipin = ?, fecfinpin = ? where pk=?";

        if (findByPk(pk) == null) {
            // La persona a actualizar no existe
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try (PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setInt(1, nuevosDatos.getNumplaza());
                prest.setString(2, nuevosDatos.getMatricula());
                prest.setString(3, nuevosDatos.getPin_desechable());
                prest.setDate(4, Date.valueOf(nuevosDatos.getFecinipin()));
                prest.setDate(5, Date.valueOf(nuevosDatos.getFecfinpin()));

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }

}
