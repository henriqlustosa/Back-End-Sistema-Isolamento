create table pacientes
(
    id              	SERIAL      PRIMARY KEY,
    prontuario      	bigint		 not null,
    nome            	varchar(60)  not null,
    vinculo            	varchar(60)  not null,
    orgao_prefeitura	varchar(60)  not null,
    rf_matricula  		bigint		 not null,
    nome_mae       		varchar(60)  not null,
  	data_nascimento 	date         not null
  

);