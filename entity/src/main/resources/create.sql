-- Eliminacion

drop table if exists bay_col.tbl_locations;
drop table if exists bay_col.tbl_rel_cust_emp;
drop table if exists bay_col.tbl_rel_cust_tbl_details;
drop table if exists bay_col.tbl_rel_emp_tbl_details;
drop table if exists bay_col.tbl_customers;
drop table if exists bay_col.tbl_users;
drop table if exists bay_col.tbl_details;
drop table if exists bay_col.tbl_master;
drop schema if exists bay_col;

create schema if not exists bay_col;

-- INICIA LA CREACIÓN DE TABLAS

-- tablas comunes
-- tipos de emeplados
-- estados
-- tipos de usuarios
create table if not exists bay_col.tbl_master (
	id				serial primary key not null,
	code			text UNIQUE not null,
	description		text,
	created			timestamp with time zone default current_timestamp
);

create table if not exists bay_col.tbl_details (
	id				serial primary key not null,
	code			text not null,
	name			text not null,
	description		text,
	id_master integer not null references bay_col.tbl_master(id),
	created			timestamp with time zone default current_timestamp,
	UNIQUE(code, id_master)
);

--Puede ser almacen o peluquería.
-- Se debe tener relacion con tbl_details
create table if not exists bay_col.tbl_customers (
	id				serial primary key not null,
	full_name		text not null,
	date_created	timestamp,
	username		text UNIQUE not null,
	password		text not null,
	email			text UNIQUE not null,
	created			timestamp with time zone default current_timestamp
);

create table if not exists bay_col.tbl_locations
(
   id      		serial primary key,
   country    	text,
   region    	text,
   city       	text,
   postalcode 	text,
   location   	point,
   metrocode  	text,
   areacode   	text,
   id_customer  integer not null references bay_col.tbl_customers(id),
   created		timestamp with time zone default current_timestamp
);

-- El usuario, puede ser un empleado en cualquier momento
-- si es empleado, se debe definir que tipo de empleado es: peluquero, uñas, etc.
-- Se debe tener relacion con tbl_details
create table if not exists bay_col.tbl_users (
	id				serial primary key not null,
	full_name		text not null,
	date_created	timestamp,
	username		text UNIQUE not null,
	password		text not null,
	email			text UNIQUE not null,
	id_gender		integer not null references bay_col.tbl_details(id),
	created			timestamp with time zone default current_timestamp
);

--Un cliente puede tener muchos empleados
create table if not exists bay_col.tbl_rel_cust_emp (
	id				serial primary key not null,
	id_customer  	integer not null references bay_col.tbl_customers(id),
	id_user		 	integer not null references bay_col.tbl_users(id),
	created			timestamp with time zone default current_timestamp,
	UNIQUE(id_customer, id_user)
);


create table if not exists bay_col.tbl_rel_cust_tbl_details (
	id				serial primary key not null,
	id_customer  	integer not null references bay_col.tbl_customers(id),
	id_detail		integer not null references bay_col.tbl_details(id),
	created			timestamp with time zone default current_timestamp,
	UNIQUE(id_customer, id_detail)
);


create table if not exists bay_col.tbl_rel_emp_tbl_details (
	id				serial primary key not null,
	id_user		 	integer not null references bay_col.tbl_users(id),
	id_detail		integer not null references bay_col.tbl_details(id),
	created			timestamp with time zone default current_timestamp,
	UNIQUE(id_user, id_detail)
);

-- FINALIZA LA CREACIÓN DE TABLAS

--INICIA LA CREACIÓN DE DATOS

	INSERT INTO bay_col.tbl_master (code, description) 
	select 'types_of_employees', 'Tipo de usuarios configurados para el sistema: Peluquero, barbero, etc.'
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_master m where m.code = 'types_of_employees'
	);

	INSERT INTO bay_col.tbl_details(code, name, description, id_master) 
	select 'peluquero', 'Peluquero(a)', 'Artesano del pelo', (select ID from bay_col.tbl_master m where m.code = 'types_of_employees')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'peluquero'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, description, id_master) 
	select 'barbero', 'Barbero(a)', 'Persona cuya ocupación es afeitar, cortar y acondicionar el pelo o cabello.', (select ID from bay_col.tbl_master m where m.code = 'types_of_employees')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'barbero'
	);

	INSERT INTO bay_col.tbl_details(code, name, description, id_master) 
	select 'coloraciones_capilares', 'Coloraciones capilares', 'Coloraciones capilares', (select ID from bay_col.tbl_master m where m.code = 'types_of_employees')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'coloraciones_capilares'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, description, id_master) 
	select 'posticeria', 'Posticería', 'Creación de distintas prótesis capilares: cubrir la falta del cabello, cambiar de imagen, caracterizar a un personaje, entre otros.', (select ID from bay_col.tbl_master m where m.code = 'types_of_employees')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'posticeria'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, description, id_master) 
	select 'manicura', 'Manicura', 'Tratamiento de belleza cosmético para las uñas y manos que suele realizarse en casa o en un salón de belleza.', (select ID from bay_col.tbl_master m where m.code = 'types_of_employees')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'manicura'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, description, id_master) 
	select 'pedicura', 'Pedicura', 'Tratamiento de las afecciones cutáneas córneas propias de los pies.​', (select ID from bay_col.tbl_master m where m.code = 'types_of_employees')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'pedicura'
	);
	
	
	INSERT INTO bay_col.tbl_master (code, description) 
	select 'types_of_customers', 'Tipo de clientes configurados para el sistema: Almacen, peluquería.'
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_master m where m.code = 'types_of_customers'
	);

	INSERT INTO bay_col.tbl_details(code, name, description, id_master) 
	select 'peluqueria', 'Peluquería', 'Punto de corte y tratamiento del cabello.', (select ID from bay_col.tbl_master m where m.code = 'types_of_customers')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'peluqueria'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, description, id_master) 
	select 'almacen', 'Almacen', 'Punto de distribución de artículos.', (select ID from bay_col.tbl_master m where m.code = 'types_of_customers')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'almacen'
	);
	
	
	
	
	INSERT INTO bay_col.tbl_master (code, description) 
	select 'genders', 'Géneros disponibles en el sistema.'
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_master m where m.code = 'genders'
	);

	INSERT INTO bay_col.tbl_details(code, name, description, id_master) 
	select 'masculino', 'Masculino', '', (select ID from bay_col.tbl_master m where m.code = 'genders')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'masculino'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, description, id_master) 
	select 'femenino', 'Femenino', '', (select ID from bay_col.tbl_master m where m.code = 'genders')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'femenino'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, description, id_master) 
	select 'otro', 'Otro', '', (select ID from bay_col.tbl_master m where m.code = 'genders')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'otro'
	);
	