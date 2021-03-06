insert into tb_dominio (id, nome) values (1, 'Banco de Dados');
insert into tb_dominio (id, nome) values (2, 'LDAP');

insert into tb_usuario (id, nome, login, senha, dominio_id, ultimoAcesso) values (1, 'Cal Lightman', 'cal', '123', 1, sysdate());
insert into tb_usuario (id, nome, login, senha, dominio_id, ultimoAcesso) values (2, 'Gillian Foster', 'gillian', '123', 1, sysdate());
insert into tb_usuario (id, nome, login, senha, dominio_id, ultimoAcesso) values (3, 'Ria Torres', 'ria', '123', 1, sysdate());
insert into tb_usuario (id, nome, login, senha, dominio_id, ultimoAcesso) values (4, 'Eli Locker', 'eli', '123', 1, sysdate());
insert into tb_usuario (id, nome, login, senha, dominio_id, ultimoAcesso) values (5, 'Emily Lightman', 'emily', '123', 1, sysdate());

insert into tb_configuracao (usuario_id, receberNotificacoes, encerrarSessaoAutomaticamente) values (1, false, false);

insert into tb_funcionario (id, versao, nome, salario, bancoDeHoras, valorHoraExtra) values (1, 0, 'Cal Lightman', 5000, 20, 0);

insert into tb_artigo (id, titulo, conteudo) values (1, 'Título do artigo', 'Conteúdo do artigo');
