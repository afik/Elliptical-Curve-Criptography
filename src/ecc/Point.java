package ecc;

/**
 *
 * @author Afik & Hayyu
 */
public class Point {
    private long x;
    private long y;
    private long a = 1;
    public static Point O = new Point(Long.MAX_VALUE, Long.MAX_VALUE);

    public Point() {
        this.x = 0;
        this.y = 0; 
    }
    
    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getA() {
        return a;
    }

    public void setA(long a) {
        this.a = a;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }
    
    public Point copy(){
        Point r = new Point(this.x, this.y);
        return r;
    }
    
    //Mengembalikan titik inverse
    public Point inverse(){
        Point r = new Point(this.x, -this.y);
        return r;
    }
    
    //Returns a string representation of point
    public String toString(){
        String r = "" + x + " " + y;
        return r;
    }
}
