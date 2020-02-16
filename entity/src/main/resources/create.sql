-- Eliminacion
drop table if exists bay_col.tbl_tokens;
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

--create extension if not exists cube;
--create extension if not exists earthdistance;

-- INICIA LA CREACIÓN DE TABLAS

-- tablas comunes
-- tipos de emeplados
-- estados
-- tipos de usuarios
create table if not exists bay_col.tbl_master (
	id				serial primary key not null,
	code			text UNIQUE not null,
	description		text,
	created			timestamp with time zone not null default current_timestamp
);

create table if not exists bay_col.tbl_details (
	id				serial primary key not null,
	code			text not null,
	name			text not null,
	value			text not null,
	description		text,
	id_master 		integer not null references bay_col.tbl_master(id),
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
	status			text not null,
	email			text UNIQUE not null,
	phone			text UNIQUE not null,
	created			timestamp with time zone default current_timestamp
);

create table if not exists bay_col.tbl_locations
(
   id      		serial primary key,
   country    	text,
   region    	text,
   city       	text,
   postalcode 	text,
   longitude   	float not null,
   latitude   	float not null,
   metrocode  	text,
   areacode   	text,
   id_customer  integer not null references bay_col.tbl_customers(id),
   created		timestamp with time zone default current_timestamp
);

create table if not exists bay_col.tbl_tokens
(
   id      		serial primary key,
   token    	text not null,
   expiry_date  timestamp with time ZONE  CHECK (expiry_date > created)  not null,
   status		text  not null,
   type_token	text  not null,
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

	INSERT INTO bay_col.tbl_details(code, name, value, description, id_master) 
	select 'peluquero', 'Peluquero(a)', '', 'Artesano del pelo', (select ID from bay_col.tbl_master m where m.code = 'types_of_employees')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'peluquero'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, value,  description, id_master) 
	select 'barbero', 'Barbero(a)', '',  'Persona cuya ocupación es afeitar, cortar y acondicionar el pelo o cabello.', (select ID from bay_col.tbl_master m where m.code = 'types_of_employees')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'barbero'
	);

	INSERT INTO bay_col.tbl_details(code, name, value,  description, id_master) 
	select 'coloraciones_capilares', 'Coloraciones capilares', '',  'Coloraciones capilares', (select ID from bay_col.tbl_master m where m.code = 'types_of_employees')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'coloraciones_capilares'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, value,  description, id_master) 
	select 'posticeria', 'Posticería', '',  'Creación de distintas prótesis capilares: cubrir la falta del cabello, cambiar de imagen, caracterizar a un personaje, entre otros.', (select ID from bay_col.tbl_master m where m.code = 'types_of_employees')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'posticeria'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, value,  description, id_master) 
	select 'manicura', 'Manicura', '',  'Tratamiento de belleza cosmético para las uñas y manos que suele realizarse en casa o en un salón de belleza.', (select ID from bay_col.tbl_master m where m.code = 'types_of_employees')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'manicura'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, value,  description, id_master) 
	select 'pedicura', 'Pedicura', '',  'Tratamiento de las afecciones cutáneas córneas propias de los pies.​', (select ID from bay_col.tbl_master m where m.code = 'types_of_employees')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'pedicura'
	);
	
	
	INSERT INTO bay_col.tbl_master (code, description) 
	select 'types_of_customers', 'Tipo de clientes configurados para el sistema: Almacen, peluquería.'
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_master m where m.code = 'types_of_customers'
	);

	INSERT INTO bay_col.tbl_details(code, name, value,  description, id_master) 
	select 'peluqueria', 'Peluquería', '',  'Punto de corte y tratamiento del cabello.', (select ID from bay_col.tbl_master m where m.code = 'types_of_customers')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'peluqueria'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, value,  description, id_master) 
	select 'almacen', 'Almacen', '',  'Punto de distribución de artículos.', (select ID from bay_col.tbl_master m where m.code = 'types_of_customers')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'almacen'
	);
	
	
	
	
	INSERT INTO bay_col.tbl_master (code, description) 
	select 'genders', 'Géneros disponibles en el sistema.'
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_master m where m.code = 'genders'
	);

	INSERT INTO bay_col.tbl_details(code, name, value,  description, id_master) 
	select 'masculino', 'Masculino', '',  '', (select ID from bay_col.tbl_master m where m.code = 'genders')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'masculino'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, value,  description, id_master) 
	select 'femenino', 'Femenino', '',  '', (select ID from bay_col.tbl_master m where m.code = 'genders')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'femenino'
	);
	
	INSERT INTO bay_col.tbl_details(code, name, value,  description, id_master) 
	select 'otro', 'Otro',  '', '', (select ID from bay_col.tbl_master m where m.code = 'genders')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'otro'
	);
	
	
	INSERT INTO bay_col.tbl_master (code, description) 
	select 'systems', 'Variables del sustema.'
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_master m where m.code = 'systems'
	);

	INSERT INTO bay_col.tbl_details(code, name,  value, description, id_master) 
	select 'expiry_token', 'expiry_token', '30', '', (select ID from bay_col.tbl_master m where m.code = 'systems')
	WHERE NOT EXISTS (
    	SELECT 1 from bay_col.tbl_details m where m.code = 'expiry_token'
	);
	
	
	
	---https://www.geodatasource.com/developers/postgresql
	/*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::                                                                         :*/
