package com.apicdc.site.detalhe;

import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.apicdc.detalhelivro.Livro;

@Embeddable // não é suficiente para gerar uma tabela
public class ItemCompra {
	
	@ManyToOne
	@NotNull 
	@Valid
	private Livro livro;
	@Positive
	private int quantidade;
	@Positive
	@NotNull
	private BigDecimal preco;
	@Positive
	@NotNull
	private BigDecimal total;
	@NotBlank
	private String titulo;
	
	
	@Deprecated
	public ItemCompra() {
		super();
	}
	public ItemCompra(@NotNull @Valid Livro livro, @Positive int quantidade, @Positive @NotNull BigDecimal preco,
			@Positive @NotNull BigDecimal total, @NotBlank String titulo) {
		super();
		this.livro = livro;
		this.quantidade = quantidade;
		this.preco = preco;
		this.total = total;
		this.titulo = titulo;
	}
	@Override
	public String toString() {
		return "ItemCompra [livro=" + livro + ", quantidade=" + quantidade + ", preco=" + preco + ", total=" + total
				+ ", titulo=" + titulo + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemCompra other = (ItemCompra) obj;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	
	
}
