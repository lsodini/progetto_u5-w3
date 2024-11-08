package LucaSodini.GestioneViaggi.controller;

import LucaSodini.GestioneViaggi.entities.Prenotazione;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import LucaSodini.GestioneViaggi.entities.Evento;
import LucaSodini.GestioneViaggi.services.EventoService;
import LucaSodini.GestioneViaggi.payloads.NuovoEventoDTO;

import java.util.List;

@RestController
@RequestMapping("/api/eventi")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @PreAuthorize("hasRole('ORGANIZZATORE')")
    @PostMapping("/crea")
    public ResponseEntity<Evento> creaEvento(@RequestBody NuovoEventoDTO nuovoEventoDTO) {
        Evento evento = eventoService.creaEvento(nuovoEventoDTO);
        return ResponseEntity.ok(evento);
    }

    @GetMapping("/")
    public ResponseEntity<List<Evento>> trovaEventi() {
        List<Evento> eventi = eventoService.findAll();
        return ResponseEntity.ok(eventi);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Evento> trovaEventoPerId(@PathVariable Long id) {
        Evento evento = eventoService.trovaEventoPerId(id);
        return ResponseEntity.ok(evento);
    }

    @PreAuthorize("hasRole('ORGANIZZATORE')")
    @PutMapping("/{id}")
    public ResponseEntity<Evento> modificaEvento(@PathVariable Long id, @RequestBody NuovoEventoDTO nuovoEventoDTO) {
        Evento evento = eventoService.modificaEvento(id, nuovoEventoDTO);
        return ResponseEntity.ok(evento);
    }

    @PreAuthorize("hasRole('ORGANIZZATORE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaEvento(@PathVariable Long id) {
        eventoService.eliminaEvento(id);
        return ResponseEntity.noContent().build();
    }

}
