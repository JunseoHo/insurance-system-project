USE nemne_insurance;

-- Initialize tables

create table if not exists CUSTOMERS
(
    id                        bigint auto_increment
        primary key,
    bank_account              varchar(100) not null,
    birth                     varchar(100) null,
    family_history            mediumtext   not null,
    gender                    tinyint(1)   null,
    health_examination_record mediumtext   not null,
    job                       varchar(255) null,
    name                      varchar(255) not null,
    customer_id               varchar(255) not null
)
    collate = utf8mb4_bin;

create table if not exists CONTRACTS
(
    id                    bigint auto_increment
        primary key,
    compensation_terms    text         not null,
    fee                   int          not null,
    name                  int          not null,
    rate                  float        null,
    terms_of_subscription text         not null,
    underwriting_report   varchar(255) null,
    customer_id           bigint       null,
    contract_id           varchar(255) not null,
    constraint CONTRACTS_customers_id_fk
        foreign key (customer_id) references CUSTOMERS (id)
            on update cascade on delete cascade
)
    collate = utf8mb4_bin;

create table if not exists EMPLOYEES
(
    id          bigint auto_increment
        primary key,
    birth       varchar(100) charset utf8mb4                                                              null,
    department  enum ('marketing', 'investigating', 'supporting', 'sales', 'development') charset utf8mb4 not null,
    gender      tinyint(1)                                                                                null,
    name        varchar(100) charset utf8mb4                                                              not null,
    employee_id varchar(255)                                                                              not null
)
    collate = utf8mb4_bin;

create table if not exists CLAIMS
(
    id           bigint auto_increment
        primary key,
    compensation int                                                     not null,
    customer_id  bigint                                                  not null,
    date         varchar(100)                                            not null,
    description  text                                                    null,
    employee_id  bigint                                                  null,
    is_paid      tinyint(1)                                              null,
    location     varchar(255)                                            not null,
    report       varchar(255)                                            null,
    reviewer     bigint                                                  null,
    status       enum ('reviewing', 'reporting', 'accepted', 'rejected') null,
    claim_id     varchar(255)                                            not null,
    constraint CLAIMS_customers_id_fk
        foreign key (customer_id) references CUSTOMERS (id)
            on update cascade on delete cascade,
    constraint claims_employees_id_fk
        foreign key (employee_id) references EMPLOYEES (id),
    constraint claims_employees_id_fk2
        foreign key (reviewer) references EMPLOYEES (id)
)
    collate = utf8mb4_bin;

create table if not exists insurance_development
(
    id                     bigint       not null
        primary key,
    product_name           varchar(255) null,
    target                 varchar(255) null,
    compensation_detail    text         null,
    rate                   int          null,
    profit_n_loss_analysis varchar(255) null
);


-- Add dummy data
INSERT INTO CUSTOMERS (bank_account, birth, family_history, gender, health_examination_record, job, name, customer_id) values('KB92938202-20-2938293', '1999-10-29', 'none', False, 'none', 'Student', 'Junseo', 'C001');

INSERT INTO EMPLOYEES (birth, department, gender, name, employee_id) values('1992-05-03', 'investigating', False, 'James', 'E001');
INSERT INTO EMPLOYEES (birth, department, gender, name, employee_id) values('1993-02-10', 'investigating', True, 'Nella', 'E002');
INSERT INTO EMPLOYEES (birth, department, gender, name, employee_id) values('1980-02-03', 'supporting', False, 'Paul', 'E003');
INSERT INTO EMPLOYEES (birth, department, gender, name, employee_id) values('1980-02-03', 'supporting', True, 'Emma', 'E004');

INSERT INTO CLAIMS (compensation, customer_id, date, description, employee_id, is_paid, location, report, reviewer, status, claim_id) values('3098345', 1, '2023-05-23', 'none', 1, False, 'seoul_korean', 'https://your-bucket-name.s3.amazonaws.com/randomString/report.docx', 2, 'reviewing', 'CLAIM_ID');