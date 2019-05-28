 drop database parkingdawS_G;
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
    constraint pk_vehiculo primary key (matricula)

) ;
drop table if exists plazas;
create table plazas
(
	numplaza int,
    tipoPlaza enum('plaza_turismos','plaza_motocicletas','plaza_caravanas'),
    estadoplaza enum('ocupada','libre','libre_abono','ocupada_abono'),
	tarifa decimal(7,2),
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

drop table if exists tickets;
create table tickets
(
	numplaza int,
	matricula char(8),
    pin_desechable char(6),
    fecinipin date,
    fecfinpin date,
	horaenticket time,
    horasalticket time, 
    precio decimal(7,2), 
    constraint pk_gestionPines primary key(numplaza,matricula,fecinipin,horaenticket),
	constraint fk_gestionPines_plazas foreign key(numplaza)references plazas(numplaza)
		on delete no action on update cascade,
	constraint fk_gestionPines_vehiculos foreign key(matricula)references vehiculos(matricula)
		on delete no action on update cascade
);

insert into plazas
(numplaza,tipoPlaza,estadoplaza,tarifa)
values
(100,'plaza_motocicletas','libre',0.08),
(101,'plaza_motocicletas','libre',0.08),
(102,'plaza_motocicletas','libre',0.08),
(103,'plaza_motocicletas','libre',0.08),
(104,'plaza_motocicletas','libre',0.08),
(105,'plaza_motocicletas','libre',0.08),
(106,'plaza_motocicletas','libre',0.08),
(107,'plaza_motocicletas','libre',0.08),
(108,'plaza_motocicletas','libre',0.08),
(109,'plaza_motocicletas','libre',0.08),
(110,'plaza_motocicletas','libre',0.08),
(111,'plaza_motocicletas','libre',0.08),
(112,'plaza_motocicletas','libre',0.08),
(113,'plaza_motocicletas','libre',0.08),
(114,'plaza_motocicletas','libre',0.08),
(115,'plaza_turismos','libre',0.12),
(116,'plaza_turismos','libre',0.12),
(117,'plaza_turismos','libre',0.12),
(118,'plaza_turismos','libre',0.12),
(119,'plaza_turismos','libre',0.12),
(120,'plaza_turismos','libre',0.12),
(121,'plaza_turismos','libre',0.12),
(122,'plaza_turismos','libre',0.12),
(123,'plaza_turismos','libre',0.12),
(124,'plaza_turismos','libre',0.12),
(125,'plaza_turismos','libre',0.12),
(126,'plaza_turismos','libre',0.12),
(127,'plaza_turismos','libre',0.12),
(128,'plaza_turismos','libre',0.12),
(129,'plaza_turismos','libre',0.12),
(130,'plaza_caravanas','libre',0.45),
(131,'plaza_caravanas','libre',0.45),
(132,'plaza_caravanas','libre',0.45),
(133,'plaza_caravanas','libre',0.45),
(134,'plaza_caravanas','libre',0.45),
(135,'plaza_caravanas','libre',0.45),
(136,'plaza_caravanas','libre',0.45),
(137,'plaza_caravanas','libre',0.45),
(138,'plaza_caravanas','libre',0.45),
(139,'plaza_caravanas','libre',0.45),
(140,'plaza_caravanas','libre',0.45),
(141,'plaza_caravanas','libre',0.45),
(142,'plaza_caravanas','libre',0.45),
(143,'plaza_caravanas','libre',0.45),
(144,'plaza_caravanas','libre',0.45);

