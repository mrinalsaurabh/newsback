CREATE TABLE IF NOT EXISTS statuses (
  id serial primary key,
  status varchar(50));

CREATE UNIQUE INDEX status_id_idx ON statuses(id);
