package com.celzo_aveiro.fipe_api_consumer.interfaces.messaging;

import com.celzo_aveiro.fipe_api_consumer.application.usecase.ImportarVeiculosDaMarca;
import com.celzo_aveiro.fipe_api_consumer.domain.model.Marca;
import com.celzo_aveiro.fipe_api_consumer.infrastructure.messaging.MarcaMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("MarcaListener")
class MarcaListenerTest {

    @Mock
    private ImportarVeiculosDaMarca importarVeiculosDaMarca;

    @Test
    @DisplayName("converte mensagem em domínio e delega ao use case")
    void converteEDelegaAoUseCase() {
        var listener = new MarcaListener(importarVeiculosDaMarca);
        var message = new MarcaMessage("59", "Honda");

        listener.onMarca(message);

        verify(importarVeiculosDaMarca).importar(Marca.of("59", "Honda"));
    }
}
