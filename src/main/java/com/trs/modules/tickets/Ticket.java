package com.trs.modules.tickets;

import java.sql.Date;

public abstract class Ticket {

    public String ticketNumber;
    public int fare;
    public int trainNumber;
    public Date reservationDate;

    public Ticket(String TicketNumber, int fare, int TrainNumber, Date reservationDate) {
        this.ticketNumber = TicketNumber;
        this.trainNumber = TrainNumber;
        this.fare = fare;
        this.reservationDate = reservationDate;
    }


    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    //get the ticket type
    public abstract int getTicketClass();
}
