package au.com.maxcheung.fileingestor;

import static au.com.maxcheung.fileingestor.utils.LogUtils.futureLogMsg;

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

import au.com.maxcheung.fileingestor.future.service.FutureService;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class App implements CommandLineRunner {

    private static final int ARGUMENT_LENGTH = 2;

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    private FutureService futureService;

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(App.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

        LOGGER.info(futureLogMsg("Writing logs to temp folder : {}"), System.getProperty("java.io.tmpdir") + "logs");
        LOGGER.info(futureLogMsg("Arguments passed : {}"), Arrays.toString(args));
        if (args.length != ARGUMENT_LENGTH) {
            LOGGER.error(futureLogMsg("\n Usage: java -jar fileingestor.jar datafile.txt out.csv"));
            LOGGER.error("Usage: datafile outputfile");
        } else {
            String dataFile = args[0];
            String reportFile = args[1];
            futureService.lookupLoad(dataFile, reportFile);
        }
        SpringApplication.exit(context);
    }

}
