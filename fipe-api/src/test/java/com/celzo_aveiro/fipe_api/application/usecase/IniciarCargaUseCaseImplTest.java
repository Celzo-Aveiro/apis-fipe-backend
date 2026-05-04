package com.celzo_aveiro.fipe_api.application.usecase;

import com.celzo_aveiro.fipe_api.application.port.out.FipeMarcaClient;
import com.celzo_aveiro.fipe_api.application.port.out.MarcaMessagePublisher;
import com.celzo_aveiro.fipe_api.application.usecase.impl.IniciarCargaUseCaseImpl;
import com.celzo_aveiro.fipe_api.domain.model.Marca;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("IniciarCargaUseCase")
class IniciarCargaUseCaseImplTest {

    @Mock
    private FipeMarcaClient fipeMarcaClient;

    @Mock
    private MarcaMessagePublisher marcaMessagePublisher;

    @Test
    @DisplayName("busca marcas na FIPE e publica cada uma na fila")
    void buscaMarcasNaFipeEPublicaNaFila() {
        var honda = Marca.of("59", "Honda");
        var toyota = Marca.of("56", "Toyota");
        when(fipeMarcaClient.buscarMarcas()).thenReturn(List.of(honda, toyota));
        var useCase = new IniciarCargaUseCaseImpl(fipeMarcaClient, marcaMessagePublisher);

        var response = useCase.iniciar();

        assertThat(response.cargaId()).isNotNull();
        assertThat(response.iniciadoEm()).isNotNull();
        assertThat(response.marcasEncontradas()).isEqualTo(2);

        InOrder inOrder = inOrder(fipeMarcaClient, marcaMessagePublisher);
        inOrder.verify(fipeMarcaClient).buscarMarcas();
        inOrder.verify(marcaMessagePublisher).publicar(honda);
        inOrder.verify(marcaMessagePublisher).publicar(toyota);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("retorna zero quando a FIPE nao possui marcas")
    void retornaZeroQuandoFipeNaoPossuiMarcas() {
        when(fipeMarcaClient.buscarMarcas()).thenReturn(List.of());
        var useCase = new IniciarCargaUseCaseImpl(fipeMarcaClient, marcaMessagePublisher);

        var response = useCase.iniciar();

        assertThat(response.marcasEncontradas()).isZero();
        verify(fipeMarcaClient).buscarMarcas();
        verify(marcaMessagePublisher, never()).publicar(any());
    }
}
