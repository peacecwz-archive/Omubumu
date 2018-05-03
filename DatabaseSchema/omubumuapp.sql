-- phpMyAdmin SQL Dump
-- version 3.4.10
-- http://www.phpmyadmin.net
--
-- Anamakine: 94.73.146.213
-- Üretim Zamanı: 14 Eki 2015, 12:04:20
-- Sunucu sürümü: 5.5.34
-- PHP Sürümü: 5.2.6-1+lenny10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Veritabanı: `omubumuapp`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `kategoriler`
--

CREATE TABLE IF NOT EXISTS `kategoriler` (
  `KategoriID` int(11) NOT NULL AUTO_INCREMENT,
  `KategoriAdi` varchar(70) COLLATE utf8_turkish_ci DEFAULT NULL,
  `resim` varchar(2222) COLLATE utf8_turkish_ci NOT NULL DEFAULT 'http://api.omubumuapp.com/Uploads/profil.jpg	',
  PRIMARY KEY (`KategoriID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci AUTO_INCREMENT=15 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `logs`
--

CREATE TABLE IF NOT EXISTS `logs` (
  `LogID` int(11) NOT NULL AUTO_INCREMENT,
  `UserAgent` varchar(1000) DEFAULT NULL,
  `IPAdress` varchar(255) DEFAULT NULL,
  `Mesaj` text,
  `UserID` int(11) DEFAULT NULL,
  `SaveDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`LogID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1150 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `oylar`
--

CREATE TABLE IF NOT EXISTS `oylar` (
  `oyID` int(255) NOT NULL AUTO_INCREMENT,
  `UyeID` int(11) NOT NULL,
  `SoruID` int(11) NOT NULL,
  `ResimNo` bit(1) DEFAULT NULL,
  `SaveDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`oyID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci AUTO_INCREMENT=895 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `sorular`
--

CREATE TABLE IF NOT EXISTS `sorular` (
  `SoruID` int(11) NOT NULL AUTO_INCREMENT,
  `UyeID` int(11) NOT NULL,
  `Resim1` text COLLATE utf8_turkish_ci NOT NULL,
  `Resim2` text COLLATE utf8_turkish_ci NOT NULL,
  `Baslik` varchar(255) COLLATE utf8_turkish_ci NOT NULL,
  `Aciklama` text COLLATE utf8_turkish_ci NOT NULL,
  `KategoriID` int(11) NOT NULL,
  `SaveDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`SoruID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci AUTO_INCREMENT=159 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `takipciler`
--

CREATE TABLE IF NOT EXISTS `takipciler` (
  `TakipID` int(11) NOT NULL AUTO_INCREMENT,
  `TakipEdenID` int(11) DEFAULT NULL,
  `TakipEdilenID` int(11) DEFAULT NULL,
  `SaveDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`TakipID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `uyeler`
--

CREATE TABLE IF NOT EXISTS `uyeler` (
  `UyeID` int(11) NOT NULL AUTO_INCREMENT,
  `AdiSoyadi` varchar(255) COLLATE utf8_turkish_ci NOT NULL,
  `KullaniciAdi` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `Sifre` varchar(32) COLLATE utf8_turkish_ci NOT NULL,
  `Email` varchar(100) COLLATE utf8_turkish_ci NOT NULL,
  `ProfileImage` varchar(2222) COLLATE utf8_turkish_ci DEFAULT 'http://api.omubumuapp.com/Uploads/profil.jpg',
  `kapak` varchar(2222) COLLATE utf8_turkish_ci DEFAULT 'http://api.omubumuapp.com/Uploads/profil.jpg	',
  `DeviceType` int(11) DEFAULT NULL,
  `RegID` text COLLATE utf8_turkish_ci,
  `SifirlamaKodu` varchar(32) COLLATE utf8_turkish_ci DEFAULT NULL,
  `Ban` tinyint(1) NOT NULL DEFAULT '0',
  `AuthType` int(11) DEFAULT '0',
  `UserId` varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
  `Token` varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
  `Bildirim` tinyint(1) NOT NULL DEFAULT '1',
  `SaveDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`UyeID`),
  UNIQUE KEY `KullaniciAdi` (`KullaniciAdi`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci AUTO_INCREMENT=154 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `yorumlar`
--

CREATE TABLE IF NOT EXISTS `yorumlar` (
  `YorumID` int(11) NOT NULL AUTO_INCREMENT,
  `UyeID` int(11) NOT NULL,
  `SoruID` int(11) NOT NULL,
  `Yorum` text COLLATE utf8_turkish_ci NOT NULL,
  `Resim` text COLLATE utf8_turkish_ci,
  `SaveDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`YorumID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci AUTO_INCREMENT=77 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
