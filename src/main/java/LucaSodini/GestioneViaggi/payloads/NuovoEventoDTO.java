package LucaSodini.GestioneViaggi.payloads;

import java.time.LocalDateTime;

public record NuovoEventoDTO(
         String titolo,
         String descrizione,
         LocalDateTime dataInizio,
         LocalDateTime dataFine,
         String luogo,
         int postiDisponibili,
         long utenteId
) {}

