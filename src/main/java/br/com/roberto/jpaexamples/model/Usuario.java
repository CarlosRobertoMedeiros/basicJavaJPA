package br.com.roberto.jpaexamples.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String nome;
	
	private String login;

	private String senha;

	private LocalDateTime ultimoAcesso;
	
	@ManyToOne
	private Dominio dominio;
	
	@OneToOne(mappedBy="usuario")
	private Configuracao configuracao;

	
	public final Dominio getDominio() {
		return dominio;
	}

	public final String getNome() {
		return nome;
	}

	public final void setNome(String nome) {
		this.nome = nome;
	}

	public final void setDominio(Dominio dominio) {
		this.dominio = dominio;
	}

	public final Configuracao getConfiguracao() {
		return configuracao;
	}

	public final void setConfiguracao(Configuracao configuracao) {
		this.configuracao = configuracao;
	}

	public final Integer getId() {
		return id;
	}

	public final void setId(Integer id) {
		this.id = id;
	}

	public final String getLogin() {
		return login;
	}

	public final void setLogin(String login) {
		this.login = login;
	}

	public final String getSenha() {
		return senha;
	}

	public final void setSenha(String senha) {
		this.senha = senha;
	}

	public final LocalDateTime getUltimoAcesso() {
		return ultimoAcesso;
	}

	public final void setUltimoAcesso(LocalDateTime ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
