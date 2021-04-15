package com.apicdc.site.continuapagamento;

import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apicdc.detalhelivro.LivroRepository;
import com.apicdc.site.detalhe.Carrinho;
import com.apicdc.site.detalhe.ItemCompra;

@RestController
public class ContinuaPagamentoController {

	@Autowired
	private LivroRepository livroRepository;
	@PersistenceContext
	private EntityManager manager;

	@PostMapping(value = "/api/carrinho/finaliza")
	@Transactional
	public String processo(@Valid DadosCompradorForm form, @CookieValue("carrinho") String jsonCarrinho) {
		
		/* Recupera o carrinho */
		Carrinho carrinho = Carrinho.cria(Optional.of(jsonCarrinho));
		Set<ItemCompra> itens = carrinho.getItensCompra(livroRepository);
		
		Compra novaCompra = form.novaCompra(itens);
		
		manager.persist(novaCompra);
		
		return novaCompra.toString();
	}

}
