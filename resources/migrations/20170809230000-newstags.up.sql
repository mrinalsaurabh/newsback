CREATE TABLE IF NOT EXISTS newstags (
  id serial primary key,
  tagid int,
  newsid int,
  FOREIGN KEY (newsid) REFERENCES newsdetails(id),
  FOREIGN KEY (tagid) REFERENCES tags(id));

CREATE INDEX newstags_idx ON newstags(id);