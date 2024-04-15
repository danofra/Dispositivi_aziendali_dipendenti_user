package dano_fra.Dispositivi_aziendali_dipendenti_user.services;

import dano_fra.Dispositivi_aziendali_dipendenti_user.entities.Dipendente;
import dano_fra.Dispositivi_aziendali_dipendenti_user.exceptions.UnauthorizedException;
import dano_fra.Dispositivi_aziendali_dipendenti_user.payloads.DipendenteLoginDTO;
import dano_fra.Dispositivi_aziendali_dipendenti_user.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private JWTTools jwtTools;

    public String authwnticateDipAndToken(DipendenteLoginDTO payload) {
        Dipendente dipendente = this.dipendenteService.findByEmail(payload.email());
        if (dipendente.getPassword().equals(payload.password())) {
            return this.jwtTools.createToken(dipendente);
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }
}
