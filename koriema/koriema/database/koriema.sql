-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 29, 2022 at 03:38 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `koriema`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(25) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `email`, `password`) VALUES
(1, 'diana', 'diana@gmail.com', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `assign`
--

CREATE TABLE `assign` (
  `id` int(11) NOT NULL,
  `emp_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `assign_status` varchar(20) NOT NULL DEFAULT 'Pending deliver'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `assign`
--

INSERT INTO `assign` (`id`, `emp_id`, `order_id`, `assign_status`) VALUES
(1, 3, 1, 'Delivered'),
(2, 5, 2, 'Delivered'),
(3, 3, 3, 'Delivered'),
(4, 5, 4, 'Delivered'),
(5, 3, 5, 'Delivered'),
(6, 4, 6, 'Delivered');

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

CREATE TABLE `clients` (
  `client_id` int(11) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `phone_no` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL,
  `status` varchar(55) NOT NULL DEFAULT 'Pending approval',
  `password` varchar(50) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT current_timestamp(),
  `remarks` text DEFAULT NULL,
  `user` varchar(10) NOT NULL DEFAULT 'Client'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `clients`
--

INSERT INTO `clients` (`client_id`, `first_name`, `last_name`, `username`, `phone_no`, `email`, `status`, `password`, `date_created`, `remarks`, `user`) VALUES
(1, 'Amos', 'kibee', 'amoo', '0712345678', 'amos@gmail.com', 'Approved', '1234', '2022-09-27 12:24:16', NULL, 'Client'),
(2, 'abigael', 'kiprotich', 'abby', '0723456789', 'abby@gmail.com', 'Approved', '1234', '2022-09-27 12:31:27', NULL, 'Client'),
(3, 'maryann', 'ngina', 'mary', '0712345610', 'mary@gmail.com', 'Approved', '1235', '2022-09-27 12:43:33', NULL, 'Supplier'),
(4, 'timo', 'tuitoek', 'timoo', '0756868496', 'timon@gmail.com', 'Approved', '1234', '2022-09-27 13:34:15', NULL, 'Client'),
(5, 'david', 'kaje', 'davii', '0758567858', 'david@gmail.com', 'Approved', '1234', '2022-09-27 14:14:19', NULL, 'Client'),
(6, 'peter', 'njau', 'n', '0783661863', 'nja@gmail.com', 'Approved', '1234', '2022-09-28 06:43:04', NULL, 'Client'),
(7, 'peter', 'waweru', 'pita', '0785963241', 'timothyanondo@gmail.com', 'Rejected', '1234', '2022-09-28 08:54:51', 'Wrong user side', 'Client'),
(8, 'timothy', 'murithi', 'kaje', '0758698532', 'kaje@gmail.com', 'Approved', '1234', '2022-09-28 08:56:36', NULL, 'Client'),
(9, 'mary', 'ngina', 'ngina', '0720345245', 'ngina@gmail.com', 'Approved', '4321', '2022-10-03 16:54:50', NULL, 'Client'),
(10, 'Maryanne', 'chebet', 'anne', '0785694523', 'maryanne@gmail.com', 'Approved', '1234', '2022-10-11 13:30:28', NULL, 'Client'),
(11, 'Eunice', 'kibet', 'Eunny', '0758964285', 'eunice@gmail.com', 'Approved', '1234', '2022-10-11 13:42:31', NULL, 'Supplier'),
(12, 'Brenda', 'chepkemoi', 'Bree', '0785695458', 'Bree@gmail.com', 'Approved', '1234', '2022-10-11 13:44:39', NULL, 'Supplier'),
(13, 'victor', 'kamau', 'vic', '0758698560', 'vic@gmail.com', 'Pending approval', '1234', '2022-10-11 13:46:39', NULL, 'Supplier');

-- --------------------------------------------------------

--
-- Table structure for table `counties`
--

CREATE TABLE `counties` (
  `county_id` int(11) NOT NULL,
  `county_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `counties`
--

INSERT INTO `counties` (`county_id`, `county_name`) VALUES
(1, 'Meru'),
(2, 'Kiambu'),
(3, 'Mombasa'),
(4, 'Kwale'),
(5, 'Embu');

-- --------------------------------------------------------

--
-- Table structure for table `delivery`
--

CREATE TABLE `delivery` (
  `id` int(11) NOT NULL,
  `county_id` int(11) NOT NULL,
  `town_id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `address` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `delivery`
--

INSERT INTO `delivery` (`id`, `county_id`, `town_id`, `client_id`, `address`) VALUES
(1, 1, 2, 2, '2354'),
(2, 1, 3, 4, '2548'),
(3, 1, 2, 5, '2587'),
(4, 1, 2, 6, '25408'),
(5, 1, 3, 8, '2558'),
(6, 1, 2, 10, '254'),
(7, 3, 9, 9, '00100');

-- --------------------------------------------------------

--
-- Table structure for table `delivery_cost`
--

CREATE TABLE `delivery_cost` (
  `d_id` int(11) NOT NULL,
  `cost` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `delivery_cost`
--

INSERT INTO `delivery_cost` (`d_id`, `cost`) VALUES
(1, 600);

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `emp_id` int(11) NOT NULL,
  `f_name` varchar(30) NOT NULL,
  `l_name` varchar(15) NOT NULL,
  `username` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `date_added` timestamp NOT NULL DEFAULT current_timestamp(),
  `userlevel` varchar(20) NOT NULL DEFAULT 'Staff',
  `status` varchar(20) NOT NULL DEFAULT 'Active',
  `contact` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`emp_id`, `f_name`, `l_name`, `username`, `email`, `password`, `date_added`, `userlevel`, `status`, `contact`) VALUES
(1, 'Admin', 'Admin', 'Admin', 'admin@gmail.com', '1234', '2020-11-19 14:18:21', 'Admin', 'Active', '0784514512'),
(2, 'Carl', 'Carl', 'Carl', 'carl@gmail.com', '1234', '2020-11-19 17:09:48', 'Finance', 'Active', '0787485412'),
(3, 'Kithinji', 'Mwangi', 'Kamau', 'kamaa@gmail.com', '1234', '2020-11-21 10:04:19', 'Driver', 'Active', '0784857482'),
(4, 'Kimani', 'wanjiku', 'wanjiku', 'wanjiku@gmail.com', '1234', '2020-11-21 10:05:14', 'Driver', 'Active', '0787485410'),
(5, 'Mandera', 'Hani', 'Mandera', 'hani@gmail.com', '1234', '2020-11-21 10:06:34', 'Driver', 'Active', '0784854710'),
(6, 'mary', 'Waceke', 'mary', 'waceke@gmail.com', '1234', '2020-11-23 13:50:38', 'Shipping manager', 'Active', '0787485100'),
(7, 'francis', 'waweru', 'francis', 'waweru@gmail.com', '1234', '2020-11-24 16:47:45', 'Store manager', 'Active', '0734986547');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `fb_id` int(11) NOT NULL,
  `comment` text NOT NULL,
  `fb` text NOT NULL,
  `fb_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `client_id` int(11) NOT NULL,
  `staff_id` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`fb_id`, `comment`, `fb`, `fb_date`, `client_id`, `staff_id`) VALUES
(5, 'fff', 'too', '2022-11-15 12:06:27', 9, 'Finance'),
(6, 'hello inventory', '', '2022-11-15 12:20:53', 9, 'ShippingMan'),
(7, 'hello shiiping manager', '', '2022-11-15 12:24:46', 9, 'Shipping ma'),
(8, 'hello shipping', 'too', '2022-11-15 12:28:10', 9, 'Shipping manager'),
(9, 'hi', 'too', '2022-11-15 12:35:46', 3, 'Finance'),
(10, 'hi', 'too', '2022-11-15 12:38:58', 3, 'Shipping manager'),
(11, 'hello', 'too', '2022-11-15 12:40:04', 3, 'Driver'),
(12, 'hi', '', '2022-11-15 12:41:06', 3, 'InventoryManager'),
(13, 'Hi', '', '2022-11-15 12:41:16', 3, 'InventoryManager'),
(14, 'inventory', '', '2022-11-15 12:42:38', 3, 'InventoryManager');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `client_id` int(15) NOT NULL,
  `county_id` int(11) DEFAULT NULL,
  `town_id` int(11) DEFAULT NULL,
  `address` varchar(11) DEFAULT NULL,
  `date_to_deliver` varchar(20) DEFAULT NULL,
  `time_to_deliver` varchar(20) DEFAULT NULL,
  `order_status` varchar(11) NOT NULL DEFAULT '0',
  `order_date` varchar(20) DEFAULT NULL,
  `date_delivered` varchar(20) DEFAULT NULL,
  `order_remark` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `item_id` int(11) NOT NULL,
  `stock_id` int(15) NOT NULL,
  `order_id` int(15) NOT NULL,
  `item_price` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `item_status` int(11) NOT NULL,
  `client_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL,
  `order_id` int(15) NOT NULL,
  `mpesa_code` varchar(15) NOT NULL,
  `client_id` int(15) NOT NULL,
  `order_cost` int(11) NOT NULL,
  `delivery_cost` int(11) NOT NULL,
  `total_cost` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

CREATE TABLE `request` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `items` text NOT NULL,
  `request_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `request_status` varchar(30) NOT NULL DEFAULT 'Pending approval',
  `remarks` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `request`
--

INSERT INTO `request` (`id`, `client_id`, `items`, `request_date`, `request_status`, `remarks`) VALUES
(1, 8, 'fgd', '2022-03-24 21:05:15', 'Approve', ''),
(2, 8, '500g honey', '2022-09-27 09:26:41', 'Pending approval', ''),
(3, 8, '1kg honey', '2022-09-27 09:38:56', 'Approve', ''),
(4, 15, '10kg honey (20 pcs)', '2022-09-27 10:43:31', 'Approve', ''),
(5, 15, '1kg honey(40pcs)', '2022-09-27 11:38:58', 'Pending approval', ''),
(6, 8, '500g honey(30pcs)', '2022-09-27 12:00:17', 'Approve', ''),
(7, 3, '350g honey', '2022-09-27 12:45:52', 'Pending approval', ''),
(8, 3, '350g', '2022-09-27 12:46:09', 'Approve', ''),
(9, 3, '1234', '2022-09-27 13:46:03', 'Approve', ''),
(10, 3, '500g honey', '2022-09-28 06:10:19', 'Approve', ''),
(11, 3, '500g honey(100pcs)', '2022-09-28 07:03:54', 'Approve', ''),
(12, 3, 'purehoney 500g (100 PCs)', '2022-09-28 09:08:05', 'Approve', ''),
(13, 11, 'honey 10kg(40pcs)', '2022-10-11 13:49:59', 'Pending approval', '');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `stock_id` int(11) NOT NULL,
  `image` varchar(50) NOT NULL,
  `product_name` varchar(50) NOT NULL,
  `price` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`stock_id`, `image`, `product_name`, `price`, `stock`, `last_update`, `description`) VALUES
(11, 'honey500g.jpg', ' Pure Honey 500grams', 450, 750, '2022-11-14 12:48:02', 'Best of Baringo Pure honey in 500g Pack'),
(12, 'honey250g.jpg', 'Honey 250g', 250, 400, '2022-11-14 13:03:24', 'Best Pure honey in 250g pack'),
(13, 'honey300g.jpg', 'Pure Honey 300g', 350, 422, '2022-11-14 13:27:05', 'Pure Organic Honey from Baringo County.\n\nHealth Benefits;-\n\n>A good source of antioxidants.\n>Raw honey nutrition.\n>Antibacterial and antifungal properties.\n>Heals wounds.\n'),
(14, 'honey1kg.jpg', 'Pure Honey 1kg', 900, 154, '2022-11-14 13:17:01', 'A lovely wildflower honey, sourced by the bees from varieties of backyard herbs, flowers and native flora in the Victor Harbor and Port Elliot region of South Australia. No overheating or ultrafiltering is used to ensure all of the goodness is left in.\r\n\r\nPackaged weight 1.0kg'),
(15, 'honey5kg.jpg', 'Pure honey 5kg Bucket', 4500, 84, '2022-11-14 13:25:19', 'Proudly kenyan made & owned,  100% Baringo honey is sourced from over 800 Aussie beekeeping families. Mild in flavour with a smooth, sweet finish Baringo Pure Honey is a family favourite and a great all-rounder suitable for breakfast, baking, desserts and savoury dishes. Plus, did you know that Aussie honey is naturally twice as sweet as sugar? This means you can reduce your sugar intake without compromising on taste. Make the switch and Create it with Baringo Honey.');

-- --------------------------------------------------------

--
-- Table structure for table `towns`
--

CREATE TABLE `towns` (
  `town_id` int(11) NOT NULL,
  `town_name` varchar(50) NOT NULL,
  `county_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `towns`
--

INSERT INTO `towns` (`town_id`, `town_name`, `county_id`) VALUES
(1, 'Meru town', 1),
(2, 'Maua', 1),
(3, 'Kainjai', 1),
(4, 'Kiambu town', 2),
(5, 'Limuru', 2),
(6, 'Thika', 2),
(7, 'Kikuyu', 2),
(8, 'Old town', 3),
(9, 'Mambasa town', 3),
(10, 'Ngumbu', 1),
(11, 'Mikinduri', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `assign`
--
ALTER TABLE `assign`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `emp_id` (`emp_id`);

--
-- Indexes for table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`client_id`);

--
-- Indexes for table `counties`
--
ALTER TABLE `counties`
  ADD PRIMARY KEY (`county_id`);

--
-- Indexes for table `delivery`
--
ALTER TABLE `delivery`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ap_id` (`county_id`),
  ADD KEY `client_id` (`client_id`),
  ADD KEY `town_id` (`town_id`);

--
-- Indexes for table `delivery_cost`
--
ALTER TABLE `delivery_cost`
  ADD PRIMARY KEY (`d_id`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`emp_id`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`fb_id`),
  ADD KEY `staff_id` (`staff_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `client_code` (`client_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `client_code_2` (`client_id`),
  ADD KEY `client_id` (`client_id`),
  ADD KEY `location_id` (`county_id`),
  ADD KEY `ap_id` (`town_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`item_id`),
  ADD KEY `product_code` (`stock_id`),
  ADD KEY `order_code` (`order_id`),
  ADD KEY `order_code_2` (`order_id`),
  ADD KEY `order_code_3` (`order_id`),
  ADD KEY `product_id` (`stock_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `pro_id` (`stock_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `order_id_2` (`order_id`),
  ADD KEY `client_id` (`client_id`);

--
-- Indexes for table `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `client_id` (`client_id`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`stock_id`);

--
-- Indexes for table `towns`
--
ALTER TABLE `towns`
  ADD PRIMARY KEY (`town_id`),
  ADD KEY `county_id` (`county_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `assign`
--
ALTER TABLE `assign`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `clients`
--
ALTER TABLE `clients`
  MODIFY `client_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `counties`
--
ALTER TABLE `counties`
  MODIFY `county_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `delivery`
--
ALTER TABLE `delivery`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `delivery_cost`
--
ALTER TABLE `delivery_cost`
  MODIFY `d_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `emp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `fb_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `payment_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `request`
--
ALTER TABLE `request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `stock_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `towns`
--
ALTER TABLE `towns`
  MODIFY `town_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `assign`
--
ALTER TABLE `assign`
  ADD CONSTRAINT `assign_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `assign_ibfk_2` FOREIGN KEY (`emp_id`) REFERENCES `employees` (`emp_id`);

--
-- Constraints for table `delivery`
--
ALTER TABLE `delivery`
  ADD CONSTRAINT `delivery_ibfk_1` FOREIGN KEY (`county_id`) REFERENCES `counties` (`county_id`),
  ADD CONSTRAINT `delivery_ibfk_2` FOREIGN KEY (`town_id`) REFERENCES `towns` (`town_id`),
  ADD CONSTRAINT `delivery_ibfk_3` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`);

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`stock_id`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`);

--
-- Constraints for table `request`
--
ALTER TABLE `request`
  ADD CONSTRAINT `request_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`);

--
-- Constraints for table `towns`
--
ALTER TABLE `towns`
  ADD CONSTRAINT `towns_ibfk_1` FOREIGN KEY (`county_id`) REFERENCES `counties` (`county_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
