/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehiculos;

/**
 *
 * @author Sindy Ferreira
 */
public class VehiculoVO {

    //Atributos: 
    private String matricula;
    private int tipoVehiculo;

    //Constructores:
    public VehiculoVO(String matricula, int tipoVehiculo) {
        this.matricula = matricula;
        this.tipoVehiculo = tipoVehiculo;
    }

    public VehiculoVO() {
    }

    //Getters y setters
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(int tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    //Método toString()
    @Override
    public String toString() {
        return matricula + " : " + tipoVehiculo;
    }
}
