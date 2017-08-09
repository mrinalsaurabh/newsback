CREATE TABLE IF NOT EXISTS approvers (
  id serial primary key,
  newsid int,
  userid int,
  FOREIGN KEY (userid) REFERENCES users(id),
  FOREIGN KEY (newsid) REFERENCES newsdetails(id));

CREATE INDEX approverid_idx ON approvers(id);