package dano_fra.Dispositivi_aziendali_dipendenti_user.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(
        @NotEmpty(message = "L'username non può essere vuoto")
        @NotNull(message = "L'username non può essere nullo")
        @Size(message = "L'username deve essere almeno 3 caratteri, ma non più lungo di 20 caratteri", min = 3, max = 20)
        String username,
        @NotEmpty(message = "Il nome non può essere vuoto")
        String nome,
        @NotEmpty(message = "Il cognome non può essere vuoto")
        String cognome,
        @NotEmpty(message = "L'email non può essere vuota")
        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "L'email inserita non è valida")
        String email,
        @NotEmpty(message = "La password non può essere vuota")
        String password,
        String avatar
) {
}
