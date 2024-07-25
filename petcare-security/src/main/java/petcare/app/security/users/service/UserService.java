package petcare.app.security.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import petcare.app.domain.entity.Client;
import petcare.app.domain.entity.Vet;
import petcare.app.domain.entity.VetEntity;
import petcare.app.domain.entity.security.AppUser;
import petcare.app.domain.entity.security.Role;
import petcare.app.domain.repository.ClientRepository;
import petcare.app.domain.repository.RoleRepository;
import petcare.app.domain.repository.VetEntityRepository;
import petcare.app.domain.repository.VetRepository;
import petcare.app.domain.utils.ExceptionMessages;
import petcare.app.domain.utils.exceptions.ResourceNotFoundException;
import petcare.app.security.users.utils.RoleConstants;

/** Servicio de usuarios. Registro y modificación de usuarios */
@Service
public class UserService implements UserDetailsService {

  /** Repositorio de clientes */
  private ClientRepository clientRepository;

  /** Repositorio de veterinarios */
  private VetRepository vetRepository;

  /** Repositorio de entidades veterinarias */
  private VetEntityRepository vetEntityRepository;

  /** Repositorio de Roles */
  private RoleRepository roleRepository;

  /** Sistema de encriptación */
  private PasswordEncoder passwordEncoder;

  /**
   * Setter - ClientRepository. Inyección de dependencias
   * 
   * @param clientRepository Repositorio de clientes
   */
  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  /**
   * Setter - VetRepository. Inyección de dependencias
   * 
   * @param vetRepository Repositorio de veterinarios
   */
  @Autowired
  public void setVetRepository(VetRepository vetRepository) {
    this.vetRepository = vetRepository;
  }

  /**
   * Setter - VetEntityRepository. Inyección de dependencias
   * 
   * @param vetEntityRepository Repositorio de entidades veterinarias
   */
  @Autowired
  public void setVetEntityRepository(VetEntityRepository vetEntityRepository) {
    this.vetEntityRepository = vetEntityRepository;
  }

  /**
   * Setter - RoleRepository. Inyección de dependencias
   * 
   * @param roleRepository Repositorio de roles
   */
  @Autowired
  public void setRoleRepository(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  /**
   * Setter - PasswordEncoder. Inyección de dependencias
   * 
   * @param passwordEncoder Sistema de encriptación de contraseñas
   */
  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    AppUser user;

    Optional<Client> optClient = clientRepository.findByUsername(username);
    Optional<Vet> optVet = vetRepository.findByUsername(username);
    Optional<VetEntity> optVetEntity = vetEntityRepository.findByUsername(username);

    user = optClient.isPresent() ? optClient.get() : optVet.isPresent() ? optVet.get() : optVetEntity.get();

    return User
        .builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .authorities(getAuthorities(user))
        .build();
  }

  /**
   * Almacena un usuario cliente del sistema
   * 
   * @param client Cliente / Usuario a almacenar
   * @return Client - Cliente / Usuario ya almacenado
   */
  @Transactional
  public Client saveClient(Client client) {

    // Se asignan los roles
    Role clientRole = roleRepository.findByName(RoleConstants.ROLE_CLIENT);

    client.setRoles(List.of(clientRole));
    client.setEnabled(true);

    // Se encripta la contraseña
    client.setPassword(passwordEncoder.encode(client.getPassword()));

    return clientRepository.save(client);
  }

  /**
   * Almacena un veterinario del sistema
   * 
   * @param vet Veterinario a almacenar
   * @return Vet - Veterinario ya almacenado
   */
  @Transactional
  public Vet saveVet(Vet vet) {

    // Se asignan los roles
    Role vetRole = roleRepository.findByName(RoleConstants.ROLE_VET);

    vet.setRoles(List.of(vetRole));
    vet.setEnabled(true);

    // Se encripta la contraseña
    vet.setPassword(passwordEncoder.encode(vet.getPassword()));

    return vetRepository.save(vet);
  }

  /**
   * Almacena una nueva entidad veterinaria en el sistema
   * 
   * @param vetEntity Entidad veterinaria a almacenar
   * @return VetEntity - La entidad veterinaria ya almacenada
   */
  @Transactional
  public VetEntity saveVetEntity(VetEntity vetEntity) {

    // Se asignan los roles. Las entidades veterinarias tienen acceso a toda la aplicación
    Role vetEntityRole = roleRepository.findByName(RoleConstants.ROLE_VET_ENTITY);

    vetEntity.setRoles(List.of(vetEntityRole));
    vetEntity.setEnabled(true);

    // Se encripta la contraseña
    vetEntity.setPassword(passwordEncoder.encode(vetEntity.getPassword()));

    return vetEntityRepository.save(vetEntity);
  }

