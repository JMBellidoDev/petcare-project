package petcare.app.domain;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/** Clase principal de la aplicación de dominio */
@SpringBootApplication
@EntityScan(basePackages = { "petcare.app.domain.security.entity" })
public class DomainModelCommonsApplication {

}
