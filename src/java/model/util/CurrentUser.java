package model.util;

/**
 *
 * @author skuarch
 */
public class CurrentUser {

    private static CurrentUser INSTANCE = null;
    private String name;
    private String password;
    private int level;
    private String description;

    //==========================================================================
    private CurrentUser() {
    } // end CurrentUser

    //==========================================================================
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CurrentUser();
        }
    }

    //==========================================================================
    public static CurrentUser getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    //==========================================================================
    public String getDescription() {
        return description;
    }

    //==========================================================================
    public void setDescription(String description) {
        this.description = description;
    }

    //==========================================================================
    public int getLevel() {
        return level;
    }

    //==========================================================================
    public void setLevel(int level) {
        this.level = level;
    }

    //==========================================================================
    public String getName() {
        return name;
    }

    //==========================================================================
    public void setName(String name) {
        this.name = name;
    }

    //==========================================================================
    public String getPassword() {
        return password;
    }

    //==========================================================================
    public void setPassword(String password) {
        this.password = password;
    }

} // end class
