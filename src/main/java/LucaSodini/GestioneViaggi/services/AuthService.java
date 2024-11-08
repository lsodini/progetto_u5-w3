package LucaSodini.GestioneViaggi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import LucaSodini.GestioneViaggi.entities.Utente;
import LucaSodini.GestioneViaggi.exceptions.UnauthorizedException;
import LucaSodini.GestioneViaggi.payloads.UserLoginDTO;
import LucaSodini.GestioneViaggi.tools.JWT;

@Service
public class AuthService {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(UserLoginDTO body) {
        Utente found = utenteService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwt.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}

