/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.rFactorServer.Guage;

/**
 *
 * @author Brendan
 */
public class Guage {
    public int length;
    
    public Guage(int length){
        this.length = length;
    }
    
    public String formatedIntGuage(double value){
        String formatedString = "" + (int) value;
        formatedString = formatedGuage(formatedString);
        return formatedString;
    }
    
    public String formatedDoubleGuage(double value){
        String formatedString = String.format("%.1f", value);
        length++;
        formatedString = formatedGuage(formatedString);
        length--;
        return formatedString;
    }
    
    private String formatedGuage(String formatedString){
        for(int i = formatedString.length(); i < length; i++){
            formatedString = "0" + formatedString;
        }
        return formatedString;
    }
}
