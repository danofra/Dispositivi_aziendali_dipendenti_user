package dano_fra.Dispositivi_aziendali_dipendenti_user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dano_fra.Dispositivi_aziendali_dipendenti_user.enums.stato;
import dano_fra.Dispositivi_aziendali_dipendenti_user.enums.tipologia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dispositivo")
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private tipologia tipologia;
    @Enumerated(EnumType.STRING)
    private stato stato;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;
}
