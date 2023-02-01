import java.io.IOException;

public class ex1 {
    public static void main(String[] args) throws IOException{
        byte[] b = new byte[128];
        int lidos = System.in.read(b);
        String s = new String(b,0,lidos-1);
        int valor = Integer.parseInt(s.substring(0,lidos-1));
        valor++;
        System.out.println("Sucessor: " +valor);
    }
}
