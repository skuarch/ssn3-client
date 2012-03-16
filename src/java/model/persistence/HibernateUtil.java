package model.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;

    //==========================================================================
    static {
        try {

            Configuration cfg = new Configuration();
            sessionFactory = cfg.configure().buildSessionFactory();

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //==========================================================================
    public static void closeSession(Session session) {

        if (session != null) {

            if (session.isOpen()) {
                session.close();
            }
        }

    }

    //==========================================================================
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}