package model.beans;

/**
 *
 * @author skuarch
 */
public class SubPiece implements Piece {

    private String view = "not applicable";
    private String job = "not applicable";
    private String collector = "not applicable";
    private String collectorType = "not applicable";
    private String dates = "not applicable";
    private String drillDown = "not applicable";
    private String notes = "not applicable";
    private String limit = "not applicable";
    private String categorie = "not applicable";
    private String netmask = "not applicable";
    private String subnet = "not applicable";
    private String webServerHosts = "not applicable";
    private String typeService = "not applicable";
    private String typeProtocol = "not applicable";
    private String networkProtocols = "not applicable";
    private String IPProtocols = "not applicable";
    private String TCPProtocols = "not applicable";
    private String UDPProtocols = "not applicable";
    private String secondsLive = "20";
    private String ipAddress = "not applicable";
    private String websites = "not applicable";
    private boolean isTable = false;

    //==========================================================================
    @Override
    public String getView() {
        return this.view;
    }

    //==========================================================================
    @Override
    public void setView(String view) {
        this.view = view;
    }

    //==========================================================================
    @Override
    public String getJob() {
        return this.job;
    }

    //==========================================================================
    @Override
    public void setJob(String job) {
        this.job = job;
    }

    //==========================================================================
    @Override
    public String getCollector() {
        return this.collector;
    }

    //==========================================================================
    @Override
    public void setCollector(String collector) {
        this.collector = collector;
    }

    //==========================================================================
    @Override
    public String getCollectorType() {
        return this.collectorType;
    }

    //==========================================================================
    @Override
    public void setCollectorType(String type) {
        this.collectorType = type;
    }

    //==========================================================================
    @Override
    public String getDates() {
        return this.dates;
    }

    //==========================================================================
    @Override
    public void setDates(String dates) {
        this.dates = dates;
    }

    //==========================================================================
    @Override
    public String getDrillDown() {
        return this.drillDown;
    }

    //==========================================================================
    @Override
    public void setDrillDown(String drillDown) {
        this.drillDown = drillDown;
    }

    //==========================================================================
    @Override
    public String getNotes() {
        return this.notes;
    }

    //==========================================================================
    @Override
    public void setNotes(String notes) {
        this.notes = notes;
    }

    //==========================================================================
    @Override
    public String getLimit() {
        return this.limit;
    }

    //==========================================================================
    @Override
    public void setLimit(String limit) {
        this.limit = limit;
    }

    //==========================================================================
    @Override
    public String getCategorie() {
        return this.categorie;
    }

    //==========================================================================
    @Override
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    //==========================================================================
    @Override
    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    //==========================================================================
    @Override
    public String getSubnet() {
        return this.subnet;
    }

    //==========================================================================
    @Override
    public void setNetmask(String netMask) {
        this.netmask = netMask;
    }

    //==========================================================================
    @Override
    public String getNetmask() {
        return this.netmask;
    }

    //==========================================================================
    @Override
    public void setWebServerHosts(String webServerHosts) {
        this.webServerHosts = webServerHosts;
    }

    //==========================================================================
    @Override
    public String getWebServerHosts() {
        return this.webServerHosts;
    }

    //==========================================================================
    @Override
    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    //==========================================================================
    @Override
    public String getTypeService() {
        return this.typeService;
    }

    //==========================================================================
    @Override
    public void setTypeProtocol(String typeProtocol) {
        this.typeProtocol = typeProtocol;
    }

    //==========================================================================
    @Override
    public String getTypeProtocol() {
        return this.typeProtocol;
    }

    //==========================================================================
    @Override
    public void setNetworkProtocols(String networkProtocols) {
        this.networkProtocols = networkProtocols;
    }

    //==========================================================================
    @Override
    public String getNetworkProtocols() {
        return this.networkProtocols;
    }

    //==========================================================================
    @Override
    public void setIPProtocols(String IPProtocols) {
        this.IPProtocols = IPProtocols;
    }

    //==========================================================================
    @Override
    public String getIPProtocols() {
        return this.IPProtocols;
    }

    //==========================================================================
    @Override
    public void setTCPProtocols(String TCPProtocols) {
        this.TCPProtocols = TCPProtocols;
    }

    //==========================================================================
    @Override
    public String getTCPProtocols() {
        return TCPProtocols;
    }

    //==========================================================================
    @Override
    public void setUDPProtocols(String UDPProtocols) {
        this.UDPProtocols = UDPProtocols;
    }

    //==========================================================================
    @Override
    public String getUDPProtocols() {
        return this.UDPProtocols;
    }

    //==========================================================================
    @Override
    public void setSecondsLive(String seconds) {
        this.secondsLive = seconds;
    }

    //==========================================================================
    @Override
    public String getSecondsLive() {
        return this.secondsLive;
    }

    //==========================================================================
    @Override
    public String getIpAddress() {
        return this.ipAddress;
    }

    //==========================================================================
    @Override
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    //==========================================================================
    @Override
    public String getWebsites() {
        return this.websites;
    }

    //==========================================================================
    @Override
    public void setWebsites(String websites) {
        this.websites = websites;
    }

    //==========================================================================
    @Override
    public void isTable(boolean flag) {
        this.isTable = flag;
    }

    //==========================================================================
    @Override
    public boolean isTable() {
        return this.isTable;
    }
} // end class