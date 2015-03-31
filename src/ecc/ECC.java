/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ecc;

import com.google.common.math.LongMath;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.floor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author Afik & Hayyu
 */
public class ECC {
    
    //Attributes
    private String pesan;
    private byte[] pesanInByte;
    private Point encKey;
    private long decKey;
    private String cipher; //dalam hexa, untuk dibaca lalu diubah ke byte
    private long auxParam; //nilai k yang digunakan saat enkripsi
    private Point basePoint; //asumsi: basepoint yang dipilih user pasti ada di
                            //grup eliptik
    private Curve ec = new Curve();
    
    //Getter - Setter
    public String getPesan (){
        return pesan;
    }
    
    public void setPesan (String s){
        this.pesan = s;
    }
    
    //Read input from file txt 
    public String readFile(String fileInput) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileInput));
        try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();
                }
                return sb.toString();
        } finally {
            br.close();
        }
    }
    
    //Read input file to byte[]
    public byte[] readFileToBytes(String fileInput) throws IOException {
        Path path = Paths.get(fileInput);
        byte[] data = Files.readAllBytes(path);
        return data;
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
    public static void main(String[] args) throws IOException {
        Point pm = new Point();
        ECC eccrypt = new ECC();
        pm = eccrypt.encodeChar(11,20);
//        System.out.println(pm.getX() + ", " + pm.getY());
//        long bChar;
//        bChar = eccrypt.decodeChar(224, 20);
//        System.out.println(bChar);
//        char c = 'a';
//        byte b = (byte)c;
//        System.out.println(b);
        byte[] B = eccrypt.readFileToBytes("D:\\[6]\\IF4020 Kripto\\Tucil 3\\testfile.txt");
        System.out.println(B[0] + "," + B[1] + "," + B.length);
    }
    
}
