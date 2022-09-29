package test.app.jpa;

import com.laganini.cloud.logging.author.AuthorProvider;
import com.laganini.cloud.storage.audit.provider.jpa.EnableLaganiniCloudStorageAuditJpa;
import com.laganini.cloud.storage.jpa.EnableLaganiniCloudStorageJpa;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "test.app.jpa")
@EntityScan(basePackages = {"test.app.jpa"})
@EnableLaganiniCloudStorageJpa(basePackages = {"test.app.jpa", "com.laganini.cloud.storage.audit.provider.jpa"})
@EnableLaganiniCloudStorageAuditJpa
public class JpaAuditTestConfiguration {

    @Bean
    AuthorProvider authorProvider() {
        return () -> "test";
    }

}
