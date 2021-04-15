package com.apicdc.site.detalhe;


import java.util.Optional;

import javax.management.RuntimeErrorException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.CookieGenerator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.apicdc.detalhelivro.Livro;
import com.apicdc.detalhelivro.LivroRepository;
import com.apicdc.shared.Cookies;


@RestController
public class DetalheLivroController {
	
	@Autowired
	private LivroRepository livroRepository;
	@Autowired
	private Cookies cookies;

	@GetMapping(value = "/api/livro/{id}")
	public LivroDetalheDTO exibeDetalhesLivro(@PathVariable("id") Long id) {
		Livro livro = livroRepository.findById(id).get();
		
		/* Pode ser utilizado o orElseTrow para retornar um erro 404 */
//		Livro livro = livroRepository.findById(id).orElseThrow()->Criar_a_exception ;
		
		return new LivroDetalheDTO(livro);
	}
	
	/* HttpServletResponse response escreve no http */
	@PostMapping(value = "/api/carrinho/{idLivro}")
	public String adionaLivroCarrinho(@PathVariable("idLivro") Long idLivro, @CookieValue("carrinho") Optional<String> jsonCarrinho, HttpServletResponse response) throws JsonProcessingException { // @CookieValue("carrinho") recebe o cookie enviado pela web
		
		Carrinho carrinho = Carrinho.cria(jsonCarrinho);
		
		/* Verifica se o carrinho foi criado */
//		System.out.print("carrinho criado");
		
		/* Adiciona o livro ao carrinho*/
		carrinho.adiciona(livroRepository.findById(idLivro).get());

		cookies.writeAsJson("carrinho",carrinho,response);
		
		return carrinho.toString();
		/*
		 * receber o carrinho pelo cookie(json)
		 * se não tiver ainda cookie para o carrinho, então cria um novo carrinho
		 * precisa de capa, titulo e preço do livro
		 */
	}

}
