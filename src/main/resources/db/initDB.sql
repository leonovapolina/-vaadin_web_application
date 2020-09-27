CREATE TABLE IF NOT EXISTS Patients (
    id BIGINT IDENTITY PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    lastName VARCHAR(32) NOT NULL,
    patronymic VARCHAR(32) NOT NULL,
    phone VARCHAR(16) UNIQUE
);

CREATE TABLE IF NOT EXISTS Doctors (
    id BIGINT IDENTITY PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    lastName VARCHAR(32) NOT NULL,
    patronymic VARCHAR(32) NOT NULL,
    specialization VARCHAR(64) NOT NULL
);

CREATE  TABLE IF NOT EXISTS RecipePriority (
    id BIGINT IDENTITY PRIMARY KEY,
    priority VARCHAR(32) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Recipes (
    id BIGINT IDENTITY PRIMARY KEY,
    doctorId BIGINT FOREIGN KEY REFERENCES Doctors,
    patientId BIGINT FOREIGN KEY REFERENCES Patients,
    description VARCHAR(512) NOT NULL,
    creationDate DATE DEFAULT current_date NOT NULL,
    validity INTEGER NOT NULL,
    priorityId BIGINT FOREIGN KEY REFERENCES RecipePriority
);

INSERT INTO RECIPEPRIORITY
    (PRIORITY)
VALUES
    ('NORMAL'),
    ('CITO'),
    ('STATIM');

INSERT INTO PATIENTS
    (NAME, LASTNAME, PATRONYMIC, PHONE)
VALUES
    ('Инна', 'Шилова', 'Александровна', '89603521489'),
    ('Анна', 'Петрова', 'Владимировна', '89532154789'),
    ('Иван', 'Иванов', 'Иванович', '89173254695'),
    ('Петр', 'Петров', 'Петрович', '89063215874'),
    ('Александр', 'Куделкин', 'Олегович', '89271456988');

INSERT INTO DOCTORS
    (NAME, LASTNAME, PATRONYMIC, SPECIALIZATION)
VALUES
    ('Петр', 'Макаров', 'Дмитриевич', 'Терапевт'),
    ('Сергей', 'Борисов', 'Олегович', 'Терапевт'),
    ('Александр', 'Попов', 'Сергеевич', 'Хирург'),
    ('Сергей', 'Николаев', 'Владимирович', 'Офтальмолог'),
    ('Виталий', 'Пугачев', 'Абрамович', 'Невролог');

INSERT INTO RECIPES
	(DOCTORID, PATIENTID, DESCRIPTION, CREATIONDATE, VALIDITY, PRIORITYID)
VALUES
	(0, 0, 'Обезболивающее, принимать на ночь 5 дней', '2020-09-15', 30, 0),
    (0, 1, 'Антибиотик, 2 раза в день, 7 дней', '2020-09-10', 7, 1),
    (4, 2, 'Седативное, 2 раза в день, 2 месяца', '2020-09-15', 60, 2),
    (3, 2, 'Капли глазные, 3 раза в день, 30 дней', '2020-09-17', 30, 0),
    (2, 3, 'Витамины кальций, принимать 1 месяц', '2020-09-17', 30, 0);