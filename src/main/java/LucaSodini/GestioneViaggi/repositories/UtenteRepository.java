package LucaSodini.GestioneViaggi.repositories;

import LucaSodini.GestioneViaggi.entities.Prenotazione;
import LucaSodini.GestioneViaggi.entities.Utente;
import LucaSodini.GestioneViaggi.enums.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByUsername(String username);
    List<Utente> findByRuolo(Ruolo ruolo);
    Optional<Utente> findByEmail(String email);
    List<Prenotazione> findByUtenteId(Long utenteId);
}
