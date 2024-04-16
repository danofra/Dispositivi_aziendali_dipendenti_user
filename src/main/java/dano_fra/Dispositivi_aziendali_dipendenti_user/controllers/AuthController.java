package dano_fra.Dispositivi_aziendali_dipendenti_user.controllers;


import dano_fra.Dispositivi_aziendali_dipendenti_user.exceptions.BadRequestException;
import dano_fra.Dispositivi_aziendali_dipendenti_user.payloads.DipendenteDTO;
import dano_fra.Dispositivi_aziendali_dipendenti_user.payloads.DipendenteLoginDTO;
import dano_fra.Dispositivi_aziendali_dipendenti_user.payloads.DipendenteLoginResponseDTO;
import dano_fra.Dispositivi_aziendali_dipendenti_user.payloads.DipendenteResponseDTO;
import dano_fra.Dispositivi_aziendali_dipendenti_user.services.AuthService;
import dano_fra.Dispositivi_aziendali_dipendenti_user.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public DipendenteLoginResponseDTO login(@RequestBody DipendenteLoginDTO payload) {
        return new DipendenteLoginResponseDTO(this.authService.authwnticateDipAndToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public DipendenteResponseDTO saveDipendente(@RequestBody @Validated DipendenteDTO body, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors());
        }
        return new DipendenteResponseDTO(this.dipendenteService.save(body).email());
    }
}
