package hr.fer.zr.backend.service.impl;

import hr.fer.zr.backend.dao.GymAttendantsRepository;
import hr.fer.zr.backend.dao.TrainerRepository;
import hr.fer.zr.backend.dao.UserAccountRepository;
import hr.fer.zr.backend.domain.GymAttendantsEntity;
import hr.fer.zr.backend.domain.TrainerEntity;
import hr.fer.zr.backend.domain.UsersEntity;
import hr.fer.zr.backend.rest.UserAccountRole;
import hr.fer.zr.backend.service.EntityMissingException;
import hr.fer.zr.backend.service.RequestDeniedException;
import hr.fer.zr.backend.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountServiceJpa implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private GymAttendantsRepository gymAttendantsRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    private static final String EMAIL_FORMAT = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";


    @Override
    public List<UsersEntity> listAll() {
        return userAccountRepository.findAll();
    }

    @Override
    public List<UsersEntity> listAllDisabled() {
        return userAccountRepository.findByEnabledFalse();
    }

    @Override
    public UsersEntity saveUser(String firstName, String lastName, String email, Date birthdate, UserAccountRole role, String username, String password, boolean enabled) {
        if (firstName == null || lastName == null ||email == null || username == null || password == null) {
            throw new RequestDeniedException("Must enter first and last name, email, username and password!");
        }
        UsersEntity usersEntity = new UsersEntity(firstName, lastName,
                email, birthdate, role, username, bcryptEncoder.encode(password), enabled);
        return userAccountRepository.save(usersEntity);
    }

    @Override
    public TrainerEntity saveTrainer(UsersEntity user) {
        System.out.println("Save trainer metoda");
        TrainerEntity trainer = new TrainerEntity();
        trainer.setUser(user);
        trainer.setRating(BigDecimal.ZERO);
        System.out.println("Trener konfiguriran");
        return trainerRepository.save(trainer);
    }

    @Override
    public GymAttendantsEntity saveGymA(UsersEntity user) {
        System.out.println("Save gymA metoda");
        GymAttendantsEntity gymA = new GymAttendantsEntity();
        gymA.setUser(user); 
        gymA.setGymPlanId(1); //deafult value for now
        gymA.setTrainingsDone(0);
        System.out.println("Gym attendant konfiguriran");
        return gymAttendantsRepository.save(gymA);
    }

    @Override
    public Optional<UsersEntity> findUserByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }


    @Override
    public UsersEntity getUserById(Long id) {
        return userAccountRepository.getById(id);
    }

    @Override
    public UsersEntity updateUser(UsersEntity user) {
        Long userId = user.getId();
        System.out.println("Usa u update user");
        if(!userAccountRepository.existsById(userId))
            throw new EntityMissingException(UsersEntity.class);
        System.out.println("Sad bi se tribala zvat metoda save nad userom");

        return userAccountRepository.save(user);
    }

    //the ones with isEnabled false

    @Override
    public Optional<UsersEntity> findUserByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsersEntity> ua= userAccountRepository.findByEmail(username);
        if(ua.isEmpty()) {
            throw new UsernameNotFoundException("no username");
        }
        UsersEntity userAcc=ua.get();
        UserDetails user =
                User.builder()
                        .username(userAcc.getEmail())
                        .password(userAcc.getPassword())
                        .roles(userAcc.getRole().toString())
                        .build();
        return user;
    }

    @Override
    public void validateEmail(String email) {
        if (email == null || email.isBlank() || !email.matches(EMAIL_FORMAT)) {
            throw new RequestDeniedException("Email " + email + "is not valid");
        }
    }

    @Override
    public void createAdmin() {
        UsersEntity admin = new UsersEntity(
            "Admin",
            "Admin",
            "admin@fer.hr",
            new Date(2002, 10, 26),
                UserAccountRole.ADMIN,
            "adminUsername",
            bcryptEncoder.encode("adminpass"),
                true

        );
        admin.setEnabled(true);
        userAccountRepository.save(admin);
    }
    @Override
    public UsersEntity deleteUser(String username) {
        UsersEntity user = fetch(username);

        if (user.getRole() == UserAccountRole.TRAINER) {
            deleteTrainer(user.getId());
        }

        userAccountRepository.delete(user);
        System.out.println("User deleted successfully.");
        return user;
    }

    @Override
    public UsersEntity fetch(String username) {
        return findUserByUsername(username).orElseThrow(
                ()-> new EntityMissingException(username)
        );
    }

    @Override
    public void confirmUser(UsersEntity existingUser) {
        userAccountRepository.save(existingUser);
    }

    @Override
    public void deleteTrainer(Long userId) {
        Optional<TrainerEntity> trainer = trainerRepository.findById(userId);
        if (trainer.isPresent()) {
            trainerRepository.delete(trainer.get());
            System.out.println("Trainer deleted successfully.");
        } else {
            throw new EntityMissingException("Trainer with user ID " + userId + " not found.");
        }
    }

    @Override
    public List<UsersEntity> listAllTrainers() {
        List<UsersEntity> trainersList= userAccountRepository.listAllTrainers();
        return trainersList;
    }

}
