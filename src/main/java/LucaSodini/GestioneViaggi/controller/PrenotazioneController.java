package LucaSodini.GestioneViaggi.controller;

import LucaSodini.GestioneViaggi.services.PrenotazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import LucaSodini.GestioneViaggi.entities.Prenotazione;
import LucaSodini.GestioneViaggi.payloads.NuovaPrenotazioneDTO;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    @PreAuthorize("hasRole('UTENTE') or hasRole('ORGANIZZATORE')")
    @PostMapping("/crea")
    public ResponseEntity<Prenotazione> creaPrenotazione(@RequestBody NuovaPrenotazioneDTO nuovaPrenotazioneDTO) {
        Prenotazione prenotazione = prenotazioneService.prenotaPosto(nuovaPrenotazioneDTO);
        return ResponseEntity.ok(prenotazione);
    }

    @GetMapping("/utente/{utenteId}")
    public ResponseEntity<List<Prenotazione>> trovaPrenotazioniPerUtente(@PathVariable Long utenteId) {
        List<Prenotazione> prenotazioni = prenotazioneService.getPrenotazioniPerUtente(utenteId);
        return ResponseEntity.ok(prenotazioni);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> annullaPrenotazione(@PathVariable Long id) {
        prenotazioneService.annullaPrenotazione(id);
        return ResponseEntity.noContent().build();
    }
}
