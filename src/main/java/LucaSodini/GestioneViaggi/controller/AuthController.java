package LucaSodini.GestioneViaggi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import LucaSodini.GestioneViaggi.entities.Utente;
import LucaSodini.GestioneViaggi.exceptions.BadRequestException;
import LucaSodini.GestioneViaggi.payloads.NuovoUtenteDTO;
import LucaSodini.GestioneViaggi.payloads.UserLoginDTO;
import LucaSodini.GestioneViaggi.payloads.UserLoginResponseDTO;
import LucaSodini.GestioneViaggi.services.AuthService;
import LucaSodini.GestioneViaggi.services.UtenteService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
        return new UserLoginResponseDTO(authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente register(@RequestBody @Validated NuovoUtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return utenteService.salva(body);
    }
}
