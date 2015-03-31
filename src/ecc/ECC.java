/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ecc;

import com.google.common.math.LongMath;
import static java.lang.Math.floor;
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
    private Curve ec = new Curve();
    
    //Getter - Setter
    public String getPesan (){
        return pesan;
    }
    
    public void setPesan (String s){
        this.pesan = s;
    }
    
    //Encode
    public Point encodeChar (long m, long k){
        Point pm = new Point();
        long it = 1;
        long x, y;
        do{
            x = m*k + it;
            y = ec.getY(x);
            if(y != 0){
                pm.setX(x);
                pm.setY(y);
            }
            it++;
        }while (ec.getY(x) == 0 && it < k);
        
        return pm;
    }
    
    //Decode
    public long decodeChar (long x, long k){
        long m;
        m = (x-1) / k;
        double temp = floor((double)m);
        return (long)temp;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        ec = new Curve();
//        long x = 2;
//        System.out.println(c.getY(x));
        Point pm = new Point();
        ECC eccrypt = new ECC();
        pm = eccrypt.encodeChar(11,20);
        System.out.println(pm.getX() + ", " + pm.getY());
        long bChar;
        bChar = eccrypt.decodeChar(224, 20);
        System.out.println(bChar);
//        ArrayList<Point> tes = new ArrayList();
//        tes.add(new Point(2,2));
//        Point p = new Point(2,2);
//        System.out.println(tes.contains(p));
    }
    
    public char decode(Point p, int k) {
        char ret;
        long val = (p.getX()-1)/k;
        System.out.println(val);
        ret = (char) LongMath.mod(val, 255);
        return ret;
    }

    
}
      
