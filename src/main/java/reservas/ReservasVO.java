/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservas;

import clientes.ClienteDAO;
import clientes.ClienteVO;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabriel
 */
public class ReservasVO {

    //Atributos de la clase
    private String matricula;
    private int numplaza;
    private String pin_fijo;
    private LocalDate feciniabono;
    private LocalDate fecfinabono;
    private double precio;

    //Constructor por defecto
    public ReservasVO() {
    }

    //Constructor parametrizado
    public ReservasVO(String matricula, int numplaza, String pin_fijo, LocalDate feciniabono, LocalDate fecfinabono) throws SQLException {
        this.matricula = matricula;
        this.numplaza = numplaza;
        this.pin_fijo = pin_fijo;
        this.feciniabono = feciniabono;
        this.fecfinabono = fecfinabono;
        try {
            this.precio = ReservasVO.obtenerPrecio(this.matricula);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    //Getters and setters
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getNumplaza() {
        return numplaza;
    }

    public void setNumplaza(int numplaza) {
        this.numplaza = numplaza;
    }

    public String getPin_fijo() {
        return pin_fijo;
    }

    public void setPin_fijo(String pin_fijo) {
        this.pin_fijo = pin_fijo;
    }

    public LocalDate getFeciniabono() {
        return feciniabono;
    }

    public void setFeciniabono(LocalDate feciniabono) {
        this.feciniabono = feciniabono;
    }

    public LocalDate getFecfinabono() {
        return fecfinabono;
    }

    public void setFecfinabono(LocalDate fecfinabono) {
        this.fecfinabono = fecfinabono;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    //ToString
    @Override
    public String toString() {
        try {
            return matricula + " : " + numplaza + " : " + pin_fijo + " : " + feciniabono + " : " + fecfinabono + " : " + obtenerPrecio(matricula);
        } catch (SQLException ex) {
            Logger.getLogger(ReservasVO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return matricula + " : " + numplaza + " : " + pin_fijo + " : " + feciniabono + " : " + fecfinabono + " : " + precio;
    }

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
