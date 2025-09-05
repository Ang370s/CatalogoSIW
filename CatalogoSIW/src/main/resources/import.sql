-- Sequenze
ALTER SEQUENCE prodotto_seq INCREMENT BY 1 RESTART WITH 1;
ALTER SEQUENCE utente_seq INCREMENT BY 1 RESTART WITH 1;
ALTER SEQUENCE credentials_seq INCREMENT BY 1 RESTART WITH 1;
ALTER SEQUENCE commento_seq INCREMENT BY 1 RESTART WITH 1;

-- UTENTI
INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Admin', 'System', 'admin@gmail.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Mario', 'Rossi', 'mario@gmail.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Luigi', 'Verdi', 'luigi@gmail.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Giulia', 'Bianchi', 'giulia@gmail.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Paolo', 'Neri', 'paolo@gmail.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Sara', 'Gialli', 'sara@gmail.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Francesco', 'Rossi', 'francesco@gmail.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Elena', 'Ferrari', 'elena@gmail.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Andrea', 'Russo', 'andrea@gmail.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Martina', 'Romano', 'martina@gmail.com');

-- CREDENTIALS
INSERT INTO credentials (id, username, password, role, utente_id) VALUES (nextval('credentials_seq'), 'admin', '$2a$10$QsQS93zMmOeRVf6baRoCCe0ERCN4lWmOHwI0E3dXPm95rYb354cpa', 'ADMIN', 1);
INSERT INTO credentials (id, username, password, role, utente_id) VALUES (nextval('credentials_seq'), 'mario', '$2a$10$QsQS93zMmOeRVf6baRoCCe0ERCN4lWmOHwI0E3dXPm95rYb354cpa', 'USER', 2);
INSERT INTO credentials (id, username, password, role, utente_id) VALUES (nextval('credentials_seq'), 'luigi', '$2a$10$QsQS93zMmOeRVf6baRoCCe0ERCN4lWmOHwI0E3dXPm95rYb354cpa', 'USER', 3);
INSERT INTO credentials (id, username, password, role, utente_id) VALUES (nextval('credentials_seq'), 'giulia', '$2a$10$QsQS93zMmOeRVf6baRoCCe0ERCN4lWmOHwI0E3dXPm95rYb354cpa', 'USER', 4);
INSERT INTO credentials (id, username, password, role, utente_id) VALUES (nextval('credentials_seq'), 'paolo', '$2a$10$QsQS93zMmOeRVf6baRoCCe0ERCN4lWmOHwI0E3dXPm95rYb354cpa', 'USER', 5);
INSERT INTO credentials (id, username, password, role, utente_id) VALUES (nextval('credentials_seq'), 'sara', '$2a$10$QsQS93zMmOeRVf6baRoCCe0ERCN4lWmOHwI0E3dXPm95rYb354cpa', 'USER', 6);
INSERT INTO credentials (id, username, password, role, utente_id) VALUES (nextval('credentials_seq'), 'francesco', '$2a$10$QsQS93zMmOeRVf6baRoCCe0ERCN4lWmOHwI0E3dXPm95rYb354cpa', 'USER', 7);
INSERT INTO credentials (id, username, password, role, utente_id) VALUES (nextval('credentials_seq'), 'elena', '$2a$10$QsQS93zMmOeRVf6baRoCCe0ERCN4lWmOHwI0E3dXPm95rYb354cpa', 'USER', 8);
INSERT INTO credentials (id, username, password, role, utente_id) VALUES (nextval('credentials_seq'), 'andrea', '$2a$10$QsQS93zMmOeRVf6baRoCCe0ERCN4lWmOHwI0E3dXPm95rYb354cpa', 'USER', 9);
INSERT INTO credentials (id, username, password, role, utente_id) VALUES (nextval('credentials_seq'), 'martina', '$2a$10$QsQS93zMmOeRVf6baRoCCe0ERCN4lWmOHwI0E3dXPm95rYb354cpa', 'USER', 10);

