/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import java.time.LocalDate;

/**
 *
 * @author Sindy Ferreira
 */
class ClienteVO {

    //Atributos:
    private String matricula; //La tabla cliente tiene como pk matricula
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private int numTarjeta;
    private int tipoAbono;
    private String email;
    private LocalDate fecIniAbono;
    private LocalDate fecFinAbono;

    //Constructores
    public ClienteVO(String matricula, String dni, String nombre, String apellido1,
            String apellido2, int numTarjeta, int tipoAbono, String email,
            LocalDate fecIniAbono, LocalDate fecFinAbono) {
        this.matricula = matricula;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.numTarjeta = numTarjeta;
        this.tipoAbono = tipoAbono;
        this.email = email;
        this.fecIniAbono = fecIniAbono;
        this.fecFinAbono = fecFinAbono;
    }

    public ClienteVO(String matricula, String dni, String nombre, String apellido1,
            String apellido2, int numTarjeta, int tipoAbono, String email,
            LocalDate fecIniAbono) {
        this.matricula = matricula;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.numTarjeta = numTarjeta;
        this.tipoAbono = tipoAbono;
        this.email = email;
        this.fecIniAbono = fecIniAbono;
        this.fecFinAbono = LocalDate.of(1, 1, 1);
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

    public int getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(int numTarjeta) {
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

    public LocalDate getFecIniAbono() {
        return fecIniAbono;
    }

    public void setFecIniAbono(LocalDate fecIniAbono) {
        this.fecIniAbono = fecIniAbono;
    }

    public LocalDate getFecFinAbono() {
        return fecFinAbono;
    }

    public void setFecFinAbono(LocalDate fecFinAbono) {
        this.fecFinAbono = fecFinAbono;
    }

    //Método toString()
    @Override
    public String toString() {
        return "ClienteVO" + "matricula=" + matricula + ", dni=" + dni
                + ", nombre=" + nombre + ", apellido1=" + apellido1
                + ", apellido2=" + apellido2 + ", numTarjeta=" + numTarjeta
                + ", tipoAbono=" + tipoAbono + ", email=" + email
                + ", fecIniAbono=" + fecIniAbono + ", fecFinAbono=" + fecFinAbono;
    }

    //Método que muestra el número asociado al tipo de abono.
    public static int obtenerTipoAbono(String tipoAbono) {
        switch (tipoAbono) {
            case "mensual":
                return 1;
            case "trimestral":
                return 2;
            case "semestral":
                return 3;
            //Tanto en el caso de que el tipo de abono es "anual" como si
            //el usuario se equivoca y mete otro tipo de abono que no se 
            //recoge en nuestro sistema el tipo de abono aparecerá como 4.     
            default:
                return 4;
        }
    }

    //Método que muestra el tipo de abono
    public static String obtenerTipoAbono(ClienteVO c) {
        switch (c.getTipoAbono()) {
            case 1:
                return "mensual";
            case 2:
                return "trimestral";
            case 3:
                return "semestral";
            //Tanto en el caso de que el tipo de abono sea 4 como si el 
            //usuario se equivoca y mete un número mayor que 4 o menor que 1,
            //el cliente tendrá abono tipo "anual".    
            default:
                return "anual";
        }
    }
}
