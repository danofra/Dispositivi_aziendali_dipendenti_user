package dano_fra.Dispositivi_aziendali_dipendenti_user.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(int id) {
        super("La tua ricerca non è andata a buon fine! L' id " + id + " non è stato trovato!");
    }
}

