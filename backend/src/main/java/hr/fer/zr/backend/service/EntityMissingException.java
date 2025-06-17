package hr.fer.zr.backend.service;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class EntityMissingException extends RuntimeException{

    private static final long serialVersionUID = 10L;

    public EntityMissingException(Object ref) {
        super("Entity with reference " + ref + " not found.");
    }

    public EntityMissingException(Object ref1, Object ref2) {
        super("Entitys with reference " + ref1 + " and " + ref2+ " were not found.");
    }


}

