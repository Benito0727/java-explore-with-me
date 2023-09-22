INSERT INTO events_status (status_name)
VALUES ('WAITING_EVENT'),
       ('REJECTED_EVENT'),
       ('CANCELED_EVENT'),
       ('PUBLISHED_EVENT');

INSERT INTO participation_requests_status (status_name)
VALUES ('PENDING'),
       ('REJECTED'),
       ('CANCELED'),
       ('APPROVED')