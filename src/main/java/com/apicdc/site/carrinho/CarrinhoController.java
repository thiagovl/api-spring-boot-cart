package com.apicdc.site.carrinho;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apicdc.detalhelivro.Livro;
import com.apicdc.detalhelivro.LivroRepository;
import com.apicdc.shared.Cookies;
import com.apicdc.site.detalhe.Carrinho;

@RestController
public class CarrinhoController {

	@Autowired
	private LivroRepository livroRepository;
	@Autowired
	private Cookies cookies;
	
	
	@PostMapping(value = "/api/carrinho/{idLivro}/atualiza")
	public void atualiza(@PathVariable("idLivro") Long idLivro, @RequestParam int novaQuantidade, @CookieValue("carrinho") String jsonCarrinho, HttpServletResponse response) {
		Carrinho carrinho = Carrinho.cria(Optional.of(jsonCarrinho));
		Livro livro = livroRepository.findById(idLivro).get();
		carrinho.atualiza(livro, novaQuantidade);
		
		cookies.writeAsJson("carrinho", carrinho, response);
	
	}

}
