-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time:  4 яну 2019 в 22:05
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
(1, 'The amazing Maurice and his educated rodents', 'Terry Pratchett', 'Фентъзи', 2001, 270, 1),
(2, 'Going postal', 'Terry Pratchett', 'Фентъзи', 2004, 267, 1),
(3, 'The color of magic', 'Terry Pratchett', 'Фентъзи', 1983, 167, 1);

-- --------------------------------------------------------

--
-- Структура на таблица `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL,
  `email` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Схема на данните от таблица `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`) VALUES
(1, 'Dancheff', 'pass', 'georgi.dancheff@gmail.com'),
(2, 'Georgi', 'secret', 'georgi_danchev@abv.bg'),
(3, 'nooro', 'secret_pass', 'georgi_dancheff'),
(4, 'test', 'test', 'test@test.test'),
(6, 'asd', 'asd', 'asd@asd.asd'),
(7, 'asdd', 'asd', 'asd@asd.v');

-- --------------------------------------------------------

--
-- Структура на таблица `users_books`
--

CREATE TABLE `users_books` (
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `take_date` date NOT NULL,
  `return_date` date NOT NULL,
  `is_returned` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Схема на данните от таблица `users_books`
--

INSERT INTO `users_books` (`user_id`, `book_id`, `take_date`, `return_date`, `is_returned`) VALUES
(1, 1, '2018-12-20', '2018-12-22', 0);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
