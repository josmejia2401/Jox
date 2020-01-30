create scheme if not exists bay;

--tablas comunes
create table if not exists bay.tbl_common_master (
	id				serial primary key not null,
	code			text not null,
	description		text,
	created			timestamp with time zone default current_timestamp
);

create table if not exists bay.tbl_common_details (
	id				serial primary key not null,
	code			text not null,
	name			text not null,
	description		text,
	id_tbl_c_master integer not null references tbl_common_master(id),
	created			timestamp with time zone default current_timestamp
);

create table if not exists bay.tbl_types_of_customers (
	id				serial primary key not null,
	code			text not null,
	description		text,
	created			timestamp with time zone default current_timestamp
);

create table if not exists bay.types_of_employees (
	id				serial primary key not null,
	code			text not null,
	description		timestamp,
	id_employee   	integer not null references employees(id),
	created			timestamp with time zone default current_timestamp
);

create table if not exists bay.tbl_customers (
	id				serial primary key not null,
	full_name		text not null,
	date_of_birth	timestamp,
	username		text not null,
	password		text not null,
	email			text not null,
	created			timestamp with time zone default current_timestamp
);

create table if not exists bay.locations
(
   id      		serial primary key,
   country    	text,
   region    	text,
   city       	text,
   postalcode 	text,
   location   	point,
   metrocode  	text,
   areacode   	text,
   id_customer  integer not null references bay.tbl_customers(id),
   created		timestamp with time zone default current_timestamp
);

-- Los empleados pertenecen a muchas customers
create table if not exists bay.tbl_employees (
	id				serial primary key not null,
	full_name		text not null,
	date_of_birth	timestamp,
	username		text not null,
	password		text not null,
	email			text not null,
	id_customer   	integer not null references users(id),
	created			timestamp with time zone default current_timestamp
);



create table if not exists bay.tbl_rel_cust_tcust (
	id				serial primary key not null,
	id_customer   	integer not null references bay.tbl_customers(id),
	id_t_customer  	integer not null references tbl_bay.tbl_types_of_customers(id),
	created			timestamp with time zone default current_timestamp
);

create table if not exists bay.tbl_rel_cust_empl (
	id				serial primary key not null,
	id_customer   	integer not null references bay.tbl_customers(id),
	id_employee  	integer not null references tbl_bay.tbl_employees(id),
	created			timestamp with time zone default current_timestamp
);
