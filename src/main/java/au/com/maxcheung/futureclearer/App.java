package au.com.maxcheung.futureclearer;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import au.com.maxcheung.futureclearer.future.FutureService;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, 
        DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class App implements CommandLineRunner {
    
    private static final int ARGUMENT_LENGTH = 2;

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    private FutureService lookupService;

    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(App.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        
        LOGGER.info("Writing logs to temp folder : {}", System.getProperty("java.io.tmpdir") + "logs");
        LOGGER.info("Arguments passed : {}", Arrays.toString(args));
        if (args.length != ARGUMENT_LENGTH) {
            LOGGER.error("Usage: datafile outputfile");
        } else {
            String dataFile = args[0].toString();
            String reportFile = args[1].toString();
            lookupService.lookupLoad(dataFile, reportFile);
        }
        SpringApplication.exit(context);
    }

}