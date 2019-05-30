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
public class Cliente {
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
