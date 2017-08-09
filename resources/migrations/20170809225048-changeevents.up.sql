CREATE TABLE IF NOT EXISTS changeevents (
  id serial primary key,
  changeid int,
  eventid int,
  userid int,
  changed_at timestamp,
  FOREIGN KEY (userid) REFERENCES users(id),
  FOREIGN KEY (eventid) REFERENCES events(id),
  FOREIGN KEY (changeid) REFERENCES changelog(id));

CREATE INDEX changeeventid_idx ON changeevents(id);