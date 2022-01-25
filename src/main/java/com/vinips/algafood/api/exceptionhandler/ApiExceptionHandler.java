package com.vinips.algafood.api.exceptionhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.vinips.algafood.domain.exception.EntidadeEmUsoException;
import com.vinips.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.vinips.algafood.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	// Sobrescrevendo método que lança a exceção do formato invalido dos dados do
	// Json. ex: o campo é int e manda string.
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		// ExceptionUtils é da dependency org.apache.commons no POM
		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
		}

		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());

		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String detail = "";

		String path = joinPath(ex.getPath());

		if (ex instanceof UnrecognizedPropertyException) {
			detail = String.format(
					"A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente.", path);
		} else if (ex instanceof IgnoredPropertyException) {
			detail = String.format("A propriedade '%s' é ignorada pela Regra de Negócio e não pode ser inserida.",
					path);
		}

		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	// Sobrecarga do método que executa exception de URL errada
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String detail = String.format(
				"O parâmetro da URL '%s' recebeu o valor '%s' que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, ProblemType.PARAMETRO_INVALIDO, detail);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente", ex.getRequestURL());

		Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, detail);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	// @ExceptionHandler é utilizado para podermos alterar livremente as informações
	// passadas pelo Response Entity quando da erro.
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, ex.getMessage());

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;

		Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage());

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, ex.getMessage());

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	// Customização do HandleExceptionInternal que é chamado por todos os
	// HandleExcetion do ResponseEntityExceptionHandler
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = new Problem(status.value(), null, status.getReasonPhrase(), null);
		} else if (body instanceof String) {
			body = new Problem(status.value(), null, status.getReasonPhrase(), ex.getMessage());
		}

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}
		return new ResponseEntity<>(body, headers, status);
	}

	private Problem createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return new Problem(status.value(), problemType.getUri(), problemType.getTitulo(), detail);
	}

	private String joinPath(List<Reference> ex) {
		return ex.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}

}
