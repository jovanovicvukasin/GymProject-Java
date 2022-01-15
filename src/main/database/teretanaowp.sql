DROP SCHEMA IF EXISTS teretanaowp;
CREATE SCHEMA teretanaowp DEFAULT CHARACTER SET utf8;
USE teretanaowp;

CREATE TABLE tipoviTreninga (
	id BIGINT AUTO_INCREMENT,
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
	cena DOUBLE NOT NULL,
	vrstaTreninga ENUM('pojedinacni', 'grupni') DEFAULT 'pojedinacni',
	nivoTreninga ENUM('amaterski', 'srednji', 'napredni') DEFAULT 'amaterski',
    trajanje TIME NOT NULL,
    prosecnaOcena FLOAT NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE treningTipTreninga (
    treningId BIGINT,
    tipTreningaId BIGINT,
    PRIMARY KEY(treningId, tipTreningaId),
    FOREIGN KEY(treningId) REFERENCES treninzi(id)
		ON DELETE CASCADE,
    FOREIGN KEY(tipTreningaId) REFERENCES tipoviTreninga(id)
		ON DELETE CASCADE
);

INSERT INTO tipoviTreninga (id, ime, opis) VALUES (1, 'Fitness', 'Funkcionalni trening.');
INSERT INTO tipoviTreninga (id, ime, opis) VALUES (2, 'Cardio', 'Trening za postizanje i odrzavanje kondicije.');
INSERT INTO tipoviTreninga (id, ime, opis) VALUES (3, 'Yoga', 'Trening koji opusta telo i misli.');


INSERT INTO treninzi (id, naziv, trener, opis, slika, cena, vrstaTreninga, nivoTreninga, trajanje, prosecnaOcena) VALUES (1, 'CardioBox trening', 'Milos Mikic', 'Trening je inspirisan borilackim vestinama idealan za svakog pojedinca.', 'images/box.jpg', 2000.0, 'pojedinacni', 'srednji', '01:00:00', 4.8);
INSERT INTO treninzi (id, naziv, trener, opis, slika, cena, vrstaTreninga, nivoTreninga, trajanje, prosecnaOcena) VALUES (2, 'Pilates trening', 'Ema Emic', 'Trening je idealan za svakog pojedinca i za svaki fitness nivo.', 'images/pilates.jpg', 1500.0, 'grupni', 'amaterski', '01:15:00', 4.6);
INSERT INTO treninzi (id, naziv, trener, opis, slika, cena, vrstaTreninga, nivoTreninga, trajanje, prosecnaOcena) VALUES (3, 'Yoga trening', 'Tea Todorovic', 'Trening je idealan za svakog pojedinca i za svaki fitness nivo.', 'images/yoga.jpg', 2000.0, 'grupni', 'napredni', '01:00:00', 5.0);
INSERT INTO treninzi (id, naziv, trener, opis, slika, cena, vrstaTreninga, nivoTreninga, trajanje, prosecnaOcena) VALUES (4, 'Zumba trening', 'Nina Nikolic', 'Trening je idealan za svakog pojedinca i za svaki fitness nivo.', 'images/zumba.jpg', 1900.0, 'grupni', 'srednji', '01:00:00', 5.0);

INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (1, 2);
INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (2, 1);
INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (3, 3);
INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (4, 1);
INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (4, 2);

