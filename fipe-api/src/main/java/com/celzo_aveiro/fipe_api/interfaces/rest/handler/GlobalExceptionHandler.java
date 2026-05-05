package com.celzo_aveiro.fipe_api.interfaces.rest.handler;

import com.celzo_aveiro.fipe_api.domain.exception.MarcaNaoEncontradaException;
import com.celzo_aveiro.fipe_api.domain.exception.VeiculoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MarcaNaoEncontradaException.class)
    public ProblemDetail handleMarcaNaoEncontrada(MarcaNaoEncontradaException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Marca não encontrada");
        problem.setProperty("codigoFipe", ex.codigoFipe());
        return problem;
    }

    @ExceptionHandler(VeiculoNaoEncontradoException.class)
    public ProblemDetail handleVeiculoNaoEncontrado(VeiculoNaoEncontradoException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Veículo não encontrado");
        problem.setProperty("id", ex.id());
        return problem;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Dados inválidos");
        return problem;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthentication(AuthenticationException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "credenciais inválidas");
        problem.setTitle("Não autenticado");
        return problem;
    }
}
