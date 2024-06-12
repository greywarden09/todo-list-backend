package pl.greywarden.tutorial.config;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

import javax.sql.DataSource;

@Factory
public class JooqConfigurationFactory {
    private final DataSource dataSource;

    public JooqConfigurationFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Singleton
    public Configuration configuration() {
        final var configuration = new DefaultConfiguration();
        configuration.setSQLDialect(SQLDialect.POSTGRES);
        configuration.set(dataSource);

        return configuration;
    }
}
