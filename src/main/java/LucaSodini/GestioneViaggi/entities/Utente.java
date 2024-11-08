package LucaSodini.GestioneViaggi.entities;

import LucaSodini.GestioneViaggi.enums.Ruolo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Utente implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    public Utente(String nome, String cognome, String email, String password, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    public Utente(@NotEmpty(message = "Il nome proprio è obbligatorio!") @Size(min = 2, max = 40, message = "Il nome proprio deve essere compreso tra 2 e 40 caratteri!") String nome, @NotEmpty(message = "Il cognome proprio è obbligatorio!") @Size(min = 2, max = 40, message = "Il cognome deve essere compreso tra 2 e 40 caratteri!") String cognome, @NotEmpty(message = "L'email è un campo obbligatorio!") @Email(message = "L'email inserita non è un'email valida") String email, String encode, @NotEmpty(message = "Il nome proprio è obbligatorio!") @Size(min = 2, max = 40, message = "Il nome proprio deve essere compreso tra 2 e 40 caratteri!") String password, Ruolo ruolo) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + ruolo.name());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
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
