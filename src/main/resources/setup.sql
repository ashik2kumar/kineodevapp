drop schema if exists e3test;
create schema e3test;
use e3test;

CREATE TABLE company (
company_id INT AUTO_INCREMENT NOT NULL,
name VARCHAR(100),
PRIMARY KEY (company_id)
) ENGINE = InnoDB ROW_FORMAT = DEFAULT;

CREATE TABLE employee (
employee_id INT AUTO_INCREMENT NOT NULL,
first_name VARCHAR(100),
last_name VARCHAR(100),
company_id INT NOT NULL,
PRIMARY KEY (employee_id),
FOREIGN KEY (company_id) REFERENCES company(company_id)
) ENGINE = InnoDB ROW_FORMAT = DEFAULT;

insert into company values (1, 'Kineo');
insert into company values (2, 'Hungry Jacks');

insert into employee values (1, 'Joe', 'Jones',1);
insert into employee values (2, 'Bob', 'Brown',1);
insert into employee values (3, 'Annie', 'Armstrong',2);
insert into employee values (4, 'Susan', 'Smith',2);

commit;

select * from employee;
select * from company;