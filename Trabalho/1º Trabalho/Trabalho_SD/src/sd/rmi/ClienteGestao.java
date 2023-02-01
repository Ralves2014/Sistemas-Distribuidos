package sd.rmi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class ClienteGestao {

    public static void showMenu() {
        System.out.println(" _____________		        BEM VINDO ADMIN	                   _____________");
        System.out.println("|	Insere o comando que quer executar:                                     |");
        System.out.println("|	la - Listar anúncios por estado	                                        |");
        System.out.println("|	od - Obter detalhes de um anúncio                                       |");
        System.out.println("|	aa - Aprovar um anúncio                                                 |");
        System.out.println("|	ae - Alterar o estado de um anúncio                                     |");
        System.out.println("|	s - Sair                                                                |");
        System.out.println("|_______________________________________________________________________________|");
        System.out.println("");
    }

    public static void main(String[] args) {
        try {

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String regHost = "localhost";
            String regPort = "9000";  // porto do binder

            if (args.length != 2) {
                System.out.println("Utilizar o seguinte comando: java -classpath build/classes:resources/postgresql.jar sd.rmi.ClienteGestao registryHost registryPort");
                System.exit(1);
            }
            regHost = args[0];
            regPort = args[1];

            Anuncio anuncio
                    = (Anuncio) java.rmi.Naming.lookup("rmi://"
                            + regHost + ":" + regPort + "/Anuncio");

            showMenu();

            String command_choice;
            int num;
            while (true) {
                System.out.print("->");
                command_choice = input.readLine();

                switch (command_choice) {
                    case "la":
                        LinkedList<Anuncio_Entity> list_a = anuncio.listarAnuncios_ativos();
                        if(list_a.isEmpty())
                            System.out.println("Não há anúncios ativos.");
                        else
                            System.out.println("Lista de anúncios ativos:");
                        for (int i = 0; i < list_a.size(); i++) {
                            System.out.println(anuncio.print_anuncio(list_a.get(i).getaid(), list_a.get(i).getlocalizacao(), 
                                    list_a.get(i).getpreco(), list_a.get(i).getdata(), list_a.get(i).getgenero(), list_a.get(i).getanunciante(), 
                                    list_a.get(i).gettipo_alojamento(), list_a.get(i).gettipo_anuncio(), list_a.get(i).getestado()));
                        }
                        
                        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        System.out.println("");
                        
                        LinkedList<Anuncio_Entity> list_i = anuncio.listarAnuncios_inativos();
                        if(list_i.isEmpty()) {
                            System.out.println("Não há anúncios inativos.");
                        }
                        else
                            System.out.println("Lista de anúncios inativos:");
                        for (int i = 0; i < list_i.size(); i++) {
                            System.out.println(anuncio.print_anuncio(list_i.get(i).getaid(), list_i.get(i).getlocalizacao(),
                                    list_i.get(i).getpreco(), list_i.get(i).getdata(), list_i.get(i).getgenero(), list_i.get(i).getanunciante(), 
                                    list_i.get(i).gettipo_alojamento(), list_i.get(i).gettipo_anuncio(), list_i.get(i).getestado()));
                        }
                        System.out.println("#################################################################");
                        break;
                    case "od":
                        System.out.print("Id do anúncio -> ");
                        num=Integer.parseInt(input.readLine());
                        String detalhes = anuncio.get_details(num);
                        if (detalhes.length()==0) {
                            System.out.println("Anúncio não existente");
                        }
                        else
                            System.out.println(detalhes);
                        System.out.println("#################################################################");
                        anuncio.get_details(num);
                        break;
                    case "aa":
                        System.out.print("Id do anúncio -> ");
                        String num_a;
                        num_a=input.readLine();
                        System.out.println(anuncio.aprove_anuncio(num_a));
                        System.out.println("#################################################################");
                        break;
                    case "ae":
                        System.out.print("Id do anúncio -> ");
                        String num_b;
                        num_b=input.readLine();
                        System.out.println(anuncio.change_state(num_b));
                        System.out.println("#################################################################");
                        break;
                    case "s":
                        System.out.println("Sessão encerrada!");
                        System.exit(0);
                    default:
                        System.out.println("Comando inválido, tente novamente outro comando");
                        System.out.println("");
                        showMenu();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
