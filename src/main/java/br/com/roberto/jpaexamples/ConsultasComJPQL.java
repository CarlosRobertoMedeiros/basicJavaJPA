package br.com.roberto.jpaexamples;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.roberto.jpaexamples.dto.UsuarioDTO;
import br.com.roberto.jpaexamples.model.Configuracao;
import br.com.roberto.jpaexamples.model.Dominio;
import br.com.roberto.jpaexamples.model.Usuario;

public class ConsultasComJPQL {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Usuarios-PU");
		EntityManager em = emf.createEntityManager();

		// Código Aqui
		// primeiraConsulta(em); //Usando TypedQuery
		// segundaConsulta(em); // Usando TypedQuery
		// terceiraConsulta(em); // Usando Query
		// quartaConsultaEscolhendoRetorno(em);
		// quintaConsultaListaNomeUsuarios(em);
		// sextaConsultaFazendoProjecoes(em);
		// setimaConsultaFazendoProjecoesRetornandoDTO(em);
		// oitavaConsultaPassandoParametros(em);
		// nonaConsultaFazendoJoins(em);
		// decimaConsultaFazendoLeftJoin(em);
		// decimaPrimeiraConsultaCarregamentocomJoinFetch(em);
		// decimaSegundaConsultaFiltrandoRegistros(em);
		// decimaTerceiraUtilizandoOperadoresLogicos(em);
		// decimaQuartaOrdenandoResultados(em);
		   decimaQuintaPaginandoResultados(em);
		

