create table b_user(
    id integer identity not null,
    provider varchar(12) not null,
    username varchar(128) not null, 
    first_name varchar(255), 
    last_name varchar(255), 
    email_address varchar(255),
    user_type varchar(64), -- enum: CUSTOMER/SERVICE_PROVIDER/ADMIN
    user_request_type varchar(64) default null,
    user_request_type_date timestamp default null,
    company_name varchar(256),
    company_field_of_work integer,
    company_address varchar(512),
    company_phone_number varchar(64),
    company_email varchar(512),
    company_vat_id varchar(128),
    company_notes varchar(1024)
);
alter table b_user add constraint "unique_b_user" unique(provider, username);

create table b_job_request(
    id integer identity not null,
    creation_time timestamp  not null,
    creation_user_fk integer not null,
    address varchar(2048) not null,
    urgent boolean not null,
    fault_type varchar(64) not null,
    description varchar(4096) not null,
    request_type varchar(64) not null, -- enum: urgent_request, quote_request (preventivo), ...
    request_state varchar(64) not null -- enum: open, assigned, processed
);
alter table b_job_request add foreign key(creation_user_fk) references b_user(id);

create table b_job_request_bid(
    job_request_fk integer not null,
    user_fk integer not null,
    price integer,
    selected_date timestamp,
    accepted boolean,
    creation_time timestamp,
    last_update timestamp,
    -- other fields: state, date, price (?) etc...
);
alter table b_job_request_bid add foreign key(job_request_fk) references b_job_request(id);
alter table b_job_request_bid add foreign key(user_fk) references b_user(id);
alter table b_job_request_bid add constraint "unique_bid" unique(job_request_fk, user_fk);

create table b_configuration(
    id integer identity not null,
    c_key varchar(255) not null,
    c_value varchar(2048) not null,
    description varchar(2048)
);
alter table b_configuration add constraint "unique_b_configuration_c_key" unique(c_key);


create table b_file_upload(
    file_id integer identity not null,
    file_hash char(64) not null,
    file_name varchar(512) not null,
    file_content_type varchar(255) not null,
    file_path varchar(512),
    file_upload_date timestamp default sysdate not null,
    file_user_id_fk integer not null
);
alter table b_file_upload add constraint "unique_b_file_upload_file_hash" unique(file_hash);
alter table b_file_upload add foreign key(file_user_id_fk) references b_user(id);

create table b_job_request_attachments(
    file_id_fk integer not null,
    job_request_fk integer not null,
    primary key (file_id_fk, job_request_fk)
);
alter table b_job_request_attachments add foreign key(job_request_fk) references b_job_request(id);
alter table b_job_request_attachments add foreign key(file_id_fk) references b_file_upload(file_id);

