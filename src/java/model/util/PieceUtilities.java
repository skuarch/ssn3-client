package model.util;

import java.util.HashMap;
import model.beans.SubPiece;
import view.notifications.Notifications;

/**
 *
 * @author skuarch
 */
public class PieceUtilities {

    private Notifications notifications = null;

    //==========================================================================
    public PieceUtilities() {
        notifications = new Notifications();
    }

    //==========================================================================
    public SubPiece createSubPieceFromSubPiece(SubPiece subPiece) {

        if (subPiece == null) {
            notifications.error("error subpiece is null", new NullPointerException("subPiece is null"));
            return null;
        }

        SubPiece newSubPiece = null;

        try {

            newSubPiece = new SubPiece();

            newSubPiece.setView(subPiece.getView());
            newSubPiece.setJob(subPiece.getJob());
            newSubPiece.setCollector(subPiece.getCollector());
            newSubPiece.setCollectorType(subPiece.getCollectorType());
            newSubPiece.setDates(subPiece.getDates());
            newSubPiece.setDrillDown(subPiece.getDrillDown());
            newSubPiece.setNotes(subPiece.getNotes());
            newSubPiece.setLimit(subPiece.getLimit());
            newSubPiece.setCategorie(subPiece.getCategorie());
            newSubPiece.setNetmask(subPiece.getNetmask());
            newSubPiece.setSubnet(subPiece.getSubnet());
            newSubPiece.setWebServerHosts(subPiece.getWebServerHosts());
            newSubPiece.setTypeService(subPiece.getTypeService());
            newSubPiece.setNetworkProtocols(subPiece.getNetworkProtocols());
            newSubPiece.setIPProtocols(subPiece.getIPProtocols());
            newSubPiece.setTCPProtocols(subPiece.getTCPProtocols());
            newSubPiece.setUDPProtocols(subPiece.getUDPProtocols());
            newSubPiece.setSecondsLive(subPiece.getSecondsLive());
            newSubPiece.setIpAddress(subPiece.getIpAddress());
            newSubPiece.setWebsites(subPiece.getWebsites());
            newSubPiece.isTable(subPiece.isTable());
            newSubPiece.setPortNumber(subPiece.getPortNumber());
            newSubPiece.setHostname(subPiece.getHostname());

        } catch (Exception e) {
            notifications.error("error creating sub-piece", e);
        }

        return newSubPiece;

    } //  end createSubPieceFromSubPiece

    //==========================================================================
    public String[][] PieceToArray(SubPiece subPiece) {

        if (subPiece == null) {
            return null;
        }

        String[][] areturn = null;

        try {

            String[][] array = {
                {"view", subPiece.getView()},
                {"job", subPiece.getJob()},
                {"collector", subPiece.getCollector()},
                {"collector type", subPiece.getCollectorType()},
                {"dates", subPiece.getDates()},
                {"drill down", subPiece.getDrillDown()},
                {"notes", subPiece.getNotes()},
                {"limit", subPiece.getLimit()},
                {"categorie", subPiece.getCategorie()},
                {"netmask", subPiece.getNetmask()},
                {"subnet", subPiece.getSubnet()},
                {"web servers hosts", subPiece.getWebServerHosts()},
                {"type service", subPiece.getTypeService()},
                {"type protocol", subPiece.getTypeProtocol()},
                {"network protocols", subPiece.getNetworkProtocols()},
                {"ip protocols", subPiece.getIPProtocols()},
                {"tcp protocols", subPiece.getTCPProtocols()},
                {"udp protocols", subPiece.getUDPProtocols()},
                {"secondLive", subPiece.getSecondsLive()},
                {"ip address", subPiece.getIpAddress()},
                {"websites", subPiece.getWebsites()},
                {"is table", subPiece.isTable() + ""},
                {"port number", subPiece.getPortNumber()},
                {"hostname", subPiece.getHostname()}
            };

            areturn = array;
        } catch (Exception e) {
            notifications.error("error creating array", e);
        }

        return areturn;
    } // end PieceToArray

    //==========================================================================
    public HashMap subPieceToHashMap(SubPiece subPiece) {

        if (subPiece == null) {
            return null;
        }

        HashMap hashMap = null;

        try {

            hashMap = new HashMap();
            hashMap.put("view", subPiece.getView());
            hashMap.put("job", subPiece.getJob());
            hashMap.put("collector", subPiece.getCollector());
            hashMap.put("collector type", subPiece.getCollectorType());
            hashMap.put("dates", subPiece.getDates());
            hashMap.put("drillDown", subPiece.getDrillDown());
            hashMap.put("notes", subPiece.getNotes());
            hashMap.put("limit", subPiece.getLimit());
            hashMap.put("categorie", subPiece.getLimit());
            hashMap.put("netmask", subPiece.getNetmask());
            hashMap.put("subnet", subPiece.getSubnet());
            hashMap.put("web server hosts", subPiece.getWebServerHosts());
            hashMap.put("type service", subPiece.getTypeService());
            hashMap.put("type protocol", subPiece.getTypeProtocol());
            hashMap.put("network protocols", subPiece.getNetworkProtocols());
            hashMap.put("ip protocols", subPiece.getIPProtocols());
            hashMap.put("tcp protocols", subPiece.getTCPProtocols());
            hashMap.put("udp protocols", subPiece.getUDPProtocols());
            hashMap.put("seconds live", subPiece.getSecondsLive());
            hashMap.put("ip address", subPiece.getIpAddress());
            hashMap.put("websites", subPiece.getWebsites());
            hashMap.put("is table", subPiece.isTable());
            hashMap.put("port number", subPiece.getPortNumber());
            hashMap.put("hostname", subPiece.getHostname());

        } catch (Exception e) {
            notifications.error("error creating hashmap", e);
        }

        return hashMap;

    } // end subPieceToHashMap
} // end class
