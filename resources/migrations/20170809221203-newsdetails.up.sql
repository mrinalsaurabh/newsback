CREATE TABLE IF NOT EXISTS newsdetails (
  id serial primary key,
  news text);

CREATE UNIQUE INDEX newsdetails_id_idx ON newsdetails(id);