		em.close();
		emf.close();

	}


	public static void primeiraConsulta(EntityManager em) {
		String jpql = "select u from Usuario u";
		TypedQuery<Usuario> typedQuery = em.createQuery(jpql, Usuario.class);
		List<Usuario> lista = typedQuery.getResultList();
		lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
	}

	public static void segundaConsulta(EntityManager em) {
		String jpqlSingle = "select u from Usuario u where u.id=1";
		TypedQuery<Usuario> typedQuerySing = em.createQuery(jpqlSingle, Usuario.class);
		Usuario usuario = typedQuerySing.getSingleResult();
		System.out.println(usuario.getId() + ", " + usuario.getNome());
	}

	public static void terceiraConsulta(EntityManager em) {
		String jpqlQ = "select u from Usuario u where u.id=1";
		Query query = em.createQuery(jpqlQ, Usuario.class);
		Usuario usuario = (Usuario) query.getSingleResult();
		System.out.println(usuario.getId() + ", " + usuario.getNome());
	}

	public static void quartaConsultaEscolhendoRetorno(EntityManager em) {
		String jpql = "select u.dominio from Usuario u where u.id = 1";
		TypedQuery<Dominio> typedQuery = em.createQuery(jpql, Dominio.class);
		Dominio dominio = typedQuery.getSingleResult();
		System.out.println(dominio.getId() + ", " + dominio.getNome());
	}

	public static void quintaConsultaListaNomeUsuarios(EntityManager em) {
		// Retorna apenas a String
		String jpql = "select u.nome from Usuario u";
		TypedQuery<String> typedQuery = em.createQuery(jpql, String.class);
		List<String> usuarios = typedQuery.getResultList();
		usuarios.forEach(nome -> System.out.println(nome));
	}

	public static void sextaConsultaFazendoProjecoes(EntityManager em) {
		// Realizando as projeções
		// Retornando o Array
		String jpqlArr = "select id,login,nome from Usuario u";
		TypedQuery<Object[]> typedQueryArr = em.createQuery(jpqlArr, Object[].class);
		List<Object[]> listaArr = typedQueryArr.getResultList();
		listaArr.forEach(arr -> System.out.println(String.format("%s %s %s", arr)));
	}

	public static void setimaConsultaFazendoProjecoesRetornandoDTO(EntityManager em) {
		// Realizando as projeções
		// Retornando uma lista de Usuario DTO
		String jpqlDTO = "select new br.com.roberto.jpaexamples.dto.UsuarioDTO(id,login,nome) " + "from Usuario u";

		TypedQuery<UsuarioDTO> typedQueryDTO = em.createQuery(jpqlDTO, UsuarioDTO.class);
		List<UsuarioDTO> listaDto = typedQueryDTO.getResultList();
		listaDto.forEach(u -> System.out.println(u.getId() + " " + u.getNome()));
	}

	public static void oitavaConsultaPassandoParametros(EntityManager em) {
		// Pesquisa por Id
		String jpql = "select u from Usuario u where u.id = :id ";
		TypedQuery<Usuario> typedQuery = em.createQuery(jpql, Usuario.class).setParameter("id", 1);

		Usuario usuario = typedQuery.getSingleResult();
		System.out.println(usuario.getId() + ", " + usuario.getNome());

		// Pesquisa por Login
		String jpqlLogin = "select u from Usuario u where u.login = :login ";
		TypedQuery<Usuario> typedQueryLogin = em.createQuery(jpqlLogin, Usuario.class).setParameter("login", "ria");

		Usuario usuarioLogin = typedQueryLogin.getSingleResult();
		System.out.println(usuarioLogin.getId() + ", " + usuarioLogin.getNome());

	}

	public static void nonaConsultaFazendoJoins(EntityManager em) {
		String jpql = "select u from Usuario u inner join u.dominio d where d.id = 1";
		TypedQuery<Usuario> typedQuery = em.createQuery(jpql, Usuario.class);
		List<Usuario> lista = typedQuery.getResultList();
		lista.forEach(u -> System.out.println(u.getId() + " , " + u.getNome()));
	}

	private static void decimaConsultaFazendoLeftJoin(EntityManager em) {
		String jpql = "select u, c from Usuario u left join u.configuracao c";
		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);
		List<Object[]> usuarios = typedQuery.getResultList();

		usuarios.forEach(arr -> {
			//arr[0] == Usuario
			//arr[1] == Configuracao
			String out = ((Usuario) arr[0]).getNome();
			if (arr[1] == null) {
				out += ", NULL";
			} else {
				out += " , " + ((Configuracao) arr[1]).getId();
			}
			System.out.println(out);
		});
	}
	
	private static void decimaPrimeiraConsultaCarregamentocomJoinFetch(EntityManager em) {
		String jpql = "select u from Usuario u join fetch u.configuracao join fetch u.dominio d ";
		TypedQuery<Usuario> typedQuery = em.createQuery(jpql,Usuario.class);
		List<Usuario> usuarios = typedQuery.getResultList();
		usuarios.forEach(u -> System.out.println(u.getId()+" , "+u.getNome()));
	}
	
	private static void decimaSegundaConsultaFiltrandoRegistros(EntityManager em) {
		//LIKE, IS NULL, IS EMPTY, BETWEEN, >,<,>=,<=,<>,=
		//IS NULL =  select u from Usuario u where u.senha is null;
		//IS EMPTY = select d from Dominio d where u.usuario is empty;

		
		//String jpql = " select u from Usuario u where u.nome like :nome"; //Ex 1
		String jpql = " select u from Usuario u where u.nome like concat(:nome,'%')";
		TypedQuery<Usuario> typedQuery = em.createQuery(jpql,Usuario.class)
				//.setParameter("nome", "Cal%"); //Ex 1
				.setParameter("nome", "Cal");
		List<Usuario> usuarios = typedQuery.getResultList();
		usuarios.forEach(u -> System.out.println(u.getId()+" , "+u.getNome()));
		
				
		
		//Exemplo com Between Traz todos devido a ser sysdate
		String jpql2 = " select u from Usuario u where u.ultimoAcesso between :ontem and :hoje";
		TypedQuery<Usuario> typedQuery2 = em.createQuery(jpql2,Usuario.class)
				//.setParameter("nome", "Cal%"); //Ex 1
				.setParameter("ontem", LocalDateTime.now().minusDays(1))
				.setParameter("hoje", LocalDateTime.now());
		List<Usuario> usuarios2 = typedQuery2.getResultList();
		usuarios2.forEach(u -> System.out.println(u.getId()+" , "+u.getNome()));
	}
	
	private static void decimaTerceiraUtilizandoOperadoresLogicos(EntityManager em) {
		String jpql = " select u from Usuario u where "
				+ " (u.ultimoAcesso > :ontem and u.ultimoAcesso < :hoje) "
				+ " or u.ultimoAcesso is null ";
		
		TypedQuery<Usuario> typedQuery = em.createQuery(jpql,Usuario.class)
				.setParameter("ontem", LocalDateTime.now().minusDays(1))
				.setParameter("hoje", LocalDateTime.now());
		List<Usuario> usuarios = typedQuery.getResultList();
		usuarios.forEach(u -> System.out.println(u.getId()+" , "+u.getNome()));
	}
	
	private static void decimaQuartaOrdenandoResultados(EntityManager em) {
		String JPQL = " select u from Usuario u order by u.nome ";
		//String JPQL = " select u from Usuario u order by u.dominio.nome ";
		TypedQuery<Usuario> typedQuery = em.createQuery(JPQL,Usuario.class);
		List<Usuario> usuarios = typedQuery.getResultList();
		usuarios.forEach(u -> System.out.println(u.getId()+" , " + u.getNome()));
	}

	private static void decimaQuintaPaginandoResultados(EntityManager em) {
		String JPQL = " select u from Usuario u ";
		//String JPQL = " select u from Usuario u order by u.dominio.nome ";
		TypedQuery<Usuario> typedQuery = em.createQuery(JPQL,Usuario.class)
				.setFirstResult(0) //.setFirstResult(2) //.setFirstResult(4) 
				.setMaxResults(2);
		List<Usuario> usuarios = typedQuery.getResultList();
		usuarios.forEach(u -> System.out.println(u.getId()+" , " + u.getNome()));
	}
}