package hr.fer.zr.backend.rest;

import hr.fer.zr.backend.DTOs.UsersDTO;
import hr.fer.zr.backend.domain.UsersEntity;
import hr.fer.zr.backend.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/dashboard")
    public ResponseEntity<List<UsersDTO>> getUserInfo() {
        List<UsersDTO> trainerDTOs = userAccountService.listAllDisabled().stream()
                .map(UsersDTO::fromEntity)
                .collect(Collectors.toList());
        for(UsersDTO ac:trainerDTOs) {
            System.out.println(ac);
        }
        return new ResponseEntity<>(trainerDTOs, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptTrainerRequest(@PathVariable("id") Long id) {
        System.out.println("Usa u acceptTrainerRequest");
        try {
            System.out.println("Usa u try catch blok");
            UsersEntity user = userAccountService.getUserById(id);

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            user.setEnabled(true);
            System.out.println("Set enabled na useru i sad se poziva updateUser metoda");
            userAccountService.updateUser(user);

            return ResponseEntity.ok().body("Trainer request accepted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error accepting trainer request: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/decline/{id}")
    public ResponseEntity<?> declineTrainerRequest(@PathVariable("id") Long id) {
        try {
            UsersEntity user = userAccountService.getUserById(id);

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            userAccountService.deleteUser(user.getUsername());

            return ResponseEntity.ok().body("Trainer request declined successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error declining trainer request: " + e.getMessage());
        }
    }


}
