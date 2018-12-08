--begin
--create database baie_electronique;

create table UTILISATEUR (
  email_utilisateur character varying(50) not null,
  nom_utilisateur character varying(30),
  prenom_utilisateur character varying(30),
  adresse_postale_utilisateur character varying(50),
  primary key (email_utilisateur)
);

create table CATEGORIE (
  nom_categorie character varying(30) not null,
  description_categorie character varying(60),
  primary key (nom_categorie)
);

create table SALLE (
  id_salle int not null,
  nom_categorie character varying(30) not null,
  primary key (id_salle),
  foreign key (nom_categorie) references CATEGORIE(nom_categorie)
);

create table PRODUIT (
  id_produit int not null,
  nom_produit character varying(30),
  prix_revient_produit int,
  stock_produit int,
  nom_categorie character varying(30) not null,
  id_salle int not null,
  primary key (id_produit),
  foreign key (nom_categorie) references CATEGORIE(nom_categorie),
  foreign key (id_salle) references SALLE(id_salle),
  constraint CHK_Prod check (prix_revient_produit >= 0 and stock_produit > 0)
);

create table CARACTERISTIQUE (
  nom_caracteristique character varying(30) not null,
  description_caracteristique character varying(60),
  id_produit int not null,
  primary key (nom_caracteristique, id_produit), -- l'id produit est dans la clé (Done)
  foreign key (id_produit) references PRODUIT(id_produit)
);

-- Done
-- alter table CARACTERISTIQUE drop primary key;
-- alter table CARACTERISTIQUE add primary key (nom_caracteristique, id_produit);

create table VENTE (
  id_vente int not null,
  prix_depart_vente int check (prix_depart_vente > 0),
  date_debut_vente timestamp not null default current_timestamp,
  id_salle int not null,
  unicite_enchere character varying(30) default 'plusieurs',
  sens_vente character varying(30) default 'montante',
  annulation_vente character varying(30) default 'non_revocable',
  duree_vente character varying(30) default 'non_limitee',
  date_fin timestamp null,
  primary key (id_vente),
  foreign key (id_salle) references SALLE(id_salle),
  constraint CHK_Typ check (unicite_enchere in ('unique', 'plusieurs') and sens_vente in ('descendante', 'montante') and annulation_vente in ('revocable', 'non_revocable') and duree_vente in ('limitee', 'non_limitee'))
);
-- constraint CHK_Date check ((duree_vente = 'non_limitee' AND date_fin = null) OR (duree_vente = 'limitee' AND date_fin != null))

create table ENCHERE (
  num_enchere int not null,
  prix_enchere int,
  date_enchere timestamp,
  quantite int,
  id_vente int not null,
  primary key (num_enchere),
  foreign key (id_vente) references VENTE(id_vente),
  constraint CHK_Ench check (prix_enchere > 0 and quantite > 0)
);

create table RENTRE_DANS (
  email_utilisateur character varying(30) not null,
  id_salle int not null,
  primary key (email_utilisateur, id_salle),
  foreign key (email_utilisateur) references UTILISATEUR(email_utilisateur),
  foreign key (id_salle) references SALLE(id_salle)
);

create table PRODUIT_SOUMIS_A_LA_VENTE (
  id_produit int not null,
  id_vente int not null,
  email_utilisateur character varying(30) not null,
  primary key (id_produit, id_vente, email_utilisateur),
  foreign key (id_produit) references PRODUIT(id_produit),
  foreign key (id_vente) references VENTE(id_vente),
  foreign key (email_utilisateur) references UTILISATEUR(email_utilisateur)
);

create table ENCHERE_PROPOSEE (
  num_enchere int not null,
  id_vente int not null,
  email_utilisateur character varying(30) not null,
  primary key (num_enchere, id_vente, email_utilisateur),
  foreign key (num_enchere) references ENCHERE(num_enchere),
  foreign key (id_vente) references VENTE(id_vente),
  foreign key (email_utilisateur) references UTILISATEUR(email_utilisateur)
);

create table SALLE_DE_VENTE_CREEE (
  id_salle int not null,
  id_produit int not null,
  id_vente int not null,
  primary key (id_salle, id_produit, id_vente),
  foreign key (id_salle) references SALLE(id_salle),
  foreign key (id_produit) references PRODUIT(id_produit),
  foreign key (id_vente) references VENTE(id_vente)
);

--end;
/

-- l'application java gère l'unicité des types de ventes présentes dans chaque salle
-- java gère la contrainte de validité d'enchère, ie quantité <= stock_produit and prix_enchere >= prix_depart_vente and date_enchere >= date_debut_vente

-- Done
-- dans la relation enchere : rajouter l'id_vente
-- dans la relation vente : rajouter l'id salle
