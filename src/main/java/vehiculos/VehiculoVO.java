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

    //Método que muestra el tipo de vehiculo
    public static String obtenerTipoVehiculo(VehiculoVO v) {
        switch (v.getTipoVehiculo()) {
            case 1:
                return "turismos";
            case 2:
                return "motocicletas";
            //Tanto en el caso de que el tipo de vehiculo sea 3 como si el 
            //usuario se equivoca y mete un número mayor que 3 o menor que 1,
            //el vehiculo aparecerá como tipo caravana.    
            default:
                return "caravanas";
        }
    }

    //Método que muestra el número asociado al tipo de vehiculo.
    public static int obtenerTipoVehiculo(String tipoVehiculo) {
        switch (tipoVehiculo) {
            case "turismos":
                return 1;
            case "motocicletas":
                return 2;
            //Tanto en el caso de que el tipo de vehiculo es "caravanas" como si
            //el usuario se equivoca y mete otro tipo de vehiculo que no se 
            //recoge en nuestro sistema el tipo del vehiculo aparecerá como 3. 
            default:
                return 3;
        }
    }
}
