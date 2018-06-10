CREATE TABLE "room" (
	"id" serial NOT NULL,
	"number" integer NOT NULL UNIQUE,
	"floor" integer NOT NULL,
	"type" character varying(50) NOT NULL,
	"accomodation" character varying(50) NOT NULL,
	"view" character varying(50) NOT NULL,
	"actual_price" numeric(12,2) NOT NULL,
	"description" character varying(500) NOT NULL,
	"dirty" BOOLEAN NOT NULL,
	"broken" BOOLEAN NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT room_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "photo_link" (
	"id" serial NOT NULL,
	"room_id" integer NOT NULL,
	"user_account_id" integer NOT NULL,
	"link" character varying(300) NOT NULL UNIQUE,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT photo_link_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "employee" (
	"id" serial NOT NULL,
	"hiring" TIMESTAMP NOT NULL,
	"layoff" TIMESTAMP,
	"post_id" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT employee_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "guest" (
	"id" serial NOT NULL,
	"verify_key" character varying(50) NOT NULL UNIQUE,
	"verified" BOOLEAN NOT NULL,
	"credit" numeric(12,2) NOT NULL,
	"guest_status_id" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT guest_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "guest_status" (
	"id" serial NOT NULL,
	"name" character varying(50) NOT NULL UNIQUE,
	"discount" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT guest_status_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "booking" (
	"id" serial NOT NULL,
	"booking_status_id" integer NOT NULL,
	"room_id" integer NOT NULL,
	"user_account_id" integer,
	"check_in" TIMESTAMP NOT NULL,
	"check_out" TIMESTAMP NOT NULL,
	"room_price" numeric(12,2) NOT NULL,
	"version" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT booking_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "booked_maintenance" (
	"id" serial NOT NULL,
	"user_account_id" integer NOT NULL,
	"maintenance_id" integer NOT NULL,
	"time" TIMESTAMP,
	"price" numeric(12,2) NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT booked_maintenance_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "task" (
	"id" serial NOT NULL,
	"to_do" character varying(50) NOT NULL,
	"description" character varying(500) NOT NULL,
	"priority" character varying(50) NOT NULL,
	"execution_time" TIMESTAMP NOT NULL,
	"answerable_id" integer NOT NULL,
	"executed" BOOLEAN NOT NULL,
	"creator_id" integer NOT NULL,
	"version" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT task_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "booking_status" (
	"id" serial NOT NULL,
	"name" character varying(50) NOT NULL UNIQUE,
	"color" character varying(30) NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT booking_status_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "comment" (
	"id" serial NOT NULL,
	"room_id" integer NOT NULL,
	"user_account_id" integer NOT NULL,
	"comment" character varying NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT comment_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "maintenance" (
	"id" serial NOT NULL,
	"name" character varying(50) NOT NULL,
	"actual_price" numeric(12,2) NOT NULL,
	"available" BOOLEAN NOT NULL,
	"photo_link" character varying(300),
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT maintenance_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "user_account" (
	"id" serial NOT NULL,
	"email" character varying(100) NOT NULL UNIQUE,
	"password" character varying(100) NOT NULL,
	"role" character varying(50) NOT NULL,
	"first_name" character varying(50) NOT NULL,
	"last_name" character varying(50) NOT NULL,
	"birthday" TIMESTAMP NOT NULL,
	"address" character varying(200) NOT NULL,
	"phone" character varying(50) NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT user_account_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "post" (
	"id" serial NOT NULL,
	"name" character varying(100) NOT NULL UNIQUE,
	"description" character varying(1000) NOT NULL,
	"supervisor_id" integer,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT post_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "unstructured_object" (
	"id" serial NOT NULL,
	"name" character varying(50) NOT NULL,
	"content" bytea NOT NULL,
	"user_account_id" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT unstructured_object_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "message" (
	"id" serial NOT NULL,
	"name" character varying(50) NOT NULL,
	"phone" character varying(50) NOT NULL,
	"email" character varying(50) NOT NULL,
	"message" character varying(1000) NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT message_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);




ALTER TABLE "photo_link" ADD CONSTRAINT "photo_link_fk0" FOREIGN KEY ("room_id") REFERENCES "room"("id");
ALTER TABLE "photo_link" ADD CONSTRAINT "photo_link_fk1" FOREIGN KEY ("user_account_id") REFERENCES "user_account"("id");

ALTER TABLE "employee" ADD CONSTRAINT "employee_fk0" FOREIGN KEY ("id") REFERENCES "user_account"("id");
ALTER TABLE "employee" ADD CONSTRAINT "employee_fk1" FOREIGN KEY ("post_id") REFERENCES "post"("id");

ALTER TABLE "guest" ADD CONSTRAINT "guest_fk0" FOREIGN KEY ("id") REFERENCES "user_account"("id");
ALTER TABLE "guest" ADD CONSTRAINT "guest_fk1" FOREIGN KEY ("guest_status_id") REFERENCES "guest_status"("id");


ALTER TABLE "booking" ADD CONSTRAINT "booking_fk0" FOREIGN KEY ("booking_status_id") REFERENCES "booking_status"("id");
ALTER TABLE "booking" ADD CONSTRAINT "booking_fk1" FOREIGN KEY ("room_id") REFERENCES "room"("id");
ALTER TABLE "booking" ADD CONSTRAINT "booking_fk2" FOREIGN KEY ("user_account_id") REFERENCES "user_account"("id");

ALTER TABLE "booked_maintenance" ADD CONSTRAINT "booked_maintenance_fk0" FOREIGN KEY ("user_account_id") REFERENCES "user_account"("id");
ALTER TABLE "booked_maintenance" ADD CONSTRAINT "booked_maintenance_fk1" FOREIGN KEY ("maintenance_id") REFERENCES "maintenance"("id");

ALTER TABLE "task" ADD CONSTRAINT "task_fk0" FOREIGN KEY ("answerable_id") REFERENCES "user_account"("id");
ALTER TABLE "task" ADD CONSTRAINT "task_fk1" FOREIGN KEY ("creator_id") REFERENCES "user_account"("id");


ALTER TABLE "comment" ADD CONSTRAINT "comment_fk0" FOREIGN KEY ("room_id") REFERENCES "room"("id");
ALTER TABLE "comment" ADD CONSTRAINT "comment_fk1" FOREIGN KEY ("user_account_id") REFERENCES "user_account"("id");



ALTER TABLE "post" ADD CONSTRAINT "post_fk0" FOREIGN KEY ("supervisor_id") REFERENCES "post"("id");

ALTER TABLE "unstructured_object" ADD CONSTRAINT "unstructured_object_fk0" FOREIGN KEY ("user_account_id") REFERENCES "user_account"("id");


