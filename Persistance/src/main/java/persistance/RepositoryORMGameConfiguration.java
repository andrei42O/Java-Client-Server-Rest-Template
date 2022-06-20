package persistance;

import model.GameConfiguration;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Random;

@Component
public class RepositoryORMGameConfiguration implements IRepositoryORMGameConfiguration{
    private static SessionFactory sessionFactory = null;

    public RepositoryORMGameConfiguration(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public GameConfiguration add(GameConfiguration elem) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(elem);
                tx.commit();
                return elem;
            } catch (Exception e) {
                e.printStackTrace();
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }

    @Override
    public GameConfiguration delete(GameConfiguration elem) {
        return null;
    }

    @Override
    public GameConfiguration update(GameConfiguration elem, Long id) {
        return null;
    }

    @Override
    public GameConfiguration findById(Long id) {
        return null;
    }

    @Override
    public Iterable<GameConfiguration> findAll() {
        return null;
    }

    @Override
    public Collection<GameConfiguration> getAll() {
        return null;
    }

    @Override
    public GameConfiguration getRandomConfiguration() {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Integer result = session.createQuery("SELECT COUNT(*) FROM GameConfiguration", Integer.class)
                        .uniqueResult();
                Random rand = new Random();
                int randomPosition = rand.nextInt(result);
                GameConfiguration model =
                        session.createQuery("FROM GameConfiguration", GameConfiguration.class)
                                .setFirstResult(randomPosition)
                                .uniqueResult();
                tx.commit();

                return model;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la incarcatoare tuturor entitatilor din baza de date "+ex);
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }
}
