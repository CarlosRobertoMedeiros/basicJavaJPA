package br.com.roberto.jpaexamples.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_configuracao")
public class Configuracao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@MapsId
	@OneToOne
	private Usuario usuario;
	
	private boolean receberNotificacoes;

	private boolean encerrarSessaoAutomaticamente;

	public final Integer getId() {
		return id;
	}

	public final void setId(Integer id) {
		this.id = id;
	}

	public final Usuario getUsuario() {
		return usuario;
	}

	public final void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public final boolean isReceberNotificacoes() {
		return receberNotificacoes;
	}

	public final void setReceberNotificacoes(boolean receberNotificacoes) {
		this.receberNotificacoes = receberNotificacoes;
	}

	public final boolean isEncerrarSessaoAutomaticamente() {
		return encerrarSessaoAutomaticamente;
	}

	public final void setEncerrarSessaoAutomaticamente(boolean encerrarSessaoAutomaticamente) {
		this.encerrarSessaoAutomaticamente = encerrarSessaoAutomaticamente;
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
		Configuracao other = (Configuracao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
