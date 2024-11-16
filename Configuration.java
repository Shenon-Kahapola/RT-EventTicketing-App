package lk.shenon.oop.cw;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Configuration {
    private static final Logger logger = Logger.getLogger(Configuration.class.getName());

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public int getTotalTickets(){
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets){
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate(){ 
        return ticketReleaseRate; 
    }

    public void setTicketReleaseRate(int ticketReleaseRate){ 
        this.ticketReleaseRate = ticketReleaseRate; 
    }

    public int getCustomerRetrievalRate(){ 
        return customerRetrievalRate; 
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate){ 
        this.customerRetrievalRate = customerRetrievalRate; 
    }

    public int getMaxTicketCapacity(){ 
        return maxTicketCapacity; 
    }

    public void setMaxTicketCapacity(int maxTicketCapacity){ 
        this.maxTicketCapacity = maxTicketCapacity; 
    }

    public void saveConfig(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(filename), this);
            logger.info("Configuration saved to " + filename);
        } 
        
        catch (IOException e){
            logger.severe("Failed to save configuration to " + filename + ": " + e.getMessage());
            throw e;
        }
    }

    public static Configuration loadConfig(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Configuration config = mapper.readValue(new File(filename), Configuration.class);
            logger.info("Configuration loaded successfully from " + filename);
            return config;
        } 
        
        catch (IOException e){
            logger.severe("Failed to load configuration from " + filename + ": " + e.getMessage());
            throw e;
        }
    }

    public boolean validate(){
        if (totalTickets > 0 && ticketReleaseRate >= 0 && customerRetrievalRate >= 0 && maxTicketCapacity > 0){
            logger.info("Configuration validated successfully.");
            return true;
        } 
        
        else {
            logger.warning("Configuration validation failed.");
            return false;
        }
    }

}
