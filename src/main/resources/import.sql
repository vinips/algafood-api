-- COZINHA
insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

-- RESTAURANTE
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Hangu Italiano', 15.99, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Sabor da Bahia', 9.70, 2);

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