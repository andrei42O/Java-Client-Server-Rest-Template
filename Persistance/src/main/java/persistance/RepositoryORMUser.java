package persistance;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class RepositoryORMUser implements IRepositoryORMUser{
    private static SessionFactory sessionFactory = null;

    public RepositoryORMUser(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User add(User elem) {
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
    public User delete(User elem) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                Query<User> query = session.createQuery("FROM User WHERE id = :id", User.class);
                query.setParameter("id", elem.getID());
                User user = query.setMaxResults(1).uniqueResult();
                System.err.println("Stergem userul: " + elem.getID());
                session.delete(user);
                tx.commit();
                return user;
            } catch (Exception e) {
                e.printStackTrace();
                if(tx != null)
                    tx.rollback();
                return null;
            }
        }
    }

    @Override
    public User update(User elem, Long id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                User user = session.get(User.class, id);
                user.setUsername(elem.getUsername());
                session.update(user);
                tx.commit();
                return user;

            } catch (RuntimeException ex) {
                System.err.println("Eroare la update " + ex);
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }

    @Override
    public User findById(Long id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                User user = session.get(User.class, id);
                tx.commit();
                return user;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la findById "+ex);
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }

    @Override
    public Iterable<User> findAll() {
        Iterable<User> ret = getAll();
        return ret;
    }

    @Override
    public Collection<User> getAll() {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<User> flights =
                        session.createQuery("FROM User", User.class)
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

    @Override
    public User findByUsername(String username) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query<User> query = session.createQuery("from User where username = :username", User.class);
                query.setParameter("username", username, StandardBasicTypes.STRING);
                User user = query.uniqueResult();
                tx.commit();
                return user;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la findById "+ex);
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }
}
