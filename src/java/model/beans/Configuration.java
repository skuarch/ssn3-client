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
@Table(name = "configuration")
public class Configuration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_configuration")
    private long id;
    @Column(name = "jws_path")
    private String JWSPath;
    @Column(name = "help_path")
    private String HelpPath;
    @Column(name = "project_name")
    private String projectName;
    @Column(name = "jms_time_wait_message")
    private int jmsTimeWaitMessage;
    @Column(name = "jms_time_wait_connectivity")
    private int jmsTimeWaitConnectivity;
    @Column(name="seconds_live")
    private String secondsLive = "20";

    //==========================================================================
    public Configuration() {
    }

    //==========================================================================
    public long getId() {
        return id;
    }

    //==========================================================================
    public String getJWSPath() {
        return JWSPath;
    }

    //==========================================================================
    public void setJWSPath(String JWSPath) {
        this.JWSPath = JWSPath;
    }

    //==========================================================================
    public String getHelpPath() {
        return HelpPath;
    }

    //==========================================================================
    public void setHelpPath(String HelpPath) {
        this.HelpPath = HelpPath;
    }

    //==========================================================================
    public int getJmsTimeWaitConnectivity() {
        return jmsTimeWaitConnectivity;
    }

    //==========================================================================
    public void setJmsTimeWaitConnectivity(int jmsTimeWaitConnectivity) {
        this.jmsTimeWaitConnectivity = jmsTimeWaitConnectivity;
    }

    //==========================================================================
    public int getJmsTimeWaitMessage() {
        return jmsTimeWaitMessage;
    }

    //==========================================================================
    public void setJmsTimeWaitMessage(int jmsTimeWaitMessage) {
        this.jmsTimeWaitMessage = jmsTimeWaitMessage;
    }

    //==========================================================================
    public String getProjectName() {
        return projectName;
    }

    //==========================================================================
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    //==========================================================================
    public String getSecondsLive() {
        return secondsLive;
    }

    //==========================================================================
    public void setSecondsLive(String secondsLive) {
        this.secondsLive = secondsLive;
    }    
    
} // end class

