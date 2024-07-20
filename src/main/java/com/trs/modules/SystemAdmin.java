package com.trs.modules;

import com.trs.modules.tickets.Ticket;

import java.sql.SQLException;
import java.util.List;

public class SystemAdmin extends TicketingOfficer {
    public SystemAdmin(int id, String FirstName, String LastName, String PhoneNumber, int salary, String Address, String username, String password) throws SQLException {
        super(id, FirstName, LastName, PhoneNumber, salary, Address, username, password);
        this.position = getPosition();
    }

    public SystemAdmin(String FirstName, String LastName, String PhoneNumber, int salary, String Address, String username, String password) throws SQLException {
        super(FirstName, LastName, PhoneNumber, salary, Address, username, password);
        this.position = getPosition();
    }

    public SystemAdmin() throws SQLException {
        super();
        this.position = getPosition();
    }


    public List<TicketingOfficer> viewOfficers() throws SQLException {
        return officerManager.getAllTicketingOfficers();
    }

    public String addOfficer(TicketingOfficer officer) {
        try {
            officerManager.insertTicketingOfficer(officer);
            return "Success";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public boolean deleteOfficer(TicketingOfficer officer) {
        try {
            officerManager.deleteTicketingOfficer(officer);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public String updateOfficer(TicketingOfficer newOfficer) {
        try {
            officerManager.updateTicketingOfficer(newOfficer);
            return "Success";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String addTrain(Train train) {
        try {
            trainManager.insertTrain(train);
            return "Success";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public boolean deleteTrain(Train train) {
        try {
            trainManager.deleteTrain(train);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public String updateTrain(Train otherTrain) {
        try {
            trainManager.updateTrain(otherTrain);
            return "Success";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public boolean editTicket(Ticket otherTicket) {
        try {
            ticketManager.updateTicket(otherTicket);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteTicket(Ticket ticket) {
        try {
            ticketManager.removeTicket(ticket);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public String getPosition() {
        return "System Admin";
    }

}

