package com.vinips.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
import com.vinips.algafood.domain.exception.ValidacaoException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. "
			+ "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";

	@Autowired
	private MessageSource messageSource;

	// Bem genérica, para pegar o que as outras específicas não pegam
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		Problem problem = createProblemBuilder(status, ProblemType.ERRO_DE_SISTEMA, MSG_ERRO_GENERICA_USUARIO_FINAL,
				MSG_ERRO_GENERICA_USUARIO_FINAL);

		// Temporário
		ex.printStackTrace();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

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

		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail,
				MSG_ERRO_GENERICA_USUARIO_FINAL);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	// Pega o erro de quanto você passa o valor do tipo errado na propriedade
	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());

		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail,
				MSG_ERRO_GENERICA_USUARIO_FINAL);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	// Pega o erro de quanto passa uma propriedade inexistente ou ignorada naquela
	// entidade.
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

		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail,
				MSG_ERRO_GENERICA_USUARIO_FINAL);

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

	// Pega o erro de quanto você passa o caminho certo porém com valor do tipo
	// errado no caminho.
	// Ex: "restaurantes/juawdad" em vez de "restaurantes/1".
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String detail = String.format(
				"O parâmetro da URL '%s' recebeu o valor '%s' que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, ProblemType.PARAMETRO_INVALIDO, detail,
				MSG_ERRO_GENERICA_USUARIO_FINAL);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	// Pega o erro de quanto você acessa a URL errada. em vez de "cozinha" digita
	// "cosinha" por exemplo.
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente", ex.getRequestURL());

		Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, detail,
				MSG_ERRO_GENERICA_USUARIO_FINAL);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	// Pega o erro que lança quando a validação do Bean não é cumprida.
	// Ex: O restaurante tem o Bean Validation no nome para NotNull e você não passa
	// o nome no Json.
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	// Serve para validar os campos quando usamos o protocolo patch.
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<?> handleValidacao(ValidacaoException ex, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

		// BindingResult armazena as violações de constraints de validação.

		// Utilizando stream, pega os FieldErrors e transforma em Field(minha classe).
		// Se for um Field, usamos o FieldError, se for uma classe usamos o ObjectError
		List<Field> problemFields = bindingResult.getAllErrors().stream().map(objectError -> {
			// Interface messageSource serve para usarmos mensagens padronizadas no
			// messages.properties
			String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

			String name = objectError.getObjectName();

			if (objectError instanceof FieldError) {
				name = ((FieldError) objectError).getField();
			}

			return new Field(name, message);
		}).collect(Collectors.toList());

		Problem problem = createProblemBuilder(status, ProblemType.DADOS_INVALIDOS, detail, detail);
		problem.setFields(problemFields);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	// @ExceptionHandler é utilizado para podermos alterar livremente as informações
	// passadas pelo Response Entity quando da erro.
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, ex.getMessage(),
				ex.getMessage());

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;

		Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage(), ex.getMessage());

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, ex.getMessage(), ex.getMessage());

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	// Customização do HandleExceptionInternal que é chamado por todos os
	// HandleExcetion do ResponseEntityExceptionHandler
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = new Problem(status.value(), LocalDateTime.now(), null, status.getReasonPhrase(), null,
					MSG_ERRO_GENERICA_USUARIO_FINAL, null);
		} else if (body instanceof String) {
			body = new Problem(status.value(), LocalDateTime.now(), null, status.getReasonPhrase(), ex.getMessage(),
					MSG_ERRO_GENERICA_USUARIO_FINAL, null);
		}

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}
		return new ResponseEntity<>(body, headers, status);
	}

	private Problem createProblemBuilder(HttpStatus status, ProblemType problemType, String detail,
			String userMessage) {
		return new Problem(status.value(), LocalDateTime.now(), problemType.getUri(), problemType.getTitulo(), detail,
				userMessage, null);
	}

	private String joinPath(List<Reference> ex) {
		return ex.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}

}
