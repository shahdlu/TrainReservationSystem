package com.trs.modules.tickets;

import java.sql.Date;

public class FirstClassTicket extends Ticket {

    public FirstClassTicket(String ticketNumber, int fare, int trainNumber, Date reservationDate) {
        super(ticketNumber, fare, trainNumber, reservationDate);
    }

    @Override
    public int getTicketClass() {
        return 1;
    }


}

