-- ############################################# 
-- 1. inserção de professores (teachers)
--    colunas: (name, email)
-- #############################################

insert into tb_teacher (name, email) values ('carlos silva', 'carlos@fatec.edu');
insert into tb_teacher (name, email) values ('marina souza', 'marina@fatec.edu');
insert into tb_teacher (name, email) values ('pedro santos', 'pedro@fatec.edu');

-- ids gerados: 1, 2, 3

-- #############################################
-- 2. inserção de estudantes (students)
--    colunas: (name, email)
-- #############################################

insert into tb_student (name, email) values ('isabela ferreira', 'isa@email.com');
insert into tb_student (name, email) values ('bruna oliveira', 'bru@email.com');
insert into tb_student (name, email) values ('guilherme costa', 'gui@email.com');

-- ids gerados: 1, 2, 3

-- #############################################
-- 3. inserção de cursos (courses)
--    colunas: (name)
-- #############################################

insert into tb_course (name) values ('engenharia de software');
insert into tb_course (name) values ('banco de dados');
insert into tb_course (name) values ('sistemas operacionais');

-- ids gerados: 1, 2, 3

-- #############################################
-- 4. estabelecimento de relacionamentos many-to-many
-- #############################################

-- tabela: course_teacher (liga cursos a professores)
-- padrão: (course_id, teacher_id)

insert into course_teacher (course_id, teacher_id) values (1, 1);
insert into course_teacher (course_id, teacher_id) values (1, 2);
insert into course_teacher (course_id, teacher_id) values (2, 3);
insert into course_teacher (course_id, teacher_id) values (3, 1);

-- tabela: course_student (liga cursos a estudantes)
-- padrão: (course_id, student_id)

insert into course_student (course_id, student_id) values (1, 1);
insert into course_student (course_id, student_id) values (1, 2);
insert into course_student (course_id, student_id) values (2, 1);
insert into course_student (course_id, student_id) values (3, 2);
insert into course_student (course_id, student_id) values (3, 3);
