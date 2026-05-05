package com.celzo_aveiro.fipe_api.application.usecase;

import com.celzo_aveiro.fipe_api.application.port.out.MarcaConsultaRepository;
import com.celzo_aveiro.fipe_api.application.usecase.impl.ListarMarcasImpl;
import com.celzo_aveiro.fipe_api.domain.model.Marca;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ListarMarcas")
class ListarMarcasImplTest {

    @Mock
    private MarcaConsultaRepository marcaConsultaRepository;

    @Test
    @DisplayName("retorna todas as marcas do repositório")
    void retornaTodasAsMarcasDoRepositorio() {
        var honda = Marca.of("59", "Honda");
        var toyota = Marca.of("56", "Toyota");
        when(marcaConsultaRepository.listarTodas()).thenReturn(List.of(honda, toyota));
        var useCase = new ListarMarcasImpl(marcaConsultaRepository);

        var marcas = useCase.listar();

        assertThat(marcas).containsExactly(honda, toyota);
    }

    @Test
    @DisplayName("retorna lista vazia quando o banco não tem marcas")
    void retornaListaVaziaQuandoBancoNaoTemMarcas() {
        when(marcaConsultaRepository.listarTodas()).thenReturn(List.of());
        var useCase = new ListarMarcasImpl(marcaConsultaRepository);

        var marcas = useCase.listar();

        assertThat(marcas).isEmpty();
    }
}
