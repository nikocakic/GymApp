package hr.fer.zr.backend.rest;

import hr.fer.zr.backend.DTOs.UsersDTO;
import hr.fer.zr.backend.dao.TrainerRepository;
import hr.fer.zr.backend.domain.GymAttendantsEntity;
import hr.fer.zr.backend.domain.UsersEntity;
import hr.fer.zr.backend.domain.TrainerEntity;
import hr.fer.zr.backend.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
@EnableWebSecurity
public class AuthController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private TrainerRepository trainerRepository;

    @PostMapping("/signup")
    public ResponseEntity<UsersDTO> registration(@RequestBody UsersDTO userAccountDTO) {
        // Check for existing email and username in a single step
        boolean emailExists = userAccountService.findUserByEmail(userAccountDTO.getEmail()).isPresent();
        boolean usernameExists = userAccountService.findUserByUsername(userAccountDTO.getUsername()).isPresent();

        // Debug logs
        System.out.println("Email exists: " + emailExists);
        System.out.println("Username exists: " + usernameExists);
        System.out.println("Prije if-a");
        if (!emailExists && !usernameExists) {
            //userAccountService.validateEmail(userAccountDTO.getEmail());

            UsersEntity user;
            user = userAccountService.saveUser(
                    userAccountDTO.getFirstName(),
                    userAccountDTO.getLastName(),
                    userAccountDTO.getEmail(),
                    userAccountDTO.getBirthdate(),
                    userAccountDTO.getRole(),
                    userAccountDTO.getUsername(),
                    userAccountDTO.getPassword(),
                    userAccountDTO.getRole().equals(UserAccountRole.TRAINER) ? false : true
            );
            System.out.println("User kreiran");
            if (userAccountDTO.getRole().equals(UserAccountRole.TRAINER)){
                System.out.println("Usa u if za trenera");
                TrainerEntity t = userAccountService.saveTrainer(user);
            } else if (userAccountDTO.getRole().equals(UserAccountRole.GYM_ATTENDANT)) {
                GymAttendantsEntity g = userAccountService.saveGymA(user);
            }
            System.out.println("Izasa iz returna za savenje trenera");
            user.setPassword(null); // Hide password in response
            return ResponseEntity.ok(UsersDTO.fromEntity(user));
        }

        return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<UsersDTO>> admin(){
        List<UsersDTO> userAccountDTOs = userAccountService.listAllDisabled().stream()
                .map(UsersDTO::fromEntity)
                .collect(Collectors.toList());
    for(UsersDTO ac:userAccountDTOs) {
        System.out.println(ac);
    }
        return new ResponseEntity<>(userAccountDTOs, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<UsersDTO> user(@RequestBody UsersDTO userAccountDTO) {
        System.out.println(userAccountDTO.toString());
        Optional<UsersEntity> existingUserEmail = userAccountService.findUserByEmail(userAccountDTO.getEmail());
        if (existingUserEmail.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }else {
            UsersEntity existingUser = (UsersEntity) existingUserEmail.get();

            if(userAccountDTO.isEnabled()) {
                existingUser.setEnabled(true);
                userAccountService.confirmUser(existingUser);}
            else  {
               // user
                userAccountService.deleteUser(existingUser.getUsername());
            }
            return ResponseEntity.ok(userAccountDTO);
        }
    }

    //treba get metoda adminove stranice sa prikazom usera kojima je varijabla isEnabled false i onda gumb pored
    //svakoga koji bi postavio varijablu isEnabled na true i time potvrdio registraciju novog usera

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        Optional<UsersEntity> existingUserEmail = userAccountService.findUserByEmail("admin@fer.hr");
        if (existingUserEmail.isEmpty()){
            userAccountService.createAdmin();

        }
    }

}
