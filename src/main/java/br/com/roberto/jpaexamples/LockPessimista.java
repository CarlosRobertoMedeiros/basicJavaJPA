package br.com.roberto.jpaexamples;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

import br.com.roberto.jpaexamples.model.Artigo;

public class LockPessimista {

	private static final Integer ID = 1;

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Usuarios-PU");
		
		// entendoAoOpcoes(emf);
		// javaEWorkBench(emf);
		casoMaisPratico(emf);

//		em.close();
//		emf.close();

	}

	private static void casoMaisPratico(EntityManagerFactory emf) {
		EntityManager entityManager1 = emf.createEntityManager();
		EntityManager entityManager2 = emf.createEntityManager();

		Runnable runnable1Joao = () -> {
			entityManager1.getTransaction().begin();
			log(1, "Imediatamente antes find.");
			Artigo artigo1 = entityManager1.find(Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
			log(1, "Imediatamente após find.");

			artigo1.setConteudo("Alteração do João (TH1)");

			log(1, "Esperando 3 segundos...");
			esperar(3000);
			log(1, "Espera dos 3 segs terminada.");

			log(1, "Imediatamente antes do commit.");
			entityManager1.getTransaction().commit();
			log(1, "Imediatamente após o commit.");
		};

		Runnable runnable2Maria = () -> {
			log(2, "Esperando 100 milis...");
			esperar(100);
			log(2, "Espera dos 100 milis terminada.");

			entityManager2.getTransaction().begin();
			log(2, "Imediatamente antes find.");
			Artigo artigo2 = entityManager2.find(Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
			log(2, "Imediatamente após find.");

			artigo2.setConteudo(artigo2.getConteudo() + " + Alteração da Maria (TH2)");

			log(2, "Imediatamente antes do commit.");
			entityManager2.getTransaction().commit();
			log(2, "Imediatamente após o commit.");
		};

		Thread thread1 = new Thread(runnable1Joao);
		Thread thread2 = new Thread(runnable2Maria);

		thread1.start();
		thread2.start();

	}

	private static void javaEWorkBench(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		log(1, "Imediatamente antes find.");
		Artigo artigo1 = em.find(Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
		log(1, "Imediatamente após find.");

		artigo1.setConteudo("Alteração do João (TH1)");

		log(1, "Esperando 25 segundos...");
		esperar(25000); // espera pois estou tentando alterar no workbench
		log(1, "Espera dos 25 segs terminada.");

		log(1, "Imediatamente antes do commit.");
		em.getTransaction().commit();
		log(1, "Imediatamente após o commit.");

	}

	private static void esperar(int milisegundos) {
		try {
			Thread.sleep(milisegundos);
		} catch (Exception e) {

		}

	}

	private static void log(Integer thread, String msg) {
		System.out.println("[THREAD_]" + thread + "]" + msg);

	}

	private static void entendoAoOpcoes(EntityManagerFactory emf) {
		EntityManager em1 = emf.createEntityManager();
		EntityManager em2 = emf.createEntityManager();

		// LockModeType.PESSIMISTIC_WRITE = QUER O LOCK NÃO DEIXA NINGUÉM LÊ OS DADOS
		// ATÉ O SEU TERMINAR, GARANTINDO
		// ASSIM CONSULTAS FUNTURAS COM O DADO ATUALIZADO
		// LockModeType.PESSIMISTIC_READ = QUER O LOCK DEIXA TODO MUNDO LER MAIS NINGUÉM
		// COMMITA NENHUM DADO ANTES DESSE
		em1.getTransaction().begin();
		Artigo artigo1 = em1.find(Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
		artigo1.setConteudo("Alteração do João");
		em1.getTransaction().commit();

		em2.getTransaction().begin();
		Artigo artigo2 = em2.find(Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
		artigo2.setConteudo(artigo2.getConteudo() + " " + "Alteração da Maria");
		em2.getTransaction().commit();

	}

}
