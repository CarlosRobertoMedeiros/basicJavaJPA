package br.com.roberto.jpaexamples;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.roberto.jpaexamples.model.Funcionario;
import lombok.Getter;
import lombok.Setter;

public class LockOtimista {

	private static final Integer CALL_LIGHTMAN_ID = 1;

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Usuarios-PU");
		EntityManager em = emf.createEntityManager();

		Funcionario funcionario = em.find(Funcionario.class, CALL_LIGHTMAN_ID);

		em.close();

		TelaDeFuncionarios sessao1 = 
				new TelaDeFuncionarios("SESSAO_1", emf, funcionario, "Call Lightman Moreira");

		sessao1.editarNome();
		sessao1.submeterFormulario();
		
		TelaDeFuncionarios sessao2 = 
				new TelaDeFuncionarios("SESSAO_1", emf, funcionario, "Call Lightman Silva");
		
		sessao2.editarNome();
		sessao2.submeterFormulario();
		
		
		sessao1.atualizarTelaParaVerificarNome();
		sessao2.atualizarTelaParaVerificarNome();
		
		sessao1.fecharTela();
		sessao2.fecharTela();

		emf.close();

	}

	@Getter
	@Setter
	public static class TelaDeFuncionarios {
		private final String sessao;
		private final EntityManager entityManager;
		private final Funcionario funcionario;
		private final String novoNome;

		public TelaDeFuncionarios(String sessao, EntityManagerFactory entityManagerFactory, Funcionario funcionario,
				String novoNome) {
			super();
			this.sessao = sessao;
			this.entityManager = entityManagerFactory.createEntityManager();
			this.funcionario = funcionario;
			this.novoNome = novoNome;
		}

		public void editarNome() {
			funcionario.setNome(novoNome);
		}

		public void submeterFormulario() {
			System.out.println(sessao + ": Iniciando Tentativa de Atualizar Funcionário.");

			try {
				entityManager.getTransaction().begin();
				entityManager.merge(funcionario);
				entityManager.getTransaction().commit();
				System.out.println(sessao + ": Funcionário foi atualizado.");

			} catch (Exception e) {
				System.out.println(sessao + ": Erro na atualização do Funcionário. MSG:" + e.getMessage());
				throw e;
			}

		}

		public void atualizarTelaParaVerificarNome() {
			entityManager.clear();

			Funcionario funcionario = entityManager.find(Funcionario.class, CALL_LIGHTMAN_ID);

			System.out.println(sessao + ": Tela da sessão " + sessao + " atualizada.");
			if (novoNome.equals(funcionario.getNome())) {
				System.out.println(sessao + ": Bom... Foi atualizado certinho. " + "Agora vou continuar meu trabalho.");
			} else {
				System.out.println(sessao + ": Ueh! Não tinha deixado o nome " + funcionario.getNome()
						+ " eu tenho certeza que coloquei " + novoNome);
			}

		}

		public void fecharTela() {
			entityManager.close();
		}

	}

}