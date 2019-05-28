/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import java.util.Objects;

/**
 *
 * @author gabriel
 */

//Clase que nos permite guardar los atributos de la clase abonado para encapsularlo y pasarlo a un arrays de 
//clientes abonados
public class ClienteAbonado {
    
    
    //Atributos de la clase
    
    private String dni;
    private String tipoVehiculo;
    private String matricula;
    private String pin;
    
    //Constructores

    public ClienteAbonado(String dni, String tipoVehiculo, String matricula, String pin) {
        this.dni = dni;
        this.tipoVehiculo = tipoVehiculo;
        this.matricula = matricula;
        this.pin = pin;
    }
    
     public ClienteAbonado(String dni, String tipoVehiculo, String matricula) {
        this.dni = dni;
        this.tipoVehiculo = tipoVehiculo;
        this.matricula = matricula;
       
    }
    


    public ClienteAbonado() {
    }
    
    //Getters and setters

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    
    
    
    //toString 

    @Override
    public String toString() {
        return   "dni: " + dni + ", tipoVehiculo: " + tipoVehiculo + ", matricula: " + matricula + " pin : " + pin;
    }
    
    //Equals and hashCode

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.dni);
        hash = 59 * hash + Objects.hashCode(this.tipoVehiculo);
        hash = 59 * hash + Objects.hashCode(this.matricula);
        hash = 59 * hash + Objects.hashCode(this.pin);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClienteAbonado other = (ClienteAbonado) obj;
        if (!Objects.equals(this.dni, other.dni)) {
            return false;
        }
        if (!Objects.equals(this.tipoVehiculo, other.tipoVehiculo)) {
            return false;
        }
        if (!Objects.equals(this.matricula, other.matricula)) {
            return false;
        }
        if (!Objects.equals(this.pin, other.pin)) {
            return false;
        }
        return true;
    }

    
    
    
    
}
