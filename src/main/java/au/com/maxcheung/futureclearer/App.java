package au.com.maxcheung.futureclearer;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.MoreObjects.firstNonNull;
import static java.lang.String.format;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import com.google.common.collect.ImmutableMap;



@SpringBootApplication
public class App extends SpringBootServletInitializer {

    /**
     * Tool used: http://patorjk.com/software/taag/
     */
    private static final String[] BANNER = {
            "  _____      _                  ",
            " |  ___|   _| |_ _   _ _ __ ___ ",
            " | |_ | | | | __| | | | '__/ _ \\",
            " |  _|| |_| | |_| |_| | | |  __/",
            " |_|   \\__,_|\\__|\\__,_|_|  \\___|"};

    private static final String INDENTATION = "    ";

    private static final String DEFAULT_TITLE = "Future Clearing Service";
    private static final String DEFAULT_VERSION = "1.0.0";
    private static final String DEFAULT_VENDOR = "Future Clearer";
    private static final String DEFAULT_DESCRIPTION = "Future Clearing Service.";

    /**
     * {@inheritDoc}
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // Obtain information where possible from Manifest file.
        final String title = firstNonNull(App.class.getPackage().getImplementationTitle(), DEFAULT_TITLE);
        final String version = firstNonNull(App.class.getPackage().getImplementationVersion(), DEFAULT_VERSION);
        final ImmutableMap.Builder<String, Object> properties = ImmutableMap.<String, Object>builder().put("application.title", title)
                .put("application.version", version).put("application.vendor", DEFAULT_VENDOR).put("application.description", DEFAULT_DESCRIPTION);

        return builder.sources(App.class).properties(properties.build()).web(true).banner((environment, sourceClass, out) -> {
            for (String line : BANNER) {
                out.print(format("\n%s%s", INDENTATION, line));
            }

            out.print(format("\n%s%s : %s (%s)\n\n", INDENTATION, title, version, on(",").join(environment.getActiveProfiles())));
        });
    }

    public static void main(String[] args) {
        final App app = new App();
        Map<String, Object> props = makeMap(args);
        app.run(app.configure(new SpringApplicationBuilder(App.class).properties(props)).build());
    }

    public static Map<String, Object> makeMap(String[] args) {
        Map<String, Object> kvs =
                Arrays.asList(args)
                    .stream()
                    .map(elem -> elem.split("="))
                    .filter(elem -> elem.length == 2)
                    .collect(Collectors.toMap(e -> e[0], e -> e[1]));
        return kvs;
    }
}