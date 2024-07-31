CREATE DATABASE IF NOT EXISTS bd_velo_4b2;
USE bd_velo_4b2;
CREATE USER IF NOT EXISTS 'read_4b2'@'localhost' IDENTIFIED BY 'read_4b2';
CREATE USER IF NOT EXISTS 'write_4b2'@'localhost' IDENTIFIED BY 'write_4b2';
GRANT SELECT ON bd_velo_4b2.* TO 'read_4b2'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON bd_velo_4b2.* TO 'write_4b2'@'localhost';
FLUSH PRIVILEGES;