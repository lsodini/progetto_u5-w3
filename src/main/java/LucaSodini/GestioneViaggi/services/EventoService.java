package LucaSodini.GestioneViaggi.services;

import LucaSodini.GestioneViaggi.entities.Evento;
import LucaSodini.GestioneViaggi.entities.Utente;
import LucaSodini.GestioneViaggi.exceptions.NotFoundException;
import LucaSodini.GestioneViaggi.payloads.NuovoEventoDTO;
import LucaSodini.GestioneViaggi.repositories.EventoRepository;
import LucaSodini.GestioneViaggi.repositories.UtenteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UtenteRepository utenteRepository;

    public Evento creaEvento(NuovoEventoDTO nuovoEventoDTO) {
        Utente organizzatore = utenteRepository.findById(nuovoEventoDTO.utenteId())
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));

        Evento evento = new Evento();
        evento.setTitolo(nuovoEventoDTO.titolo());
        evento.setDataInizio(nuovoEventoDTO.dataInizio());
        evento.setPostiDisponibili(nuovoEventoDTO.postiDisponibili());
        evento.setDescrizione(nuovoEventoDTO.descrizione());
        evento.setOrganizzatore(organizzatore);

        return eventoRepository.save(evento);
    }



    public List<Evento> getEventiPerOrganizzatore(Utente organizzatore) {
        return eventoRepository.findByOrganizzatore(organizzatore);
    }

    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }


    public Evento trovaEventoPerId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento con ID " + id + " non trovato"));
    }

    @PreAuthorize("hasRole('ORGANIZZATORE')")
    @Transactional
    public Evento modificaEvento(Long id, NuovoEventoDTO nuovoEventoDTO) {
        Evento evento = trovaEventoPerId(id);
        evento.setTitolo(nuovoEventoDTO.titolo());
        evento.setDescrizione(nuovoEventoDTO.descrizione());
        evento.setDataInizio(nuovoEventoDTO.dataInizio());
        evento.setLuogo(nuovoEventoDTO.luogo());
        return eventoRepository.save(evento);
    }


    public void eliminaEvento(Long eventoId) {
        eventoRepository.deleteById(eventoId);
    }
}
