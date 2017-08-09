CREATE TABLE IF NOT EXISTS userpoints (
  id serial primary key,
  userid int,
  eventid int,
  FOREIGN KEY (userid) REFERENCES users(id),
  FOREIGN KEY (eventid) REFERENCES events(id));

CREATE INDEX userpoints_idx ON userpoints(id);