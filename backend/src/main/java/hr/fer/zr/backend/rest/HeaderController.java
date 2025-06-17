package hr.fer.zr.backend.rest;

import hr.fer.zr.backend.DTOs.UsersDTO;
import hr.fer.zr.backend.service.UserAccountService;
import hr.fer.zr.backend.domain.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/header")
public class HeaderController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/info")
    public ResponseEntity<UsersDTO> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof String user) {
                Optional<UsersEntity> userAccount = userAccountService.findUserByEmail(user);
                if (userAccount.isPresent()){
                    UsersEntity account = userAccount.get();
                    account.setPassword(null);
                    return ResponseEntity.ok(UsersDTO.fromEntity(account));
                }
            }
        }
        return ResponseEntity.notFound().build();
    }
}
