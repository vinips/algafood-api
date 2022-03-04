CREATE TABLE forma_pagamento (
    id BIGINT NOT NULL AUTO_INCREMENT,
    descricao VARCHAR(60) NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB CHARSET=UTF8;

CREATE TABLE grupo (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB CHARSET=UTF8;

CREATE TABLE grupo_permissao (
    grupo_id BIGINT NOT NULL,
    permissao_id BIGINT NOT NULL
)  ENGINE=INNODB CHARSET=UTF8;

CREATE TABLE permissao (
    id BIGINT NOT NULL AUTO_INCREMENT,
    descricao VARCHAR(80) NOT NULL,
    nome VARCHAR(60) NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB CHARSET=UTF8;

CREATE TABLE produto (
    id BIGINT NOT NULL AUTO_INCREMENT,
    ativo BIT NOT NULL,
    descricao VARCHAR(80) NOT NULL,
    nome VARCHAR(60) NOT NULL,
    preco DECIMAL(19 , 2 ) NOT NULL,
    restaurante_id BIGINT NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB CHARSET=UTF8;

CREATE TABLE restaurante (
    id BIGINT NOT NULL AUTO_INCREMENT,
    data_atualizacao DATETIME NOT NULL,
    data_cadastro DATETIME NOT NULL,
    endereco_bairro VARCHAR(80),
    endereco_cep VARCHAR(20),
    endereco_complemento VARCHAR(20),
    endereco_logradouro VARCHAR(60),
    endereco_numero VARCHAR(10),
    nome VARCHAR(60) NOT NULL,
    taxa_frete DECIMAL(19 , 2 ) NOT NULL,
    cozinha_id BIGINT NOT NULL,
    endereco_cidade_id BIGINT,
    PRIMARY KEY (id)
)  ENGINE=INNODB CHARSET=UTF8;

CREATE TABLE restaurante_forma_pagamento (
    restaurante_id BIGINT NOT NULL,
    forma_pagamento_id BIGINT NOT NULL
)  ENGINE=INNODB CHARSET=UTF8;

CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    data_cadastro DATETIME NOT NULL,
    email VARCHAR(60) NOT NULL,
    nome VARCHAR(60) NOT NULL,
    senha VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB CHARSET=UTF8;

CREATE TABLE usuario_grupo (
    usuario_id BIGINT NOT NULL,
    grupo_id BIGINT NOT NULL
)  ENGINE=INNODB CHARSET=UTF8;

alter table grupo_permissao add constraint fk_grupo_permissao_permissao foreign key (permissao_id) references permissao (id);

alter table grupo_permissao add constraint fk_grupo_permissao_grupo foreign key (grupo_id) references grupo (id);

alter table produto add constraint fk_produto_restaurante foreign key (restaurante_id) references restaurante (id);

alter table restaurante add constraint fk_restaurante_cozinha foreign key (cozinha_id) references cozinha (id);

alter table restaurante add constraint fk_restaurante_cidade foreign key (endereco_cidade_id) references cidade (id);

alter table restaurante_forma_pagamento add constraint fk_restaurante_forma_pagamento_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id);

alter table restaurante_forma_pagamento add constraint fk_restaurante_forma_pagamento_restaurante foreign key (restaurante_id) references restaurante (id);

alter table usuario_grupo add constraint fk_usuario_grupo_grupo foreign key (grupo_id) references grupo (id);

alter table usuario_grupo add constraint fk_usuario_grupo_usuario foreign key (usuario_id) references usuario (id);