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
    private long kForKoblitz = 20;
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
    
    //set base point
    //ensure it exist in elliptic grup of curve ec
    public int setBasePoint (Point p){
        int retVal = -1;
        if (ec.isPointInGroup(p)){
            this.basePoint = p;
            retVal = 1;
        }
        return retVal;
    }
    
    public void setCipherX (Point p){
        this.cipher_x = p;
    }
    
    public void setCipherInByte (byte[] b){
        this.cipherInByte = b;
    }
    
    //Unsure about this
    public void setCipherYs (ArrayList<Point> alp){
        this.cipher_ys = alp;
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
        pesanInByte = data;
        return data;
    }
    
    
    //Encode using koblitz (long -> Point)
    public Point encodeChar (long m, long k){
        Point pm = new Point();
        boolean encoded = false;
        long it = 1;
        long x, y;
        do{
            x = m*k + it;
            y = ec.getY(x);
            if(y != 0){
                pm.setX(x);
                pm.setY(y);
                encoded = true;
            }
            it++;
        }while (!encoded && it < k);
        
        if (!encoded) {
            pm = Point.O;
        }
        //System.out.println(pm.getX() + " " + pm.getY());
        return pm;
    }
    
    //Decode using koblitz (point.x -> long)
    public char decodeChar (long x, long k){
        long m;
        m = (x-1) / k;
        //double temp = floor((double)m);
        return (char)m;
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
            if (temp != Point.O){
                arrPoint.add(temp);
            }
        }
        return arrPoint;
    }
    
    //Enkripsi ECC El Gamal
    public void Encrypt (){
        Point temp;
        Point kPb;
        kPb = ec.perkalian(encKey, auxParam);
        ArrayList<Point> toEncrypt = new ArrayList<>();
        System.out.println(pesanInByte);
        toEncrypt = getEncodedMsg(pesanInByte);
        this.cipher_x = ec.perkalian(basePoint, auxParam);
        for (int i=0; i < toEncrypt.size(); i++){
            temp = ec.penjumlahan(toEncrypt.get(i), kPb);
            this.cipher_ys.add(temp);
        }
        System.out.println("Point asli hasil enkripsi");
        for(int i=0; i<cipher_ys.size(); i++){
            System.out.println(cipher_ys.get(i).getX() + " " +cipher_ys.get(i).getY());
        }
    }
    
    //Fungsi untuk mengubah pasangan titik ciphertext (cipher_ys) ke notasi hexa
    ////foreach point in cipher_ys di decode dulu, dapet bytenya, convert ke hexa
    public String pointToHexa(ArrayList<Point> alp){
        String hexaString;
        //byte[] temp;
        cipherInByte = new byte[alp.size()];
        for(int i = 0; i < alp.size(); i++){
            this.cipherInByte[i] = (byte)decodeChar(alp.get(i).getX(),kForKoblitz);
        }
        hexaString = byteArrayToHex(cipherInByte);
        return hexaString;
    }
    
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
           sb.append(String.format("%02x ", b & 0xff));
        return sb.toString();
     }
    
    //Fungsi untuk membaca hexa lalu menyimpannya ke cipherInByte, utk dekripsi
    public byte[] hexaToByte (String s){
        int len = s.length();
        byte[] data = new byte[len / 3];
        for (int i = 0; i < len-3; i += 3) {
            data[i/3] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }    
    
    //Fungsi untuk dekripsi: encode cipherInByte, proses per byte, decode
    public void Decrypt (){
        ArrayList<Point> temp = new ArrayList<>();
        ArrayList<Point> toBeDecoded = new ArrayList<>();
        
        Point p_temp;
        Point kB;
        kB = ec.perkalian(basePoint, auxParam);
        Point bkB;
        bkB = ec.perkalian(kB, decKey);
        Point inv_bkB;
        inv_bkB = bkB.inverse();
        for (int i = 0; i < cipherInByte.length ; i++){
            p_temp = encodeChar((long)cipherInByte[i],kForKoblitz);
            temp.add(p_temp);
        }
        System.out.println("Second point encoded in decrypt");
        for (int i=0; i<temp.size(); i++){
            System.out.println(temp.get(i).getX() + " " + temp.get(i).getY());
        }
        for (int i = 0; i < temp.size(); i++){
            p_temp = ec.penjumlahan(temp.get(i), inv_bkB);
            toBeDecoded.add(p_temp);
        }
        System.out.println("Tobedecoded");
        for (int i=0; i<toBeDecoded.size(); i++){
            System.out.println(toBeDecoded.get(i).getX() + " " + toBeDecoded.get(i).getY());
        }
        String decResult = "";
        for (int i=0; i<toBeDecoded.size(); i++){
            decResult += decodeChar(toBeDecoded.get(i).getX(), kForKoblitz);
        }
        System.out.println("Dec result : " + decResult);
        String plaintext = new String(decResult);
        pesan = plaintext;
    }
      
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ECC eccrypt = new ECC();
        Point pm = new Point();
        //pm = eccrypt.encodeChar(11,20);
        
        //set curve
        eccrypt.getCurve().setP(97);
        eccrypt.getCurve().setEllipticGrup();
        eccrypt.setAuxParam(87);
        int retBase = eccrypt.setBasePoint(new Point(93,61));
        
        eccrypt.setPesan("B");
        byte[] pesanByte = new byte[1];
        pesanByte[0] = Byte.parseByte("B");
        eccrypt.setPesanInByte(pesanByte);
        eccrypt.setEncKey(new Point(5682,-597397));
        Point p = eccrypt.encodeChar(Long.getLong("B"),eccrypt.kForKoblitz);
//        eccrypt.Encrypt();
//        String cipherHexa = eccrypt.pointToHexa(eccrypt.getCipherYs());
//        System.out.println("Cipher : ");
//        System.out.println(cipherHexa);
//        
        System.out.println(p.getX() + " " + p.getY());
        //Encrypt
//        String pesan = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum at purus sed metus consequat condimentum. Ut pulvinar consequat eros, sed consectetur dolor commodo nec. Nullam ornare elit at eros luctus faucibus. Duis viverra massa sed lorem fermentum, sit amet dapibus dui finibus. Aenean augue neque, scelerisque et semper in, lacinia ac orci. Aliquam erat volutpat. Nulla sem nunc, varius eget varius ut, tincidunt rutrum velit.";
//        eccrypt.setPesan(pesan);
//        byte[] pesanByte = eccrypt.readFileToBytes("D:\\AFIK\\Project\\ECC\\lorem.txt");
//        eccrypt.setPesanInByte(pesanByte);
//        eccrypt.setEncKey(new Point(5682,-597397));
//        eccrypt.Encrypt();
//        String cipherHexa = eccrypt.pointToHexa(eccrypt.getCipherYs());
//        System.out.println("Cipher : ");
//        System.out.println(cipherHexa);
//        
//        //Decrypt
//        eccrypt.setDecKey(87); //private key
//        eccrypt.setCipher(cipherHexa);
//        eccrypt.setCipherInByte(eccrypt.hexaToByte(cipherHexa));
//        eccrypt.Decrypt();
//        System.out.println(eccrypt.getPesan());
//      
    }

    
}
      
