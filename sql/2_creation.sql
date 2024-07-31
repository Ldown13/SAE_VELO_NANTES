USE bd_velo_4b2;

DROP TABLE IF EXISTS COMPTAGE;
DROP TABLE IF EXISTS COMPTEUR;
DROP TABLE IF EXISTS DATEINFO;
DROP TABLE IF EXISTS QUARTIER;

CREATE TABLE QUARTIER(
		idQuartier INT PRIMARY KEY,
		nomQuartier VARCHAR(50),
        longueurPisteVelo FLOAT
);
     
CREATE TABLE COMPTEUR(
		idCompteur INT PRIMARY KEY,
        nomCompteur VARCHAR(50),
        sens VARCHAR(50),
		coord_X FLOAT,
        coord_Y FLOAT,
        leQuartier INT,
		FOREIGN KEY (leQuartier) REFERENCES QUARTIER(idQuartier)
);

CREATE TABLE DATEINFO(
		laDate DATE PRIMARY KEY,
		tempMoy FLOAT,
        jour ENUM('Lundi','Mardi','Mercredi','Jeudi','Vendredi','Samedi','Dimanche'),
		vacances VARCHAR(20)
);

CREATE TABLE COMPTAGE(
		leCompteur INT,
		FOREIGN KEY (leCompteur) REFERENCES COMPTEUR(idCompteur),
        dateComptage DATE,
		FOREIGN KEY (dateComptage) REFERENCES DATEINFO(laDate),
		h00 INT,
        h01 INT,
        h02 INT,
        h03 INT,
        h04 INT,
        h05 INT,
        h06 INT,
        h07 INT,
        h08 INT,
        h09 INT,
        h10 INT,
        h11 INT,
        h12 INT,
        h13 INT,
        h14 INT,
        h15 INT,
        h16 INT,
        h17 INT,
        h18 INT,
        h19 INT,
        h20 INT,
        h21 INT,
        h22 INT,
        h23 INT,
        presenceAnomalie ENUM('Forte','Faible'),
        
        PRIMARY KEY (leCompteur,dateComptage)
);
