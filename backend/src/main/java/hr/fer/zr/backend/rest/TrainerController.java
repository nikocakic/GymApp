package hr.fer.zr.backend.rest;

import hr.fer.zr.backend.domain.SessionsEntity;
import hr.fer.zr.backend.domain.UsersEntity;
import hr.fer.zr.backend.service.SessionService;
import hr.fer.zr.backend.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/trainers")
public class TrainerController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/getAll")
    public ResponseEntity<List<UsersEntity>> getSessionsForWeek() {
        List<UsersEntity> trainerList =userAccountService.listAllTrainers();
        return ResponseEntity.ok(trainerList);
    }
}
