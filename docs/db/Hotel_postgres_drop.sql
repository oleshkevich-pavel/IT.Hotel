ALTER TABLE "photo_link" DROP CONSTRAINT IF EXISTS "photo_link_fk0";

ALTER TABLE "photo_link" DROP CONSTRAINT IF EXISTS "photo_link_fk1";

ALTER TABLE "employee" DROP CONSTRAINT IF EXISTS "employee_fk0";

ALTER TABLE "employee" DROP CONSTRAINT IF EXISTS "employee_fk1";

ALTER TABLE "guest" DROP CONSTRAINT IF EXISTS "guest_fk0";

ALTER TABLE "guest" DROP CONSTRAINT IF EXISTS "guest_fk1";

ALTER TABLE "booking" DROP CONSTRAINT IF EXISTS "booking_fk0";

ALTER TABLE "booking" DROP CONSTRAINT IF EXISTS "booking_fk1";

ALTER TABLE "booking" DROP CONSTRAINT IF EXISTS "booking_fk2";

ALTER TABLE "booked_maintenance" DROP CONSTRAINT IF EXISTS "booked_maintenance_fk0";

ALTER TABLE "booked_maintenance" DROP CONSTRAINT IF EXISTS "booked_maintenance_fk1";

ALTER TABLE "booked_maintenance" DROP CONSTRAINT IF EXISTS "booked_maintenance_fk2";

ALTER TABLE "task" DROP CONSTRAINT IF EXISTS "task_fk0";

ALTER TABLE "task" DROP CONSTRAINT IF EXISTS "task_fk1";

ALTER TABLE "comment" DROP CONSTRAINT IF EXISTS "comment_fk0";

ALTER TABLE "comment" DROP CONSTRAINT IF EXISTS "comment_fk1";

ALTER TABLE "post" DROP CONSTRAINT IF EXISTS "post_fk0";

ALTER TABLE "unstructured_object" DROP CONSTRAINT IF EXISTS "unstructured_object_fk0";

DROP TABLE IF EXISTS "room";

DROP TABLE IF EXISTS "photo_link";

DROP TABLE IF EXISTS "employee";

DROP TABLE IF EXISTS "guest";

DROP TABLE IF EXISTS "guest_status";

DROP TABLE IF EXISTS "booking";

DROP TABLE IF EXISTS "booked_maintenance";

DROP TABLE IF EXISTS "task";

DROP TABLE IF EXISTS "booking_status";

DROP TABLE IF EXISTS "comment";

DROP TABLE IF EXISTS "maintenance";

DROP TABLE IF EXISTS "user_account";

DROP TABLE IF EXISTS "post";

DROP TABLE IF EXISTS "unstructured_object";

DROP TABLE IF EXISTS "message";

