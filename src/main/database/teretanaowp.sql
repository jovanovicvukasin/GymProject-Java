DROP SCHEMA IF EXISTS teretanaowp;
CREATE SCHEMA teretanaowp DEFAULT CHARACTER SET utf8;
USE teretanaowp;

CREATE TABLE tipTreninga (
	id integer AUTO_INCREMENT,
	ime VARCHAR(20) NOT NULL,
    opis VARCHAR(100) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE treninzi (
	id BIGINT AUTO_INCREMENT,
	naziv VARCHAR(20) NOT NULL,
	trener VARCHAR(20) NOT NULL,
	opis VARCHAR(100) NOT NULL,
	slika VARCHAR(150) NOT NULL,
	tipTreningaId INT NOT NULL DEFAULT 0,
	cena DOUBLE NOT NULL,
	vrstaTreninga VARCHAR(20) NOT NULL,
	nivoTreninga VARCHAR(20) NOT NULL,
    trajanje TIME NOT NULL,
    prosecnaOcena FLOAT NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (tipTreningaId) REFERENCES tipTreninga (id)
);

INSERT INTO tipTreninga (id, ime, opis) VALUES (1, 'Fitness', 'Funkcionalni trening.');
INSERT INTO tipTreninga (id, ime, opis) VALUES (2, 'Box', 'Trening koji nudi znacajno sagorevanje kalorija.');
INSERT INTO tipTreninga (id, ime, opis) VALUES (3, 'CrossFit', 'Trening snage, kondicije i izdrzljivosti.');
INSERT INTO tipTreninga (id, ime, opis) VALUES (4, 'Cardio', 'Trening za postizanje i odrzavanje kondicije.');
INSERT INTO tipTreninga (id, ime, opis) VALUES (5, 'Pilates', 'Trening manjeg opterecenja za celo telo.');
INSERT INTO tipTreninga (id, ime, opis) VALUES (6, 'Yoga', 'Trening koji opusta telo i misli.');
INSERT INTO tipTreninga (id, ime, opis) VALUES (7, 'Zumba', 'Najefektivniji cardio trening.');


INSERT INTO treninzi (id, naziv, trener, opis, slika, tipTreningaId, cena, vrstaTreninga, nivoTreninga, trajanje, prosecnaOcena) VALUES (1, 'Fitness trening', 'Ana Ancic', 'Trening je idealan za svakog pojedinca i za svaki fitness nivo.', 'images/fitness.jpg', 1, 1550.0, 'Grupni trening', 'Amaterski nivo', '01:30:00', 5.0);
INSERT INTO treninzi (id, naziv, trener, opis, slika, tipTreningaId, cena, vrstaTreninga, nivoTreninga, trajanje, prosecnaOcena) VALUES (2, 'Box trening', 'Milos Mikic', 'Trening je inspirisan borilackim vestinama idealan za svakog pojedinca.', 'images/box.jpg', 2, 2000.0, 'Pojedinacni trening', 'Srednji nivo', '01:00:00', 4.8);
INSERT INTO treninzi (id, naziv, trener, opis, slika, tipTreningaId, cena, vrstaTreninga, nivoTreninga, trajanje, prosecnaOcena) VALUES (3, 'CrossFit trening', 'Nikola Nikolic', 'Trening koji kombinuje nekoliko sportova idealan za iskusnije sportiste.', 'images/crossfit.jpg', 3, 2500.0, 'Grupni trening', 'Napredni nivo', '01:30:00', 5.0);
INSERT INTO treninzi (id, naziv, trener, opis, slika, tipTreningaId, cena, vrstaTreninga, nivoTreninga, trajanje, prosecnaOcena) VALUES (4, 'Cardio trening', 'Mina Minic', 'Trening je idealan za svakog pojedinca i za svaki fitness nivo.', 'images/kardio.jpg', 4, 1700.0, 'Pojedinacni trening', 'Amaterski nivo', '01:00:00', 4.0);
INSERT INTO treninzi (id, naziv, trener, opis, slika, tipTreningaId, cena, vrstaTreninga, nivoTreninga, trajanje, prosecnaOcena) VALUES (5, 'Pilates trening', 'Ema Emic', 'Trening je idealan za svakog pojedinca i za svaki fitness nivo.', 'images/pilates.jpg', 5, 1500.0, 'Grupni trening', 'Amaterski nivo', '01:15:00', 4.6);
INSERT INTO treninzi (id, naziv, trener, opis, slika, tipTreningaId, cena, vrstaTreninga, nivoTreninga, trajanje, prosecnaOcena) VALUES (6, 'Yoga trening', 'Tea Todorovic', 'Trening je idealan za svakog pojedinca i za svaki fitness nivo.', 'images/yoga.jpg', 6, 1900.0, 'Grupni trening', 'Srednji nivo', '01:00:00', 5.0);
INSERT INTO treninzi (id, naziv, trener, opis, slika, tipTreningaId, cena, vrstaTreninga, nivoTreninga, trajanje, prosecnaOcena) VALUES (7, 'Zumba trening', 'Nina Nikolic', 'Trening je idealan za svakog pojedinca i za svaki fitness nivo.', 'images/zumba.jpg', 7, 1900.0, 'Grupni trening', 'Srednji nivo', '01:00:00', 5.0);



