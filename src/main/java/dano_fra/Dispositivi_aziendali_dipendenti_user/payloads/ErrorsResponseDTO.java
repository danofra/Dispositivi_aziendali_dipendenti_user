package dano_fra.Dispositivi_aziendali_dipendenti_user.payloads;

import java.time.LocalDateTime;

public record ErrorsResponseDTO(
        String message,
        LocalDateTime timestamp
) {
}
