package dano_fra.Dispositivi_aziendali_dipendenti_user.controllers;


import dano_fra.Dispositivi_aziendali_dipendenti_user.entities.Dipendente;
import dano_fra.Dispositivi_aziendali_dipendenti_user.exceptions.BadRequestException;
import dano_fra.Dispositivi_aziendali_dipendenti_user.exceptions.CorrectDeleteDipendente;
import dano_fra.Dispositivi_aziendali_dipendenti_user.payloads.DipendenteDTO;
import dano_fra.Dispositivi_aziendali_dipendenti_user.services.DipendenteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/dipendente")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public DipendenteDTO dipendente(@RequestBody @Validated DipendenteDTO body, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors());
        }
        Dipendente dipendente = new Dipendente();
        dipendente.setUsername(body.username());
        dipendente.setNome(body.nome());
        dipendente.setCognome(body.cognome());
        dipendente.setEmail(body.email());
        dipendente.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        dipendenteService.save(body);
        return body;
    }

    @GetMapping("")
    public Page<Dipendente> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sortBy) {
        return this.dipendenteService.getDipendente(page, size, sortBy);
    }

    @GetMapping("/{dipendenteId}")
    public Dipendente findById(@PathVariable int dipendenteId) {
        return dipendenteService.findById(dipendenteId);
    }

    @PutMapping("/{dipendenteId}")
    public Dipendente findAndUpdate(@PathVariable int dipendenteId, @RequestBody DipendenteDTO body) {
        return dipendenteService.findByIdAndUpdate(dipendenteId, body);
    }

    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.OK)
    public void findAndDelete(@PathVariable int dipendenteId) throws CorrectDeleteDipendente {
        dipendenteService.findByIdAndDelete(dipendenteId);
        throw new CorrectDeleteDipendente();
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public String uploadAvatar(@RequestParam("avatar") MultipartFile image) throws IOException {
        return this.dipendenteService.upload(image);
    }

    @PostMapping("/upload/{dipendenteId}")
    public String uploadDipendenteImage(@RequestParam("avatar") MultipartFile image, @PathVariable int dipendenteId) throws IOException {
        this.dipendenteService.uploadDipendenteImageToId(image, dipendenteId);
        return this.dipendenteService.upload(image);
    }

}