-- PRODOTTI
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Cesoie da giardino', 19.99, 'Ottime per potare rose e arbusti', 'Giardinaggio');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Romanzo fantasy', 12.50, 'Un emozionante viaggio tra draghi e magie', 'Libri');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Cesoie professionali', 29.99, 'Cesoie da giardiniere professionista', 'Giardinaggio');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Romanzo giallo', 10.99, 'Un thriller avvincente con colpi di scena', 'Libri');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Rastrello da giardino', 15.50, 'Per raccogliere foglie e erba tagliata', 'Giardinaggio');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Romanzo storico', 14.75, 'Un viaggio nel tempo attraverso epoche passate', 'Libri');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Annaffiatoio', 12.99, 'Capacità 10 litri, con beccuccio regolabile', 'Giardinaggio');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Romanzo rosa', 9.99, 'Una storia d''amore appassionante', 'Libri');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Guanti da giardinaggio', 8.50, 'Resistenti e comodi, taglia unica', 'Giardinaggio');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Romanzo horror', 11.25, 'Terrorizzante e pieno di suspense', 'Libri');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Vaso per piante', 7.99, 'Diametro 25cm, in terracotta', 'Giardinaggio');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Romanzo avventura', 13.50, 'Un''epica avventura in terre lontane', 'Libri');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Concime universale', 9.99, 'Per tutte le piante, 5kg', 'Giardinaggio');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Romanzo biografico', 16.75, 'La vita di un personaggio famoso', 'Libri');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Paletta da giardino', 6.50, 'Per trapiantare piante e fiori', 'Giardinaggio');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Romanzo distopico', 12.99, 'Un futuro oscuro e inquietante', 'Libri');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Tagliaerba elettrico', 149.99, 'Tagliaerba a filo, facile da usare', 'Giardinaggio');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Romanzo fantasy epico', 18.50, 'Una saga fantasy in più volumi', 'Libri');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Semi per prato', 4.99, 'Miscela di semi per prato inglese', 'Giardinaggio');
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Romanzo thriller', 14.99, 'Pieno di suspense e colpi di scena', 'Libri');

-- COMMENTI
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Prodotto fantastico!', CURRENT_DATE, 2, 1);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Bellissima storia!', CURRENT_DATE, 2, 2);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Molto utili per il giardinaggio', CURRENT_DATE, 3, 1);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Avvincente dalla prima all''ultima pagina', CURRENT_DATE, 4, 2);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Qualità eccellente, consigliate', CURRENT_DATE, 5, 3);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Finale a sorpresa, bellissimo!', CURRENT_DATE, 6, 4);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Robusto e affidabile', CURRENT_DATE, 7, 5);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Scrittura coinvolgente', CURRENT_DATE, 8, 6);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Prattico e comodo da usare', CURRENT_DATE, 9, 7);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Storia d''amore commovente', CURRENT_DATE, 10, 8);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Proteggono bene le mani', CURRENT_DATE, 2, 9);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Davvero spaventoso!', CURRENT_DATE, 3, 10);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Di buona qualità, resistente', CURRENT_DATE, 4, 11);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Avventura emozionante', CURRENT_DATE, 5, 12);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Effetto visibile sulle piante', CURRENT_DATE, 6, 13);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Biografia interessante', CURRENT_DATE, 7, 14);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Utile per i trapianti', CURRENT_DATE, 8, 15);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Visione del futuro inquietante', CURRENT_DATE, 9, 16);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Taglia erba perfettamente', CURRENT_DATE, 10, 17);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Saga epica, non vedo l''ora del prossimo volume', CURRENT_DATE, 2, 18);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Prato cresciuto in due settimane', CURRENT_DATE, 3, 19);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Thriller psicologico avvincente', CURRENT_DATE, 4, 20);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Le cesoie sono affilate e precise', CURRENT_DATE, 5, 1);
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Personaggi ben caratterizzati', CURRENT_DATE, 6, 2);