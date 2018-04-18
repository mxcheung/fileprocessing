package au.com.lonsec.service.company;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.MoreObjects.firstNonNull;
import static java.lang.String.format;

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

    		"                       .-') _               _  .-')     ('-.                               ('-.   ('-.     _  .-')     ('-.  _  .-')   ",
    		"                      (  OO) )             ( \\( -O )  _(  OO)                            _(  OO) ( OO ).-.( \\( -O )  _(  OO)( \\( -O )  ",
    		"   ,------.,--. ,--.  /     '._ ,--. ,--.   ,------. (,------.         .-----. ,--.     (,------./ . --. / ,------. (,------.,------.  ",
    		"('-| _.---'|  | |  |  |'--...__)|  | |  |   |   /`. ' |  .---'        '  .--./ |  |.-')  |  .---'| \\-.  \\  |   /`. ' |  .---'|   /`. ' ",
    		"(OO|(_\\    |  | | .-')'--.  .--'|  | | .-') |  /  | | |  |            |  |('-. |  | OO ) |  |  .-'-'  |  | |  /  | | |  |    |  /  | | ",
    		"/  |  '--. |  |_|( OO )  |  |   |  |_|( OO )|  |_.' |(|  '--.        /_) |OO  )|  |`-' |(|  '--.\\| |_.'  | |  |_.' |(|  '--. |  |_.' | ",
    		"\\_)|  .--' |  | | `-' /  |  |   |  | | `-' /|  .  '.' |  .--'        ||  |`-'|(|  '---.' |  .--' |  .-.  | |  .  '.' |  .--' |  .  '.' ",
    		"  \\|  |_) ('  '-'(_.-'   |  |  ('  '-'(_.-' |  |\\  \\  |  `---.      (_'  '--'\\ |      |  |  `---.|  | |  | |  |\\  \\  |  `---.|  |\\  \\  ",
    		"   `--'     `-----'      `--'    `-----'    `--' '--' `------'         `-----' `------'  `------'`--' `--' `--' '--' `------'`--' '--' " };

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
        app.run(app.configure(new SpringApplicationBuilder(App.class)).build());
    }

}