/*::  This routine calculates the distance between two points (given the     :*/
/*::  latitude/longitude of those points). It is being used to calculate     :*/
/*::  the distance between two locations using GeoDataSource(TM) Products    :*/
/*::                                                                         :*/
/*::  Definitions:                                                           :*/
/*::    South latitudes are negative, east longitudes are positive           :*/
/*::                                                                         :*/
/*::  Passed to function:                                                    :*/
/*::    lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)  :*/
/*::    lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)  :*/
/*::    unit = the unit you desire for results                               :*/
/*::           where: 'M' is statute miles (default)                         :*/
/*::                  'K' is kilometers                                      :*/
/*::                  'N' is nautical miles                                  :*/
/*::  Worldwide cities and other features databases with latitude longitude  :*/
/*::  are available at https://www.geodatasource.com                         :*/
/*::                                                                         :*/
/*::  For enquiries, please contact sales@geodatasource.com                  :*/
/*::                                                                         :*/
/*::  Official Web site: https://www.geodatasource.com                       :*/
/*::                                                                         :*/
/*::  Thanks to Kirill Bekus for contributing the source code.               :*/
/*::                                                                         :*/
/*::         GeoDataSource.com (C) All Rights Reserved 2019                  :*/
/*::                                                                         :*/
/*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/

CREATE OR REPLACE FUNCTION calculate_distance(lat1 float, lon1 float, lat2 float, lon2 float, units varchar)
RETURNS float AS $dist$
    DECLARE
        dist float = 0;
        radlat1 float;
        radlat2 float;
        theta float;
        radtheta float;
    BEGIN
        IF lat1 = lat2 OR lon1 = lon2
            THEN RETURN dist;
        ELSE
            radlat1 = pi() * lat1 / 180;
            radlat2 = pi() * lat2 / 180;
            theta = lon1 - lon2;
            radtheta = pi() * theta / 180;
            dist = sin(radlat1) * sin(radlat2) + cos(radlat1) * cos(radlat2) * cos(radtheta);

            IF dist > 1 THEN dist = 1; END IF;

            dist = acos(dist);
            dist = dist * 180 / pi();
            dist = dist * 60 * 1.1515;

            IF units = 'K' THEN dist = dist * 1.609344; END IF;
            IF units = 'N' THEN dist = dist * 0.8684; END IF;

            RETURN dist;
        END IF;
    END;
$dist$ LANGUAGE plpgsql;

SELECT calculate_distance(32.9697, -96.80322, 29.46786, -98.53506, 'M');