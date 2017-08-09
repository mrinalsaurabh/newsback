CREATE TABLE IF NOT EXISTS changelog (
  id serial primary key,
  change text,
  news_id int,
  changeapproved boolean, 
  FOREIGN KEY (news_id) REFERENCES newsdetails(id));

CREATE INDEX changelog_country_month_idx ON changelog (id,news_id);
