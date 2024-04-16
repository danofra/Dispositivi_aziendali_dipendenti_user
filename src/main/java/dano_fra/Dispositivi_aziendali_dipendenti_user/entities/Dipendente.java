package dano_fra.Dispositivi_aziendali_dipendenti_user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dano_fra.Dispositivi_aziendali_dipendenti_user.enums.ruolo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"password", "role", "authorities", "accountNonExpired",
        "credentialsNonExpired", "accountNonLocked", "enabled"})
@Entity
@Table(name = "dipendente")
public class Dipendente implements UserDetails {
    @JsonIgnore
    @OneToMany(mappedBy = "dipendente")
    List<Dispositivo> dispositivo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private ruolo ruoloAuthor;

    public Dipendente(String username, String nome, String cognome, String email, String password, String avatar, ruolo ruoloAuthor) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.ruoloAuthor = ruoloAuthor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruoloAuthor.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
