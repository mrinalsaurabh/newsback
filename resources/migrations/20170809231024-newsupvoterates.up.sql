CREATE TABLE IF NOT EXISTS newsupvoterates (
  id serial primary key,
  newsid int,
  upvoterate double precision,
  FOREIGN KEY (newsid) REFERENCES newsdetails(id));

CREATE INDEX newsupvoterates_idx ON newsupvoterates(id);