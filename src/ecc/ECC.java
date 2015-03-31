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
 * @author Afik
 */
public class ECC {
    Curve curve = new Curve(); 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ECC ecc = new ECC();
        Curve c = new Curve();
        long x = 2;
        Point p = new Point(224,248);
        System.out.println(ecc.decode(p,20));
        
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
