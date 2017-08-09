CREATE TABLE IF NOT EXISTS countries (
  id serial primary key,
  countryname varchar(500),
  countrycode varchar(5));

CREATE INDEX countries_idx ON countries(id);

