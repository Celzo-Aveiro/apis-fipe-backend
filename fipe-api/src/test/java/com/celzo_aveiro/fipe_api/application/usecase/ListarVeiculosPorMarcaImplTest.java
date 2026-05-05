package com.celzo_aveiro.fipe_api.application.usecase;

import com.celzo_aveiro.fipe_api.application.dto.VeiculoView;
import com.celzo_aveiro.fipe_api.application.port.out.MarcaConsultaRepository;
import com.celzo_aveiro.fipe_api.application.port.out.VeiculoRepository;
import com.celzo_aveiro.fipe_api.application.usecase.impl.ListarVeiculosPorMarcaImpl;
import com.celzo_aveiro.fipe_api.domain.exception.MarcaNaoEncontradaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ListarVeiculosPorMarca")
class ListarVeiculosPorMarcaImplTest {

    @Mock
    private MarcaConsultaRepository marcaConsultaRepository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Test
    @DisplayName("retorna veículos da marca quando ela existe")
    void retornaVeiculosDaMarcaQuandoElaExiste() {
        var civic = new VeiculoView(1L, "5940-1", "Civic LXS 1.7", null);
        var fit = new VeiculoView(2L, "5901-1", "Fit LX 1.4", "manual");
        when(marcaConsultaRepository.existePorCodigoFipe("59")).thenReturn(true);
        when(veiculoRepository.listarPorMarca("59")).thenReturn(List.of(civic, fit));
        var useCase = new ListarVeiculosPorMarcaImpl(marcaConsultaRepository, veiculoRepository);

        var veiculos = useCase.listar("59");

        assertThat(veiculos).containsExactly(civic, fit);
    }

    @Test
    @DisplayName("lança MarcaNaoEncontradaException quando a marca não existe")
    void lancaQuandoMarcaNaoExiste() {
        when(marcaConsultaRepository.existePorCodigoFipe("999")).thenReturn(false);
        var useCase = new ListarVeiculosPorMarcaImpl(marcaConsultaRepository, veiculoRepository);

        assertThatThrownBy(() -> useCase.listar("999"))
                .isInstanceOf(MarcaNaoEncontradaException.class)
                .hasMessageContaining("999");

        verify(veiculoRepository, never()).listarPorMarca("999");
    }
}
