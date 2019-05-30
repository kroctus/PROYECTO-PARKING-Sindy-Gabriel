/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plazas;

/**
 *
 * @author Sindy Ferreira
 */
public class PlazaVO {

    //Atributos:
    private int numPlaza; //Clave primaria en la tabla Plazas
    private int tipoPlaza;
    private int estadoPlaza;
    private double tarifa;

    //Constructores
    public PlazaVO(int numPlaza, int tipoPlaza, int estadoPlaza, double tarifa) {
        this.numPlaza = numPlaza;
        this.tipoPlaza = tipoPlaza;
        this.estadoPlaza = estadoPlaza;
        this.tarifa = tarifa;
    }

    public PlazaVO() {
    }

    //Getters y setters
    public int getNumPlaza() {
        return numPlaza;
    }

    public void setNumPlaza(int numPlaza) {
        this.numPlaza = numPlaza;
    }

    public int getTipoPlaza() {
        return tipoPlaza;
    }

    public void setTipoPlaza(int tipoPlaza) {
        this.tipoPlaza = tipoPlaza;
    }

    public int getEstadoPlaza() {
        return estadoPlaza;
    }

    public void setEstadoPlaza(int estadoPlaza) {
        this.estadoPlaza = estadoPlaza;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    //Método toString()
    @Override
    public String toString() {
        return  numPlaza +  " : " + tipoPlaza
                +  " : "+ estadoPlaza +  " : " + tarifa;
    }
}

