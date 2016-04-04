create table b_user(
    id integer identity not null,
    provider varchar(512) not null,
    username varchar(512) not null, 
    first_name varchar(255), 
    last_name varchar(255), 
    email_address varchar(255),
    user_type varchar(64) -- enum: CUSTOMER/SERVICE_PROVIDER/ADMIN
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

create table b_job_request_attachments(
    id integer identity not null,
    job_request_fk integer not null,
    -- attachments : photo, video, additional comments, ...
);
alter table b_job_request_attachments add foreign key(job_request_fk) references b_job_request(id);

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