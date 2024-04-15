package dano_fra.Dispositivi_aziendali_dipendenti_user.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dano_fra.Dispositivi_aziendali_dipendenti_user.entities.Dipendente;
import dano_fra.Dispositivi_aziendali_dipendenti_user.exceptions.NotFoundException;
import dano_fra.Dispositivi_aziendali_dipendenti_user.payloads.DipendenteDTO;
import dano_fra.Dispositivi_aziendali_dipendenti_user.payloads.DipendenteResponseDTO;
import dano_fra.Dispositivi_aziendali_dipendenti_user.repositories.DipendenteDAO;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteDAO dipendenteDAO;
    @Autowired
    private Cloudinary cloudinary;

    public DipendenteResponseDTO save(DipendenteDTO newDipendente) throws BadRequestException {
        Optional<Dipendente> existingDipendente = this.dipendenteDAO.findByEmail(newDipendente.email());
        if (existingDipendente.isPresent()) {
            throw new BadRequestException("L'email " + newDipendente.email() + " è già in uso!");
        }
        Dipendente dipendente = new Dipendente();
        dipendente.setUsername(newDipendente.username());
        dipendente.setNome(newDipendente.nome());
        dipendente.setCognome(newDipendente.cognome());
        dipendente.setEmail(newDipendente.email());
        dipendente.setPassword(newDipendente.password());
        dipendente.setAvatar("https://ui-avatars.com/api/?name=" + newDipendente.nome() + "+" + newDipendente.cognome());
        dipendenteDAO.save(dipendente);
        return new DipendenteResponseDTO(dipendente.getEmail());
    }

    public Page<Dipendente> getDipendente(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.dipendenteDAO.findAll(pageable);
    }


    public Dipendente findById(int authorId) {
        return this.dipendenteDAO.findById(authorId).orElseThrow(() -> new NotFoundException(authorId));
    }

    public void findByIdAndDelete(int id) {
        Dipendente dipendente = this.findById(id);
        this.dipendenteDAO.delete(dipendente);
    }

    public Dipendente findByIdAndUpdate(int id, DipendenteDTO newDipendente) {
        Dipendente dipendente = this.findById(id);
        dipendente.setUsername(newDipendente.username());
        dipendente.setNome(newDipendente.nome());
        dipendente.setCognome(newDipendente.cognome());
        dipendente.setEmail(newDipendente.email());
        dipendente.setAvatar("https://ui-avatars.com/api/?name=" + newDipendente.nome() + "+" + newDipendente.cognome());
        return this.dipendenteDAO.save(dipendente);
    }

    public String upload(MultipartFile image) throws IOException {
        String url = (String) cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        return url;
    }

    public Dipendente uploadDipendenteImageToId(MultipartFile image, int dipendenteId) throws IOException {
        Dipendente dipendente = this.findById(dipendenteId);
        dipendente.setAvatar(this.upload(image));
        this.dipendenteDAO.save(dipendente);
        return dipendente;
    }

    public Dipendente findByEmail(String email) {
        return dipendenteDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }
}
