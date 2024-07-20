package com.trs.modules;

import com.trs.api.managers.OfficerManager;
import com.trs.api.managers.TicketManager;
import com.trs.api.managers.TrainManager;
import com.trs.modules.tickets.Ticket;

import java.sql.SQLException;
import java.util.List;

public class TicketingOfficer {
    protected final OfficerManager officerManager = new OfficerManager();
    protected int id;
    protected int salary;
    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected String address;
    protected String position;
    TrainManager trainManager = new TrainManager();
    TicketManager ticketManager = new TicketManager();

    public TicketingOfficer() throws SQLException {
        this.position = getPosition();
    }


    public TicketingOfficer(int id, String FirstName, String LastName, String PhoneNumber, int salary, String Address, String username, String password) throws SQLException {
        this.id = id;
        this.salary = salary;
        this.firstName = FirstName;
        this.lastName = LastName;
        this.phoneNumber = PhoneNumber;
        this.address = Address;
        this.username = username;
        this.password = password;
        this.position = getPosition();
    }

    public TicketingOfficer(String FirstName, String LastName, String PhoneNumber, int salary, String Address, String username, String password) throws SQLException {
        this.salary = salary;
        this.firstName = FirstName;
        this.lastName = LastName;
        this.phoneNumber = PhoneNumber;
        this.address = Address;
        this.username = username;
        this.password = password;
        this.position = getPosition();
    }

    public String getPosition() {
        return "Ticketing Officer";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //TODO: add implementation
    public boolean bookTicket(Ticket ticket, Train train) throws SQLException {
        try {
            ticketManager.addTicket(ticket);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Ticket findTicket(String ticketNumber) {
        try {
            return ticketManager.getTicket(ticketNumber);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Ticket> viewTickets() {
        try {
            return ticketManager.getAllTickets();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Train> viewTrains() {
        try {
            return trainManager.getAllTrains();
        } catch (Exception e) {
            return null;
        }
    }

    public Train findTrain(int number) {
        try {
            return trainManager.getTrain(number);
        } catch (Exception e) {
            return null;
        }
    }

}
