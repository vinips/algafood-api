CREATE TABLE restaurante_usuario_responsavel (
    restaurante_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    
    primary key (restaurante_id, usuario_id)
)  ENGINE=INNODB CHARSET=UTF8;

alter table restaurante_usuario_responsavel add constraint fk_restaurante_usuario_responsavel_usuario foreign key (usuario_id) references usuario (id);

alter table restaurante_usuario_responsavel add constraint fk_restaurante_usuario_responsavel_restaurante foreign key (restaurante_id) references restaurante (id);