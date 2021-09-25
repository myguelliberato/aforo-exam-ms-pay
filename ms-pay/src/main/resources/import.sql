
 
create table operacion(
	id_operation int auto_increment not null,
	id_invoice int,
	amount numeric(17,2),
	date timestamp,
	constraint pk_operacion primary key (id_operation)
);

 