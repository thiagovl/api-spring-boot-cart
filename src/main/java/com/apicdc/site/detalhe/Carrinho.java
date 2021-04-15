package com.apicdc.site.detalhe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;

import com.apicdc.detalhelivro.Livro;
import com.apicdc.detalhelivro.LivroRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora o total em getTotal()
public class Carrinho {
	
	private Set<LivroCarrinhoDTO> livros = new LinkedHashSet<>(); // Mantém a ordem e não permite objetos iguais
	
	@Deprecated
	public Carrinho() {
		super();
	}

	public void adiciona(Livro livro) {
		LivroCarrinhoDTO novoItem = new LivroCarrinhoDTO(livro);
		boolean result = livros.add(novoItem); // Adiciona um novo item
		if(!result) {
			LivroCarrinhoDTO itemExistente = livros.stream().filter(novoItem :: equals).findFirst().get();
			itemExistente.incrementa();
		}
	}
	

	@Override
	public String toString() {
		return "Carrinho [livros=" + livros + "]";
	}

	public Set<LivroCarrinhoDTO> getLivros() {
		return livros;
	}
	
	/*  jsonCarrinho possível json de um carrinho já criado
	 */
	public static Carrinho cria(Optional<String> jsonCarrinho) {
		
		/* Mapea o objeto jsonCarrinho - json, vindo do cookie do cliente e mapea para a classe Carrinho.class 
		 * .orElse(new Carrinho()) se não tiver nada no carrinho cria um novo carrinho 
		 */
		return jsonCarrinho.map(json -> {
			try {
				return new ObjectMapper().readValue(json, Carrinho.class);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}).orElse(new Carrinho()); //.orElse(new Carrinho()) se não tiver nada no carrinho cria um novo carrinho 
	}

	public void atualiza(@NotNull Livro livro, @Positive int novaQuantidade) {
		Assert.isTrue(novaQuantidade > 0, "A quantidade de atualização tem que ser maior que zero(0)");
		LivroCarrinhoDTO possivelItemAdicionado = new LivroCarrinhoDTO(livro);
		Optional<LivroCarrinhoDTO> possivelItem = livros.stream().filter(possivelItemAdicionado :: equals).findFirst();
	
		Assert.isTrue(possivelItem.isPresent(), "Você não pode atualizar um livro que não foi colocado no carrinho");
		
		LivroCarrinhoDTO itemQueExiste = possivelItem.get();
		itemQueExiste.atualizaQuantidade(novaQuantidade);
		
	}
	
	public BigDecimal getTotal() {
		return livros.stream().map(item -> item.getTotal()).reduce(BigDecimal.ZERO, 
				(atual, proximo) -> atual.add(proximo));
	}

	public Set<ItemCompra> getItensCompra(LivroRepository livroRepository) {
		// TODO Auto-generated method stub
		return this.livros.stream().map(itemCarrinho -> {
			return itemCarrinho.novoItemCompra(livroRepository);
		}).collect(Collectors.toSet());
	}
}
