-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Gegenereerd op: 20 mrt 2020 om 08:45
-- Serverversie: 5.7.29-0ubuntu0.18.04.1
-- PHP-versie: 7.2.28-3+ubuntu18.04.1+deb.sury.org+1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `team04`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `feedback`
--

CREATE TABLE `feedback` (
  `feedback_id` int(10) NOT NULL,
  `feedback` int(3) NOT NULL,
  `feedback_opmerking` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `feedback`
--

INSERT INTO `feedback` (`feedback_id`, `feedback`, `feedback_opmerking`) VALUES
(1, 100, ''),
(2, 25, 'Opmerking moet verplicht'),
(3, 85, 'hallo'),
(4, 75, 'Wat een mooie tekst'),
(5, 75, 'Wat een mooie tekst'),
(6, 75, 'Wat een mooie tekst'),
(7, 75, 'Wat een mooie tekst');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `opleiding`
--

CREATE TABLE `opleiding` (
  `opleiding_id` int(10) NOT NULL,
  `opleiding_naam` varchar(50) NOT NULL,
  `boeken_link` varchar(510) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `opleiding`
--

INSERT INTO `opleiding` (`opleiding_id`, `opleiding_naam`, `boeken_link`) VALUES
(1, 'Informatica', 'https://www.studystore.nl/boekenlijst/Associate-degrees-Academie/2019/BL079169/Ad-Informatica-leerjaar-1'),
(2, 'Accountancy', 'https://www.studystore.nl/boekenlijst/Associate-degrees-Academie/2019/BL079170/Ad-Accountancy-leerjaar-1'),
(3, 'Bedrijfskunde', 'https://www.studystore.nl/boekenlijst/Associate-degrees-Academie/2019/BL079174/Ad-Bedrijfskunde-leerjaar-1-'),
(4, 'Built Environment', 'https://www.studystore.nl/boekenlijst/Associate-degrees-Academie/2019/BL088447/Built-Environment-leerjaar-1'),
(5, 'Engineering', 'https://www.studystore.nl/boekenlijst/Associate-degrees-Academie/2019/BL079173/Ad-Engineering-leerjaar-1'),
(6, 'Health & Social Work', 'geen'),
(7, 'Human Resources Management', 'https://www.studystore.nl/boekenlijst/Associate-degrees-Academie/2019/BL079166/Ad-Human-Resource-Management-leerjaar-1'),
(8, 'Logistiek', 'https://www.studystore.nl/boekenlijst/Associate-degrees-Academie/2019/BL079171/Ad-Logistiek-leerjaar-1'),
(9, 'Management', 'https://www.studystore.nl/boekenlijst/Associate-degrees-Academie/2019/BL079172/Ad-Management-leerjaar-1');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `student`
--

CREATE TABLE `student` (
  `studentnummer` int(10) NOT NULL,
  `student_voornaam` varchar(50) NOT NULL,
  `student_achternaam` varchar(100) NOT NULL,
  `opleiding_id` int(10) DEFAULT NULL,
  `start_datum` date NOT NULL,
  `gegevens_correct` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `student`
--

INSERT INTO `student` (`studentnummer`, `student_voornaam`, `student_achternaam`, `opleiding_id`, `start_datum`, `gegevens_correct`) VALUES
(1, 'Marvin', 'Vissers', 1, '2020-09-25', 2),
(2, 'iets', 'anders', 1, '0000-00-00', 2);

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`feedback_id`);

--
-- Indexen voor tabel `opleiding`
--
ALTER TABLE `opleiding`
  ADD PRIMARY KEY (`opleiding_id`),
  ADD UNIQUE KEY `opleiding_naam` (`opleiding_naam`),
  ADD UNIQUE KEY `boeken_link` (`boeken_link`);

--
-- Indexen voor tabel `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`studentnummer`),
  ADD KEY `studentOpleiding` (`opleiding_id`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `feedback`
--
ALTER TABLE `feedback`
  MODIFY `feedback_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT voor een tabel `opleiding`
--
ALTER TABLE `opleiding`
  MODIFY `opleiding_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Beperkingen voor geëxporteerde tabellen
--

--
-- Beperkingen voor tabel `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `studentOpleiding` FOREIGN KEY (`opleiding_id`) REFERENCES `opleiding` (`opleiding_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
