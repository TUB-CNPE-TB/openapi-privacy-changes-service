package cnpe.team.blue.privacychangesservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@SpringBootApplication
public class PrivacyChangesServiceApplication {
    protected static final Logger logger = LoggerFactory.getLogger(PrivacyChangesServiceApplication.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder(PrivacyChangesServiceApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}

