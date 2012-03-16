package model.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author skuarch
 */
@Entity
@Table(name = "users")
public class Users  implements Serializable {

    @Id
    @Column (name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column (name="user_name")
    private String name;
    @Column (name="user_password")
    private String password;
    @Column (name="user_level")
    private int level;
    @Column (name="user_description")
    private String description;

    //==========================================================================
    public Users() {
    } // end Users

    //==========================================================================
    public Long getIdUser() {
        return idUser;
    }

    //==========================================================================
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
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

