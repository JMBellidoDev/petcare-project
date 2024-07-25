package petcare.app.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/** Clase principal */
@SpringBootApplication
@EnableDiscoveryClient
public class PetcareGatewayApplication {

  /**
   * Método main
   * 
   * @param args Argumentos
   */
  public static void main(String[] args) {
    SpringApplication.run(PetcareGatewayApplication.class, args);
  }

}
