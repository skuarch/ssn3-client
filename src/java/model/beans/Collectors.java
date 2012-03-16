package model.beans;

import java.sql.Timestamp;
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
@Table(name = "collectors")
public class Collectors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collector_id")
    private long id;
    @Column(name="collector_type")
    private int type;
    @Column(name="collector_host")
    private String host;
    @Column(name="collector_ip")
    private String ip;
    @Column(name = "collector_site")
    private String site;
    @Column(name="collector_description")
    private String description;
    @Column(name="collector_is_active")
    private int isActive;
    @Column(name="collector_date_register")
    private Timestamp registerDate;   

    //==========================================================================
    public Collectors() {
    } // end Collectors

    //==========================================================================
    public String getDescription() {
        return description;
    }

    //==========================================================================
    public void setDescription(String description) {
        this.description = description;
    }

    //==========================================================================
    public String getHost() {
        return host;
    }

    //==========================================================================
    public void setHost(String host) {
        this.host = host;
    }

    //==========================================================================
    public long getId() {
        return id;
    }

    //==========================================================================
    public void setId(long id) {
        this.id = id;
    }

    //==========================================================================
    public String getIp() {
        return ip;
    }

    //==========================================================================
    public void setIp(String ip) {
        this.ip = ip;
    }

    //==========================================================================
    public int getIsActive() {
        return isActive;
    }

    //==========================================================================
    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    //==========================================================================
    public Timestamp getRegisterDate() {
        return registerDate;
    }

    //==========================================================================
    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    //==========================================================================
    public String getSite() {
        return site;
    }

    //==========================================================================
    public void setSite(String site) {
        this.site = site;
    }

    //==========================================================================
    public int getType() {
        return type;
    }

    //==========================================================================
    public void setType(int type) {
        this.type = type;
    }
    
} // end class

