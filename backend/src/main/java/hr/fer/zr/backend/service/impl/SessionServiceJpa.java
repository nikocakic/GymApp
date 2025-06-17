package hr.fer.zr.backend.service.impl;

import hr.fer.zr.backend.dao.GymAttendantsRepository;
import hr.fer.zr.backend.dao.SessionAttendantRepository;
import hr.fer.zr.backend.dao.SessionRepository;
import hr.fer.zr.backend.dao.UserAccountRepository;
import hr.fer.zr.backend.domain.SessionAttendantsEntity;
import hr.fer.zr.backend.domain.SessionsEntity;
import hr.fer.zr.backend.service.EntityMissingException;
import hr.fer.zr.backend.service.RequestDeniedException;
import hr.fer.zr.backend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceJpa implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private GymAttendantsRepository gymAttendantsRepository;

    @Autowired
    private SessionAttendantRepository sessionAttendantRepository;

    @Override
    public List<SessionsEntity> listAll() {
        return sessionRepository.listAll();
    }

    @Override
    public Optional<SessionsEntity> findSessionById(Long id) {
        return sessionRepository.findSessionById(id);
    }

    @Override
    public Optional<SessionsEntity> findSessionByDate(Date date, LocalTime timeSlot) {
        return sessionRepository.findSessionByDate(date, timeSlot);
    }

    @Override
    public void saveSession(String dayInWeek, LocalTime timeSlot, String title, String description, Integer maxAttendants, Integer currentAttendants, Date date) {
        System.out.println("Value of time_slot is " +  timeSlot);
        if (dayInWeek == null || timeSlot == null || title == null || description == null || maxAttendants == null || currentAttendants == null || date == null) {
            throw new RequestDeniedException("One of required parameters is null!!!");
        }
        SessionsEntity sessionsEntity = new SessionsEntity(dayInWeek, timeSlot, title, description, maxAttendants, currentAttendants, date);
        System.out.println(sessionsEntity);
        //sessionRepository.save(sessionsEntity);
        sessionRepository.saveSession(sessionsEntity);
    }

    @Override
    public void deleteSession(long id, Date date, LocalTime timeSlot) {
        System.out.println("Delete session method");
        System.out.println("date: " + date + " timeSlot " + timeSlot);
        sessionRepository.deleteById(id);
        System.out.println("Session deleted successfully.");

    }

    @Override
    public SessionsEntity fetch(Date date, LocalTime timeSlot) {
        return findSessionByDate(date, timeSlot).orElseThrow(() -> new EntityMissingException(date, timeSlot));
    }

    @Override
    public Optional<SessionsEntity> getSessionById(Long id) {
        return sessionRepository.findSessionById(id);
    }

    @Override
    public List<SessionsEntity> findSessionsByDateRange(Date startDate, Date endDate) {
        return sessionRepository.findSessionsByDateRange(startDate, endDate);
    }

    @Override
    public void incrementNOAttendants(Long id) {
        sessionRepository.incrementCurrentAttendants(id);
    }

    @Override
    public void saveSessionAttendant(Long idSes, Long idUser) {
        SessionAttendantsEntity sa = new SessionAttendantsEntity(idSes, idUser);
        sessionAttendantRepository.save(sa);
        //gymAttendantsRepository.addSessionAttendant(idSes, idUser);
    }

    @Override
    public Optional<SessionAttendantsEntity> getSessionAttendant(Long idSes, Long idUser) {
        Optional <SessionAttendantsEntity> s = sessionAttendantRepository.findSessionAttendant(idSes, idUser);
        return s;
    }

    @Override
    public List<SessionAttendantsEntity> getSessionAttendantsForUserId(Long idUser) {
        List<SessionAttendantsEntity> sal;
        sal =sessionAttendantRepository.getSessionAttendantsForUserId(idUser);
        return sal;
    }


}
