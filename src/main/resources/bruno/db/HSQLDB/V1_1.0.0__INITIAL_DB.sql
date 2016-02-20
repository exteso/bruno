create table b_user(
    id integer identity not null, 
    username varchar(255) not null, 
    password varchar(2048) not null,
    first_name varchar(255) not null, 
    last_name varchar(255) not null, 
    email_address varchar(255) not null,
    user_type varchar(64) not null -- enum: consumer/service provider/admin (or role based?)
);

create table b_intervention_request(
    id integer identity not null,
    creation_time timestamp  not null,
    creation_user_fk integer not null,
    location varchar(2048) not null,
    description varchar(4096) not null,
    request_type varchar(64) not null -- enum: urgent_request, quote_request (preventivo), ... 
);
alter table b_intervention_request add foreign key(creation_user_fk) references b_user(id);

create table b_intervention_request_attachments(
    id integer identity not null,
    intervention_request_fk integer not null,
    -- attachments : photo, video, additional comments, ...
);
alter table b_intervention_request_attachments add foreign key(intervention_request_fk) references b_intervention_request(id);

create table b_intervention_request_responder(
    intervention_request_fk integer not null,
    user_fk integer not null
    -- other fields: state, date, price (?) etc...
);
alter table b_intervention_request_responder add foreign key(intervention_request_fk) references b_intervention_request(id);
alter table b_intervention_request_responder add foreign key(user_fk) references b_user(id);

create table b_configuration(
    id integer identity not null,
    c_key varchar(255) not null,
    c_value varchar(2048) not null,
    description varchar(2048)
);
alter table b_configuration add constraint "unique_b_configuration_c_key" unique(c_key);