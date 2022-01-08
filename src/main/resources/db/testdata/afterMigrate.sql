-- Não é uma Migração, é um arquivo SQL que roda automaticamente no callback do Flyway depois de rodar todas as migrações.
-- Precisa ter esse nome. Nesse arquivo é importante colocar a keyword IGNORE para não dar erro se tentar inserir caso já tenha.
-- Exemplo: insert IGNORE into cozinha (id, nome) values (1, 'Tailandesa');

set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;

-- PERMISSAO
insert into permissao (id, nome, descricao) values (1, 'Salvar', 'Permite Salvar');
insert into permissao (id, nome, descricao) values (2, 'Editar', 'Permite Editar');
insert into permissao (id, nome, descricao) values (3, 'Visualizar', 'Permite Visualizar');
insert into permissao (id, nome, descricao) values (4, 'Todos', 'Permite Todas as opções');

-- ESTADO
insert into estado (id, nome) values (1, 'Santa Catarina');
insert into estado (id, nome) values (2, 'Rio de Janeiro');
insert into estado (id, nome) values (3, 'São Paulo');
insert into estado (id, nome) values (4, 'Paraná');
insert into estado (id, nome) values (5, 'Rio Grande do Sul');
insert into estado (id, nome) values (6, 'Bahia');

-- CIDADE
insert into cidade (id, nome, estado_id) values (1, 'Palhoça', 1);
insert into cidade (id, nome, estado_id) values (2, 'Blumenau', 1);
insert into cidade (id, nome, estado_id) values (3, 'Campinas', 3);
insert into cidade (id, nome, estado_id) values (4, 'Curitiba', 4);
insert into cidade (id, nome, estado_id) values (5, 'Pelotas', 5);
insert into cidade (id, nome, estado_id) values (6, 'Salvador', 6);

-- COZINHA
insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Mexicana');
insert into cozinha (id, nome) values (4, 'Francesa');
insert into cozinha (id, nome) values (5, 'Japonesa');
insert into cozinha (id, nome) values (6, 'Koreana');
insert into cozinha (id, nome) values (7, 'Polonesa');
insert into cozinha (id, nome) values (8, 'Brasileira');
insert into cozinha (id, nome) values (9, 'Italiana');

-- RESTAURANTE
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) values (1, 'Hangu Italiano', 15.99, 9, utc_timestamp, utc_timestamp, '88136-311', 'Rua das bananeiras', '199', 'casa', 'Loteamento Jurema', 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (2, 'Sabor da Bahia', 9.70, 8, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (3, 'Tuk Tuk do Mohamed', 8.30, 2, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (4, 'LeBonTon', 11.90, 4, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (5, 'Hamei', 18.99, 5, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (6, 'Hong Ju', 14.44, 6, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (7, 'Churras Gaúchesco', 0, 8, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (8, 'Benvenuto', 7.33, 9, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (9, 'Guacamole', 13.23, 3, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (10, 'Hi Thai', 0, 1, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (11, 'Bangkok Thai', 18.01, 1, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (12, 'Polska', 13.99, 7, utc_timestamp, utc_timestamp);

-- PRODUTO
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (1, 'Bobó de Camarão', 'Delicioso Bobó de camarão na chapa', 27.99, true, 2);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (2, 'Linguiça no Forno', 'Linguiça italiana no forno de madeira', 30.99, true, 2);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (3, 'Vatapá', 'Vatapá ao molho branco', 14.99, true, 2);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (4, 'Camarão no Dendê', 'Cozinho de camarão com delicioso molho de dendê', 40.65, true, 7);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (5, 'Harumaki', '10 unidades de Harumaki de salmão', 10.99, true, 5);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (6, 'Temaki', 'Temaki de salmão com cream cheese e cebolinha', 13.50 , true, 5);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (7, 'Kimchi', 'Bem picante', 10.99, true, 6);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (8, 'Pão de Queijo', 'Pão de Queijo quentinho e crocante', 4.00 , true, 7);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (9, 'Lasanha', 'Lasanha com bastante queji, presunto e requeijão', 19.50, true, 1);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (10, 'Pizza', 'Pizza tradicional de calabresa com cebola', 45.14, true, 1);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (11, 'Risotto', 'Rissoto de arroz com cogumelos e queijo branco', 25.66, true, 1);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (12, 'Tiramisù', 'Fatia de Bolo pelado com toques de café', 3.54, true, 1);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (13, 'Panetone artesanal', 'Panetone do jeito que vovó gosta, com bastante frutas secas', 7.65, true, 8);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (14, 'Guacamole', 'Pote com guacamole feito com melhor abacate da região', 10.99, true, 9);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (15, 'Tacos', 'Cuidado, bem apimentado!', 13.99, true, 9);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (16, 'Foie Gras', 'Carne de pato, basicamente', 23.99, true, 4);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (17, 'Pierogi', 'Uma trouxinha de massa recheada cozida e frita', 6.45, true, 12);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (18, 'Som tam', 'Salada de mamão', 7.99, true, 10);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (19, 'Satay ', 'Espetinho de porco com molho de amendoim', 14.56, true, 10);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (20, 'Panaeng Muú', 'Carne com curry vermelho', 20.11, true, 11);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (21, 'Curry Picante', 'Curry picante', 15.54 , true, 3);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (22, 'Curry Ameno', 'Curry ameno', 15.54, true, 3);

-- FORMA PAGAMENTO
insert into forma_pagamento (id, descricao) values (1, 'Dinheiro');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de Crédito');
insert into forma_pagamento (id, descricao) values (3, 'Cartão de Débito');
insert into forma_pagamento (id, descricao) values (4, 'Vale Alimentação');
insert into forma_pagamento (id, descricao) values (5, 'Vale Refeição');
insert into forma_pagamento (id, descricao) values (6, 'Pix');

-- RESTAURANTE_FORMA_PAGAMENTO
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 2);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 3);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 4);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 5);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (2, 3);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (2, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (2, 2);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (3, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (3, 3);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (3, 4);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (4, 5);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (5, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (5, 2);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (5, 3);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (6, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (6, 2);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (6, 3);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (7, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (8, 2);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (9, 3);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (10, 4);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (10, 5);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (11, 3);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (11, 1);



