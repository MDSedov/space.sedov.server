package space.sedov.server.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "serverEntityManagerFactory",
        transactionManagerRef = "serverTransactionManager",
        basePackages = "space.sedov.server.repository"
)
public class DatabaseConfiguration {
    @Value("${spring.server.datasource.url}")
    private String url;

    @Value("${spring.server.datasource.username}")
    private String username;

    @Value("${spring.server.datasource.password}")
    private String password;

    @Primary
    @Bean(name = "serverDataSource")
    public DataSource serverDataSource() {
        return DataSourceBuilder.create().url(url).username(username).password(password).build();
    }

    @Primary
    @Bean(name = "serverEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean serverEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("serverDataSource") DataSource serverDataSource) {
        return builder.dataSource(serverDataSource)
                .packages("space.sedov.server.entity")
                .build();
    }

    @Primary
    @Bean(name = "serverTransactionManager")
    public PlatformTransactionManager serverTransactionManager(
            @Qualifier("serverEntityManagerFactory") EntityManagerFactory serverEntityManagerFactory) {
        return new JpaTransactionManager(serverEntityManagerFactory);
    }
}
