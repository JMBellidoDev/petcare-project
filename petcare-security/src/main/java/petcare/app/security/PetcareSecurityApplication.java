package petcare.app.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "petcare.app.domain.entity" })
@EnableJpaRepositories(basePackages = { "petcare.app.domain.repository" })
@RefreshScope
public class PetcareSecurityApplication {

  /**
   * Método main
   * 
   * @param args Argumentos
   */
  public static void main(String[] args) {
    SpringApplication.run(PetcareSecurityApplication.class, args);
  }

}
