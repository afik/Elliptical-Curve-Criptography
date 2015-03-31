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
    private String encrypt;
    private Point basePoint;
    private long k;
    private Curve ec = new Curve();
    
    //Getter - Setter
    public String getPesan (){
        return pesan;
    }
    
    public void setPesan (String s){
        this.pesan = s;
    }
    
    public void setBasePoint(Point bp){
        
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
     * Encrypt message to point
     */
    public Point EnkripsiKarakter(char ch){
        return new Point();
    }
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Point pm = new Point();
//        ECC eccrypt = new ECC();
//        pm = eccrypt.encodeChar(11,20);
//        System.out.println(pm.getX() + ", " + pm.getY());
//        long bChar;
//        bChar = eccrypt.decodeChar(224, 20);
//        System.out.println(bChar);
//        
        Curve cur = new Curve();
        cur.setEllipticGrup();
        Point po = new Point(2,2);
        System.out.println(cur.isPointInGroup(po));
    }

    
}
      
