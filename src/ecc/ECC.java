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