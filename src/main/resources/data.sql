-- data.sql CORRIGIDO (Versão One-to-Many para Curso/Professor)

-- 1. Teachers (IDs 1, 2, 3)
INSERT INTO TBL_TEACHER (name) VALUES ('Professor João');
INSERT INTO TBL_TEACHER (name) VALUES ('Professora Maria');
INSERT INTO TBL_TEACHER (name) VALUES ('Professor Carlos');

-- 2. Students (IDs 1, 2, 3, 4)
INSERT INTO TBL_STUDENT (name) VALUES ('Aluno Pedro');
INSERT INTO TBL_STUDENT (name) VALUES ('Aluna Ana');
INSERT INTO TBL_STUDENT (name) VALUES ('Aluno Lucas');
INSERT INTO TBL_STUDENT (name) VALUES ('Aluna Sofia');

-- 3. Courses (Corrigido: 'name' no lugar de 'title' e 'teacher_id' adicionado)
-- Course 1 (Professor João - id=1)
INSERT INTO TBL_COURSE (name, teacher_id) VALUES ('Spring Boot Avançado', 1);

-- Course 2 (Professor Carlos - id=3)
INSERT INTO TBL_COURSE (name, teacher_id) VALUES ('Introdução a SQL', 3);

-- Course 3 (Professora Maria - id=2)
INSERT INTO TBL_COURSE (name, teacher_id) VALUES ('Desenvolvimento Web', 2);


-- 4. TBL_COURSE_STUDENT (Relacionamento Many-to-Many) - OK
-- Course 1 (Spring Boot) com Pedro (id=1) e Ana (id=2)
INSERT INTO TBL_COURSE_STUDENT (course_id, student_id) VALUES (1, 1);
INSERT INTO TBL_COURSE_STUDENT (course_id, student_id) VALUES (1, 2);

-- Course 3 (Desenvolvimento Web) com todos os 4 estudantes
INSERT INTO TBL_COURSE_STUDENT (course_id, student_id) VALUES (3, 1);
INSERT INTO TBL_COURSE_STUDENT (course_id, student_id) VALUES (3, 2);
INSERT INTO TBL_COURSE_STUDENT (course_id, student_id) VALUES (3, 3);
INSERT INTO TBL_COURSE_STUDENT (course_id, student_id) VALUES (3, 4);

-- **Atenção:** A tabela TBL_COURSE_TEACHER foi removida pois a coluna 'teacher_id' já existe em TBL_COURSE.