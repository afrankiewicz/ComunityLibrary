INSERT INTO USERS (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, STREET, CITY, CITY_LATITUDE, CITY_LONGITUDE) VALUES
('Agata', 'Kowalska', 'agata.kowalska@gmail.com', '$2a$12$on5d4jkzCv/oFJ4IiGw87.AIR.0EdDDihGci6A6zTo/uP8k0WevaS',
 'Szewska', 'Warszawa', 52.237049, 21.017532),
('Mateusz', 'Nowak', 'mat.nowak@gmail.com', '$2a$12$bcKgdpAu/8vOdWm.MG.aQOjH7Z3501sV33Daceagjren0s6nb0cCu',
'Marszałkowska', 'Warszawa', 52.237049, 21.017532),
('Jan', 'Burak', 'jan.burak@gmail.com', '$2a$12$bcKgdpAu/8vOdWm.MG.aQOjH7Z3501sV33Daceagjren0s6nb0cCu',
'Zachodnia', 'Wroclaw', 51.107883, 17.038538);

INSERT INTO BOOKS (ISBN, TITLE, AUTHOR, LANGUAGE, RETURN_DATE, OWNER_ID, BORROWER_ID) VALUES
('978-83-89405-00-5', 'The Land of Laughs', 'Jonathan Carroll', 'EN', NULL, 1, NULL),
('978-83-7432-357-4', 'Norwegian Wood', 'Haruki Murakami', 'EN', NULL, 1, NULL),
('978-83-6532-456-1', 'Harry Potter and the Deathly Hallows', 'J.K. Rowling', 'EN', NULL, 1, NULL),
('978-83-7432-222-3', 'In Search of Lost Time', 'Marcel Proust', 'EN', NULL, 1, NULL),
('978-83-7432-2346-4', 'Moby Dick', 'Herman Melville', 'EN', NULL, 1, NULL),
('978-83-3452-12-1', 'War and Peace', 'Leo Tolstoy', 'EN', NULL, 2, NULL),
('978-83-3452-2387-1', 'The Odyssey', 'Homer', 'EN', NULL, 2, NULL),
('978-83-3452-568-3', 'Madame Bovary', 'Gustave Flaubert', 'EN', NULL, 2, NULL),
('978-83-67-5765-3', 'The Adventures of Huckleberry Finn', 'Mark Twain', 'EN', NULL, 2, NULL),
('978-83-76432-4774-4', 'Alice Adventures in Wonderland', 'Lewis Carroll', 'EN', NULL, 3, NULL),
('978-83-76432-532-3', ' The Iliad', 'Homer', 'EN', NULL, 3, NULL),
('978-83-76432-45-3', 'To the Lighthouse', 'Virginia Woolf', 'EN', NULL, 3, NULL),
('978-83-456-43-3', 'Heart of Darkness', 'Joseph Conrad', 'EN', NULL, 3, NULL),
('978-83-456-651-2', 'The Sound and the Fury', 'William Faulkner', 'EN', DATEADD(dd, 30, NOW()), 3, 1),
('978-83-3452-8762-1', 'To Kill a Mockingbird', 'Harper Lee', 'EN', DATEADD(dd, 15, NOW()), 3, 1);