/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

/**
 *
 * @author Sindy Ferreira
 */
public class ClienteVO {

    //Atributos:
    private String matricula; //La tabla cliente tiene como pk matricula
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String numTarjeta;
    private int tipoAbono;
    private String email;

    //Constructores
    public ClienteVO(String matricula, String dni, String nombre, String apellido1,
            String apellido2, String numTarjeta, int tipoAbono, String email) {
        this.matricula = matricula;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.numTarjeta = numTarjeta;
        this.tipoAbono = tipoAbono;
        this.email = email;
    }

    public ClienteVO() {
    }

    //Getters y setters
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public int getTipoAbono() {
        return tipoAbono;
    }

    public void setTipoAbono(int tipoAbono) {
        this.tipoAbono = tipoAbono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Método toString()
    @Override
    public String toString() {
        return matricula +  " : "+ dni
                +  " : "+ nombre +  " : " + apellido1
                +  " : " + apellido2 +  " : " + numTarjeta
                +  " : " + tipoAbono + " : " + email;
    }
}
