import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import persistance.IRepositoryORMUser;
import persistance.RepositoryORMUser;
import server.UserService;
import services.IServices;
import services.ServiceException;
import utils.AbstractServer;
import utils.ObjectConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class StartObjectServer {
    private static int defaultPort = 55555;

    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(StartObjectServer.class.getResourceAsStream("/props.properties"));
            System.out.println("Props loaded");
            props.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find props.properties");
            return;
        }

        int port = defaultPort;

        try{
            port = Integer.parseInt(props.getProperty("port"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        System.out.println("Starting server on port: " + port);


        IServices serv =  getService();

        AbstractServer server = new ObjectConcurrentServer(port, serv);
        try{
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server: " + e.getMessage());
        }


    }

    private static IServices getService() {
        initialize();
        IRepositoryORMUser repoUser = new RepositoryORMUser(sessionFactory);
        return new UserService(repoUser);
    }

    static SessionFactory sessionFactory;

    static void initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry )
                    .buildMetadata()
                    .buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception " + e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }
}
