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
    precio decimal(7,2),
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

/*PROBAR COPIA DE SEGURIDAD*/

/*insert into clientes
(matricula,dni,nombre,apellido,apellido2,numTarjetaCredito,tipoAbono,email)
values
('1234-LKJ','12345678K','Paco', 'Perez', 'Medina' ,'1234567899876543','mensual','pepe@gmail.com'),
('1235-DKJ','12345678Y','Paco2', 'Perez', 'Medina' ,'1234567899876543','mensual','pepe@gmail.com'),
('1236-JKJ','12345678S','Paco3', 'Perez', 'Medina' ,'1234567899876543','mensual','pepe@gmail.com');

insert into reservas
(matricula,numplaza,pin_fijo,feciniabono,fecfinabono,precio)
values
('1234-LKJ', 106, 698532, '2019-05-26', '2019-06-26',25),
('1235-DKJ', 108, 888888, '2019-05-26', '2019-06-26',70),
('1236-JKJ', 107, 777777, '2019-05-26', '2019-06-26',130);

insert into vehiculos
(matricula,tipoVehiculo)
values
('9874-JSJ','turismos'),
('8874-JPJ','turismos'),
('1074-JDJ','turismos');

-- SELECT * FROM parkingdawS_G.tickets;

delete from tickets;

INSERT INTO `parkingdawS_G`.`tickets` 
(`numplaza`, `matricula`, `pin_desechable`, `fecinipin`, `fecfinpin`, `horaenticket`, `horasalticket`, `precio`) 
VALUES
('100', '1074-JDJ', '987498', '2019-05-26', '2019-05-31', '01:00:50', '05:30:44', '2.6');

-- INSERT INTO `parkingdawS_G`.`tickets` (`numplaza`, `matricula`, `pin_desechable`, `fecinipin`, `fecfinpin`, `horaenticket`, `horasalticket`, `precio`) VALUES ('101', '8874-JPJ', '111111', '2019-05-27', '2019-05-30', '01:00:00', '06:00:00', '3.00');
-- INSERT INTO `parkingdawS_G`.`tickets` (`numplaza`, `matricula`, `pin_desechable`, `fecinipin`, `fecfinpin`, `horaenticket`, `horasalticket`, `precio`) VALUES ('103', '9874-JJJ', '987498', '2019-05-25', '2019-05-26', '02:00:00', '02:00:00', '4.00');

INSERT INTO `parkingdawS_G`.`tickets` 
(`numplaza`, `matricula`, `pin_desechable`, `fecinipin`, `fecfinpin`, `horaenticket`, `horasalticket`, `precio`) 
VALUES
('101', '1074-JDJ', '111111', '2019-05-26', '2019-05-30', '01:02:50', '05:30:44', '2.6');
INSERT INTO `parkingdawS_G`.`tickets` 
(`numplaza`, `matricula`, `pin_desechable`, `fecinipin`, `fecfinpin`, `horaenticket`, `horasalticket`, `precio`) 
VALUES
('102', '1074-JDJ', '000000', '2019-05-26', '2019-05-29', '01:03:50', '05:30:44', '2.6');*/



/*INSERT DE RESERVA Y CLIENTE PARA PROBAR métodos: CADUCA_EN_UN_MES  Y CADUCA_EN_10_DIAS*/
/*insert into clientes
(matricula,dni,nombre,apellido,apellido2,numTarjetaCredito,tipoAbono,email)  
values
('5555-TTT','12345678K', 'Paco','Perez','Martin','0000000000000001','mensual','paco1@gmail.com'),
('1478-POI','12345678Y', 'Paco2','Perez2','Martin2','0000000000000002','mensual','paco2@gmail.com'),
('1234-LLL','12345678D', 'Paco3','Perez3','Martin3','0000000000000003','mensual','paco3@gmail.com'),
('7895-LLL','12345678A', 'Paco4','Perez4','Martin4','0000000000000003','anual','paco3@gmail.com'),
('7894-LLL','12345678E', 'Paco5','Perez5','Martin5','0000000000000004','trimestral','paco3@gmail.com'),  
('9632-LLL','12345678R', 'Paco6','Perez6','Martin6','0000000000000005','trimestral','paco3@gmail.com'),  
('8741-LLL','12345678T', 'Paco7','Perez7','Martin7','0000000000000006','mensual','paco3@gmail.com');  
  

insert into reservas
(matricula,numplaza,pin_fijo,feciniabono,fecfinabono)
values
('5555-TTT',100,123654,'2019-05-26','2019-06-26'),
('1478-POI',101,223444,'2019-05-27','2019-06-27'),
('1234-LLL',102,323444,'2019-05-27','2019-06-27'),
('7895-LLL',103,423444,'2019-05-27','2020-05-27'),
('7894-LLL',104,523654,'2019-05-26','2019-09-30'),
('9632-LLL',105,623444,'2019-05-27','2019-09-01'),
('8741-LLL',106,777888,'2019-05-05','2019-06-05');-- PARA PROBAR CADUCA_10_DIAS;
*/

/*insert clientes
(matricula,dni,nombre,apellido,apellido2,numTarjetaCredito,tipoAbono,email)
values
('1234-LKJ','12345678K','Paco', 'Perez', 'Medina' ,'1234567899876543','mensual','pepe@gmail.com');

insert reservas
(matricula,numplaza,pin_fijo,feciniabono,fecfinabono)
values
('1234-LKJ', 106, 698532, '2019-05-26', '2019-06-26');

select * from clientes;
select * from reservas;

delete from clientes where matricula= '1234-LKJ';*/