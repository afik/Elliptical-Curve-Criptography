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
import static sun.security.krb5.Confounder.bytes;

/**
 *
 * @author Afik & Hayyu
 */
public class ECC {
    
    //Attributes
    private long kForKoblitz;
    private String pesan;
    private byte[] pesanInByte;
    private Point encKey;
    private long decKey;
    private String cipher; //dalam hexa, untuk dibaca lalu diubah ke byte
    private long auxParam; //nilai k yang digunakan saat enkripsi
    private Point basePoint; //asumsi: basepoint yang dipilih user pasti ada di
                            //grup eliptik
    private Curve ec = new Curve();
    private Point cipher_x;
    private ArrayList<Point> cipher_ys = new ArrayList<>();
    private byte[] cipherInByte; 

    public ECC() {
        this.kForKoblitz = 20; //for simplicity, dibuat selalu tetap
    }
    
    //Getter - Setter
    public String getPesan (){
        return pesan;
    }
    
    public byte[] getPesanInByte (){
        return pesanInByte;
    }
    
    public Point getEncKey (){
        return encKey;
    }
    
    public long getDecKey (){
        return decKey;
    }
    
    public String getCipher (){
        return cipher;
    }
    
    public long getAuxParam (){
        return auxParam;
    }
    
    public Point getBasePoint (){
        return basePoint;
    }
    
    public Curve getCurve (){
        return ec;
    }
    
    public Point getCipherX (){
        return cipher_x;
    }
    
    public ArrayList<Point> getCipherYs (){
        return cipher_ys;
    }
    
    public byte[] getCipherInByte (){
        return cipherInByte;
    }
    
    public void setPesan (String s){
        this.pesan = s;
    }
    
    public void setPesanInByte (byte[] b){
        this.pesanInByte = b;
    }
    
    public void setEncKey (Point p){
        this.encKey = p;
    }
    
    public void setDecKey (long l){
        this.decKey = l;
    }
    
    public void setCipher (String s){
        this.cipher = s;
    }
    
    public void setAuxParam (long k){
        this.auxParam = k;
    }
    
    public void setBasePoint (Point p){
        this.basePoint = p;
    }
    
    public void setCipherX (Point p){
        this.cipher_x = p;
    }
    
    public void setCipherInByte (byte[] b){
        this.cipherInByte = b;
    }
    
    //Unsure about this
//    public void setCipherYs (ArrayList<Point> alp){
//        this.cipher_ys = alp;
//    }
    //Unsure about setting curve
//    public void setCurve (Curve c){
//        this.ec = c;
//    }
    
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
    
    //Get pesan yang sudah di-encode
    public ArrayList<Point> getEncodedMsg(byte[] toBeEncoded) {
        ArrayList<Point> arrPoint = new ArrayList<>();
        byte b;
        Point temp;
        int len = toBeEncoded.length;
        for (int i=0; i < len; i++){
            b = toBeEncoded[i];
            temp = encodeChar((long)b,kForKoblitz); //bisakah cast byte ke long?
            arrPoint.set(i, temp);
        }
        return arrPoint;
    }
    
    //Enkripsi ECC El Gamal
    public void Encrypt (){
        Point temp;
        Point kPb;
        kPb = ec.perkalian(encKey, auxParam);
        ArrayList<Point> toEncrypt = new ArrayList<>();
        toEncrypt = getEncodedMsg(pesanInByte);
        this.cipher_x = ec.perkalian(basePoint, auxParam);
        for (int i=0; i < toEncrypt.size(); i++){
            temp = ec.penjumlahan(toEncrypt.get(i), kPb);
            this.cipher_ys.set(i, temp);
        }
    }
    
    //Fungsi untuk mengubah pasangan titik ciphertext (cipher_ys) ke notasi hexa
    ////foreach point in cipher_ys di decode dulu, dapet bytenya, convert ke hexa
    public String pointToHexa(ArrayList<Point> alp){
        String hexaString;
        //byte[] temp;
        for(int i = 0; i < alp.size(); i++){
            this.cipherInByte[i] = (byte)decodeChar(alp.get(i).getX(),kForKoblitz);
        }
        hexaString = byteArrayToHex(cipherInByte);
        return hexaString;
    }
    
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
           sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
     }
    
    //Fungsi untuk membaca hexa lalu menyimpannya ke cipherInByte, utk dekripsi
    
    
    
    //Fungsi untuk dekripsi: encode cipherInByte, proses per byte, decode
    
    
    
    
    
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
