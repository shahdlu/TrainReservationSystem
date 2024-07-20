package com.trs.api.managers;

import com.trs.api.database.CRUD;
import com.trs.modules.SystemAdmin;
import com.trs.modules.TicketingOfficer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfficerManager extends CRUD {
    private final static List<String> OFFICER_COLUMNS = List.of("AID", "salary", "FName", "LName", "PhoneNumber", "Address", "username", "password", "type");
    public static ArrayList<Integer> OfficerIDs = new ArrayList<>();

    public OfficerManager() {
        super();
    }

    // a method that fixes the syntax of the TicketingOfficer  values in the list
    private static List<String> getOfficerValues(TicketingOfficer officer) {
        int type = officer.getPosition().equals("System Admin") ? 0 : 1;
        return List.of(
                "'" + officer.getId() + "'",
                String.valueOf(officer.getSalary()),
                "'" + officer.getFirstName() + "'",
                "'" + officer.getLastName() + "'",
                "'" + officer.getPhoneNumber() + "'",
                "'" + officer.getAddress() + "'",
                "'" + officer.getUsername() + "'",
                "'" + officer.getPassword() + "'",
                "'" + type + "'"
        );
    }

    public static boolean IDExists(int id) {
        return OfficerIDs.contains(id);
    }

    //a method that deletes everything in the table
    public static void deleteAll() throws SQLException {
        if (isConnected()) {
            prune("officers");
            System.out.println("Everything Deleted");
            return;
        }
        throw new SQLException("Connection to the database failed.");
    }

    //a method that takes a result set and returns a ticketing officer
    private static TicketingOfficer getOfficer(ResultSet rs) throws SQLException {
            int type = rs.getInt("type");
            if (type == 0) {
                return new SystemAdmin(rs.getInt("AID"),
                        rs.getString("FName"),
                        rs.getString("LName"),
                        rs.getString("PhoneNumber"),
                        rs.getInt("salary"),
                        rs.getString("Address"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            } else {
                return new TicketingOfficer(rs.getInt("AID"),
                        rs.getString("FName"),
                        rs.getString("LName"),
                        rs.getString("PhoneNumber"),
                        rs.getInt("salary"),
                        rs.getString("Address"),
                        rs.getString("username"),
                        rs.getString("password")
                       );
            }
    }


    //a method that returns all TicketingOfficer from database
    public List<TicketingOfficer> getAllTicketingOfficers() throws SQLException {
        List<TicketingOfficer> ticketingOfficers = new ArrayList<>();
        ResultSet rs = getAll("officers");
        while (rs.next()) {
            OfficerIDs.add(rs.getInt("AID"));
            ticketingOfficers.add(getOfficer(rs));
        }
        return ticketingOfficers;
    }

    //a method that returns a single TicketingOfficer from database
    public TicketingOfficer getTicketingOfficer(int AID) throws SQLException {
        ResultSet rs = get("officers", "AID", String.valueOf(AID));
        assert rs != null;
        return getOfficer(rs);
    }

    //a method that returns a TicketingOfficer with a specific username and password
    public TicketingOfficer getTicketingOfficer(String username, String password, int type) throws SQLException {
        ResultSet rs = get("officers", "*", " `username` = " + "'" + username + "'" + " and `password` = " + "'" + password + "'" + " and `type` = " + type);
        assert rs != null : "No Ticketing Officer with this username and password";
        if(rs.next()) {
            System.out.println("Ticketing Officer found");
            System.out.println(rs.getString("FName"));
            if(getOfficer(rs) != null) {
                return getOfficer(rs);
            }
            System.out.println("Ticketing Officer not found");
        }
        return null;
    }


    //a method that inserts a TicketingOfficer into the database
    public void insertTicketingOfficer(TicketingOfficer ticketingOfficer) throws SQLException {
        if (isConnected()) {
            insert("officers", OFFICER_COLUMNS, getOfficerValues(ticketingOfficer));
            return;
        }
        throw new SQLException("Not connected to database");

    }

    //a method that updates a TicketingOfficer in the database
    public void updateTicketingOfficer(TicketingOfficer ticketingOfficer) throws SQLException {
        if (isConnected()) {
            try {
                update("officers", OFFICER_COLUMNS, getOfficerValues(ticketingOfficer), "AID = " + ticketingOfficer.getId());
                return;
            } catch (SQLException e) {
                throw new SQLException("Ticketing Officer doesn't exists");
            }
        }
        throw new SQLException("Connection to the database failed.");
    }

    //a method that deletes a TicketingOfficer from the database
    public void deleteTicketingOfficer(TicketingOfficer officer) throws SQLException {
        if (isConnected()) {
            deleteWhereEqual("officers", "AID", String.valueOf(officer.getId()));
            return;
        }
        throw new SQLException("Connection to the database failed.");
    }


}
