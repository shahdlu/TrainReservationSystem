package com.trs.api.managers;

import com.trs.api.database.CRUD;
import com.trs.modules.tickets.FirstClassTicket;
import com.trs.modules.tickets.SecondClasssTicket;
import com.trs.modules.tickets.ThirdClassTicket;
import com.trs.modules.tickets.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketManager extends CRUD {
    private final static List<String> TICKET_COLUMNS = List.of("ticketNumber", "fare", "trainNumber", "reservation", "class");
    public TicketManager() {
        super();
    }

    // a method that fixes the syntax of the Ticket  values in the list
    private static List<String> getTicketValues(Ticket ticket) {
        return List.of(
                "'" + ticket.getTicketNumber() + "'",
                String.valueOf(ticket.getFare()),
                String.valueOf(ticket.getTrainNumber()),
                "' " + ticket.getReservationDate().toString() + "'",
                "'" + ticket.getTicketClass() + "'"
        );
    }

    //a method that returns all Tickets from database
    public static List<Ticket> getAllTickets() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        ResultSet rs = getAll("tickets");
        while (rs.next()) {
        tickets.add(getTicket(rs));
        }
        return tickets;
    }
    //a function to return whether a ticket exists or not
    public static boolean ticketNumberExists(String ticketNumber) throws SQLException {
        ResultSet rs = getAll("tickets");
        while (rs.next()) {
            if (rs.getString("ticketNumber").equals(ticketNumber)) {
                return true;
            }
        }
        return false;
    }
    //a method that takes a result set and returns a ticket
    private static Ticket getTicket(ResultSet rs) throws SQLException {
        switch (rs.getInt("Class")) {
            case 1 -> {
                return new FirstClassTicket(
                        rs.getString("ticketNumber"),
                        rs.getInt("Fare"),
                        rs.getInt("trainNumber"),
                        rs.getDate("Reservation"));
            }
            case 2 -> {
                return new SecondClasssTicket
                        (rs.getString("ticketNumber"),
                        rs.getInt("Fare"),
                        rs.getInt("trainNumber"),
                        rs.getDate("Reservation"));
            }
            case 3 -> {
                return new ThirdClassTicket(rs.getString("TicketNumber"),
                        rs.getInt("Fare"),
                        rs.getInt("trainNumber"),
                        rs.getDate("Reservation"));
            }
            default -> throw new IllegalStateException("ticket class doesn't exist: " + rs.getInt("Class"));
        }
    }
    static public List<Ticket> getTrainTickets(String number) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        ResultSet rs = get("tickets", "*","trainNumber = " + number);
        assert rs != null;
        while (rs.next()) {
           tickets.add(getTicket(rs));
        }
        return tickets;
    }
    //a method that returns a single Ticket from database
    public Ticket getTicket(String number) throws SQLException {
        ResultSet rs = get("tickets", "Number", number);
        assert rs != null;
        if (rs.next()) {
            return getTicket(rs);
        }
        return null;
    }

    //a method that removes a Ticket from database
    public static void removeTicket(Ticket ticket) throws SQLException {
        deleteWhereEqual("tickets", "ticketNumber", ticket.getTicketNumber());
    }

    //a method that adds a Ticket to database
    public void addTicket(Ticket ticket) throws SQLException {
        insert("tickets", TICKET_COLUMNS, getTicketValues(ticket));
    }

    //a method that updates a Ticket in database
    public void updateTicket(Ticket ticket) throws SQLException {
        update("tickets", TICKET_COLUMNS, getTicketValues(ticket), " ticketNumber = " + ticket.getTicketNumber());
    }

    //generate a random number from 100 to 999
    private static int generateTicketNumber(int trainNumber) {
        return Integer.parseInt( "" + trainNumber +""+ ((int) (Math.random() * 900) + 100));
    }
    //a method that removes all Tickets from database
    public static void deleteAll() throws SQLException {
        prune("tickets");
    }
    public static String generateTicketID(int trainNumber){
            return String.valueOf(generateTicketNumber(trainNumber));
    }

}

