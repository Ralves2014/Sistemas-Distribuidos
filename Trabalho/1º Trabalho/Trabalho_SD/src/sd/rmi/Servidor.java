package sd.rmi;

/**
 * Servidor
 */
public class Servidor {

    public static void main(String[] args) {

        int regPort = 1099;

        if (args.length != 5) {
            System.out.println("Utilizar o seguinte comando: java -classpath build/classes:resources/postgresql.jar sd.rmi.Servidor registryPort registryHost data_base_name username password");
            System.exit(1);
        }

        try {
            regPort = Integer.parseInt(args[0]);
            
            PostgresConnector connection = new PostgresConnector(args[1],args[2],args[3],args[4]);
            connection.connect();
            
            Anuncio anuncio = new AnuncioImpl(connection);
            
            java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(regPort);
            
            registry.rebind("Anuncio", anuncio);

            System.out.println("RMI object bound to service in registry");

            System.out.println("servidor pronto");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
