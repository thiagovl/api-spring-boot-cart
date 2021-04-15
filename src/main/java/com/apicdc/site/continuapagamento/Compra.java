package com.apicdc.site.continuapagamento;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.apicdc.site.detalhe.ItemCompra;

@Entity
public class Compra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank @Email String email;
	@NotBlank @CpfCnpj String documento; 
	@NotBlank String endereco;
	private String complemento;
	
	@ElementCollection
	@Size(min = 1) Set<ItemCompra> itens  = new HashSet<>();
	
	@PastOrPresent // a data atual
	private LocalDateTime createdAt = LocalDateTime.now();
	
	public Compra(@NotBlank @Email String email, @NotBlank String documento, @NotBlank String endereco,
			@Size(min = 1) Set<ItemCompra> itens) {
		this.email = email;
		this.documento = documento;
		this.endereco = endereco;
		this.itens.addAll(itens);
	}
	
	
	@Deprecated
	public Compra() {

	}

	public String getComplemento() {
		return complemento;
	}


	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	@Override
	public String toString() {
		return "Compra [id=" + id + ", email=" + email + ", documento=" + documento + ", endereco=" + endereco
				+ ", complemento=" + complemento + ", itens=" + itens + ", createdAt=" + createdAt + "]";
	}

	

	
}
