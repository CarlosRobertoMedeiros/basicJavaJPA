package br.com.roberto.jpaexamples.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_Funcionario")
public class Funcionario {

	@Id
	private Integer id;
	@Version //Incrementa o JPA para criar um lockOtimista
			 //Garantindo assim as versões e evitando erros
	private Integer versao;
	private String nome;
	private Integer bancoDeHoras;
	private BigDecimal salario;
	private BigDecimal valorHoraExtra;

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
		Funcionario other = (Funcionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
