package com.celzo_aveiro.fipe_api.application.usecase;

import com.celzo_aveiro.fipe_api.application.port.out.VeiculoRepository;
import com.celzo_aveiro.fipe_api.application.usecase.impl.AtualizarVeiculoImpl;
import com.celzo_aveiro.fipe_api.domain.exception.VeiculoNaoEncontradoException;
import com.celzo_aveiro.fipe_api.domain.model.Marca;
import com.celzo_aveiro.fipe_api.domain.model.Veiculo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AtualizarVeiculo")
class AtualizarVeiculoImplTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @Test
    @DisplayName("atualiza modelo e observações preservando código FIPE e marca")
    void atualizaModeloEObservacoesPreservandoIdentidade() {
        var honda = Marca.of("59", "Honda");
        var existente = new Veiculo("5940-1", honda, "Civic LXS 1.7", null);
        when(veiculoRepository.buscarPorId(7L)).thenReturn(Optional.of(existente));
        var captor = ArgumentCaptor.forClass(Veiculo.class);
        when(veiculoRepository.atualizar(eq(7L), captor.capture()))
                .thenAnswer(inv -> captor.getValue());
        var useCase = new AtualizarVeiculoImpl(veiculoRepository);

        var atualizado = useCase.executar(7L, "Civic EXS 1.8", "automático");

        assertThat(atualizado.codigoFipe()).isEqualTo("5940-1");
        assertThat(atualizado.marca()).isEqualTo(honda);
        assertThat(atualizado.modelo()).isEqualTo("Civic EXS 1.8");
        assertThat(atualizado.observacoes()).isEqualTo("automático");
    }

    @Test
    @DisplayName("lança VeiculoNaoEncontradoException quando o id não existe")
    void lancaQuandoIdNaoExiste() {
        when(veiculoRepository.buscarPorId(99L)).thenReturn(Optional.empty());
        var useCase = new AtualizarVeiculoImpl(veiculoRepository);

        assertThatThrownBy(() -> useCase.executar(99L, "qualquer", null))
                .isInstanceOf(VeiculoNaoEncontradoException.class)
                .hasMessageContaining("99");

        verify(veiculoRepository, never()).atualizar(any(), any());
    }
}
