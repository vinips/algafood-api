
CREATE TABLE item_pedido (
    id BIGINT NOT NULL AUTO_INCREMENT,
    observacao VARCHAR(255),
    preco_total DECIMAL(19 , 2 ) NOT NULL,
    preco_unitario DECIMAL(19 , 2 ) NOT NULL,
    quantidade INTEGER NOT NULL,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB CHARSET=UTF8;

CREATE TABLE pedido (
    id BIGINT NOT NULL AUTO_INCREMENT,
    data_cancelamento DATETIME,
    data_confirmacao DATETIME,
    data_criacao DATETIME NOT NULL,
    data_entrega DATETIME,
    endereco_bairro VARCHAR(80) NOT NULL,
    endereco_cep VARCHAR(20) NOT NULL,
    endereco_complemento VARCHAR(20),
    endereco_logradouro VARCHAR(60) NOT NULL,
    endereco_numero VARCHAR(10) NOT NULL,
    status varchar(10) not null,
    subtotal DECIMAL(19 , 2 ) NOT NULL,
    taxa_frete DECIMAL(19 , 2 ) NOT NULL,
    valor_total DECIMAL(19 , 2 ) NOT NULL,
    usuario_cliente_id BIGINT NOT NULL,
    endereco_cidade_id BIGINT,
    forma_pagamento_id BIGINT NOT NULL,
    restaurante_id BIGINT NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB CHARSET=UTF8;

alter table item_pedido add constraint fk_item_pedido_pedido foreign key (pedido_id) references pedido (id);

alter table item_pedido add constraint fk_item_pedido_produto foreign key (produto_id) references produto (id);

alter table pedido add constraint fk_pedido_usuario_cliente foreign key (usuario_cliente_id) references usuario (id);

alter table pedido add constraint fk_pedido_cidade foreign key (endereco_cidade_id) references cidade (id);

alter table pedido add constraint fk_pedido_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id);

alter table pedido add constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurante (id);
