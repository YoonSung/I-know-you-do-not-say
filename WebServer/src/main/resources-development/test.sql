DROP TABLE IF EXISTS tbl_user;

CREATE TABLE IF NOT EXISTS tbl_user (
  pid INT NOT NULL AUTO_INCREMENT,
  phone_number VARCHAR(15) NOT NULL,
  is_member TINYINT(1) DEFAULT 0,
  nickname VARCHAR(80) NOT NULL,
  image_path VARCHAR(255) NULL,
  created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  reg_id VARCHAR(250) NOT NULL,
  PRIMARY KEY (pid)
);

--INSERT INTO tbl_user(phone_number, nickname, reg_id) VALUES("01099258547", "testNickname", "testRegisterationId");  