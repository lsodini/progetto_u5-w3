package LucaSodini.GestioneViaggi.repositories;

import LucaSodini.GestioneViaggi.entities.Evento;
import LucaSodini.GestioneViaggi.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByOrganizzatore(Utente organizzatore);
}