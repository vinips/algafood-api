-- Não é uma Migração, é um arquivo SQL que roda automaticamente no callback do Flyway depois de rodar todas as migrações.
-- Precisa ter esse nome. Nesse arquivo é importante colocar a keyword IGNORE para não dar erro se tentar inserir caso já tenha.
-- Exemplo: insert IGNORE into cozinha (id, nome) VALUES (1, 'Tailandesa');

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
delete from restaurante_usuario_responsavel;
delete from usuario;
delete from usuario_grupo;
delete from pedido;
delete from item_pedido;

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
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;

-- PERMISSAO
INSERT INTO permissao (id, nome, descricao) VALUES (1, 'Salvar', 'Permite Salvar');
INSERT INTO permissao (id, nome, descricao) VALUES (2, 'Editar', 'Permite Editar');
INSERT INTO permissao (id, nome, descricao) VALUES (3, 'Visualizar', 'Permite Visualizar');
INSERT INTO permissao (id, nome, descricao) VALUES (4, 'Todos', 'Permite Todas as opções');

-- GRUPO
INSERT INTO grupo (id, nome) VALUES (1, 'ADM');
INSERT INTO grupo (id, nome) VALUES (2, 'Gerente');
INSERT INTO grupo (id, nome) VALUES (3, 'Vendedor');
INSERT INTO grupo (id, nome) VALUES (4, 'Secretário');
INSERT INTO grupo (id, nome) VALUES (5, 'Cadastrador');
INSERT INTO grupo (id, nome) VALUES (6, 'Entregador');
INSERT INTO grupo (id, nome) VALUES (7, 'Faxineiro');

-- USUARIO
INSERT INTO usuario (id, nome, email, senha, data_cadastro) VALUES (1, 'Adm', 'adm@gmail.com', '1234', utc_timestamp);
INSERT INTO usuario (id, nome, email, senha, data_cadastro) VALUES (2, 'Jorge', 'jorge@yahoo.com.br', '12345', utc_timestamp);
INSERT INTO usuario (id, nome, email, senha, data_cadastro) VALUES (3, 'Amanda', 'amanda@bol.com.br', '12323', utc_timestamp);
INSERT INTO usuario (id, nome, email, senha, data_cadastro) VALUES (4, 'Ricardo', 'ricardo@hotmail.com', '11233', utc_timestamp);
INSERT INTO usuario (id, nome, email, senha, data_cadastro) VALUES (5, 'vendas', 'vendas@algafood.com', '33442', utc_timestamp);

-- ESTADO
INSERT INTO estado (id, nome) VALUES (1, 'Santa Catarina');
INSERT INTO estado (id, nome) VALUES (2, 'Rio de Janeiro');
INSERT INTO estado (id, nome) VALUES (3, 'São Paulo');
INSERT INTO estado (id, nome) VALUES (4, 'Paraná');
INSERT INTO estado (id, nome) VALUES (5, 'Rio Grande do Sul');
INSERT INTO estado (id, nome) VALUES (6, 'Bahia');

-- CIDADE
INSERT INTO cidade (id, nome, estado_id) VALUES (1, 'Palhoça', 1);
INSERT INTO cidade (id, nome, estado_id) VALUES (2, 'Blumenau', 1);
INSERT INTO cidade (id, nome, estado_id) VALUES (3, 'Campinas', 3);
INSERT INTO cidade (id, nome, estado_id) VALUES (4, 'Curitiba', 4);
INSERT INTO cidade (id, nome, estado_id) VALUES (5, 'Pelotas', 5);
INSERT INTO cidade (id, nome, estado_id) VALUES (6, 'Salvador', 6);

-- COZINHA
INSERT INTO cozinha (id, nome) VALUES (1, 'Tailandesa');
INSERT INTO cozinha (id, nome) VALUES (2, 'Indiana');
INSERT INTO cozinha (id, nome) VALUES (3, 'Mexicana');
INSERT INTO cozinha (id, nome) VALUES (4, 'Francesa');
INSERT INTO cozinha (id, nome) VALUES (5, 'Japonesa');
INSERT INTO cozinha (id, nome) VALUES (6, 'Koreana');
INSERT INTO cozinha (id, nome) VALUES (7, 'Polonesa');
INSERT INTO cozinha (id, nome) VALUES (8, 'Brasileira');
INSERT INTO cozinha (id, nome) VALUES (9, 'Italiana');

