package com.vinips.algafood.domain.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//Isso quer dizer que ela é parte de outra entidade, e não uma entidade em si.
@Embeddable
public class Endereco {
	
	@Column(name="endereco_cep")
	private String cep;
	
	@Column(name="endereco_logradouro")
	private String logradouro;
	
	@Column(name="endereco_numero")
	private String numero;
	
	@Column(name="endereco_complemento")
	private String complemento;
	
	@Column(name="endereco_bairro")
	private String bairro;
	
	//fetch Lazy transforma o Eager
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="endereco_cidade_id")
	private Cidade cidade;
	
	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getLogradouro() {
		return logradouro;
	}
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public Cidade getCidade() {
		return cidade;
	}
	
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cep);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		return Objects.equals(cep, other.cep);
	}

	@Override
	public String toString() {
		return "Endereco [cep=" + cep + ", logradouro=" + logradouro + ", numero=" + numero + ", complemento="
				+ complemento + ", bairro=" + bairro + ", cidade=" + cidade + "]";
	}
	
	
	
}
