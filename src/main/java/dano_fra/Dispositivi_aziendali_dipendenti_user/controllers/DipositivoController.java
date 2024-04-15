package dano_fra.Dispositivi_aziendali_dipendenti_user.controllers;


import dano_fra.Dispositivi_aziendali_dipendenti_user.entities.Dispositivo;
import dano_fra.Dispositivi_aziendali_dipendenti_user.enums.stato;
import dano_fra.Dispositivi_aziendali_dipendenti_user.enums.tipologia;
import dano_fra.Dispositivi_aziendali_dipendenti_user.exceptions.BadRequestException;
import dano_fra.Dispositivi_aziendali_dipendenti_user.exceptions.CorrectDeleteDispositivo;
import dano_fra.Dispositivi_aziendali_dipendenti_user.payloads.DispositivoDTO;
import dano_fra.Dispositivi_aziendali_dipendenti_user.services.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dispositivo")
public class DipositivoController {
    @Autowired
    private DispositivoService dispositivoService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public DispositivoDTO dispositivo(@RequestBody @Validated DispositivoDTO body, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors());
        }
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setTipologia(tipologia.valueOf(body.tipologia()));
        dispositivo.setStato(stato.valueOf(body.stato()));
        dispositivoService.save(body);
        return body;
    }

    @GetMapping("")
    public Page<Dispositivo> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        return this.dispositivoService.getDispositivo(page, size, sortBy);
    }

    @GetMapping("/{dispositivoId}")
    @ResponseStatus(HttpStatus.OK)
    public Dispositivo findById(@PathVariable int dispositivoId) {
        return dispositivoService.findById(dispositivoId);
    }

    @PutMapping("/{dispositivoId}")
    @ResponseStatus(HttpStatus.OK)
    public Dispositivo findAndUpdate(@PathVariable int dispositivoId, @RequestBody DispositivoDTO body) {
        return dispositivoService.findByIdAndUpdate(dispositivoId, body);
    }


    @PatchMapping("/upload/{dispositivoId}/{dipendenteId}")
    @ResponseStatus(HttpStatus.OK)
    public Dispositivo findByIdDispositivoDipendenteAndUpdate(@PathVariable int dispositivoId, @PathVariable int dipendenteId) {
        return dispositivoService.findByIdDispositivoDipendenteAndUpdate(dispositivoId, dipendenteId);
    }

    @DeleteMapping("/{dispositivoId}")
    @ResponseStatus(HttpStatus.OK)
    public void findAndDelete(@PathVariable int dispositivoId) throws CorrectDeleteDispositivo {
        dispositivoService.findByIdAndDelete(dispositivoId);
        throw new CorrectDeleteDispositivo("Dispositivo eliminato correttamente");
    }


}
