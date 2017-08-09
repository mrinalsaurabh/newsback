CREATE TABLE IF NOT EXISTS events (
  id serial primary key,
  eventname varchar(100));

CREATE INDEX eventid_idx ON events (id);
