package lk.shenon.oop.cw;

import java.util.logging.Logger;

public class Vendor implements Runnable {
    private static final Logger logger = Logger.getLogger(Vendor.class.getName());

    private int vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;
    private TicketPool ticketPool;

    public Vendor(int vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool){
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run(){
        try {
            while (true){
                synchronized (ticketPool){
                    if (ticketPool.getTicketCount() < ticketPool.getMaxCapacity()){
                        ticketPool.addTickets(ticketsPerRelease);
                        logger.info("Vendor " + vendorId + " added " + ticketsPerRelease + " tickets. Current ticket count: " + ticketPool.getTicketCount());
                    } 
                    
                    else {
                        logger.warning("Vendor " + vendorId + " cannot add tickets. Ticket pool is full with " + ticketPool.getTicketCount() + " tickets.");
                    }
                }

                Thread.sleep(releaseInterval);
            }
        } 
        
        catch (InterruptedException e){
            logger.warning("Vendor " + vendorId + " was interrupted.");
            Thread.currentThread().interrupt();
        } 
        
        catch (Exception e) {
            logger.severe("Unexpected error in Vendor " + vendorId + ": " + e.getMessage());
        }
    }

}
