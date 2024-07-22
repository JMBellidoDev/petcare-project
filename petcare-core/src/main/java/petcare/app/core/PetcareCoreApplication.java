package petcare.app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/** Clase principal */
@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = { "petcare.app.domain.entity" })
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
