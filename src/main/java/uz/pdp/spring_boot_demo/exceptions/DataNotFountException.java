package uz.pdp.spring_boot_demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFountException extends RuntimeException {

    public DataNotFountException(String message) {
        super(message);
    }
}
