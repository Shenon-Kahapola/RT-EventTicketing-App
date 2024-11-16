package lk.shenon.oop.cw;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TicketPool {
    private ConcurrentLinkedQueue<String> tickets = new ConcurrentLinkedQueue<>();
    private int maxTicketCapacity;

    public TicketPool(int maxTicketCapacity){
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public synchronized void addTickets(int count){
        int addedTickets = 0;
        while (addedTickets < count && tickets.size() < maxTicketCapacity) {
            tickets.add("Ticket " + (tickets.size() + 1));
            addedTickets++;
        }
    }

    public synchronized boolean removeTicket(){
        return tickets.poll() != null;
    }

    public int getTicketCount(){
        return tickets.size();
    }

    public int getMaxCapacity(){
        return maxTicketCapacity;
    }

}
