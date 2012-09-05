package model.dao;

import java.util.List;
import model.net.ConnectPool;
import model.persistence.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author skuarch
 */
public class DAO {

    private Session session = null;

    //==========================================================================
    public DAO() throws Exception {
        startSession();
    } // end DAO

    //==========================================================================
    private void startSession() throws Exception {

        try {

            session = HibernateUtil.getSessionFactory().openSession();

        } catch (Exception e) {
            HibernateUtil.closeSession(session);
            throw e;
        }
    } // end startSession

    //==========================================================================
    private void handlerError(Exception e) throws Exception {
        try {

            throw new Exception(e);

        } catch (Exception ex) {
            throw ex;
        } finally {
            HibernateUtil.closeSession(session);
        }
    } // end handlerError

    //==========================================================================
    public long create(Object object) throws Exception {

        if(object == null){
            throw new NullPointerException("object is null");
        }
        
        long id = 0;

        try {

            id = (Long) session.save(object);
            session.beginTransaction().commit();

        } catch (Exception e) {
            handlerError(e);
        } finally {
            HibernateUtil.closeSession(session);
        }

        return id;

    } // end create

    //==========================================================================
    public void update(Object object) throws Exception {

        try {
            session.update(object);
            session.beginTransaction().commit();
        } catch (Exception e) {
            handlerError(e);
        } finally {
            HibernateUtil.closeSession(session);
        }

    } // end update

    //==========================================================================
    public Object get(long id, Class clazz) throws Exception {

        Object object = null;

        try {

            object = session.get(clazz, id);
            session.beginTransaction().commit();

        } catch (Exception e) {
            handlerError(e);
        } finally {
            HibernateUtil.closeSession(session);
        }

        return object;
    } // end get

    //==========================================================================
    public void delete(Object object) throws Exception {

        try {
            session.delete(object);
        } catch (Exception e) {
            handlerError(e);
        } finally {
            HibernateUtil.closeSession(session);
        }
    } // end delete

    //==========================================================================
    public List getAll(String stringClass) throws Exception {

        List list = null;

        try {

            list = session.createQuery("from " + stringClass).list();

        } catch (Exception e) {
            handlerError(e);
        } finally {
            HibernateUtil.closeSession(session);
        }

        return list;

    } // end getAll

    //==========================================================================
    public List find(Class clazz, String stringToFind, String field) throws Exception {

        List objects = null;
        Criteria criteria = null;

        try {

            criteria = session.createCriteria(clazz);
            criteria.add(Restrictions.like(field, stringToFind +"%"));
            objects = criteria.list();

        } catch (Exception e) {
            handlerError(e);
        } finally {
            HibernateUtil.closeSession(session);
        }

        return objects;

    } // end find

    //==========================================================================
    public void deleteAll(String table) throws Exception {

        try {

            new ConnectPool().update("truncate table " + table);

        } catch (Exception e) {
            throw e;
        }

    } // end deleteAll

    //==========================================================================
    public List hsql(String hsql) throws Exception {

        List list = null;

        try {

            list = session.createQuery(hsql).list();

        } catch (Exception e) {
            handlerError(e);
        } finally {
            HibernateUtil.closeSession(session);
        }

        return list;
    }
} // end class

