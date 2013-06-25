CREATE TABLE `auth_attempts` (
  `date_last` datetime NOT NULL,
  `number` int(10) unsigned NOT NULL,
  `name` varchar(60) NOT NULL,
  `blocked` int(11) default '0',
  PRIMARY KEY  (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1
