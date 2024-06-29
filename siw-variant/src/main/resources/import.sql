INSERT INTO editore (id, nazione, nome) VALUES(nextval('editore_seq'), 'Italia', 'J-POP')
INSERT INTO editore (id, nazione, nome) VALUES(nextval('editore_seq'), 'Italia', 'Planet Manga')
INSERT INTO editore (id, nazione, nome) VALUES(nextval('editore_seq'), 'Italia', 'Star Comics')


INSERT INTO manga (id, titolo, numero_volumi, autore) VALUES(nextval('manga_seq'), 'Tokyo Ghoul', 16, 'Sui Ishida')
INSERT INTO manga (id, titolo, numero_volumi, autore) VALUES(nextval('manga_seq'), 'Tokyo Ghoul:Re', 14, 'Sui Ishida')
INSERT INTO manga (id, titolo, numero_volumi, autore) VALUES(nextval('manga_seq'), 'Oni Chichi', 10000, 'Tua zia')
INSERT INTO manga (id, titolo, numero_volumi, autore) VALUES(nextval('manga_seq'), 'BBB', 10000, 'Tua zia')
INSERT INTO manga (id, titolo, numero_volumi, autore) VALUES(nextval('manga_seq'), 'ZZZZ', 10000, 'Tua zia')
INSERT INTO manga (id, titolo, numero_volumi, autore) VALUES(nextval('manga_seq'), 'AAAA', 10000, 'Tua zia')


INSERT INTO variant (nome_variant, id, data_uscita, manga_id, rarità, effetto_copertina, editore_id, volume) VALUES('Tokyo Ghoul Figo', nextval('variant_seq'), '2022-01-01', (SELECT id FROM manga WHERE titolo='Tokyo Ghoul'), 0, 'effetto figo', (SELECT id FROM editore WHERE nome='J-POP'), 1)
INSERT INTO variant (nome_variant, id, data_uscita, manga_id, rarità, effetto_copertina, editore_id, volume) VALUES('Tokyo Ghoul Re fantastico', nextval('variant_seq'), '2023-01-01', (SELECT id FROM manga WHERE titolo='Tokyo Ghoul:Re'), 0, 'effetto fantastico', (SELECT id FROM editore WHERE nome='J-POP'), 1)
INSERT INTO variant (nome_variant, id, data_uscita, manga_id, rarità, effetto_copertina, editore_id, volume) VALUES('ZZZZ variant', nextval('variant_seq'), '2023-01-01', (SELECT id FROM manga WHERE titolo='ZZZZ'), 0, 'effetto fantastico', (SELECT id FROM editore WHERE nome='J-POP'), 1)
INSERT INTO variant (nome_variant, id, data_uscita, manga_id, rarità, effetto_copertina, editore_id, volume) VALUES('AAAA variant', nextval('variant_seq'), '2023-01-01', (SELECT id FROM manga WHERE titolo='AAAA'), 0, 'effetto fantastico', (SELECT id FROM editore WHERE nome='J-POP'), 1)
INSERT INTO variant (nome_variant, id, data_uscita, manga_id, rarità, effetto_copertina, editore_id, volume) VALUES('BBB variant', nextval('variant_seq'), '2023-01-01', (SELECT id FROM manga WHERE titolo='BBB'), 0, 'effetto fantastico', (SELECT id FROM editore WHERE nome='J-POP'), 1)
INSERT INTO variant (nome_variant, id, volume, rarità) VALUES('Variant unica 1', nextval('variant_seq'), 1, 1)
INSERT INTO variant (nome_variant, id, volume, path_immagine, rarità) VALUES('Variant unica 2', nextval('variant_seq'), 2, '/pizza_epica.jpeg', 2)

