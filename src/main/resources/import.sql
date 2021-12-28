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
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (1, 'Hangu Italiano', 15.99, 9);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (2, 'Sabor da Bahia', 9.70, 8);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (3, 'Tuk Tuk do Mohamed', 8.30, 2);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (4, 'LeBonTon', 11.90, 4);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (5, 'Hamei', 18.99, 5);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (6, 'Hong Ju', 14.44, 6);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (7, 'Churras Gaúchesco', 0, 8);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (8, 'Benvenuto', 7.33, 9);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (9, 'Guacamole', 13.23, 3);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (10, 'Hi Thai', 0, 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (11, 'Bangkok Thai', 18.01, 1);

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

-- PERMISSAO
insert into permissao (id, nome, descricao) values (1, 'Salvar', 'Permite Salvar');
insert into permissao (id, nome, descricao) values (2, 'Editar', 'Permite Editar');
insert into permissao (id, nome, descricao) values (3, 'Visualizar', 'Permite Visualizar');
insert into permissao (id, nome, descricao) values (4, 'Todos', 'Permite Todas as opções');

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