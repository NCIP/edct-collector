-- phpMyAdmin SQL Dump
-- version 3.2.3
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 21, 2011 at 08:03 PM
-- Server version: 5.1.40
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `project2`
--

-- --------------------------------------------------------

--
-- Table structure for table `blocks`
--

CREATE TABLE IF NOT EXISTS `blocks` (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `module` varchar(64) NOT NULL DEFAULT '',
  `delta` varchar(32) NOT NULL DEFAULT '0',
  `theme` varchar(64) NOT NULL DEFAULT '',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `weight` tinyint(4) NOT NULL DEFAULT '0',
  `region` varchar(64) NOT NULL DEFAULT '',
  `custom` tinyint(4) NOT NULL DEFAULT '0',
  `throttle` tinyint(4) NOT NULL DEFAULT '0',
  `visibility` tinyint(4) NOT NULL DEFAULT '0',
  `pages` text NOT NULL,
  `title` varchar(64) NOT NULL DEFAULT '',
  `cache` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`bid`),
  UNIQUE KEY `tmd` (`theme`,`module`,`delta`),
  KEY `list` (`theme`,`status`,`region`,`weight`,`module`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1066 ;

--
-- Dumping data for table `blocks`
--

INSERT INTO `blocks` (`bid`, `module`, `delta`, `theme`, `status`, `weight`, `region`, `custom`, `throttle`, `visibility`, `pages`, `title`, `cache`) VALUES
(804, 'addtoany', '0', 'mix_and_match', 1, -60, 'header_top', 0, 0, 0, '', '', 1),
(805, 'aggregator', 'category-1', 'mix_and_match', 0, -13, '', 0, 0, 1, '<front>', '', 1),
(806, 'aggregator', 'feed-1', 'mix_and_match', 0, -10, '', 0, 0, 0, '', '', 1),
(1062, 'aggregator', 'feed-10', 'mix_and_match', 0, -8, '', 0, 0, 0, '', '', 1),
(1063, 'aggregator', 'feed-11', 'mix_and_match', 0, -9, '', 0, 0, 0, '', '', 1),
(807, 'aggregator', 'feed-2', 'mix_and_match', 0, -47, '', 0, 0, 1, 'va-resources\r\naf-resources', '', 1),
(808, 'aggregator', 'feed-3', 'mix_and_match', 0, -46, '', 0, 0, 1, 'hhs-home-main', '', 1),
(809, 'aggregator', 'feed-4', 'mix_and_match', 0, -14, '', 0, 0, 0, '', '', 1),
(810, 'aggregator', 'feed-5', 'mix_and_match', 0, -12, '', 0, 0, 0, '', '', 1),
(1058, 'aggregator', 'feed-6', 'mix_and_match', 0, -3, '', 0, 0, 0, '', '', 1),
(1059, 'aggregator', 'feed-7', 'mix_and_match', 0, -4, '', 0, 0, 0, '', '', 1),
(1060, 'aggregator', 'feed-8', 'mix_and_match', 0, -6, '', 0, 0, 0, '', '', 1),
(1061, 'aggregator', 'feed-9', 'mix_and_match', 0, -7, '', 0, 0, 0, '', '', 1),
(811, 'autologout', '0', 'mix_and_match', 0, -16, '', 0, 0, 0, '', '', 1),
(812, 'block', '12', 'mix_and_match', 1, -44, 'content_bottom', 0, 0, 1, 'content/my-how-account/my-current-questionnaire/*\r\nnode/72\r\neditform/*', '', -1),
(813, 'block', '13', 'mix_and_match', 1, -44, 'content_bottom', 0, 0, 1, 'my-how-account/submit-questionnaire', '', -1),
(814, 'block', '14', 'mix_and_match', 1, -44, 'content_bottom', 0, 0, 1, 'my-how-account/my-current-questionnaire\r\nnode/73', '', -1),
(815, 'block', '18', 'mix_and_match', 1, -59, 'content_top', 0, 0, 1, 'my-how-account/my-current-questionnaire\r\nnode/73', '', -1),
(816, 'block', '19', 'mix_and_match', 1, -57, 'content_top', 0, 0, 1, 'my-account/my-account-overview\r\nnode/71\r\nva-forms\r\nfema-forms\r\nhhs-forms\r\naf-forms', '', -1),
(817, 'block', '21', 'mix_and_match', 0, -56, '', 0, 0, 1, 'va-home-main', '', -1),
(818, 'block', '23', 'mix_and_match', 0, -51, '', 0, 0, 1, 'va-home-main', '', -1),
(819, 'block', '24', 'mix_and_match', 0, -48, '', 0, 0, 1, 'va-home-main', '', -1),
(820, 'block', '25', 'mix_and_match', 0, -45, '', 0, 0, 1, 'fema-home-main\r\nfema-resources', '', -1),
(821, 'block', '26', 'mix_and_match', 0, -60, '', 0, 0, 1, 'fema-home-main', '', -1),
(822, 'block', '29', 'mix_and_match', 0, -57, '', 0, 0, 1, 'af-home-main', '', -1),
(823, 'block', '30', 'mix_and_match', 0, -44, '', 0, 0, 1, 'af-home-main', '', -1),
(824, 'block', '35', 'mix_and_match', 0, -59, '', 0, 0, 1, 'fema-home-main\r\nhosting', '', -1),
(825, 'block', '37', 'mix_and_match', 0, -38, '', 0, 0, 1, 'va-resources', '', -1),
(826, 'block', '39', 'mix_and_match', 0, -55, '', 0, 0, 1, 'fema-resources', '', -1),
(827, 'block', '4', 'mix_and_match', 1, -59, 'header', 0, 0, 1, '<front>', '', -1),
(828, 'block', '40', 'mix_and_match', 0, -50, '', 0, 0, 1, 'fema-resources', '', -1),
(829, 'block', '41', 'mix_and_match', 0, -58, '', 0, 0, 1, '<front>', '', -1),
(830, 'block', '42', 'mix_and_match', 0, -40, '', 0, 0, 1, 'af-resources', '', -1),
(831, 'block', '43', 'mix_and_match', 0, -37, '', 0, 0, 1, 'af-resources', '', -1),
(832, 'block', '44', 'mix_and_match', 0, -42, '', 0, 0, 1, 'af-resources', '', -1),
(833, 'block', '45', 'mix_and_match', 1, 0, 'preface_top', 0, 0, 0, '', '', -1),
(834, 'block', '46', 'mix_and_match', 0, -43, '', 0, 0, 1, 'va-forms', '', -1),
(835, 'block', '47', 'mix_and_match', 0, -36, '', 0, 0, 1, 'fema-forms', '', -1),
(836, 'block', '48', 'mix_and_match', 0, -41, '', 0, 0, 1, 'hhs-home-main', '', -1),
(837, 'block', '49', 'mix_and_match', 0, -49, '', 0, 0, 1, 'va-home-main', '', -1),
(838, 'block', '5', 'mix_and_match', 1, -60, 'header', 0, 0, 0, '<front>', '', -1),
(839, 'block', '50', 'mix_and_match', 1, -59, 'sidebar_last', 0, 0, 1, '<front>', '', -1),
(840, 'block', '51', 'mix_and_match', 1, -57, 'sidebar_last', 0, 0, 1, '<front>', '', -1),
(841, 'block', '52', 'mix_and_match', 1, -53, 'sidebar_first', 0, 0, 1, '<front>', '', -1),
(842, 'block', '53', 'mix_and_match', 1, -52, 'sidebar_first', 0, 0, 1, '<front>', '', -1),
(843, 'block', '54', 'mix_and_match', 1, -56, 'sidebar_last', 0, 0, 1, '<front>', '', -1),
(844, 'block', '55', 'mix_and_match', 1, -51, 'sidebar_first', 0, 0, 1, '<front>', '', -1),
(845, 'block', '56', 'mix_and_match', 1, -55, 'sidebar_last', 0, 0, 1, '<front>', '', -1),
(846, 'block', '57', 'mix_and_match', 1, -50, 'sidebar_first', 0, 0, 1, '<front>', '', -1),
(847, 'block', '58', 'mix_and_match', 1, -54, 'sidebar_last', 0, 0, 1, '<front>', '', -1),
(848, 'block', '59', 'mix_and_match', 1, -49, 'sidebar_first', 0, 0, 1, '<front>', '', -1),
(849, 'block', '60', 'mix_and_match', 1, -48, 'sidebar_first', 0, 0, 1, '<front>', '', -1),
(850, 'block', '61', 'mix_and_match', 1, -53, 'sidebar_last', 0, 0, 1, '<front>', '', -1),
(851, 'block', '62', 'mix_and_match', 1, -51, 'sidebar_last', 0, 0, 1, '<front>', '', -1),
(852, 'block', '63', 'mix_and_match', 0, -2, '', 0, 0, 1, '<front>', '', -1),
(853, 'block', '64', 'mix_and_match', 1, -52, 'sidebar_last', 0, 0, 1, '<front>', '', -1),
(854, 'block', '66', 'mix_and_match', 1, -47, 'sidebar_first', 0, 0, 1, '<front>', '', -1),
(855, 'block', '67', 'mix_and_match', 1, -50, 'sidebar_last', 0, 0, 1, '<front>', '', -1),
(856, 'block', '68', 'mix_and_match', 1, -60, 'sidebar_last', 0, 0, 1, '<front>', '', -1),
(857, 'block', '69', 'mix_and_match', 1, -59, 'sidebar_first', 0, 0, 1, '<front>', '', -1),
(1035, 'block', '70', 'mix_and_match', 1, -49, 'sidebar_last', 0, 0, 1, 'switch-theme', '', -1),
(1040, 'block', '71', 'mix_and_match', 1, -48, 'sidebar_last', 0, 0, 1, 'switch-theme', '', -1),
(1045, 'block', '72', 'mix_and_match', 1, -41, 'sidebar_first', 0, 0, 1, 'node/71\r\nmy-current-questionnaire\r\nmy-account/my-submitted-questionnaires', '', -1),
(858, 'cacure_mgmt', '1', 'mix_and_match', 1, -58, 'content_top', 0, 0, 1, 'node/73\r\nnode/71\r\nform/*\r\nsubmit-questionnaire/*\r\nva-forms\r\nfema-forms\r\nhhs-forms\r\naf-forms', '', -1),
(859, 'cacure_mgmt', '2', 'mix_and_match', 1, -43, 'content_bottom', 0, 0, 1, 'my-account/my-account-overview', '', -1),
(860, 'cacure_mgmt', '3', 'mix_and_match', 1, -56, 'content_top', 0, 0, 1, 'submit-questionnaire/*', '', 1),
(861, 'cacure_mgmt', '4', 'mix_and_match', 1, -42, 'content_bottom', 0, 0, 1, 'node/71\r\nva-forms\r\nfema-forms\r\nhhs-forms\r\naf-forms', '', -1),
(862, 'cacure_mgmt', '5', 'mix_and_match', 1, -44, 'content_bottom', 0, 0, 1, 'my-account/my-submitted-questionnaires', '', -1),
(863, 'cacure_t', '13', 'mix_and_match', 0, -39, '', 0, 0, 0, 'autologout/logout', '', -1),
(864, 'counter', '0', 'mix_and_match', 0, -33, '', 0, 0, 1, '<front>', '', 1),
(865, 'faq', '0', 'mix_and_match', 0, -27, '', 0, 0, 1, 'content/how-faqs', '', 1),
(866, 'faq', '1', 'mix_and_match', 0, -23, '', 0, 0, 0, '', '', 1),
(867, 'faq', '2', 'mix_and_match', 0, -24, '', 0, 0, 0, '', '', 1),
(868, 'locale', '0', 'mix_and_match', 0, -15, '', 0, 0, 0, '', '', -1),
(869, 'logintoboggan', '0', 'mix_and_match', 0, -26, '', 0, 0, 0, '', '', -1),
(870, 'menu', 'devel', 'mix_and_match', 0, -30, '', 0, 0, 0, '', '', -1),
(871, 'menu', 'menu-account-links', 'mix_and_match', 0, -53, '', 0, 0, 1, 'user/password\r\nuser/login', '', -1),
(872, 'menu', 'menu-diabetes-external-links', 'mix_and_match', 1, -58, 'header', 0, 0, 0, '', '', -1),
(873, 'menu', 'menu-external-links', 'mix_and_match', 1, -58, 'header_top', 0, 0, 0, '', '', -1),
(874, 'menu', 'menu-footer-links', 'mix_and_match', 1, -44, 'footer', 0, 0, 0, '', '', -1),
(875, 'menu', 'menu-my-account-info', 'mix_and_match', 0, -5, '', 0, 0, 1, 'user/*/edit\r\nuser/*/edit/Personal Info\r\nuser/*/edit/Alternate Contact info', '', -1),
(876, 'menu', 'menu-my-account-quicklinks', 'mix_and_match', 0, -32, '', 0, 0, 1, '<front>', '', -1),
(877, 'menu', 'menu-pvt-account', 'mix_and_match', 0, -52, '', 0, 0, 1, 'user/*\r\nuser/*/*\r\nuser/*/*/*\r\nnotes\r\nnotes/add\r\nnotes/edit/*\r\nnotes/delete/*\r\nnode/71\r\neditform/*\r\nquestionnaire/*\r\nnode/75\r\nmy-account/my-submitted-questionnaires\r\nnode/73\r\nmy-account/questionnaire-submitted\r\nwithdraw\r\nform/*', '', -1),
(878, 'menu', 'menu-research-team', 'mix_and_match', 0, -54, '', 0, 0, 1, 'national-cancer-institute\r\nhow-research-team-overview', '', -1),
(879, 'menu', 'menu-terms-and-privacy', 'mix_and_match', 0, -31, '', 0, 0, 0, '', '', -1),
(880, 'menu', 'primary-links', 'mix_and_match', 0, -34, '', 0, 0, 0, '', '', -1),
(881, 'menu', 'secondary-links', 'mix_and_match', 1, -59, 'header_top', 0, 0, 0, '', '', -1),
(1064, 'nice_menus', '1', 'mix_and_match', 0, -1, '', 0, 0, 0, '', '', -1),
(1065, 'nice_menus', '2', 'mix_and_match', 0, 0, '', 0, 0, 0, '', '', -1),
(882, 'node', '0', 'mix_and_match', 0, -22, '', 0, 0, 0, '', '', -1),
(883, 'notes', '0', 'mix_and_match', 1, -44, 'content_bottom', 0, 0, 1, 'node/71\r\nva-forms\r\nfema-forms\r\nhhs-forms\r\naf-forms', '', -1),
(884, 'print', '0', 'mix_and_match', 1, -60, 'content_top', 0, 0, 0, 'user/login\r\nuser/register', '', 4),
(885, 'print', '1', 'mix_and_match', 0, -25, '', 0, 0, 0, '', '', 8),
(886, 'profile', '0', 'mix_and_match', 0, -19, '', 0, 0, 0, '', '', 5),
(887, 'superfish', '1', 'mix_and_match', 1, -46, 'sidebar_first', 0, 0, 1, 'news\r\nsite-news', '<none>', -1),
(888, 'superfish', '10', 'mix_and_match', 1, -43, 'sidebar_first', 0, 0, 1, 'form-builder-overview\r\nform-builder-steps\r\nadding-questions', '<none>', -1),
(889, 'superfish', '11', 'mix_and_match', 1, -42, 'sidebar_first', 0, 0, 1, 'cure-suite\r\nanalyzing-data\r\ncollecting-data', '<none>', -1),
(890, 'superfish', '12', 'mix_and_match', 0, 1, '', 0, 0, 0, '', '', -1),
(891, 'superfish', '13', 'mix_and_match', 0, 2, '', 0, 0, 0, '', '', -1),
(892, 'superfish', '14', 'mix_and_match', 0, 3, '', 0, 0, 0, '', '', -1),
(893, 'superfish', '15', 'mix_and_match', 0, 4, '', 0, 0, 0, '', '', -1),
(894, 'superfish', '2', 'mix_and_match', 1, -45, 'sidebar_first', 0, 0, 1, 'faq', '', -1),
(895, 'superfish', '3', 'mix_and_match', 1, -60, 'sidebar_first', 0, 0, 1, '<front>', '<none>', -1),
(896, 'superfish', '4', 'mix_and_match', 0, 5, '', 0, 0, 0, '', '', -1),
(897, 'superfish', '5', 'mix_and_match', 1, -44, 'sidebar_first', 0, 0, 1, 'user/*/edit/*\r\nwithdraw\r\nuser/*/edit', '', -1),
(898, 'superfish', '6', 'mix_and_match', 1, -57, 'sidebar_first', 0, 0, 1, 'primary-links/about-healthcare-it-inc\r\nprimary-links/about-hosting\r\nabout/it-professional-services\r\ncontact-us\r\nabout/bigr', '<none>', -1),
(899, 'superfish', '7', 'mix_and_match', 1, -56, 'sidebar_first', 0, 0, 1, 'notes\r\nnotes/add\r\nnotes/edit/*\r\nnotes/delete/*\r\nnode/71\r\neditform/*\r\nquestionnaire/*\r\nnode/75\r\nmy-account/my-submitted-questionnaires\r\nnode/73\r\nmy-account/questionnaire-submitted\r\nform/*\r\nsubmit-questionnaire/*', '', -1),
(900, 'superfish', '8', 'mix_and_match', 1, -55, 'sidebar_first', 0, 0, 1, 'join-consent\r\njoin-now', '', -1),
(901, 'superfish', '9', 'mix_and_match', 1, -54, 'sidebar_first', 0, 0, 1, 'user/login\r\nuser/password', '', -1),
(902, 'switchtheme', '0', 'mix_and_match', 1, -58, 'sidebar_last', 0, 0, 1, 'switch-theme', 'Select Your Theme', 1),
(903, 'switchtheme', '1', 'mix_and_match', 0, 6, '', 0, 0, 0, '', '', 1),
(904, 'system', '0', 'mix_and_match', 0, -18, '', 0, 0, 0, '', '', -1),
(905, 'user', '0', 'mix_and_match', 1, -58, 'sidebar_first', 0, 0, 1, '<front>', 'Login', -1),
(906, 'user', '1', 'mix_and_match', 0, -28, '', 0, 0, 0, '', '', -1),
(907, 'user', '2', 'mix_and_match', 0, -21, '', 0, 0, 0, '', '', 1),
(908, 'user', '3', 'mix_and_match', 0, -20, '', 0, 0, 0, '', '', -1),
(909, 'views', 'health_notes-block_1', 'mix_and_match', 0, -29, '', 0, 0, 1, 'my-how-account/my-how-overview', '', -1),
(910, 'views', 'health_notes-block_2', 'mix_and_match', 0, -11, '', 0, 0, 1, 'my-how-account/my-health-notes', '', -1),
(911, 'views', 'how_news_news_articles-block_1', 'mix_and_match', 0, -17, '', 0, 0, 0, '', '', -1),
(912, 'views', 'Member_Comments-block_1', 'mix_and_match', 0, -35, '', 0, 0, 1, '<front>', '', -1);
