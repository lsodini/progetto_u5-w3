package LucaSodini.GestioneViaggi.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import LucaSodini.GestioneViaggi.entities.Utente;
import LucaSodini.GestioneViaggi.exceptions.UnauthorizedException;
import LucaSodini.GestioneViaggi.services.UtenteService;
import LucaSodini.GestioneViaggi.tools.JWT;

import java.io.IOException;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {

    @Autowired
    private JWT jwt;
    @Autowired
    private UtenteService utenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Inserire token nell'Authorization Header nel formato corretto!");
        }

        String accessToken = authHeader.substring(7);

        jwt.verifyToken(accessToken);

        String userId = jwt.getIdFromToken(accessToken);
        if (userId == null) {
            throw new UnauthorizedException("Token non valido o scaduto!");
        }
        Utente currentUser = this.utenteService.trovaPerId(Long.parseLong(userId));


        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
