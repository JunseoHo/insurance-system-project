USE nemne_insurance;

-- Initialize tables

create table if not exists CUSTOMERS
(
    customer_id               varchar(255) primary key,
    bank_account              varchar(100) not null,
    birth                     varchar(100) null,
    family_history            mediumtext   not null,
    gender                    tinyint(1)   null,
    health_examination_record mediumtext   not null,
    job                       varchar(255) null,
    name                      varchar(255) not null
    )
    collate = utf8mb4_bin;

create table if not exists EMPLOYEES
(
    employee_id varchar(255)                                                                                                                 primary key,
    birth       varchar(100) charset utf8mb4                                                                                                 null,
    department  enum ('marketing', 'investigating', 'supporting', 'sales', 'development', 'contractManager', 'underwriting') charset utf8mb4 not null,
    gender      tinyint(1)                                                                                                                   null,
    name        varchar(100) charset utf8mb4                                                                                                 not null
    )
    collate = utf8mb4_bin;

create table if not exists CLAIMS
(
    claim_id     varchar(255)                                            primary key,
    compensation int                                                     not null,
    customer_id  varchar(255)                                                  not null,
    date         varchar(100)                                            not null,
    description  text                                                    null,
    employee_id  varchar(255)                                                  null,
    type         varchar(100)                                            null,
    location     varchar(255)                                            not null,
    report       varchar(255)                                            null,
    reviewer     varchar(255)                                                  null,
    status       enum ('reviewing', 'reporting', 'accepted', 'rejected', 'paid') null
)
    collate = utf8mb4_bin;

create table if not exists PRODUCTS
(
    product_id             varchar(255) primary key,
    name                   varchar(255) null,
    target                 varchar(255) null,
    compensation_detail    text         null,
    rate                   float          null,
    profit_n_loss_analysis text         null,
    premiums               int          null
    )
    collate = utf8mb4_bin;

create table if not exists CONTRACTS
(
    contract_id           varchar(255) primary key,
    compensation_terms    text         not null,
    fee                   int          not null,
    product_id            varchar(255)       null,
    rate                  float        null,
    terms_of_subscription text         not null,
    is_underwriting       tinyint(1)   null,
    customer_id           varchar(255)       null,
    premium               int          null,
    constraint CONTRACTS_PRODUCT_id_fk
    foreign key (product_id) references PRODUCTS (product_id),
    constraint CONTRACTS_customers_id_fk
    foreign key (customer_id) references CUSTOMERS (customer_id)
    on update cascade on delete cascade
    )
    collate = utf8mb4_bin;




-- Add dummy data
INSERT INTO CUSTOMERS (bank_account, birth, family_history, gender, health_examination_record, job, name, customer_id) values('KB92938202-20-2938293', '1999-10-29', 'none', False, 'none', 'Student', 'Junseo', 'C001');

INSERT INTO EMPLOYEES (birth, department, gender, name, employee_id) values('1992-05-03', 'investigating', False, 'James', 'E001');
INSERT INTO EMPLOYEES (birth, department, gender, name, employee_id) values('1993-02-10', 'investigating', True, 'Nella', 'E002');
INSERT INTO EMPLOYEES (birth, department, gender, name, employee_id) values('1980-02-03', 'supporting', False, 'Paul', 'E003');
INSERT INTO EMPLOYEES (birth, department, gender, name, employee_id) values('1980-02-03', 'supporting', True, 'Emma', 'E004');

INSERT INTO CLAIMS (type, compensation, customer_id, date, description, employee_id, location, report, reviewer, status, claim_id) values('none', '3098345', 'C001', '2023-05-23', 'none', 'E001', 'seoul_korean', 'https://your-bucket-name.s3.amazonaws.com/randomString/report.docx', 'E002', 'reviewing', 'CLAIM_ID');