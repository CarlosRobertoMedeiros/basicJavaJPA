package br.com.roberto.jpaexamples;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.roberto.jpaexamples.model.Cliente;

/**
 * 
 * @author Roberto
 *
 */
public class App {
	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Clientes-PU");
		EntityManager em = emf.createEntityManager();
		
		
		//Inserção do Cliente
		/*Cliente cliente = new Cliente();
		cliente.setNome("Carlos Roberto");
		
		em.getTransaction().begin();
		em.persist(cliente);
		em.flush();
		em.getTransaction().commit();*/
		
		
		//Listagem do Cliente
		/*Cliente cliente = em.find(Cliente.class,1);
		System.out.println(cliente.getNome()); */
		
		//Remoção do Cliente
		/*Cliente cliente = em.find(Cliente.class,1);
		em.getTransaction().begin();
		em.remove(cliente);
		em.getTransaction().commit();*/
		
		//Listagem do Cliente com Cache de Primeiro Nível
		//Pesquisa 2 vezes o mesmo registro através de um id válido
		/*Cliente cliente = em.find(Cliente.class,2);
		Cliente cliente2 = em.find(Cliente.class,2);
		System.out.println(cliente.getNome());
		System.out.println(cliente2.getNome());*/
		
		//Atualização de Objetos
		//Atualização de Objeto gerenciado
		/*Cliente cliente = em.find(Cliente.class,2);
		em.getTransaction().begin();
		cliente.setNome(cliente.getNome()+" Alterado ");
		em.getTransaction().commit();*/
		
		//Atualização de Objetos
		//Atualização de Objeto não gerenciado
		Cliente cliente = new Cliente();
		//cliente.setId(1);
		cliente.setNome("Construtora Medeiros");
		
		em.getTransaction().begin();
		em.merge(cliente);
		em.getTransaction().commit();
		
		
		em.close();
		emf.close();

	}
}
