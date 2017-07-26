package ManiplateWorkbook;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateConnectionToShippingNetwork {
    private SessionFactory sessionFactory;
    public Session session;
    private Transaction transaction;

    public void open() {
        sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(Location.class)
                .addAnnotatedClass(Route.class)
                .addAnnotatedClass(RouteLeg.class)
                .addAnnotatedClass(RoutePathView.class)
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    public void close() {
        session.close();
        sessionFactory.close();
    }

    public void startTransaction() {
        transaction = session.beginTransaction();
    }

    public void endTransaction() {
        transaction.commit();
    }

    public java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }
}
