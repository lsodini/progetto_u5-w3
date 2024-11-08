package LucaSodini.GestioneViaggi.repositories;

import LucaSodini.GestioneViaggi.entities.Evento;
import LucaSodini.GestioneViaggi.entities.Prenotazione;
import LucaSodini.GestioneViaggi.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    List<Prenotazione> findByEvento(Evento evento);
    List<Prenotazione> findByUtenteId(Long utenteId);
}