package com.trs.modules.tickets;


import java.sql.Date;

public class ThirdClassTicket extends Ticket {
    public ThirdClassTicket(String number, int fare, int TrainNumber, Date reservationDate) {
        super(number, fare, TrainNumber, reservationDate);
    }

    @Override
    public int getTicketClass() {
        return 3;
    }

}
