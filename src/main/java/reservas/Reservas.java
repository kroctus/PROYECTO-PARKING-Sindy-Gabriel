/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservas;

import clientes.ClienteDAO;
import clientes.ClienteVO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Sindy Ferreira
 */
public class Reservas {
    
    //Método que a partir de la matricula del coche, y consultando su tipo de abono 
    //obtiene el precio del abono del cliente
     public static int obtenerPrecio(String matricula) throws SQLException {
        int tipoAbonado = 0;

        ClienteDAO daoCliente = new ClienteDAO();
        ArrayList<ClienteVO> listaCliente = new ArrayList<>();
        listaCliente = (ArrayList<ClienteVO>) daoCliente.getAll();
        for (ClienteVO clienteVO : listaCliente) {
            if (clienteVO.getMatricula().equalsIgnoreCase(matricula)) {
                tipoAbonado = clienteVO.getTipoAbono();
            }

        }

        if (tipoAbonado != 0) {
            switch (tipoAbonado) {
                //1mensual25 2trimestral70 3semestral130 4anual200
                case 1:
                    return 25;
                case 2:
                    return 70;
                case 3:
                    return 130;
                case 4:
                    return 200;
            }
        }

        return 0;

    }
}
