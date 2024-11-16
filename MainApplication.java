package lk.shenon.oop.cw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

    public static void main(String[] args){
        try {
            FileHandler fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } 
        
        catch (Exception e) {
            logger.severe("Failed to initialize logging: " + e.getMessage());
        }

        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args){
        Configuration config;

        try {
            config = Configuration.loadConfig("real-time-ticketing-system/config.json");

            if (!config.validate()){
                logger.warning("Configuration validation failed. Please check the configuration values.");
                throw new IllegalArgumentException("Invalid values provided for configuration");
            }

            logger.info("Configuration loaded and validated successfully.");
        } 
        
        catch (Exception e){
            logger.severe("Failed to load or validate configuration: " + e.getMessage());
            return;
        }

        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());
        logger.info("TicketPool initialized with max capacity: " + config.getMaxTicketCapacity());

        Thread vendor1 = new Thread(new Vendor(1, 5, config.getTicketReleaseRate(), ticketPool));
        Thread vendor2 = new Thread(new Vendor(2, 3, config.getTicketReleaseRate(), ticketPool));
        logger.info("Vendor threads initialized.");

        Thread customer1 = new Thread(new Customer(1, config.getCustomerRetrievalRate(), ticketPool));
        Thread customer2 = new Thread(new Customer(2, config.getCustomerRetrievalRate(), ticketPool));
        Thread customer3 = new Thread(new Customer(3, config.getCustomerRetrievalRate(), ticketPool));
        logger.info("Customer threads initialized.");

        vendor1.start();
        vendor2.start();
        customer1.start();
        customer2.start();
        customer3.start();
        logger.info("All threads started successfully.");
    }

}
