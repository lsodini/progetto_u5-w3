package LucaSodini.GestioneViaggi.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import LucaSodini.GestioneViaggi.entities.Utente;
import LucaSodini.GestioneViaggi.enums.Ruolo;
import LucaSodini.GestioneViaggi.services.UtenteService;
import LucaSodini.GestioneViaggi.payloads.NuovoUtenteDTO;

import java.util.List;

@RestController
@RequestMapping("/api/utenti")
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;


    @PostMapping("/registrazione")
    public ResponseEntity<Utente> registrazione(@RequestBody NuovoUtenteDTO nuovoUtenteDTO) {
        Utente utente = utenteService.salva(nuovoUtenteDTO);
        return ResponseEntity.ok(utente);
    }


    @PutMapping("/{email}/ruolo")
    public ResponseEntity<Utente> cambiaRuolo(@PathVariable String email, @RequestParam("ruolo") Ruolo nuovoRuolo) {
        Utente utente = utenteService.cambiaRuolo(email, nuovoRuolo);
        return ResponseEntity.ok(utente);
    }


    @GetMapping("/ruolo/{ruolo}")
    public ResponseEntity<List<Utente>> cercaUtentiPerRuolo(@PathVariable Ruolo ruolo) {
        List<Utente> utenti = utenteService.cercaPerRuolo(ruolo);
        return ResponseEntity.ok(utenti);
    }

    @GetMapping("/")
    public ResponseEntity<List<Utente>> trovaTutti() {
        List<Utente> utenti = utenteService.trovaTutti();
        return ResponseEntity.ok(utenti);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Utente> modificaUtente(@PathVariable Long id, @RequestBody Utente utenteModificato) {
        Utente utente = utenteService.modificaUtente(id, utenteModificato);
        return ResponseEntity.ok(utente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaUtente(@PathVariable Long id) {
        utenteService.elimina(id);
        return ResponseEntity.noContent().build();
    }
}
