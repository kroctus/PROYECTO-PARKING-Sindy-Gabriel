/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickets;

import java.time.LocalDate;

/**
 *
 * @author kroctus
 */
public class TicketsVO {

    
    //Atributos de la clase
    private int numplaza;
    private String matricula;
    private String pin_desechable;
    private LocalDate fecinipin;
    private LocalDate fecfinpin;
    
    //Constructores

    public TicketsVO(int numplaza, String matricula, String pin_desechable, LocalDate fecinipin, LocalDate fecfinpin) {
        this.numplaza = numplaza;
        this.matricula = matricula;
        this.pin_desechable = pin_desechable;
        this.fecinipin = fecinipin;
        this.fecfinpin = fecfinpin;
    }

    public TicketsVO() {
    }
    
    //Getters and setters

    public int getNumplaza() {
        return numplaza;
    }

    public void setNumplaza(int numplaza) {
        this.numplaza = numplaza;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getPin_desechable() {
        return pin_desechable;
    }

    public void setPin_desechable(String pin_desechable) {
        this.pin_desechable = pin_desechable;
    }

    public LocalDate getFecinipin() {
        return fecinipin;
    }

    public void setFecinipin(LocalDate fecinipin) {
        this.fecinipin = fecinipin;
    }

    public LocalDate getFecfinpin() {
        return fecfinpin;
    }

    public void setFecfinpin(LocalDate fecfinpin) {
        this.fecfinpin = fecfinpin;
    }
    
    // to String

    @Override
    public String toString() {
        return "TicketsVO{" + "numplaza=" + numplaza + ", matricula=" + matricula + ", pin_desechable=" + pin_desechable + ", fecinipin=" + fecinipin + ", fecfinpin=" + fecfinpin + '}';
    }
    
    

}
