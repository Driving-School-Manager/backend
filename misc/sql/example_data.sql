############
# MESSAGES #
############
INSERT INTO mailbox(id) VALUES (1),(2),(3),(4),(5),(6),(7);

# omitting BLOB data
INSERT INTO shared_message_body(id, expires_at, issued_at, title)
VALUES (1, '2024-01-01', '2023-01-01', 'to everyone'),
       (2, '2024-01-01', '2023-01-01', 'to students only'),
       (3, '2023-01-01', '2022-01-01', 'to instructors only');

INSERT INTO per_user_message(id, opened, mailbox_id, message_body_id)
VALUES (1, false, 1, 1),
       (2, false, 2, 1),
       (3, true, 3, 1),
       (4, true, 4, 1),
       (5, false, 6, 1),
       (6, true, 7, 1),
       (7, false, 1, 2),
       (8, false, 2, 2),
       (9, true, 3, 2),
       (10, true, 4, 2),
       (11, false, 6, 3),
       (12, true, 7, 3);

############
# STUDENTS #
############
INSERT INTO student(
                    id,
                    account_balance,
                    blocked,
                    email,
                    first_name,
                    last_name,
                    lesson_minutes_left,
                    marketing_enabled,
                    mailbox_id
                    )
VALUES (1, 0.50, false, 'bob@mail.com', 'Bob', 'Budowniczy', 1500, true, 1),
       (2, 100, false, 'ania@mail.com', 'Ania', 'Nowak', 0, true, 2),
       (3, 0, false, 'zosia@mail.com', 'Zosia', 'Kowalska', 100, true, 3),
       (4, -1000, true, 'marek@mail.com', 'Marek', 'Nowak', 1500, true, 4),
       (5, 1.50, false, 'jan@mail.com', 'Jan', 'Szymański', 0, false, 5);

############
# PAYMENTS #
############
INSERT INTO payment(id, amount, method, status, student_id)
VALUES (1, 500.15, 'CASH', 'PAID', 1),
       (2, 100, 'CARD', 'PAID', 1),
       (3, 200, 'CASH', 'PROCESSING', 2);

############
# VEHICLES #
############
INSERT INTO vehicle(id, available, license_category, license_plate, vehicle_name, year_of_manufacture)
VALUES (1, true, 'B', 'AAA2222', 'Oklejona Kia', 2010),
       (2, true, 'B', 'BBB2345', 'Nieoklejona Kia', 2010),
       (3, false, 'B', 'AAA0101', 'Popsuta Kia', 2005);

##############
# TIMETABLES #
##############
INSERT INTO timetable(id, timetable_date, day_of_week)
VALUES (1, '2023-04-03', 'MONDAY'),
       (2, '2023-04-04', 'TUESDAY'),
       (3, '2023-04-05', 'WEDNESDAY'),
       (4, '2023-04-06', 'THURSDAY');

###############
# INSTRUCTORS #
###############
INSERT INTO instructor(id, email, first_name, last_name, mailbox_id)
VALUES (1, 'instruktor@osk.pl', 'Zenon', 'Frąckowiak', 6),
       (2, 'admin@osk.pl', 'Anna', 'Frąckowiak', 7);

INSERT INTO instructor_assigned_vehicles(assigned_instructors_id, assigned_vehicles_id)
VALUES (1, 1),
       (1, 3),
       (2, 1),
       (2, 3);

INSERT INTO instructor_license_categories(instructor_id, license_categories)
VALUES (1, 'B'),
       (1, 'B1'),
       (1, 'C'),
       (1, 'C1'),
       (2, 'B'),
       (2, 'TRAMWAJ');

###########
# LESSONS #
###########
INSERT INTO lesson(id, lesson_status, instructor_id, student_id, vehicle_id)
VALUES (1, 'DONE', 1, 1, 1),
       (2, 'DONE', 1, 1, 1),
       (3, 'DONE', 2, 2, 2),
       (4, 'ONGOING', 2, 3, 1),
       (5, 'CONFIRMED', 1, 4, 3),
       (6, 'CONFIRMED', 2, 5, 1);

########################
# INSTRUCTOR SCHEDULES #
########################

INSERT INTO instructor_schedule(id, instructor_id, timetable_id)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 1, 3),
       (4, 1, 4),
       (5, 2, 1),
       (6, 2, 2),
       (7, 2, 4);

INSERT INTO time_slot(id, availability, end_time, start_time, instructor_schedule_id, lesson_id)
VALUES (1, 'FREE', '10:00', '9:00', 1, null),
       (2, 'FREE', '10:00', '9:00', 2, null),
       (3, 'ASSIGNED', '10:00', '9:00', 3, 5),
       (4, 'FREE', '10:00', '9:00', 4, null),
       (5, 'FREE', '10:00', '9:00', 5, null),
       (6, 'FREE', '10:00', '9:00', 6, null),
       (7, 'FREE', '10:00', '9:00', 7, null),

       (8, 'FREE', '12:00', '10:30', 1, null),
       (9, 'FREE', '12:00', '10:30', 2, null),
       (10, 'FREE', '12:00', '10:30', 3, null),
       (11, 'FREE', '12:00', '10:30', 4, null),
       (12, 'FREE', '12:00', '10:30', 5, null),
       (13, 'ASSIGNED', '12:00', '10:30', 6, 6),
       (14, 'FREE', '12:00', '10:30', 7, null),

       (15, 'FREE', '14:00', '12:30', 1, null),
       (16, 'FREE', '14:00', '12:30', 2, null),
       (17, 'FREE', '14:00', '12:30', 3, null),
       (18, 'FREE', '14:00', '12:30', 4, null),
       (19, 'FREE', '14:00', '12:30', 5, null),
       (20, 'FREE', '14:00', '12:30', 6, null),
       (21, 'FREE', '14:00', '12:30', 7, null);