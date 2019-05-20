/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickets;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author kroctus
 */
public class TicketsVO {

    
    //Atributos de la clase
    private int numplaza;
    private String matricula;
    private String pin_desechable;
    private LocalDateTime fecinipin;
    private LocalDateTime fecfinpin;
    
    //Constructores

    public TicketsVO(int numplaza, String matricula, String pin_desechable, LocalDateTime fecinipin, LocalDateTime fecfinpin) {
        this.numplaza = numplaza;
        this.matricula = matricula;
        this.pin_desechable = pin_desechable;
        this.fecinipin = fecinipin;
        this.fecfinpin = fecfinpin;
    }

    public TicketsVO() {
    }
    
    

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

    public LocalDateTime getFecinipin() {
        return fecinipin;
    }

    public void setFecinipin(LocalDateTime fecinipin) {
        this.fecinipin = fecinipin;
    }

    public LocalDateTime getFecfinpin() {
        return fecfinpin;
    }

    public void setFecfinpin(LocalDateTime fecfinpin) {
        this.fecfinpin = fecfinpin;
    }

    
    
    // to String

    @Override
    public String toString() {
        return "TicketsVO{" + "numplaza=" + numplaza + ", matricula=" + matricula + ", pin_desechable=" + pin_desechable + ", fecinipin=" + fecinipin + ", fecfinpin=" + fecfinpin + '}';
    }
    
    

}
