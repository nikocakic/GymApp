package hr.fer.zr.backend.rest;

import hr.fer.zr.backend.DTOs.UsersDTO;
import hr.fer.zr.backend.domain.SessionAttendantsEntity;
import hr.fer.zr.backend.domain.SessionsEntity;
import hr.fer.zr.backend.service.SessionService;
import hr.fer.zr.backend.DTOs.SessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/add")
    public ResponseEntity<?> addSession(@RequestBody SessionDTO sessionDto) {
        System.out.println("Usa u addSession");
        Date sqlDate = new Date(sessionDto.getDate().getTime());
        System.out.println("Checking if session exists");
        boolean sessionExists = sessionService.findSessionByDate(sqlDate, sessionDto.getTimeSlot()).isPresent();

        if(!sessionExists){
            System.out.println("Entered part where session is created");
            SessionsEntity session = new SessionsEntity(
                    sessionDto.getDayInWeek(),
                    sessionDto.getTimeSlot(),
                    sessionDto.getTitle(),
                    sessionDto.getDescription(),
                    sessionDto.getMaxAttendants(),
                    sessionDto.getCurrentAttendants(),
                    sqlDate
            );
            System.out.println(sessionDto.toString());
            sessionService.saveSession(
                    sessionDto.getDayInWeek(),
                    sessionDto.getTimeSlot(),
                    sessionDto.getTitle(),
                    sessionDto.getDescription(),
                    sessionDto.getMaxAttendants(),
                    sessionDto.getCurrentAttendants(),
                    sqlDate
            );
            System.out.println("Giving response ok");
            return ResponseEntity.ok(SessionDTO.fromEntity(session));
        }
        System.out.println("Giving bad response");
        return new ResponseEntity<>(null, HttpStatus.CONFLICT);

    }

    @GetMapping("/week")
    public ResponseEntity<List<SessionsEntity>> getSessionsForWeek(
            @RequestParam("startDate") Date startDate,
            @RequestParam("endDate") Date endDate) {
        System.out.println("Fetching sessions for the week from " + startDate + " to " + endDate);
        List<SessionsEntity> sessions = sessionService.findSessionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(sessions);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSession(
            @RequestParam ("id") long id,
            @RequestParam ("date") Date date,
            @RequestParam ("timeSlot")LocalTime timeSlot) {
        System.out.println("Usa u delete metodu");
        System.out.println(date);
        System.out.println(timeSlot);
        System.out.println("TimeSlot: " + timeSlot.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        try {
             sessionService.deleteSession(id, date, timeSlot);
             return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.out.println("Error part");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinSession(@RequestBody Map<String, Long> requestBody) {
        Long sessionId = requestBody.get("sessionId");
        Long userId = requestBody.get("userId");

        try {
            Optional<SessionsEntity> optionalSession = sessionService.findSessionById(sessionId);

            if (optionalSession.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Session not found.");
            }

            SessionsEntity session = optionalSession.get();

            if (session.getCurrentAttendants() +1 > session.getMaxAttendants()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Session is full.");
            }

            Optional<SessionAttendantsEntity> se = sessionService.getSessionAttendant(sessionId, userId);
            if (se.isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User is already joined.");
            }
            sessionService.incrementNOAttendants(sessionId);
            sessionService.saveSessionAttendant(sessionId, userId);


            return ResponseEntity.ok(session);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while joining the session.");
        }
    }

    @GetMapping("/getSessionAttendants")
    public ResponseEntity<List<SessionAttendantsEntity>> getSessionAttendantsForUserId(
            @RequestParam("id") Long id) {
        System.out.println("Trazim sessions attendants za user id " + id);
        List<SessionAttendantsEntity> sal = sessionService.getSessionAttendantsForUserId(id);
        System.out.println("Fetchani session attendants");
        for (SessionAttendantsEntity s: sal){
            System.out.println(s);
        }
        return ResponseEntity.ok(sal);
    }

}