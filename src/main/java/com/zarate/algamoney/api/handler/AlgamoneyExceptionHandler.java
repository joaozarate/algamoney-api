package com.zarate.algamoney.api.handler;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDev = ex.getCause().toString();

		return super.handleExceptionInternal(ex, new Erro(mensagemUsuario, mensagemDev), headers, HttpStatus.BAD_REQUEST, request);
	}

	public static class Erro implements Serializable {

		private static final long serialVersionUID = 7644542550331817502L;

		private String msgUsuario;
		private String msgDev;

		public Erro() {
			// TODO Auto-generated constructor stub
		}

		public Erro(String msgUsuario, String msgDev) {
			super();
			this.msgUsuario = msgUsuario;
			this.msgDev = msgDev;
		}

		public String getMsgUsuario() {
			return msgUsuario;
		}

		public void setMsgUsuario(String msgUsuario) {
			this.msgUsuario = msgUsuario;
		}

		public String getMsgDev() {
			return msgDev;
		}

		public void setMsgDev(String msgDev) {
			this.msgDev = msgDev;
		}

	}

}
