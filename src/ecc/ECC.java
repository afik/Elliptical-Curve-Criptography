/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ecc;

import com.google.common.math.LongMath;
import java.util.ArrayList;

/**
 *
 * @author Afik & Hayyu
 */
public class ECC {
    
    //Attributes
    //Point pm;
    private String pesan;
    private Point pb;
    private Curve ec;
    
    //Getter - Setter
    public String getPesan (){
        return pesan;
    }
    
    public void setPesan (String s){
        this.pesan = s;
    }
    
    //Encode
    public Point encodeChar (long m, long k){
        Point pm;
        long it = 1;
        long x, y;
        ec.setEllipticGrup();
        do {
            x = m*k + it;
            y = 
        }
        return pm;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Curve c = new Curve();
        //c.setEllipticGrup();
        
        ArrayList<Point> tes = new ArrayList();
        tes.add(new Point(2,2));
        Point p = new Point(2,2);
        System.out.println(tes.contains(p));
    }
    
}
