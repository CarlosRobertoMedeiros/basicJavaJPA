package br.com.roberto.jpaexamples;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.roberto.jpaexamples.model.Usuario;

public class ConsultasComCriteria {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Usuarios-PU");
		EntityManager em = emf.createEntityManager();

		primeiraConsulta(em); //Continuar a partir de 15:20 Class 4
		
		
		
		em.close();
		emf.close();

	}

	private static void primeiraConsulta(EntityManager em) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		
		Root<Usuario> root = criteriaQuery.from(Usuario.class);
		
		criteriaQuery.select(root);
		
		TypedQuery<Usuario> typedQuery = em.createQuery(criteriaQuery);
		List<Usuario> usuarios = typedQuery.getResultList();
		usuarios.forEach(u -> System.out.println(u.getId()+" , "+u.getNome()));
		
	}

}
