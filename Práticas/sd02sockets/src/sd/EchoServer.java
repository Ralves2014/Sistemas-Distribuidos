package sd;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private int serverPort;	
    
    public EchoServer(int p) {
	serverPort= p;		
    }
    
    
    public static void main(String[] args){
	System.err.println("SERVER...");
	if (args.length<1) {
	    System.err.println("Missing parameter: port number");	
	    System.exit(1);
	}
	int p=0;
	try {
	    p= Integer.parseInt( args[0] );
	}
	catch (Exception e) {
	    e.printStackTrace();
	    System.exit(2);
	}
	
	
	EchoServer serv= new EchoServer(p);
	serv.servico();   // activa o servico
    }

    
    // activa o servidor no porto indicado em "serverPort"
    public void servico() {
	
	// exercicio 2: inicializar um socket para aceitar ligacoes...
        try{
            byte[] b = new byte[256];
            ServerSocket ssock = new ServerSocket(serverPort);
            // ciclo de atendimento do servico
            while(true){
                // esperar por ligacoes
                Socket s = ssock.accept();
                // ler o pedido
                InputStream socketIn= s.getInputStream();
                int lidos = socketIn.read(b);

                String resp = new String(b,0,lidos);
                System.out.println("DO CLIENTE: "+ resp);
                
                OutputStream socketOut = s.getOutputStream();
                socketOut.write(b,0,lidos);
                
                // terminar a ligacao com o cliente
                s.close();
            }
            // terminar o serviço
            //ssock.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }


}
