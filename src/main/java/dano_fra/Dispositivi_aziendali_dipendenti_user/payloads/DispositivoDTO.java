package dano_fra.Dispositivi_aziendali_dipendenti_user.payloads;

import jakarta.validation.constraints.NotEmpty;

public record DispositivoDTO(
        @NotEmpty(message = "La tipologia non può essere vuoto")
        String tipologia,
        String stato
) {
}
