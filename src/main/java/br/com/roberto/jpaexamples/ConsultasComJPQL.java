package br.com.roberto.jpaexamples;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.roberto.jpaexamples.model.Usuario;

public class ConsultasComJPQL {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Usuarios-PU");
		EntityManager em = emf.createEntityManager();

		// Código Aqui
		primeiraConsulta(em);

		em.close();
		emf.close();

	}

	public static void primeiraConsulta(EntityManager em) {
		String jpql = "select u from Usuario u";
		TypedQuery<Usuario> typedQuery = em.createQuery(jpql,Usuario.class);
		List<Usuario> lista = typedQuery.getResultList();
		lista.forEach(u -> System.out.println(u.getId()+", "+u.getLogin()));

	}

}
