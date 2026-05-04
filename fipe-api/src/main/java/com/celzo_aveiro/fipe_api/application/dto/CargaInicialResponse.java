package com.celzo_aveiro.fipe_api.application.dto;

import java.time.Instant;
import java.util.UUID;

public record CargaInicialResponse(
        UUID cargaId,
        Instant iniciadoEm,
        int marcasEncontradas
) {
}
