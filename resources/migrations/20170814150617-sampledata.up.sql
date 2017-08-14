
INSERT INTO newsdetails VALUES (1,'THIS IS NEWS OF THE HOUR.');
INSERT INTO newsdetails VALUES (2,'THIS IS NEWS OF THE HOUR.....2');

INSERT INTO EVENTS VALUES (1,'create');
INSERT INTO EVENTS VALUES (2,'edit');
INSERT INTO EVENTS VALUES (3,'comment');
INSERT INTO EVENTS VALUES (4,'upvote');
INSERT INTO EVENTS VALUES (5,'downvote');
INSERT INTO EVENTS VALUES (6,'delete');

INSERT INTO STATUSES VALUES (1,'created');
INSERT INTO STATUSES VALUES (2,'edited');
INSERT INTO STATUSES VALUES (3,'commented');
INSERT INTO STATUSES VALUES (4,'upvoted');
INSERT INTO STATUSES VALUES (5,'downvoted');
INSERT INTO STATUSES VALUES (6,'deleted');

INSERT INTO countries VALUES (1,'India','+91');
INSERT INTO countries VALUES (2,'USA', '+1');

INSERT INTO changelog VALUES (1,'THIS IS NEWS OF THE HOUR.', 1, true);
INSERT INTO changelog VALUES (2,'THIS IS NEWS OF THE HOUR.....2', 2, true);
INSERT INTO changelog VALUES (3,'This is not the news of the hour.', 1, true);
INSERT INTO changelog VALUES (4,'This is the news of the hour. This change is unapproved', 1, false);
INSERT INTO changelog VALUES (5,'This is not the news of the hour....2', 2, true);
INSERT INTO changelog VALUES (6,'This is the news of the hour....2 This change is unapproved', 2, false);

INSERT INTO users VALUES (1,'Mrinal','213A, Panathur Street', 1, 9986135, 'msaurabh92@gmail.com');
INSERT INTO users VALUES (2,'Sherlock','221B, Baker Street', 2, 912345, 'sherlockholmes@gmail.com');

INSERT INTO approvers VALUES (1,1,1);
INSERT INTO approvers VALUES (2,1,2);
INSERT INTO approvers VALUES (3,2,2);

INSERT INTO changeevents VALUES(1,1,1,1,current_timestamp);
INSERT INTO changeevents VALUES(2,2,2,2,current_timestamp);
INSERT INTO changeevents VALUES(3,3,2,1,current_timestamp);
INSERT INTO changeevents VALUES(4,4,2,2,current_timestamp);
INSERT INTO changeevents VALUES(5,5,2,1,current_timestamp);
INSERT INTO changeevents VALUES(6,6,2,2,current_timestamp);

INSERT INTO tags VALUES(1,'USA');
INSERT INTO tags VALUES(2,'India');
INSERT INTO tags VALUES(3,'Business');
INSERT INTO tags VALUES(4,'National');
INSERT INTO tags VALUES(5,'Sports');
INSERT INTO tags VALUES(6,'Economy');

INSERT INTO newstags VALUES(1,1,1);
INSERT INTO newstags VALUES(2,3,1);
INSERT INTO newstags VALUES(3,6,1);
INSERT INTO newstags VALUES(4,2,2);
INSERT INTO newstags VALUES(5,4,2);

INSERT INTO newsupvoterates VALUES(1,1,5);
INSERT INTO newsupvoterates VALUES(2,2,3);

INSERT INTO userpoints VALUES(1,1,1);
INSERT INTO userpoints VALUES(2,2,2);
INSERT INTO userpoints VALUES(3,1,3);
INSERT INTO userpoints VALUES(4,2,4);
INSERT INTO userpoints VALUES(5,1,5);
INSERT INTO userpoints VALUES(6,2,6);

INSERT INTO usertags VALUES(1,1,1,current_timestamp);
INSERT INTO usertags VALUES(2,1,3,current_timestamp);
INSERT INTO usertags VALUES(3,1,4,current_timestamp);
INSERT INTO usertags VALUES(4,2,2,current_timestamp);
