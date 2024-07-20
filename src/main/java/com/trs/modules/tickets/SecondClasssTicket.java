package com.trs.modules.tickets;

import java.sql.Date;

public class SecondClasssTicket extends Ticket {

    public SecondClasssTicket(String number, int fare, int TrainNumber, Date reservationDate) {
        super(number, fare, TrainNumber, reservationDate);
    }

    @Override
    public int getTicketClass() {
        return 2;
    }


}
