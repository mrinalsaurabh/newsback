CREATE TABLE IF NOT EXISTS newsdetails (
  id serial primary key,
  news text,
  name varchar(100));

CREATE UNIQUE INDEX newsdetails_id_idx ON newsdetails(id);
