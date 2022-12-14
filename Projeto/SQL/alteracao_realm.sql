CREATE TABLE tb_cartao (
	id_cartao BIGINT AUTO_INCREMENT NOT NULL UNIQUE, 
    str_bandeira VARCHAR(255), 
    date_dataValidade DATE, 
    str_numero VARCHAR(255), 
    fk_cliente BIGINT, 
    PRIMARY KEY (id_cartao)
);
CREATE TABLE tb_usuario (id_usuario BIGINT AUTO_INCREMENT NOT NULL, disc_usuario VARCHAR(3), str_email VARCHAR(60) NOT NULL UNIQUE, str_login VARCHAR(60) NOT NULL UNIQUE, str_nome VARCHAR(60) NOT NULL, str_senha VARCHAR(16) NOT NULL, fk_endereco BIGINT, PRIMARY KEY (id_usuario));
CREATE TABLE tb_cliente (id_cliente BIGINT NOT NULL, PRIMARY KEY (id_cliente));
CREATE TABLE tb_consulta (id_consulta BIGINT AUTO_INCREMENT NOT NULL UNIQUE, disc_consulta VARCHAR(3), dat_dataMarcada DATE, str_status INTEGER, PRIMARY KEY (id_consulta));
CREATE TABLE tb_consulta_geral (id_consulta_geral BIGINT NOT NULL, fk_funcionario BIGINT, fk_servico BIGINT, PRIMARY KEY (id_consulta_geral));
CREATE TABLE tb_consulta_medica (id_consulta_medica BIGINT NOT NULL, str_diagnostico VARCHAR(400) NOT NULL, fk_exame BIGINT, fk_pet BIGINT, fk_veterinario BIGINT, PRIMARY KEY (id_consulta_medica));
CREATE TABLE tb_endereco (id_endereco BIGINT AUTO_INCREMENT NOT NULL, str_bairro VARCHAR(60) NOT NULL, str_cep VARCHAR(9) NOT NULL, str_complemento VARCHAR(60), str_logradouro VARCHAR(60) NOT NULL, int_numero INTEGER, PRIMARY KEY (id_endereco));
CREATE TABLE tb_exame (id_exame BIGINT AUTO_INCREMENT NOT NULL, str_descricao VARCHAR(100), str_nome VARCHAR(60), str_tipo VARCHAR(60), dbl_valor DOUBLE, PRIMARY KEY (id_exame));
CREATE TABLE tb_funcionario (id_funcionario BIGINT NOT NULL, enum_especialidadeFuncionario VARCHAR(255), PRIMARY KEY (id_funcionario));
CREATE TABLE tb_pet (id_pet BIGINT AUTO_INCREMENT NOT NULL, str_nome VARCHAR(60), boo_pedegree TINYINT(1) default 0 NOT NULL, flt_peso FLOAT NOT NULL, str_raca VARCHAR(60) NOT NULL, fk_cliente BIGINT, PRIMARY KEY (id_pet));
CREATE TABLE tb_servico (id_servico BIGINT AUTO_INCREMENT NOT NULL UNIQUE, str_nome VARCHAR(255) NOT NULL, dbl_valor DOUBLE NOT NULL, PRIMARY KEY (id_servico));
CREATE TABLE tb_Veterinario (id_veterinario BIGINT NOT NULL, str_crmv VARCHAR(60) NOT NULL UNIQUE, str_especialidade VARCHAR(60) NOT NULL, PRIMARY KEY (id_veterinario));



CREATE TABLE tb_grupo (
	id_grupo BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    str_nome VARCHAR(60)
);
CREATE TABLE tb_usuario_grupo (
	id_usuario BIGINT NOT NULL, 
    id_grupo BIGINT NOT NULL, 
    FOREIGN KEY (id_usuario) 
		REFERENCES tb_usuario(id_usuario),
	FOREIGN KEY (id_grupo)
		REFERENCES tb_grupo(id_grupo)
);

ALTER TABLE tb_usuario MODIFY COLUMN str_senha VARCHAR(100);
ALTER TABLE tb_usuario ADD str_sal VARCHAR(60);


ALTER TABLE tb_cartao ADD CONSTRAINT FK_tb_cartao_fk_cliente FOREIGN KEY (fk_cliente) REFERENCES tb_usuario (id_usuario);
ALTER TABLE tb_usuario ADD CONSTRAINT FK_tb_usuario_fk_endereco FOREIGN KEY (fk_endereco) REFERENCES tb_endereco (id_endereco);
ALTER TABLE tb_cliente ADD CONSTRAINT FK_tb_cliente_id_cliente FOREIGN KEY (id_cliente) REFERENCES tb_usuario (id_usuario);
ALTER TABLE tb_consulta_geral ADD CONSTRAINT FK_tb_consulta_geral_fk_funcionario FOREIGN KEY (fk_funcionario) REFERENCES tb_usuario (id_usuario);
ALTER TABLE tb_consulta_geral ADD CONSTRAINT FK_tb_consulta_geral_fk_servico FOREIGN KEY (fk_servico) REFERENCES tb_servico (id_servico);
ALTER TABLE tb_consulta_geral ADD CONSTRAINT FK_tb_consulta_geral_id_consulta_geral FOREIGN KEY (id_consulta_geral) REFERENCES tb_consulta (id_consulta);
ALTER TABLE tb_consulta_medica ADD CONSTRAINT FK_tb_consulta_medica_id_consulta_medica FOREIGN KEY (id_consulta_medica) REFERENCES tb_consulta (id_consulta);
ALTER TABLE tb_consulta_medica ADD CONSTRAINT FK_tb_consulta_medica_fk_exame FOREIGN KEY (fk_exame) REFERENCES tb_exame (id_exame);
ALTER TABLE tb_consulta_medica ADD CONSTRAINT FK_tb_consulta_medica_fk_veterinario FOREIGN KEY (fk_veterinario) REFERENCES tb_usuario (id_usuario);
ALTER TABLE tb_consulta_medica ADD CONSTRAINT FK_tb_consulta_medica_fk_pet FOREIGN KEY (fk_pet) REFERENCES tb_pet (id_pet);
ALTER TABLE tb_funcionario ADD CONSTRAINT FK_tb_funcionario_id_funcionario FOREIGN KEY (id_funcionario) REFERENCES tb_usuario (id_usuario);
ALTER TABLE tb_pet ADD CONSTRAINT FK_tb_pet_fk_cliente FOREIGN KEY (fk_cliente) REFERENCES tb_usuario (id_usuario);
ALTER TABLE tb_Veterinario ADD CONSTRAINT FK_tb_Veterinario_id_veterinario FOREIGN KEY (id_veterinario) REFERENCES tb_usuario (id_usuario);
