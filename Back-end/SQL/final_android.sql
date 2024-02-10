-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Feb 10, 2024 at 10:32 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `final_android`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `title` varchar(255) NOT NULL,
  `id` int(11) NOT NULL,
  `category` varchar(255) NOT NULL,
  `desc` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `imageId` varchar(64) NOT NULL,
  `senderID` int(11) NOT NULL,
  `senderName` varchar(255) NOT NULL,
  `buyerId` int(11) NOT NULL,
  `buyerName` varchar(255) NOT NULL,
  `condition` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`title`, `id`, `category`, `desc`, `price`, `imageId`, `senderID`, `senderName`, `buyerId`, `buyerName`, `condition`) VALUES
('comp', 1, 'cs', 'sdnckjdsjkcndjk', 8, '1', 2112, 'dssx', 231312, 'sdcsdm', 'wait');

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `course_id` int(11) NOT NULL,
  `course_code` varchar(10) NOT NULL,
  `course_title` varchar(255) NOT NULL,
  `instructor` varchar(255) NOT NULL,
  `class_num` varchar(10) NOT NULL,
  `course_days` varchar(30) NOT NULL,
  `time_from` varchar(10) NOT NULL,
  `time_to` varchar(10) NOT NULL,
  `faculty` varchar(255) NOT NULL,
  `room_num` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`course_id`, `course_code`, `course_title`, `instructor`, `class_num`, `course_days`, `time_from`, `time_to`, `faculty`, `room_num`) VALUES
(10, 'COMP2332', 'ELECTRONIC HEALTH ENTERPRISE BUSINESS PROCESS MODELLING', 'Adel Taweel', '1', 'Saturday,Monday,Wednesday', '10:50', '11:35', 'Bamieh', '102'),
(11, 'COMP334', 'WEB APPLICATION AND TECHNOLOGY', 'Fadi Khalil', '3', 'Saturday,Monday,Wednesday', '08:00', '08:50', 'Masri', '302'),
(12, 'COMP336', 'ANALYSIS OF ALGORITHMS', 'Iyad Jaber', '3', 'Tuesday,Thursday', '09:30', '10:40', 'Masri', '202'),
(13, 'COMP433', 'SOFTWARE ENGINEERING', 'Ahmad Sabbah', '3', 'Saturday,Monday,Wednesday', '09:55', '10:40', 'Masri', '306'),
(14, 'COMP438', 'SP. TOP: MOBILE APPLICATION DEVELOPMENT', 'Samer Zein', '1', 'Saturday,Wednesday', '13:30', '14:40', 'Masri', '109'),
(15, 'ENCS3320', 'COMPUTER NETWORKS', 'Imad Tartir', '4', 'Tuesday,Thursday', '12:10', '13:20', 'Bamieh', '102'),
(16, 'COMP438', 'Mobile', 'Samer', '1', 'Saturday, Wednesday, ', '08:00', '08:40', 'Bamieh', '200'),
(17, 'COMPCHINA', 'China Number One', 'Kim jong un', '404', 'Saturday, Wednesday, ', '08:00', '08:40', 'Alsadik', '111');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `student_id` int(11) NOT NULL,
  `student_name` varchar(255) NOT NULL,
  `student_email` varchar(255) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `date_of_birth` date NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `gender` enum('male','female') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`student_id`, `student_name`, `student_email`, `pass`, `date_of_birth`, `phone_number`, `gender`) VALUES
(7, 'Osaid Baba', 'o.osaidb2015@gmail.com', '0598786818*', '2002-05-31', '0598786818', 'male'),
(8, 'Mahmoud Za\'tari', 'm.moh2024@gmail.com', 'm.moh2024@gmail.com', '2001-02-10', '0599681548', 'male');

-- --------------------------------------------------------

--
-- Table structure for table `student_courses`
--

CREATE TABLE `student_courses` (
  `student_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_courses`
--

INSERT INTO `student_courses` (`student_id`, `course_id`) VALUES
(7, 10),
(7, 11),
(7, 12),
(7, 13),
(7, 14),
(7, 15),
(7, 17);

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `task_id` int(11) NOT NULL,
  `student_id` int(11) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `task_type` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `priority` enum('Low','Medium','High') DEFAULT NULL,
  `due_date` varchar(64) DEFAULT NULL,
  `due_time` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`task_id`, `student_id`, `course_id`, `task_type`, `description`, `priority`, `due_date`, `due_time`) VALUES
(10, 7, 17, 'Homework', 'Finish assignment on China Number One', 'High', '2024-02-15', '23:59'),
(11, 7, 17, 'Project', 'Group project on E-Health Business Modeling', 'Medium', '2024-03-01', '17:00'),
(12, 7, 17, 'Reading', 'Read chapter 4 of Web Technology textbook', 'Low', '2024-02-20', '20:00'),
(13, 7, 17, 'Study', 'Prepare for Algorithms midterm exam', 'High', '2024-02-25', '12:00'),
(14, 7, 17, 'Essay', 'Write essay on Software Engineering principles', 'Medium', '2024-03-05', '18:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`course_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`),
  ADD UNIQUE KEY `student_email` (`student_email`);

--
-- Indexes for table `student_courses`
--
ALTER TABLE `student_courses`
  ADD PRIMARY KEY (`student_id`,`course_id`),
  ADD KEY `course_id` (`course_id`);

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`task_id`),
  ADD KEY `student_id` (`student_id`),
  ADD KEY `course_id` (`course_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `course_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `task_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `student_courses`
--
ALTER TABLE `student_courses`
  ADD CONSTRAINT `student_courses_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `student_courses_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE;

--
-- Constraints for table `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `tasks_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student_courses` (`student_id`),
  ADD CONSTRAINT `tasks_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `student_courses` (`course_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
