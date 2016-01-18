-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 28. Feb 2013 um 18:40
-- Server Version: 5.5.27
-- PHP-Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `tatortcampusdb`
--
CREATE DATABASE `tatortcampusdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `tatortcampusdb`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tc_event`
--

CREATE TABLE IF NOT EXISTS `tc_event` (
  `EVENTERSTELLDATUM` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `TITELHTMLID` varchar(256) CHARACTER SET utf8 NOT NULL,
  `TEXTHTMLID` varchar(256) CHARACTER SET utf8 NOT NULL,
  `EVENTID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`EVENTID`),
  KEY `FK_EVENTHATTEXT` (`TEXTHTMLID`),
  KEY `FK_EVENTHATTITEL` (`TITELHTMLID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Daten für Tabelle `tc_event`
--

INSERT INTO `tc_event` (`EVENTERSTELLDATUM`, `TITELHTMLID`, `TEXTHTMLID`, `EVENTID`) VALUES
('2013-02-28 12:32:56', 'eventTitel1', 'eventText1', 1),
('2013-02-28 12:32:57', 'eventTitel2', 'eventText2', 2),
('2013-02-28 12:32:58', 'eventTitel3', 'eventText3', 3),
('2013-02-28 12:32:59', 'eventTitel4', 'eventText4', 4),
('2013-02-28 12:33:00', 'eventTitel5', 'eventText5', 5),
('2013-02-28 12:33:00', 'eventTitel6', 'eventText6', 6);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tc_gallerie`
--

CREATE TABLE IF NOT EXISTS `tc_gallerie` (
  `GALLERIEDATEIID` smallint(6) NOT NULL AUTO_INCREMENT,
  `HTMLID` varchar(256) CHARACTER SET utf8 NOT NULL,
  `ERSTELLDATUM` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`GALLERIEDATEIID`),
  KEY `FK_GALLERIEHATTITEL` (`HTMLID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Daten für Tabelle `tc_gallerie`
--

INSERT INTO `tc_gallerie` (`GALLERIEDATEIID`, `HTMLID`, `ERSTELLDATUM`) VALUES
(1, 'GallerieKategorie1', '2013-02-27 18:03:30'),
(2, 'GallerieKategorie2', '2013-02-27 18:03:55'),
(3, 'GallerieKategorie3', '2013-02-27 18:04:36');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tc_logo`
--

CREATE TABLE IF NOT EXISTS `tc_logo` (
  `LOGODATEIID` smallint(6) NOT NULL AUTO_INCREMENT,
  `HTMLID` varchar(256) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`LOGODATEIID`),
  KEY `FK_LOGOHATLINK` (`HTMLID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Daten für Tabelle `tc_logo`
--

INSERT INTO `tc_logo` (`LOGODATEIID`, `HTMLID`) VALUES
(1, 'logo1'),
(2, 'logo2'),
(3, 'logo3'),
(4, 'logo4'),
(5, 'logo5'),
(6, 'logo6');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tc_seiteninhalt`
--

CREATE TABLE IF NOT EXISTS `tc_seiteninhalt` (
  `HTMLID` varchar(256) CHARACTER SET utf8 NOT NULL,
  `INHALT` varchar(10000) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`HTMLID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `tc_seiteninhalt`
--

INSERT INTO `tc_seiteninhalt` (`HTMLID`, `INHALT`) VALUES
('footerAboutUs', '<h3>About Us</h3><p>Die kostenlose Studierenden­zeitung "Tatort Campus" ist eine studentische Initiative des Studierenden­rates der Hoch­schule Harz. In ver­schiedenen Rubriken beschäftigt sich die engagierte Redaktion, mit regionalen, hochschul­gebundenen und studien­relevanten Themen.</p>'),
('indexWillkommenText', '<h1>Hallo</h1><p><span style="font-size: 1.2em; line-height: 1.3;">Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</span></p>'),
('Aktion', 'SeitenDatenUpdaten'),
('footerContactUs', '<h3>Contact Us</h3><p>Wollt ihr uns neue Vorschläge oder Feedback schicken? Dann nutzt ruhig dieses Kontaktformular auf der rechten Seite!</p>'),
('eventTitel5', '<h2>11.11.11</h2>'),
('eventTitel3', '<h2>11.11.11</h2>'),
('eventText3', '<h3>Ipsum</h3><p>Hush now, quiet now. Its time to lay your sleepy head. Hush now, quiet now. Its time to go to bed. Hush now, Quiet now!! Its time to lay sleepy head!! Said, Hush now, Quiet now!! It is time to go to bed!!.</p>'),
('eventTitel4', '<h2>11.11.11</h2>'),
('eventText4', '<h3>Ipsum</h3><p>Hush now, quiet now. Its time to lay your sleepy head. Hush now, quiet now. Its time to go to bed. Hush now, Quiet now!! Its time to lay sleepy head!! Said, Hush now, Quiet now!! It is time to go to bed!!.</p>'),
('logo1', '5'),
('logo2', '5'),
('logo3', '9'),
('logo4', '7'),
('logo5', '3'),
('logo6', '5'),
('eventText6', '<h3>Ipsum</h3><p>Hush now, quiet now. Its time to lay your sleepy head. Hush now, quiet now. Its time to go to bed. Hush now, Quiet now!! Its time to lay sleepy head!! Said, Hush now, Quiet now!! It is time to go to bed!!.</p>'),
('GallerieKategorie1', '<p>Pony 1</p>'),
('GallerieKategorie2', '<p>Pony 2</p>'),
('GallerieKategorie3', '<p>Pony 3</p>'),
('eventTitel6', '<h2>11.11.11</h2>'),
('div', '<p>Lorem1 Ã¤ Ã¼ Ã¶</p><p></p>'),
('eventText5', '<h3>Ipsum</h3><p>Hush now, quiet now. Its time to lay your sleepy head. Hush now, quiet now. Its time to go to bed. Hush now, Quiet now!! Its time to lay sleepy head!! Said, Hush now, Quiet now!! It is time to go to bed!!.</p>'),
('eventTitel1', '<h2>11.11.11</h2>'),
('eventText1', '<h3>Ipsum</h3><p>Hush now, quiet now. Its time to lay your sleepy head. Hush now, quiet now. Its time to go to bed. Hush now, Quiet now!! Its time to lay sleepy head!! Said, Hush now, Quiet now!! It is time to go to bed!!.</p>'),
('eventTitel2', '<h2>11.11.11</h2>'),
('eventText2', '<h3>Ipsum</h3><p>Hush now, quiet now. Its time to lay your sleepy head. Hush now, quiet now. Its time to go to bed. Hush now, Quiet now!! Its time to lay sleepy head!! Said, Hush now, Quiet now!! It is time to go to bed!!.</p>'),
('Disclaimer', '<h2>Haftungsausschluss:</h2>                    <article>                        <h3>1. Inhalt des Onlineangebotes</h3>                        <p>                            Der Autor Ã¼bernimmt keinerlei GewÃ¤hr fÃ¼r die AktualitÃ¤t, Korrektheit,                            VollstÃ¤ndigkeit oder QualitÃ¤t der bereitgestellten Informationen.                            HaftungsansprÃ¼che gegen den Autor, welche sich auf SchÃ¤den materieller oder                            ideeller Art beziehen, die durch die Nutzung oder Nichtnutzung derdargebotenen                            Informationen bzw. durch die Nutzung fehlerhafter und unvollstÃ¤ndiger Informationen                            verursacht wurden, sind grundsÃ¤tzlich ausgeschlossen, sofern seitens des Autors kein                            nachweislich vorsÃ¤tzliches oder grob fahrlÃ¤ssiges Verschulden vorliegt. Alle                            Angebote sind freibleibend und unverbindlich. Der Autor behÃ¤lt es sich ausdrÃ¼cklich                            vor, Teile der Seiten oder das gesamte Angebot ohne gesonderte AnkÃ¼ndigung zu                            verÃ¤ndern, zu ergÃ¤nzen, zu lÃ¶schen oder die VerÃ¶ffentlichung zeitweise                            oder endgÃ¼ltig einzustellen.                        </p>                    </article>                    <article>                        <h3>2. Verweise und Links</h3>                        <p>                            Bei direkten oder indirekten Verweisen auf fremde Webseiten ("Hyperlinks"), die                            auÃ?erhalb des Verantwortungsbereiches des Autors liegen, wÃ¼rde eine                            Haftungsverpflichtung ausschlieÃ?lich in dem Fall in Kraft treten, in dem der                            Autor von den Inhalten Kenntnis hat und es ihm technisch mÃ¶glich und zumutbar                            wÃ¤re, die Nutzung im Falle rechtswidriger Inhalte zu verhindern. Der Autor                            erklÃ¤rt hiermit ausdrÃ¼cklich, dass zum Zeitpunkt der Linksetzung keine                            illegalen Inhalte auf den zu verlinkenden Seiten erkennbar waren. Auf die aktuelle und                            zukÃ¼nftige Gestaltung, die Inhalte oder die Urheberschaft der verlinkten/                            verknÃ¼pften Seiten hat der Autor keinerlei Einfluss. Deshalb distanziert er sich                            hiermit ausdrÃ¼cklich von allen Inhalten aller verlinkten/verknÃ¼pften Seiten,                            die nach der Linksetzung verÃ¤ndert wurden. Diese Feststellung gilt fÃ¼r alle                            innerhalb des eigenen Internetangebotes gesetzten Links und Verweise sowie fÃ¼r                            FremdeintrÃ¤ge in vom Autor eingerichteten GÃ¤stebÃ¼chern, Diskussionsforen,                            Linkverzeichnissen, Mailinglisten und in allen anderen Formen von Datenbanken, auf deren                            Inhalt externe Schreibzugriffe mÃ¶glich sind. FÃ¼r illegale, fehlerhafte oder                            unvollstÃ¤ndige Inhalte und insbesondere fÃ¼r SchÃ¤den, die aus der Nutzung                            oder Nichtnutzung solcherart dargebotener Informationen entstehen, haftet allein der                            Anbieter der Seite, auf welche verwiesen wurde, nicht derjenige, der Ã¼ber Links auf                            die jeweilige VerÃ¶ffentlichung lediglich verweist.                        </p>                    </article>                    <article>                        <h3>3. Urheber- und Kennzeichenrecht</h3>                        <p>                            Der Autor ist bestrebt, in allen Publikationen die Urheberrechte der verwendeten Bilder,                            Grafiken, Tondokumente, Videosequenzen und Texte zu beachten, von ihm selbst erstellte Bilder,                            Grafiken, Tondokumente, Videosequenzen und Texte zu nutzen oder auf lizenzfreie Grafiken,                            Tondokumente, Videosequenzen und Texte zurÃ¼ckzugreifen. Alle innerhalb des Internetangebotes                            genannten und ggf. durch Dritte geschÃ¼tzten Marken- und Warenzeichen unterliegen                            uneingeschrÃ¤nkt den Bestimmungen des jeweils gÃ¼ltigen Kennzeichenrechts und den                            Besitzrechten der jeweiligen eingetragenen EigentÃ¼mer. Allein aufgrund der bloÃ?en Nennung                            ist nicht der Schluss zu ziehen, dass Markenzeichen nicht durch Rechte Dritter geschÃ¼tzt sind!                            Das Copyright fÃ¼r verÃ¶ffentlichte, vom Autor selbst erstellte Objekte bleibt allein beim                            Autor der Seiten. Eine VervielfÃ¤ltigung oder Verwendung solcher Grafiken, Tondokumente,                            Videosequenzen und Texte in anderen elektronischen oder gedruckten Publikationen ist ohne                            ausdrÃ¼ckliche Zustimmung des Autors nicht gestattet.                        </p>                    </article>                    <article>                        <h3>4. Datenschutz</h3>                        <p>                            Sofern innerhalb des Internetangebotes die MÃ¶glichkeit zur Eingabe persÃ¶nlicher                            oder geschÃ¤ftlicher Daten (E-Mailadressen, Namen, Anschriften) besteht, so erfolgt die                            Preisgabe dieser Daten seitens des Nutzers auf ausdrÃ¼cklich freiwilliger Basis. Die                            Inanspruchnahme und Bezahlung aller angebotenen Dienste ist - soweit technisch mÃ¶glich                            und zumutbar - auch ohne Angabe solcher Daten bzw. unter Angabe anonymisierter Daten oder                            eines Pseudonyms gestattet. Die Nutzung der im Rahmen des Impressums oder vergleichbarer                            Angaben verÃ¶ffentlichten Kontaktdaten wie Postanschriften, Telefon- und Faxnummern sowie                            E-Mailadressen durch Dritte zur Ã?bersendung von nicht ausdrÃ¼cklich angeforderten                            Informationen ist nicht gestattet. Rechtliche Schritte gegen die Versender von so genannten                            Spam-Mails bei VerstÃ¶Ã?en gegen dieses Verbot sind ausdrÃ¼cklich vorbehalten.                        </p>                    </article>                    <article>                        <h3>5. Rechtswirksamkeit dieses Haftungsausschlusses</h3>                        <p>                            Dieser Haftungsausschluss ist als Teil des Internetangebotes zu betrachten, von dem aus                            auf diese Seite verwiesen wurde. Sofern Teile oder einzelne Formulierungen dieses Textes                            der geltenden Rechtslage nicht, nicht mehr oder nicht vollstÃ¤ndig entsprechen sollten,                            bleiben die Ã¼brigen Teile des Dokumentes in ihrem Inhalt und ihrer GÃ¼ltigkeit davon                            unberÃ¼hrt.                        </p>                    </article>                        '),
('KontaktInfoTatortCampus', '<h2>Studierendenzeitung "Tatort Campus"</h2><ul><li>Studierendenrat der Hochschule Harz</li><li>Friedrichstr. 57-59</li><li>38855 Wernigerode</li><li>E-Mail: <a data-cke-saved-href="mailto:tatortcampus@hs-harz.de" href="mailto:tatortcampus@hs-harz.de" title="tatortcampus@hs-harz.de"> tatortcampus@hs-harz.de </a></li></ul>'),
('KontaktInfoEntwicklung', '<h2>Design / Programmierung:</h2><ul><li>Alexander Johr</li><li>Christian Kusan</li><li>Kristina Röpke</li><li>Marc Czeszewski</li><li>Norman Henges</li><li>Stefan Bauer</li></ul>'),
('KontaktInfoRedaktion', '<h2>Chefredaktion / Vorstand:</h2><ul><li>Robert Rogosik</li><li>Lisa Krüger</li></ul>');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tc_zeitschrift`
--

CREATE TABLE IF NOT EXISTS `tc_zeitschrift` (
  `ZEITSCHRIFTDATEIID` smallint(6) NOT NULL AUTO_INCREMENT,
  `ZEITSCHRIFTUPLOADDATUM` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ZEITSCHRIFTDATEIID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Daten für Tabelle `tc_zeitschrift`
--

INSERT INTO `tc_zeitschrift` (`ZEITSCHRIFTDATEIID`, `ZEITSCHRIFTUPLOADDATUM`) VALUES
(3, '2013-02-27 17:07:22'),
(2, '2013-02-27 17:07:14');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
