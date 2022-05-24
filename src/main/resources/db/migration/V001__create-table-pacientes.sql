create table pacientes
(
   
    prontuario      	bigint		PRIMARY KEY,
    nome            	varchar(60)  not null,
    dt_Nascimento       date  not null,
    sexo	           varchar(1)     not null,
    obito  		       varchar(1)		 not null,
    dt_obito       		date  null
  

);