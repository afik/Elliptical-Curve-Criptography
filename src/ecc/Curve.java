package ecc;

import java.util.ArrayList;
import com.google.common.math.LongMath;
import java.math.RoundingMode;


/**
 *
 * @author Afik
 */
public class Curve {
    //Elliptic curves we used is short Weierstrass, brainpoolP256t1
    //y^2 = x^3-1x+188
    
    private long a = 1;
    private long b = 6;
    private long p = 11;
    public ArrayList<Point> ellipticGroup;
    
    public void setEllipticGrup(){
       ellipticGroup = new ArrayList<>();
       long y2, aCongruence, y;
       boolean found = false;
       long x = 0;
       long y3;
       
       while (x < p-1){
            System.out.println("x: " +x);
            y2 = (x*x*x + a*x + b);
            System.out.println("y2: " +y2);
            aCongruence = LongMath.mod(y2, p);
            System.out.println("aCong: " +aCongruence);
            for (int j = 1; j<p-1; j++){
                y3 = p*j + aCongruence;
                System.out.println("y3: " +y3);
                if (isPerfectSquare(y3)) {
                    y = LongMath.sqrt(y3, RoundingMode.UP);
                    Point po = new Point(x,y);
                    if (!ellipticGroup.contains(po)){
                        ellipticGroup.add(po);
                        System.out.println("masuk: " +po.getX() +"," +po.getY());
                    }
                    Point pp = new Point(x,p-y);
                    if (!ellipticGroup.contains(pp)) {
                        ellipticGroup.add(pp);
                        System.out.println("masuk: " +pp.getX() +"," +pp.getY());

                    }
                    
                }
            }
            x++;
       }
       
       System.out.println();
       System.out.println(ellipticGroup.size());
       for (int i =0; i<ellipticGroup.size(); i++){
           System.out.println(ellipticGroup.get(i).getX() + " " +ellipticGroup.get(i).getY());
       }
    }
    
    public final static boolean isPerfectSquare(long n)
    {
        if (n < 0)
            return false;

        switch((int)(n & 0xF))
        {
        case 0: case 1: case 4: case 9:
            long tst = (long)Math.sqrt(n);
            return tst*tst == n;

        default:
            return false;
        }
    }
    
    //Mengembalikan titik hasil penjumlahan sebuah titik pada dirinya sendiri
    public Point penjumlahan(Point q, Point t){
        Point r = new Point();
        if (q == Point.O){
            r.setX(t.getX());
            r.setY(t.getY());
        } else if (t == Point.O){
            r.setX(q.getX());
            r.setY(q.getY());
        } else if (t.inverse() == q){
            r = Point.O;
        } else if (t.getX() == q.getX()){
            r = Point.O;
        } else {
            long lambda = (t.getY() - q.getY())/(t.getX() - q.getX());  //Menghitung gradien garis
            long _x = lambda * lambda - t.getX() - q.getX();
            long _y = lambda * (t.getX() - _x) - t.getY();
            r.setX(_x);
            r.setY(_y);
        }
        return r;
    }
    
    //Mengembalikan titik hasil penjumlahan sebuah titik pada dirinya sendiri
    public Point penggandaan(Point p){
        if (p.getY() == 0){
            return Point.O;
        } else {
            Point r = new Point();
            long lambda = (3 * p.getX() * p.getX() + this.a)/(2 * p.getY());  //Menghitung gradien garis
            long _x = lambda * lambda - 2 * p.getX();
            long _y = lambda * (p.getX() - _x) - p.getY();
            r.setX(_x);
            r.setY(_y);            
            return r;
        }
    }
    
    //Mengembalikan titik hasil penjumlahan sebuah titik sebanyak k-1 kali terhadap dirinya sendiri
    public Point pelelaran(Point p, long k){
        Point r = new Point(p.getX(), p.getY());
        for (long i=1; i<k-1; i++){
            r = penjumlahan(r,p);
        }
        return r;
    }
    
    // Perkalian titik diperoleh dengan perulangan dua operasi dasar
    // kurva eliptik yang sudah dijelaskan:
    // 1. Penjumlahan titik (P + Q = R)
    // 2. Penggandaan titik (2P = R)
    public Point perkalian(Point p, long k){
        Point r = new Point();
        if (k == 1){
            r.setX(p.getX());
            r.setY(p.getY());
        } else {
            r.setX(perkalian(p,k/2).getX());
            r.setY(perkalian(p,k/2).getY());
            r = penggandaan(r);
            if (k % 2 == 1){
                r = penjumlahan(r,p);
            }
        }
        return r;
    }
    
}
