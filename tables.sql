create table UTILISATEUR (
  email_utilisateur character varying(30) not null,
  nom_utilisateur character varying(30),
  prenom_utilisateur character varying(30),
  adresse_postale_utilisateur character varying(30),
  primary key (email_utilisateur)
);

create table SALLE (
  id_salle int not null,
  nom_categorie character varying(30) not null,
  primary key (id_salle)
);

create table PRODUIT (
  id_produit int not null,
  nom_produit character varying(30),
  prix_revient_produit int check(prix_revient_produit >= 0),
  stock_produit int check(stock_produit > 0),
  nom_categorie character varying(30) not null,
  id_salle int not null,
  primary key (id_produit)
);

create table ENCHERE (
  num_enchere int not null,
  prix_enchere int check(prix_enchere > 0),
  date_enchere date,
  heure_enchere time,
  quantite int check(quantite > 0),
  primary key (num_enchere)
);

create table VENTE (
  id_vente int not null,
  prix_depart_vente int check (prix_depart_vente > 0),
  id_salle int not null,
  unicite_enchere character varying(30) check (unicite_enchere in ('unique', 'plusieurs')) default 'plusieurs',
  sens_vente character varying(30) check (sens_vente in ('descendante', 'montante')) default 'montante',
  annulation_vente character varying(30) check (annulation_vente in ('revocable', 'non_revocable')) default 'non_revocable',
  duree_vente character varying(30) check (duree_vente in ('limitee', 'non_limitee')) default 'non_limitee',
  date_fin date null check((duree_vente = 'non_limitee' AND date_fin = null) OR (duree_vente = 'limitee' AND date_fin != null)),
  heure_fin time null check((duree_vente = 'non_limitee' AND heure_fin = null) OR (duree_vente = 'limitee' AND heure_fin != null)),
  primary key (id_vente)
);

create table CATEGORIE (
  nom_categorie character varying(30) not null,
  description_categorie character varying(30),
  primary key (nom_categorie)
);

create table CARACTERISTIQUE (
  nom_caracteristique character varying(30) not null,
  description_caracteristique character varying(30),
  id_produit int not null,
  primary key (nom_caracteristique)
);

-- create table UNIQUE (
--   id_vente int not null,
--   primary key (id_vente),
--   foreign key (id_vente) references VENTE(id_vente)
-- );
--
-- create table DESCENDANTE (
--   id_vente int not null,
--   primary key (id_vente),
--   foreign key (id_vente) references VENTE(id_vente)
-- );
--
-- create table REVOCABLE (
--   id_vente int not null,
--   primary key (id_vente),
--   foreign key (id_vente) references VENTE(id_vente)
-- );
--
-- create table DUREE_LIMITEE (
--   id_vente int not null,
--   date_fin date,
--   heure_fin time,
--   primary key (id_vente),
--   foreign key (id_vente) references VENTE(id_vente)
-- );

create table RENTRE_DANS (
  email_utilisateur character varying(30) not null,
  id_salle int not null,
  primary key (email_utilisateur, id_salle) references UTILISATEUR(email_utilisateur), SALLE(id_salle)
);

create table PRODUIT_SOUMIS_A_LA_VENTE (
  id_produit int not null,
  id_vente int not null,
  email_utilisateur character varying(30) not null,
  primary key (id_produit, id_vente, email_utilisateur) references PRODUIT(id_produit), VENTE(id_vente), UTILISATEUR(email_utilisateur)
);

create table ENCHERE_PROPOSEE (
  num_enchere int not null,
  id_vente int not null,
  email_utilisateur character varying(30) not null,
  primary key (num_enchere, id_vente, email_utilisateur) references ENCHERE(num_enchere), VENTE(id_vente), UTILISATEUR(email_utilisateur)
);

create table SALLE_DE_VENTE_CREEE (
  id_salle int not null,
  id_produit int not null,
  id_vente int not null,
  primary key (id_salle, id_produit, id_vente) references SALLE(id_salle), PRODUIT(id_produit), VENTE(id_vente)
);

-- create table ENCHERE_UNIQUE_PROPOSEE (
--   num_enchere int not null,
--   id_vente int not null,
--   email_utilisateur character varying(30) not null,
--   primary key (num_enchere, id_vente, email_utilisateur) references ENCHERE(num_enchere), VENTE(id_vente), UTILISATEUR(email_utilisateur)
-- );
