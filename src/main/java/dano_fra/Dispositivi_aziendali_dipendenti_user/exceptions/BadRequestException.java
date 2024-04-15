package dano_fra.Dispositivi_aziendali_dipendenti_user.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException {
    private List<ObjectError> errorsList;

    public BadRequestException(List<ObjectError> errorsList) {
        super("Bad Request");
        this.errorsList = errorsList;
    }

    public BadRequestException(String message) {
        super(message);
    }
}
