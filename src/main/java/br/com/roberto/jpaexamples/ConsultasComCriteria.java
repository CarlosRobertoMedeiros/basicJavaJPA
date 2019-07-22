package br.com.roberto.jpaexamples;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.roberto.jpaexamples.dto.UsuarioDTO;
import br.com.roberto.jpaexamples.model.Dominio;
import br.com.roberto.jpaexamples.model.Usuario;

public class ConsultasComCriteria {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Usuarios-PU");
		EntityManager em = emf.createEntityManager();

		// primeiraConsulta(em); //Continuar a partir de 00:46:00 Problemas ao chamar o objeto DTO no criteria
		// segundaConsultaEscolhendoRetorno(em);
		// terceiraConsultaRetornandoUmaString(em);
		//quartaConsultaFazendoProjecoes(em);
		quintaConsultaFazendoProjecoesRetornandoUsuarioDTO(em);
		

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
		usuarios.forEach(u -> System.out.println(u.getId() + " , " + u.getNome()));

	}

	private static void segundaConsultaEscolhendoRetorno(EntityManager em) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<Dominio> criteriaQuery = criteriaBuilder.createQuery(Dominio.class);
		Root<Usuario> root = criteriaQuery.from(Usuario.class);

		// Retornando o Dominio
		criteriaQuery.select(root.get("dominio"));

		TypedQuery<Dominio> typedQuery = em.createQuery(criteriaQuery);
		List<Dominio> usuarios = typedQuery.getResultList();
		usuarios.forEach(d -> System.out.println(d.getId() + " , " + d.getNome()));

	}

	private static void terceiraConsultaRetornandoUmaString(EntityManager em) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// Query de String
		CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
		Root<Usuario> root = criteriaQuery.from(Usuario.class);

		criteriaQuery.select(root.get("nome"));

		TypedQuery<String> typedQuery = em.createQuery(criteriaQuery);
		List<String> lista = typedQuery.getResultList();

		lista.forEach(nome -> System.out.println("Nome: " + nome));

	}

	private static void quartaConsultaFazendoProjecoes(EntityManager em) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Usuario> root = criteriaQuery.from(Usuario.class);

		criteriaQuery.multiselect(root.get("id"), root.get("login"), root.get("nome"));

		TypedQuery<Object[]> typedQuery = em.createQuery(criteriaQuery);
		List<Object[]> usuarios = typedQuery.getResultList();
		usuarios.forEach(u -> System.out.println(String.format("%s, %s, %s", u)));

	}

	private static void quintaConsultaFazendoProjecoesRetornandoUsuarioDTO(EntityManager em) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<UsuarioDTO> criteriaQuery = criteriaBuilder.createQuery(UsuarioDTO.class);
		Root<Usuario> root = criteriaQuery.from(Usuario.class);

		criteriaQuery.multiselect(criteriaBuilder.construct(UsuarioDTO.class, 
						root.get("id"), 
						root.get("login"), 
						root.get("nome")));

		TypedQuery<UsuarioDTO> typedQuery = em.createQuery(criteriaQuery);
		List<UsuarioDTO> usuarios = typedQuery.getResultList();
		usuarios.forEach(u -> System.out.println("DTO: "+u.getId()+" , "+u.getNome()));
		
	}

}
