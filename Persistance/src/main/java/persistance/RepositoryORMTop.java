package persistance;

import model.Result;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class RepositoryORMTop implements IRepositoryORMTop{
    private static SessionFactory sessionFactory = null;

    public RepositoryORMTop(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Result add(Result elem) {
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
    public Result delete(Result elem) {
        return null;
    }

    @Override
    public Result update(Result elem, Long id) {
        return null;
    }

    @Override
    public Result findById(Long id) {
        return null;
    }

    @Override
    public Iterable<Result> findAll() {
        return null;
    }

    @Override
    public Collection<Result> getAll() {
        return null;
    }

    @Override
    public Collection<Result> findByUser(User user) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Result> flights =
                        session.createQuery("FROM Result WHERE user_id = :user_id", Result.class)
                                .setParameter("user_id", user.getID())
                                .list();
                tx.commit();

                return flights;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la incarcatoare tuturor entitatilor din baza de date "+ex);
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }
}
