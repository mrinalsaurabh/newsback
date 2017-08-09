CREATE TABLE IF NOT EXISTS users (
  id serial primary key,
  username varchar(100),
  useraddress varchar(500),
  countryid int,
  userphone int UNIQUE,
  useremail citext UNIQUE,
  FOREIGN KEY (countryid) REFERENCES countries(id));

CREATE INDEX usersid_idx ON users (id);
CREATE INDEX usersphone_idx ON users (userphone);
CREATE INDEX usersemail_idx ON users (useremail);
