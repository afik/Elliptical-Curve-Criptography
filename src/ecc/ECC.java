/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ecc;

import com.google.common.math.LongMath;

/**
 *
 * @author Afik & Hayyu
 */
public class ECC {
    
    //Attributes
    //Point pm;
    private String pesan;
    private Point pb;
    
    //Getter - Setter
    public String getPesan (){
        return pesan;
    }
    
    public void setPesan (String s){
        this.pesan = s;
    }
    
    //Encode
    public Point encodeChar (int m, int k){
        Point pm;
        int it;
        int x;
        for (it=1;it<k;it++){
            x = m*k + it;
//            if (){
//                
//            }
        }
        return pm;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Curve c = new Curve();
        c.setEllipticGrup();
    }
    
}
