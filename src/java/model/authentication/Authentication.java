package model.authentication;

import java.util.List;
import org.hibernate.Session;
import model.beans.Users;
import model.util.CurrentUser;
import model.persistence.HibernateUtil;

/**
 *
 * @author skuarch
 */
public class Authentication {

    //==========================================================================
    public Authentication() {
    } // end Authentication

    public boolean validateUser(String userName, String password) throws Exception {

        boolean flag = false;
        String hsql = null;
        Session session = null;
        List<Users> users = null;
        CurrentUser currentUser = null;

        try {
            
            session = HibernateUtil.getSessionFactory().openSession();
            session = session = HibernateUtil.getSessionFactory().openSession();

            if (session == null) {            
                return false;
            }

            hsql = "FROM Users WHERE user_name = '" + userName + "' AND user_password = '" + password + "'";
            users = (List<Users>) session.createQuery(hsql).list();

            if (users != null && users.size() == 1) {
                flag = true;
                //creating a userName for the application
                currentUser = CurrentUser.getInstance();
                currentUser.setDescription(users.get(0).getDescription());
                currentUser.setLevel(users.get(0).getLevel());
                currentUser.setName(users.get(0).getName());
                currentUser.setPassword(users.get(0).getPassword());
            }
        
        } catch (Exception e) {
            flag = false;
            throw e;
        } finally {            
            HibernateUtil.closeSession(session);
            users = null;
        }

        return flag;
    }
} // end class
