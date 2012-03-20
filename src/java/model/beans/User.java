package model.beans;

/**
 * singleton.
 * @author skuarch
 */
public class User {

    private static User INSTANCE = null;
    private String name;
    private String password;
    private int level;
    private String description;

    //==========================================================================
    private User() {
    }

    //==========================================================================
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new User();
        }
    }

    //==========================================================================
    public static User getInstance() {
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