-- RESTAURANTE
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) VALUES (1, 'Hangu Italiano', 15.99, 9, utc_timestamp, utc_timestamp, true, false, '88136-311', 'Rua das bananeiras', '199', 'casa', 'Loteamento Jurema', 1);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (2, 'Sabor da Bahia', 9.70, 8, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (3, 'Tuk Tuk do Mohamed', 8.30, 2, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (4, 'LeBonTon', 11.90, 4, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (5, 'Hamei', 18.99, 5, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (6, 'Hong Ju', 14.44, 6, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (7, 'Churras Gaúchesco', 0, 8, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (8, 'Benvenuto', 7.33, 9, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (9, 'Guacamole', 13.23, 3, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (10, 'Hi Thai', 0, 1, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (11, 'Bangkok Thai', 18.01, 1, utc_timestamp, utc_timestamp, true, false);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) VALUES (12, 'Polska', 13.99, 7, utc_timestamp, utc_timestamp, true, false);

-- PRODUTO
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (1, 'Bobó de Camarão', 'Delicioso Bobó de camarão na chapa', 27.99, false, 2);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (2, 'Linguiça no Forno', 'Linguiça italiana no forno de madeira', 30.99, true, 2);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (3, 'Vatapá', 'Vatapá ao molho branco', 14.99, true, 2);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (4, 'Camarão no Dendê', 'Cozinho de camarão com delicioso molho de dendê', 40.65, true, 7);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (5, 'Harumaki', '10 unidades de Harumaki de salmão', 10.99, true, 5);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (6, 'Temaki', 'Temaki de salmão com cream cheese e cebolinha', 13.50 , true, 5);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (7, 'Kimchi', 'Bem picante', 10.99, true, 6);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (8, 'Pão de Queijo', 'Pão de Queijo quentinho e crocante', 4.00 , true, 7);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (9, 'Lasanha', 'Lasanha com bastante queji, presunto e requeijão', 19.50, true, 1);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (10, 'Pizza', 'Pizza tradicional de calabresa com cebola', 45.14, true, 1);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (11, 'Risotto', 'Rissoto de arroz com cogumelos e queijo branco', 25.66, true, 1);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (12, 'Tiramisù', 'Fatia de Bolo pelado com toques de café', 3.54, true, 1);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (13, 'Panetone artesanal', 'Panetone do jeito que vovó gosta, com bastante frutas secas', 7.65, true, 8);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (14, 'Guacamole', 'Pote com guacamole feito com melhor abacate da região', 10.99, true, 9);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (15, 'Tacos', 'Cuidado, bem apimentado!', 13.99, true, 9);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (16, 'Foie Gras', 'Carne de pato, basicamente', 23.99, true, 4);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (17, 'Pierogi', 'Uma trouxinha de massa recheada cozida e frita', 6.45, true, 12);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (18, 'Som tam', 'Salada de mamão', 7.99, true, 10);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (19, 'Satay ', 'Espetinho de porco com molho de amendoim', 14.56, true, 10);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (20, 'Panaeng Muú', 'Carne com curry vermelho', 20.11, true, 11);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (21, 'Curry Picante', 'Curry picante', 15.54 , true, 3);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (22, 'Curry Ameno', 'Curry ameno', 15.54, true, 3);

-- FORMA PAGAMENTO
INSERT INTO forma_pagamento (id, descricao) VALUES (1, 'Dinheiro');
INSERT INTO forma_pagamento (id, descricao) VALUES (2, 'Cartão de Crédito');
INSERT INTO forma_pagamento (id, descricao) VALUES (3, 'Cartão de Débito');
INSERT INTO forma_pagamento (id, descricao) VALUES (4, 'Vale Alimentação');
INSERT INTO forma_pagamento (id, descricao) VALUES (5, 'Vale Refeição');
INSERT INTO forma_pagamento (id, descricao) VALUES (6, 'Pix');

-- RESTAURANTE_FORMA_PAGAMENTO
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 2);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 3);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 4);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 5);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (2, 3);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (2, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (2, 2);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (3, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (3, 3);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (3, 4);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (4, 5);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (5, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (5, 2);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (5, 3);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (6, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (6, 2);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (6, 3);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (7, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (8, 2);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (9, 3);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (10, 4);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (10, 5);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (11, 3);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (11, 1);

-- GRUPO_PERMISSAO
INSERT INTO grupo_permissao (grupo_id, permissao_id) VALUES (1, 1);
INSERT INTO grupo_permissao (grupo_id, permissao_id) VALUES (1, 2);
INSERT INTO grupo_permissao (grupo_id, permissao_id) VALUES (1, 3);
INSERT INTO grupo_permissao (grupo_id, permissao_id) VALUES (2, 2);
INSERT INTO grupo_permissao (grupo_id, permissao_id) VALUES (3, 4);

-- USUARIO_GRUPO
INSERT INTO usuario_grupo (usuario_id, grupo_id) VALUES (1, 1);
INSERT INTO usuario_grupo (usuario_id, grupo_id) VALUES (1, 2);
INSERT INTO usuario_grupo (usuario_id, grupo_id) VALUES (1, 3);
INSERT INTO usuario_grupo (usuario_id, grupo_id) VALUES (2, 2);
INSERT INTO usuario_grupo (usuario_id, grupo_id) VALUES (3, 3);

-- RESTAURANTE_USUARIO_RESPONSAVEL
INSERT INTO restaurante_usuario_responsavel (restaurante_id, usuario_id) VALUES (1, 1);
INSERT INTO restaurante_usuario_responsavel (restaurante_id, usuario_id) VALUES (1, 2);
INSERT INTO restaurante_usuario_responsavel (restaurante_id, usuario_id) VALUES (1, 3);

-- PEDIDO
INSERT INTO pedido (id, codigo, data_cancelamento, data_confirmacao, data_criacao, data_entrega, status, subtotal, taxa_frete, valor_total, usuario_cliente_id, forma_pagamento_id, restaurante_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) VALUES (1, 'f55a5397-abba-11ec-9203-b42e99ed2795', null, null, utc_timestamp, null, 'CRIADO', 45.00, 4.99, 49.99, 1, 2, 2, '88136-311', 'Rua das bananeiras', '199', 'casa', 'Loteamento Jurema', 1);
INSERT INTO pedido (id, codigo,data_cancelamento, data_confirmacao, data_criacao, data_entrega, status, subtotal, taxa_frete, valor_total, usuario_cliente_id, forma_pagamento_id, restaurante_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) VALUES (2, 'f55a6353-abba-11ec-9203-b42e99ed2795', null, utc_timestamp, utc_timestamp, utc_timestamp, 'ENTREGUE', 20.99, 10.01, 31.00, 3, 3, 1, '88136-117', 'Servidão Amaurí Junior', '1985', 'Mansão', 'Alphaville', 4);
INSERT INTO pedido (id, codigo,data_cancelamento, data_confirmacao, data_criacao, data_entrega, status, subtotal, taxa_frete, valor_total, usuario_cliente_id, forma_pagamento_id, restaurante_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) VALUES (3, 'f55a72d8-abba-11ec-9203-b42e99ed2795', null, utc_timestamp, utc_timestamp, null, 'CONFIRMADO', 39.90, 0.10, 40.00, 1, 1, 2, '88136-311', 'Rua das Jacas', '231', 'Apartamento', 'Praia da Philco', 2);

-- ITEM_PEDIDO
INSERT INTO item_pedido (id, observacao, preco_total, preco_unitario, quantidade, pedido_id, produto_id) VALUES (1, 'Nenhuma', 83.97, 27.99, 3, 1, 1);
INSERT INTO item_pedido (id, observacao, preco_total, preco_unitario, quantidade, pedido_id, produto_id) VALUES (2, 'Tirar cebola de tudo', 39.00, 19.50, 2, 2, 9);
INSERT INTO item_pedido (id, observacao, preco_total, preco_unitario, quantidade, pedido_id, produto_id) VALUES (3, 'Nenhuma', 123.96, 30.99, 4, 1, 2);
INSERT INTO item_pedido (id, observacao, preco_total, preco_unitario, quantidade, pedido_id, produto_id) VALUES (4, 'Favor mandar Molhos', 123.96, 30.99, 1, 3, 3);
INSERT INTO item_pedido (id, observacao, preco_total, preco_unitario, quantidade, pedido_id, produto_id) VALUES (5, 'Nenhuma', 14.99, 14.99, 1, 1, 3);