  /**
   * Modifica un cliente existente
   * 
   * @param client Cliente / Usuario con los datos ya modificados
   * @param id     ID del cliente a modificar
   * @return Client - El cliente ya modificado
   * @throws ResourceNotFoundException Si no se encuentra algún recurso
   */
  @Transactional
  public Client updateClient(Client client, Long id) throws ResourceNotFoundException {

    // Se recupera el cliente / Usuario
    Optional<Client> optFoundClient = clientRepository.findById(id);

    if (optFoundClient.isPresent()) {

      // Se modifican los datos. Las mascotas no se modifican
      Client foundClient = optFoundClient.get();

      foundClient.setEmail(client.getEmail());
      foundClient.setEnabled(client.isEnabled());
      foundClient.setAddress(client.getAddress());
      foundClient.setPhoneNumber(client.getPhoneNumber());

      return clientRepository.save(foundClient);

    } else {
      throw new ResourceNotFoundException(String.format(ExceptionMessages.CLIENT_NOT_FOUND_BY_ID, id));
    }
  }

  /**
   * Modifica un veterinario existente
   * 
   * @param vet Veterinario con los datos ya modificados
   * @param id  ID del veterinario a modificar
   * @return Vet - El veterinario ya modificado
   * @throws ResourceNotFoundException Si no se encuentra algún recurso
   */
  @Transactional
  public Vet updateVet(Vet vet, Long id) throws ResourceNotFoundException {

    // Se obtiene la entidad almacenada y se modifican los datos
    Optional<Vet> optFoundVet = vetRepository.findById(id);

    if (optFoundVet.isPresent()) {

      Vet foundVet = optFoundVet.get();

      // No se puede modificar username, password ni cif
      foundVet.setEnabled(vet.isEnabled());
      foundVet.setEmail(vet.getEmail());

      foundVet
          .setVetEntity(vetEntityRepository
              .findById(vet.getVetEntity().getId())
              .orElseThrow(() -> new ResourceNotFoundException(
                  String.format(ExceptionMessages.VET_ENTITY_NOT_FOUND_BY_ID, vet.getVetEntity().getId()))));

      return vetRepository.save(foundVet);

    } else {
      throw new ResourceNotFoundException(String.format(ExceptionMessages.VET_NOT_FOUND_BY_ID, id));
    }
  }

  /**
   * Modifica una entidad veterinaria existente
   * 
   * @param vetEntity Entidad veterinaria con los datos ya modificados
   * @param id        ID de la entidad a modificar
   * @return VetEntity - La entidad veterinaria ya modificada
   * @throws ResourceNotFoundException Si no se encuentra algún recurso
   */
  @Transactional
  public VetEntity updateVetEntity(VetEntity vetEntity, Long id) throws ResourceNotFoundException {

    // Se obtiene la entidad almacenada y se modifican los datos
    Optional<VetEntity> optFoundVetEntity = vetEntityRepository.findById(id);

    if (optFoundVetEntity.isPresent()) {

      VetEntity foundVetEntity = optFoundVetEntity.get();

      // No se puede modificar username, password ni cif
      foundVetEntity.setEnabled(vetEntity.isEnabled());
      foundVetEntity.setEmail(vetEntity.getEmail());
      foundVetEntity.setName(vetEntity.getName());
      foundVetEntity.setAddress(vetEntity.getAddress());
      foundVetEntity.setPhoneNumber(vetEntity.getPhoneNumber());

      return vetEntityRepository.save(foundVetEntity);

    } else {
      throw new ResourceNotFoundException(String.format(ExceptionMessages.VET_ENTITY_NOT_FOUND_BY_ID, id));
    }

  }

  /**
   * Obtiene una lista con todos los authorities en función del usuario
   * 
   * @param appUser Usuario de la aplicación
   * @return List(GrantedAuthority) - Lista con todos los authorities en función del usuario logeado
   */
  private List<? extends GrantedAuthority> getAuthorities(AppUser appUser) {

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    // Mapeo de Roles
    if (appUser instanceof Client) {
      authorities.add(RoleConstants.USER_AUTHORITY);

    } else if (appUser instanceof Vet) {
      authorities.add(RoleConstants.VET_AUTHORITY);

    } else if (appUser instanceof VetEntity) {
      authorities.add(RoleConstants.VET_ENTITY_AUTHORITY);

    }

    return authorities;
  }
}
