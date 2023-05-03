INSERT INTO mailbox(id)
VALUES
    (1), (2), (3), (4);

INSERT INTO student(id, account_balance, blocked, email, first_name, last_name, lesson_minutes_left, marketing_enabled, mailbox_id)
VALUES
    (1, 100.0, false, 'bob@budowniczy.pl', 'Bob', 'Budowniczy', 1000, true, 1),
    (2, -0.01, false, 'kasia@kowalska.pl', 'Kasia', 'Kowalska', 100, false, 2),
    (3, -100.0, true, 'bob@nowak.pl', 'Bob', 'Nowak', 0, true, 3),
    (4, 0.01, false, 'janina@kowalska.pl', 'Janina', 'Kowalska', 500, false, 4);
