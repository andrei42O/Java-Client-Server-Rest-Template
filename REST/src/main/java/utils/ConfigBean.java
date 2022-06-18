package utils;


import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBean {
    static SessionFactory sessionFactory = null;

    @Bean
    public static SessionFactory sessionFactory(){
        if(sessionFactory == null){
            initialize();
        }
        System.out.println("Been here");
        return sessionFactory;
    }


    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory =
                    new MetadataSources( registry )
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
