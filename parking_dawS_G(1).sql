-- drop database parkingdawS_G;
CREATE database parkingdawS_G;
use parkingdawS_G;

drop table if exists clientes;
create table clientes
(
	matricula char(8),
	dni char(9),
    nombre varchar(10),
    apellido varchar(20),
    apellido2 varchar(20),
    numTarjetaCredito char(16),
    tipoAbono enum('mensual','trimestral','semestral','anual'),
    email varchar(30),
    constraint pk_clientes primary key(matricula)

);

drop table if exists vehiculos;
create table vehiculos
(
    matricula char(8),
    tipoVehiculo enum('turismos','motocicletas','caravanas'),
    descripcion varchar(60),
    constraint pk_vehiculo primary key (matricula)

) ;
drop table if exists plazas;
create table plazas
(
	numplaza int,
    tipoPlaza enum('plaza_turismos','plaza_motocicletas','plaza_caravanas'),
    estadoplaza enum('ocupada','libre','libre_abono','ocupada_abono'),
	tarifa decimal,
    constraint pk_plazas primary key(numplaza)
);
drop table if exists reservas;
create table reservas
(	
	matricula char(8),
    numplaza int,
    pin_fijo char(6) unique,
    feciniabono date,
    fecfinabono date,
    constraint Pk_reservas primary key(matricula,numplaza),
    constraint fk_reservas_clientes foreign key(matricula)references clientes (matricula)
		on delete no action on update cascade,
	constraint fk_reservas_plazas foreign key(numplaza)references plazas (numplaza)
		on delete no action on update cascade

);

drop table if exists ticket;
create table ticket
(
	numplaza int,
	matricula char(8),
    pin_desechable char(6),
    fecinipin date,
    fecfinpin date,
    constraint pk_gestionPines primary key(numplaza,matricula,fecinipin),
	constraint fk_gestionPines_plazas foreign key(numplaza)references plazas(numplaza)
		on delete no action on update cascade,
	constraint fk_gestionPines_vehiculos foreign key(matricula)references vehiculos(matricula)
		on delete no action on update cascade
);






