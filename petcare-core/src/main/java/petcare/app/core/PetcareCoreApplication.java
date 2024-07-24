package petcare.app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/** Clase principal */
@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = { "petcare.app.domain.entity", "petcare.app.domain.entity.security" })
@ComponentScan(basePackages = { "petcare.app.domain.repository" })
public class PetcareCoreApplication {

  /**
   * Método main
   * 
   * @param args Argumentos
   */
  public static void main(String[] args) {
    SpringApplication.run(PetcareCoreApplication.class, args);
  }

}
