-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 11 яну 2019 в 04:46
-- Версия на сървъра: 10.1.37-MariaDB
-- PHP Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library`
--
CREATE DATABASE IF NOT EXISTS `library` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `library`;

-- --------------------------------------------------------

--
-- Структура на таблица `books`
--

CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `title` text NOT NULL,
  `author` text NOT NULL,
  `genre` text NOT NULL,
  `publishing_year` int(11) NOT NULL,
  `pages` int(11) NOT NULL,
  `amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Схема на данните от таблица `books`
--

INSERT INTO `books` (`id`, `title`, `author`, `genre`, `publishing_year`, `pages`, `amount`) VALUES
(1, 'The amazing Maurice and his educated rodents', 'Terry Pratchett', 'Fantasy', 2001, 270, 2),
(2, 'Going postal', 'Terry Pratchett', 'Fantasy', 2004, 267, 0),
(3, 'The color of magic', 'Terry Pratchett', 'Fantasy', 1983, 167, 7),
(5, 'It', 'Stephen King', 'Horror', 1986, 760, 1),
(10, 'The outsider', 'Stephen King', 'Thriller', 2018, 360, 1),
(11, 'RandomTitle', 'Noone', 'Trash', 2008, 123, 0);

-- --------------------------------------------------------

--
-- Структура на таблица `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL,
  `email` text NOT NULL,
  `is_admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Схема на данните от таблица `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `is_admin`) VALUES
(1, 'Dancheff', 'SECURED@pass', 'georgi.dancheff@gmail.com', 0),
(2, 'Georgi', 'SECURED@secret', 'georgi_danchev@abv.bg', 0),
(3, 'nooro', 'SECURED@pass', 'noor@smwr.com', 1),
(13, 'ggr', 'SECURED@asd', 'asd@asd.asd', 0),
(14, 'bobo', 'SECURED@asd', 'asd@asd.asd', 0);

-- --------------------------------------------------------

--
-- Структура на таблица `users_books`
--

CREATE TABLE `users_books` (
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `take_date` date NOT NULL,
  `return_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Схема на данните от таблица `users_books`
--

INSERT INTO `users_books` (`user_id`, `book_id`, `take_date`, `return_date`) VALUES
(1, 11, '2019-01-11', '2019-02-11');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
