package com.zarate.algamoney.api.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return "postAccessToken".equals(returnType.getMethod().getName());
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {

		String refreshToken = body.getRefreshToken().getValue();
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();

		adicionarRefreshTokenNoCookie(refreshToken, req, resp);
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		
		removeRefreshTokenDoBody(token);

		return body;
	}

	private void removeRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
	}

	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {

		Cookie cookie = new Cookie(OAuth2AccessToken.REFRESH_TOKEN, refreshToken);
		cookie.setHttpOnly(Boolean.TRUE);
		cookie.setSecure(Boolean.FALSE); // TODO: Mudar para true em PROD
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(3600 * 24);
		resp.addCookie(cookie);

	}

}
