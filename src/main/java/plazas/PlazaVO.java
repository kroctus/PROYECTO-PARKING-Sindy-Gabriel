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

    //Método que muestra el estado de la plaza.
    public static String obtenerEstado(PlazaVO p) {
        switch (p.estadoPlaza) {
            case 1:
                return "libre";
            case 2:
                return "libre_abono";
            case 3:
                return "ocupada_abono";
            //Tanto en el caso de que el estado de la plaza sea 4 como si el 
            //usuario se equivoca y mete un número mayor que 4 o menor que 1,
            //la plaza aparecerá como ocupada.    
            default:
                return "ocupada";
        }
    }

    //Método que muestra el numero asociado al estado de la plaza.
    public static int obtenerEstado(String estado) {
        switch (estado) {
            case "libre":
                return 1;
            case "libre_abono":
                return 2;
            case "ocupada_abono":
                return 3;
            //Tanto en el caso de que el estado de la plaza sea ocupada como si
            //el usuario se equivoca y mete otro tipo de estado que no se 
            //recoge en nuestro sistema el estado de la plaza aparecerá como 4.    
            default:
                return 4;
        }
    }

    //Método que muestra el tipo de plaza.
    public static String obtenerTipoPlaza(PlazaVO p) {
        switch (p.tipoPlaza) {
            case 1:
                return "plaza_turismos";
            case 2:
                return "plaza_motocicletas";
            //Tanto en el caso de que el tipo de plaza sea 3 como si el 
            //usuario se equivoca y mete un número mayor que 3 o menor que 1,
            //la plaza aparecerá como "plaza_caravanas".    
            default:
                return "plaza_caravanas";
        }
    }

    //Método que muestra el numero asociado al tipo de plaza.
    public static int obtenerTipoPlaza(String tipoPlaza) {
        switch (tipoPlaza) {
            case "plaza_turismos":
                return 1;
            case "plaza_motocicletas":
                return 2;
            //Tanto en el caso de que el tipo de plaza es "plaza_caravanas" como si
            //el usuario se equivoca y mete otro tipo de plaza que no se 
            //recoge en nuestro sistema el estado de la plaza aparecerá como 3.    
            default:
                return 3;
        }
    }
}

