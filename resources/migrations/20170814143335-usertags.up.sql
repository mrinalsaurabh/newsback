CREATE TABLE IF NOT EXISTS usertags (
  id serial primary key,
  userid int,
  tagid int,
  used_at timestamp,
  FOREIGN KEY (userid) REFERENCES users(id),
  FOREIGN KEY (tagid) REFERENCES tags(id));

CREATE INDEX usertags_idx ON usertags(id);