/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import parking.Conexion;

/**
 *
 * @author kroctus
 */
public class ReservasDAO_2 {

    /*select reservas.numplaza
from reservas join clientes
on reservas.matricula= clientes.matricula
where clientes.matricula='1234-OPU';*/
    private Connection con = null;

    public ReservasDAO_2() {
        con = Conexion.getInstance();
    }

    public int findPlaza(String matricula) throws SQLException {
        ResultSet res = null;
        ReservasVO p = new ReservasVO();
        int numPlaza=0;

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
              numPlaza= p.getNumplaza();
                return numPlaza;
            }

            return numPlaza;
        }

    }

}
