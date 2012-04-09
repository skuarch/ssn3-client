package model.beans;

/**
 *
 * @author skuarch
 */
public interface Piece {

    // this is selected when someone click in the view tree
    public String getView();
    public void setView(String view);

    //this is selected when someone click in the servers tree
    public String getJob();
    public void setJob(String job);

    // is a name of machine that 
    public String getCollector();
    public void setCollector(String collector);

    // server, openblock or what ever
    public String getCollectorType();
    public void setCollectorType(String type);

    // dates with the format yyyy-MM-dd  hh:mm:ss (sql time)
    public String getDates();
    public void setDates(String dates);

    // when tree view is drag and drop this property must be established
    public String getDrillDown();
    public void setDrillDown(String drillDown);

    // ips like 10.10...
    public String getIpAddress();
    public void setIpAddress(String ipAddress);    

    // notes for report
    public String getNotes();
    public void setNotes(String notes);    

    // websites
    public String getWebsites();
    public void setWebsites(String websites);

    // limit for pagination
    public String getLimit();
    public void setLimit(String Limit);

    // categorie is the folder from view like protocols, bandwidth ...
    public String getCategorie();
    public void setCategorie(String categorie);    
    
    //subnet
    public void setSubnet(String subnet);
    public String getSubnet();

    //netmask
    public void setNetmask(String netMask);
    public String getNetmask();

    //web server hosts, ip of web server
    public void setWebServerHosts(String webServerHosts);
    public String getWebServerHosts();
    
    //type of service
    public void setTypeService(String typeService);
    public String getTypeService();

    //type of protocol (net, ip, tcp, udp or other)
    public void setTypeProtocol(String typeProtocol);
    public String getTypeProtocol();

    //here is only for network protocols
    public void setNetworkProtocols(String networkProtocols);
    public String getNetworkProtocols();

    //here is only for ip protocols
    public void setIPProtocols(String IPProtocols);
    public String getIPProtocols();

    //here is only for TCP protocols
    public void setTCPProtocols(String TCPProtocols);
    public String getTCPProtocols();

    //here is only for UDP protocols
    public void setUDPProtocols(String UDPProtocols);
    public String getUDPProtocols();
    
    //seconds for live charts //defaul 20
    public void setSecondsLive(String seconds);
    public String getSecondsLive();
    
    //for tables
    public void isTable(boolean flag);    
    public boolean isTable();    

    //port number
    public void setPortNumber(String portNumber);
    public String getPortNumber();
    
    
} // end interface