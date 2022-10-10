-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 10, 2022 at 06:17 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jobs`
--

-- --------------------------------------------------------

--
-- Table structure for table `jobs`
--

CREATE TABLE `jobs` (
  `job_id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `errors` int(11) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `job_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jobs`
--

INSERT INTO `jobs` (`job_id`, `description`, `end_date`, `errors`, `is_active`, `start_date`, `status`, `updated_date`, `job_name`) VALUES
(1000, 'Clean RAM in every 5 minutes', '2022-10-09 10:56:30', 0, b'1', '2022-10-08 10:56:30', 3, '2022-10-09 10:56:30', 'Ram Cleaning'),
(1002, 'Display system information', '2022-10-09 12:25:05', 0, b'1', '2022-10-04 12:25:05', 0, '2022-10-09 12:25:05', 'System information'),
(1003, 'string', '2022-01-01 05:29:59', 0, b'1', '2022-01-01 05:29:59', 2, '2022-01-01 05:29:59', 'New Job'),
(1004, 'Scrape system informations', '2022-01-01 05:29:59', 0, b'1', '2022-01-01 05:29:59', 1, '2022-01-01 05:29:59', 'Metrics'),
(1005, 'Check and update software automatically', '2022-10-09 10:56:30', 0, b'1', '2022-10-09 10:56:30', 0, '2022-10-09 10:56:30', 'Software update');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `jobs`
--
ALTER TABLE `jobs`
  ADD PRIMARY KEY (`job_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `jobs`
--
ALTER TABLE `jobs`
  MODIFY `job_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1006;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
