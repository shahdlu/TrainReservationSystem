package com.trs.modules;

import com.trs.api.managers.TicketManager;
import com.trs.modules.tickets.Ticket;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class Train {
    protected int trainNumber;
    protected String type;
    protected Timestamp departureTime;
    protected Timestamp arrivalTime;
    protected String departureStation;
    protected String arrivalStation;
    protected int maximumCapacity;
    protected int currentCapacity;
    protected int price;
    protected List<Ticket> trainTickets;

    public Train(int trainNumber, String type, Timestamp departureTime, Timestamp arrivalTime, String departureStation, String arrivalStation, int maximumCapacity, int price) throws SQLException {
        this.trainNumber = trainNumber;
        this.type = type;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.maximumCapacity = maximumCapacity;
        this.price = price;
        trainTickets = TicketManager.getTrainTickets(String.valueOf(trainNumber));
        currentCapacity = calculateCurrentCapacity();
    }

    public Train() throws SQLException {
        trainTickets = TicketManager.getTrainTickets(String.valueOf(trainNumber));
        currentCapacity = calculateCurrentCapacity();
    }

    //generate trains as dummy data
    public static ArrayList<Train> generateTrains() throws SQLException {
        ArrayList<Train> trains = new ArrayList<>(List.of(
                new Train(1, "Express", Timestamp.valueOf("2020-01-01 10:00:00"), Timestamp.valueOf("2020-01-01 12:00:00"), "Cairo", "Alexandria", 100, 100),
                new Train(2, "Express", Timestamp.valueOf("2020-01-01 10:00:00"), Timestamp.valueOf("2020-01-01 12:00:00"), "Cairo", "Alexandria", 100, 100),
                new Train(3, "Express", Timestamp.valueOf("2020-01-01 10:00:00"), Timestamp.valueOf("2020-01-01 12:00:00"), "Cairo", "Alexandria", 100, 100),
                new Train(4, "Express", Timestamp.valueOf("2020-01-01 10:00:00"), Timestamp.valueOf("2020-01-01 12:00:00"), "Cairo", "Alexandria", 100, 100),
                new Train(5, "Express", Timestamp.valueOf("2020-01-01 10:00:00"), Timestamp.valueOf("2020-01-01 12:00:00"), "Cairo", "Alexandria", 100, 100),
                new Train(6, "Express", Timestamp.valueOf("2020-01-01 10:00:00"), Timestamp.valueOf("2020-01-01 12:00:00"), "Cairo", "Alexandria", 100, 100),
                new Train(7, "Express", Timestamp.valueOf("2020-01-01 10:00:00"), Timestamp.valueOf("2020-01-01 12:00:00"), "Cairo", "Alexandria", 100, 100),
                new Train(8, "Express", Timestamp.valueOf("2020-01-01 10:00:00"), Timestamp.valueOf("2020-01-01 12:00:00"), "Cairo", "Alexandria", 100, 100)));

        return trains;
    }

    private int calculateCurrentCapacity() throws SQLException {
        return maximumCapacity - trainTickets.size();
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public boolean has(Ticket ticket) {
        return ticket.getTrainNumber() == trainNumber;
    }

    public boolean isFull() {
        return currentCapacity == maximumCapacity;
    }

    public boolean addTicket(Ticket ticket) {
        if (!isFull() && this.has(ticket)) {
            currentCapacity++;
            ticket.setTrainNumber(trainNumber);
            trainTickets.add(ticket);
            return true;
        }
        return false;
    }

    public List<Ticket> getAllTrainTickets() throws SQLException {
        return trainTickets;
    }

    public boolean removeTicket(Ticket ticket) {
        if (currentCapacity > 0 && has(ticket)) {
            currentCapacity--;
            ticket.setTrainNumber(0);
            return true;
        }
        return false;
    }


}
