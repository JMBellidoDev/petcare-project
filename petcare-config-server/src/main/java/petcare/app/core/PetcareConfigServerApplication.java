package petcare.app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/** Clase principal */
@SpringBootApplication
@EnableConfigServer
public class PetcareConfigServerApplication {

  /**
   * Método main
   * 
   * @param args Argumentos
   */
  public static void main(String[] args) {
    SpringApplication.run(PetcareConfigServerApplication.class, args);
  }

}
