--begin
insert into UTILISATEUR values ('paul.dupont@gmail.com', 'Dupont', 'Paul', '1 rue des jardins');
insert into UTILISATEUR values ('pierre.durand@gmail.com', 'Durand', 'Pierre', '1 rue de lepic');

insert into CATEGORIE values ('Vins', 'A deguster avec moderation');
insert into CATEGORIE values ('Tableaux', 'On ne bride pas un artiste');

insert into SALLE values (1, 'Tableaux');
insert into SALLE values (2, 'Vins');
insert into SALLE values (3, 'Vins');
insert into SALLE values (4, 'Tableaux');
insert into SALLE values (5, 'Vins');

insert into PRODUIT values (1, 'La Joconde', 1000000, 1, 'Tableaux', 1);
insert into PRODUIT values (2, 'Le cri', 1000000, 1, 'Tableaux', 4);
insert into PRODUIT values (3, 'Vin de Bourgogne', 50, 200, 'Vins', 2);
insert into PRODUIT values (4, 'Beaujolais', 50, 200, 'Vins', 3);
insert into PRODUIT values (5, 'Champagne', 60, 100, 'Vins', 5);

insert into CARACTERISTIQUE values ('Musee', 'Le Louvre', 1);
insert into CARACTERISTIQUE values ('Musee', 'Munch', 2);
insert into CARACTERISTIQUE values ('Région', 'Bourgogne', 3);
insert into CARACTERISTIQUE values ('Région', 'Rhone-Alpes', 4);
insert into CARACTERISTIQUE values ('Région', 'Champagne-Ardennes', 5);

insert into VENTE (id_vente, prix_depart_vente, id_salle) values (1, 50, 2);
insert into VENTE (id_vente, prix_depart_vente, id_salle, unicite_enchere) values (2, 60, 3, 'unique');
insert into VENTE (id_vente, prix_depart_vente, id_salle, sens_vente) values (3, 1000000, 1, 'descendante');
insert into VENTE (id_vente, prix_depart_vente, id_salle, annulation_vente) values (4, 1000000, 4, 'revocable');
insert into VENTE (id_vente, prix_depart_vente, id_salle, duree_vente, date_fin) values (5, 50, 5, 'limitee', timestamp '2018-11-26 10:20:00');

insert into ENCHERE values (1, 60, timestamp '2018-11-26 10:20:00', 5, 1);
insert into ENCHERE values (2, 65, timestamp '2018-11-26 08:00:00', 5, 2);
insert into ENCHERE values (3, 1500000, timestamp '2018-11-26 10:20:00', 1, 3);
insert into ENCHERE values (4, 1500000, timestamp '2018-11-26 08:00:00', 1, 4);
insert into ENCHERE values (5, 55, timestamp '2018-11-26 06:00:00', 5, 5);

insert into RENTRE_DANS values ('paul.dupont@gmail.com', 1);
insert into RENTRE_DANS values ('pierre.durand@gmail.com', 2);
insert into RENTRE_DANS values ('pierre.durand@gmail.com', 3);
insert into RENTRE_DANS values ('paul.dupont@gmail.com', 4);
insert into RENTRE_DANS values ('paul.dupont@gmail.com', 5);

insert into PRODUIT_SOUMIS_A_LA_VENTE values (1, 3, 'paul.dupont@gmail.com');
insert into PRODUIT_SOUMIS_A_LA_VENTE values (2, 4, 'paul.dupont@gmail.com');
insert into PRODUIT_SOUMIS_A_LA_VENTE values (3, 1, 'pierre.durand@gmail.com');
insert into PRODUIT_SOUMIS_A_LA_VENTE values (4, 2, 'pierre.durand@gmail.com');
insert into PRODUIT_SOUMIS_A_LA_VENTE values (5, 5, 'paul.dupont@gmail.com');

insert into ENCHERE_PROPOSEE values (1, 1, 'pierre.durand@gmail.com');
insert into ENCHERE_PROPOSEE values (2, 2, 'pierre.durand@gmail.com');
insert into ENCHERE_PROPOSEE values (3, 3, 'paul.dupont@gmail.com');
insert into ENCHERE_PROPOSEE values (4, 4, 'paul.dupont@gmail.com');
insert into ENCHERE_PROPOSEE values (5, 5, 'paul.dupont@gmail.com');

insert into SALLE_DE_VENTE_CREEE values (1, 1, 3);
insert into SALLE_DE_VENTE_CREEE values (4, 2, 4);
insert into SALLE_DE_VENTE_CREEE values (2, 3, 1);
insert into SALLE_DE_VENTE_CREEE values (3, 4, 2);
insert into SALLE_DE_VENTE_CREEE values (5, 5, 5);

--end;
