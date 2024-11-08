package LucaSodini.GestioneViaggi.services;



import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import LucaSodini.GestioneViaggi.entities.Utente;
import LucaSodini.GestioneViaggi.enums.Ruolo;
import LucaSodini.GestioneViaggi.exceptions.BadRequestException;
import LucaSodini.GestioneViaggi.exceptions.NotFoundException;
import LucaSodini.GestioneViaggi.payloads.NuovoUtenteDTO;
import LucaSodini.GestioneViaggi.repositories.UtenteRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository utentiRepository;
    private final PasswordEncoder bcrypt;

    public Utente salva(NuovoUtenteDTO body) {

        utentiRepository.findByEmail(body.email()).ifPresent(
                utente -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso!");
                }
        );


        Utente nuovoUtente = new Utente(
                body.nome(),
                body.cognome(),
                body.email(),
                bcrypt.encode(body.password()),
                "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome(),
                Ruolo.UTENTE
        );

        Utente utenteSalvato = utentiRepository.save(nuovoUtente);

        return utenteSalvato;
    }


    public Utente findByEmail(String email) {
        return utentiRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato"));
    }

       @Transactional
    public Utente cambiaRuolo(String email, Ruolo nuovoRuolo) {
        Utente utente = findByEmail(email);
        utente.setRuolo(nuovoRuolo);
        return utentiRepository.save(utente);
    }


    public List<Utente> cercaPerRuolo(Ruolo ruolo) {
        return utentiRepository.findByRuolo(ruolo);
    }


    public List<Utente> trovaTutti() {
        return utentiRepository.findAll();
    }

    @Transactional
    public Utente modificaUtente(Long id, Utente utenteModificato) {
        Utente utenteEsistente = utentiRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente con ID " + id + " non trovato"));


        if (utenteModificato.getNome() != null) {
            utenteEsistente.setNome(utenteModificato.getNome());
        }
        if (utenteModificato.getCognome() != null) {
            utenteEsistente.setCognome(utenteModificato.getCognome());
        }
        if (utenteModificato.getEmail() != null) {
            utenteEsistente.setEmail(utenteModificato.getEmail());
        }


        if (utenteModificato.getPassword() != null) {
            utenteEsistente.setPassword(bcrypt.encode(utenteModificato.getPassword()));
        }

        return utentiRepository.save(utenteEsistente);
    }


    public void elimina(Long id) {
        Utente utenteEsistente = utentiRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente con ID " + id + " non trovato"));

        utentiRepository.delete(utenteEsistente);
    }


    public Utente trovaPerId(Long id) {
        return utentiRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente con ID " + id + " non trovato"));
    }
}
