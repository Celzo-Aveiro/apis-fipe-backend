package com.celzo_aveiro.fipe_api_consumer.application.usecase;

import com.celzo_aveiro.fipe_api_consumer.application.port.out.FipeVeiculoClient;
import com.celzo_aveiro.fipe_api_consumer.application.port.out.MarcaRepository;
import com.celzo_aveiro.fipe_api_consumer.application.port.out.VeiculoRepository;
import com.celzo_aveiro.fipe_api_consumer.application.usecase.impl.ImportarVeiculosDaMarcaImpl;
import com.celzo_aveiro.fipe_api_consumer.domain.model.Marca;
import com.celzo_aveiro.fipe_api_consumer.domain.model.Veiculo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImportarVeiculosDaMarca")
class ImportarVeiculosDaMarcaImplTest {

    @Mock
    private FipeVeiculoClient fipeVeiculoClient;

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Test
    @DisplayName("upserta marca, busca modelos na FIPE e persiste todos")
    void upsertaMarcaBuscaEPersisteVeiculos() {
        var honda = Marca.of("59", "Honda");
        var civic = Veiculo.of("5940-1", honda, "Civic LXS 1.7");
        var fit = Veiculo.of("5901-1", honda, "Fit LX 1.4");
        when(marcaRepository.salvar(honda)).thenReturn(honda);
        when(fipeVeiculoClient.buscarVeiculosDaMarca(honda)).thenReturn(List.of(civic, fit));
        var useCase = new ImportarVeiculosDaMarcaImpl(
                fipeVeiculoClient, marcaRepository, veiculoRepository
        );

        useCase.importar(honda);

        InOrder inOrder = inOrder(marcaRepository, fipeVeiculoClient, veiculoRepository);
        inOrder.verify(marcaRepository).salvar(honda);
        inOrder.verify(fipeVeiculoClient).buscarVeiculosDaMarca(honda);
        inOrder.verify(veiculoRepository).salvarTodos(List.of(civic, fit));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("ainda persiste lista vazia quando a FIPE não retorna modelos")
    void persisteListaVaziaQuandoFipeNaoRetornaModelos() {
        var honda = Marca.of("59", "Honda");
        when(marcaRepository.salvar(honda)).thenReturn(honda);
        when(fipeVeiculoClient.buscarVeiculosDaMarca(honda)).thenReturn(List.of());
        var useCase = new ImportarVeiculosDaMarcaImpl(
                fipeVeiculoClient, marcaRepository, veiculoRepository
        );

        useCase.importar(honda);

        verify(marcaRepository).salvar(honda);
        verify(fipeVeiculoClient).buscarVeiculosDaMarca(honda);
        verify(veiculoRepository).salvarTodos(List.of());
    }
}
