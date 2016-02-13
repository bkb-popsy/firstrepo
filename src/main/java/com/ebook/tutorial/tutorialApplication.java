package com.ebook.tutorial;

import com.ebook.tutorial.resources.ContactResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class tutorialApplication extends Application<tutorialConfiguration> {

    private static final Logger LOGGER = LoggerFactory.
            getLogger(tutorialApplication.class);

    public static void main(final String[] args) throws Exception {
        new tutorialApplication().run(args);
    }

    @Override
    public String getName() {
        return "tutorial";
    }

    @Override
    public void initialize(final Bootstrap<tutorialConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final tutorialConfiguration configuration,
            final Environment environment) {
        LOGGER.info("Method App#run() called");
        for (int i = 0; i < configuration.getMessageRepetitions(); i++) {
            LOGGER.debug(configuration.getMessage());
        }

        // Create a DBI factory and build a JDBI instance
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory
                .build(environment, configuration.getDataSourceFactory(), "mysql");

        environment.jersey().register(new ContactResource(jdbi));
    }

}
