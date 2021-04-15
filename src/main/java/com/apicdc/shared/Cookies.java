package com.apicdc.shared;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.apicdc.site.detalhe.Carrinho;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Cookies {

	/**
	 * 
	 * @param jsonCarrinho possível json de um carrinho já criado
	 * @return
	 */
	public void writeAsJson(String key, Carrinho carrinho, HttpServletResponse response) {
		
		try {
			Cookie cookie = new Cookie(key, new ObjectMapper().writeValueAsString(carrinho));
			/* O navegador cliente não irá escrever no cookie */
			cookie.setHttpOnly(true);
			
			/* Valido para todos os endereços do site */
			cookie.setPath("/");
			/* Escreve no Header da resposta */
			response.addCookie(cookie);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		
	}

}
