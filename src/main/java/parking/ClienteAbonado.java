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
    
    //Constructores

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
    
    //toString 

    @Override
    public String toString() {
        return "ClienteAbonado{" + "dni=" + dni + ", tipoVehiculo=" + tipoVehiculo + ", matricula=" + matricula + '}';
    }
    
    //Equals and hashCode

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.dni);
        hash = 13 * hash + Objects.hashCode(this.tipoVehiculo);
        hash = 13 * hash + Objects.hashCode(this.matricula);
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
        return true;
    }
    
    
    
}
