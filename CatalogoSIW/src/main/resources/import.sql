-- Sequenze
ALTER SEQUENCE prodotto_seq INCREMENT BY 1 RESTART WITH 1;
ALTER SEQUENCE utente_seq INCREMENT BY 1 RESTART WITH 1;
ALTER SEQUENCE credentials_seq INCREMENT BY 1 RESTART WITH 1;
ALTER SEQUENCE commento_seq INCREMENT BY 1 RESTART WITH 1;

-- UTENTI
INSERT INTO utente (id, nome, cognome) VALUES (nextval('utente_seq'), 'Admin', 'System');

INSERT INTO utente (id, nome, cognome) VALUES (nextval('utente_seq'), 'Mario', 'Rossi');

-- CREDENTIALS
INSERT INTO credentials (id, username, password, role, utente_id) VALUES (nextval('credentials_seq'), 'admin', '$2a$10$QsQS93zMmOeRVf6baRoCCe0ERCN4lWmOHwI0E3dXPm95rYb354cpa', 'ADMIN', 1);

INSERT INTO credentials (id, username, password, role, utente_id) VALUES (nextval('credentials_seq'), 'mario', '$2a$10$QsQS93zMmOeRVf6baRoCCe0ERCN4lWmOHwI0E3dXPm95rYb354cpa', 'DEFAULT', 2);

-- PRODOTTI
INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Cesoie da giardino', 19.99, 'Ottime per potare rose e arbusti', 'Giardinaggio');

INSERT INTO prodotto (id, nome, prezzo, descrizione, tipologia) VALUES (nextval('prodotto_seq'), 'Romanzo fantasy', 12.50, 'Un emozionante viaggio tra draghi e magie', 'Libri');

-- COMMENTI
INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Prodotto fantastico!', CURRENT_DATE, 2, 1);

INSERT INTO commento (id, testo, data_creazione, autore_id, prodotto_id) VALUES (nextval('commento_seq'), 'Bellissima storia!', CURRENT_DATE, 2, 2);
