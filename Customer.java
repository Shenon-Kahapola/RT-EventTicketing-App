package lk.shenon.oop.cw;

import java.util.logging.Logger;

public class Customer implements Runnable {
    private static final Logger logger = Logger.getLogger(Customer.class.getName());

    private int customerId;
    private int retrievalInterval;
    private TicketPool ticketPool;

    public Customer(int customerId, int retrievalInterval, TicketPool ticketPool){
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run(){
        try {
            while (true){
                synchronized (ticketPool){
                    boolean success = ticketPool.removeTicket();

                    if (success){
                        logger.info("Customer " + customerId + " bought a ticket. Tickets left: " + ticketPool.getTicketCount());
                    } 
                    
                    else {
                        logger.warning("Customer " + customerId + " did not find any tickets available.");
                    }
                }

                Thread.sleep(retrievalInterval); 
            }
        } 
        
        catch (InterruptedException e){
            logger.warning("Customer " + customerId + " was interrupted.");
            Thread.currentThread().interrupt();
        } 
        
        catch (Exception e){
            logger.severe("Unexpected error for Customer " + customerId + ": " + e.getMessage());
        }
    }

}
