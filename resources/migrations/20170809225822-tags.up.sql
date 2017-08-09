CREATE TABLE IF NOT EXISTS tags (
  id serial primary key,
  tagname citext UNIQUE);

CREATE INDEX tagname_idx ON tags(tagname);