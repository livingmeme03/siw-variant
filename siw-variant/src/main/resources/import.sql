INSERT INTO editore (id, nazione, nome) VALUES(nextval('editore_seq'), 'Italia', 'J-POP')
INSERT INTO editore (id, nazione, nome) VALUES(nextval('editore_seq'), 'Italia', 'Planet Manga')
INSERT INTO editore (id, nazione, nome) VALUES(nextval('editore_seq'), 'Italia', 'Star Comics')


INSERT INTO manga (id, titolo, numero_volumi, autore) VALUES(nextval('manga_seq'), 'Tokyo Ghoul', 16, 'Sui Ishida')
INSERT INTO manga (id, titolo, numero_volumi, autore) VALUES(nextval('manga_seq'), 'Tokyo Ghoul:Re', 14, 'Sui Ishida')
INSERT INTO manga (id, titolo, numero_volumi, autore) VALUES(nextval('manga_seq'), 'Oni Chichi', 10000, 'Tua zia')


INSERT INTO variant (id, data_uscita, manga_id, rarità, effetto_copertina, editore_id, volume) VALUES(nextval('variant_seq'), '2022-01-01', (SELECT id FROM manga WHERE titolo='Tokyo Ghoul'), 0, 'effetto figo', (SELECT id FROM editore WHERE nome='J-POP'), 1)
INSERT INTO variant (id, data_uscita, manga_id, rarità, effetto_copertina, editore_id, volume) VALUES(nextval('variant_seq'), '2023-01-01', (SELECT id FROM manga WHERE titolo='Tokyo Ghoul:Re'), 0, 'effetto fantastico', (SELECT id FROM editore WHERE nome='J-POP'), 1)