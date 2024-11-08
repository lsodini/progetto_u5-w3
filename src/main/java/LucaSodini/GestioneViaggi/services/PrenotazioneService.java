package LucaSodini.GestioneViaggi.services;

import LucaSodini.GestioneViaggi.entities.Evento;
import LucaSodini.GestioneViaggi.entities.Prenotazione;
import LucaSodini.GestioneViaggi.entities.Utente;
import LucaSodini.GestioneViaggi.exceptions.NotFoundException;
import LucaSodini.GestioneViaggi.payloads.NuovaPrenotazioneDTO;
import LucaSodini.GestioneViaggi.repositories.EventoRepository;
import LucaSodini.GestioneViaggi.repositories.PrenotazioneRepository;
import LucaSodini.GestioneViaggi.repositories.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoRepository eventoRepository;
    private final UtenteRepository utenteRepository;

    @PreAuthorize("hasRole('UTENTE') or hasRole('ORGANIZZATORE')")
    public Prenotazione prenotaPosto(NuovaPrenotazioneDTO nuovaPrenotazioneDTO) {

        Utente utente = utenteRepository.findById(nuovaPrenotazioneDTO.utenteId())
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));
        Evento evento = eventoRepository.findById(nuovaPrenotazioneDTO.eventoId())
                .orElseThrow(() -> new NotFoundException("Evento non trovato"));

        if (evento.getDataInizio().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Non puoi prenotare per un evento già iniziato!");
        }

        if (evento.getPostiDisponibili() > 0) {
            evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);
            Prenotazione prenotazione = new Prenotazione();
            prenotazione.setUtente(utente);
            prenotazione.setEvento(evento);
            prenotazione.setDataPrenotazione(LocalDateTime.now());
            eventoRepository.save(evento);
            return prenotazioneRepository.save(prenotazione);
        }
        throw new RuntimeException("Posti non disponibili");


    }


    public List<Prenotazione> getPrenotazioniPerUtente(Long utenteId) {
        return prenotazioneRepository.findByUtenteId(utenteId);
    }
    @PreAuthorize("hasRole('UTENTE') or hasRole('ORGANIZZATORE')")
    public void annullaPrenotazione(Long prenotazioneId) {
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));

        Evento evento = prenotazione.getEvento();
        evento.setPostiDisponibili(evento.getPostiDisponibili() + 1);
        eventoRepository.save(evento);

        prenotazioneRepository.delete(prenotazione);
    }
}
