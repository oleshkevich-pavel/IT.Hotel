--
-- PostgreSQL database dump
--

-- Dumped from database version 10.1
-- Dumped by pg_dump version 10.1

-- Started on 2018-06-08 17:17:29

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2998 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 209 (class 1259 OID 246730)
-- Name: booked_maintenance; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE booked_maintenance (
    id integer NOT NULL,
    user_account_id integer NOT NULL,
    maintenance_id integer NOT NULL,
    "time" timestamp without time zone,
    price numeric(12,2) NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 208 (class 1259 OID 246728)
-- Name: booked_maintenance_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE booked_maintenance_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2999 (class 0 OID 0)
-- Dependencies: 208
-- Name: booked_maintenance_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE booked_maintenance_id_seq OWNED BY booked_maintenance.id;


--
-- TOC entry 207 (class 1259 OID 246722)
-- Name: booking; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE booking (
    id integer NOT NULL,
    booking_status_id integer NOT NULL,
    room_id integer NOT NULL,
    user_account_id integer,
    check_in timestamp without time zone NOT NULL,
    check_out timestamp without time zone NOT NULL,
    room_price numeric(12,2) NOT NULL,
    version integer NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 206 (class 1259 OID 246720)
-- Name: booking_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE booking_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3000 (class 0 OID 0)
-- Dependencies: 206
-- Name: booking_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE booking_id_seq OWNED BY booking.id;


--
-- TOC entry 213 (class 1259 OID 246749)
-- Name: booking_status; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE booking_status (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    color character varying(30) NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 212 (class 1259 OID 246747)
-- Name: booking_status_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE booking_status_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3001 (class 0 OID 0)
-- Dependencies: 212
-- Name: booking_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE booking_status_id_seq OWNED BY booking_status.id;


--
-- TOC entry 215 (class 1259 OID 246759)
-- Name: comment; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE comment (
    id integer NOT NULL,
    room_id integer NOT NULL,
    user_account_id integer NOT NULL,
    comment character varying NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 214 (class 1259 OID 246757)
-- Name: comment_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE comment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3002 (class 0 OID 0)
-- Dependencies: 214
-- Name: comment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE comment_id_seq OWNED BY comment.id;


--
-- TOC entry 201 (class 1259 OID 246694)
-- Name: employee; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE employee (
    id integer NOT NULL,
    hiring timestamp without time zone NOT NULL,
    layoff timestamp without time zone,
    post_id integer NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 200 (class 1259 OID 246692)
-- Name: employee_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3003 (class 0 OID 0)
-- Dependencies: 200
-- Name: employee_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE employee_id_seq OWNED BY employee.id;


--
-- TOC entry 203 (class 1259 OID 246702)
-- Name: guest; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE guest (
    id integer NOT NULL,
    verify_key character varying(50) NOT NULL,
    verified boolean NOT NULL,
    credit numeric(12,2) NOT NULL,
    guest_status_id integer NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 202 (class 1259 OID 246700)
-- Name: guest_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE guest_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3004 (class 0 OID 0)
-- Dependencies: 202
-- Name: guest_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE guest_id_seq OWNED BY guest.id;


--
-- TOC entry 205 (class 1259 OID 246712)
-- Name: guest_status; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE guest_status (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    discount integer NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 204 (class 1259 OID 246710)
-- Name: guest_status_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE guest_status_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3005 (class 0 OID 0)
-- Dependencies: 204
-- Name: guest_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE guest_status_id_seq OWNED BY guest_status.id;


--
-- TOC entry 217 (class 1259 OID 246770)
-- Name: maintenance; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE maintenance (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    actual_price numeric(12,2) NOT NULL,
    available boolean NOT NULL,
    photo_link character varying(300),
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 216 (class 1259 OID 246768)
-- Name: maintenance_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE maintenance_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3006 (class 0 OID 0)
-- Dependencies: 216
-- Name: maintenance_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE maintenance_id_seq OWNED BY maintenance.id;


--
-- TOC entry 225 (class 1259 OID 246815)
-- Name: message; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE message (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    phone character varying(50) NOT NULL,
    email character varying(50) NOT NULL,
    message character varying(1000) NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 224 (class 1259 OID 246813)
-- Name: message_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE message_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3007 (class 0 OID 0)
-- Dependencies: 224
-- Name: message_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE message_id_seq OWNED BY message.id;


--
-- TOC entry 199 (class 1259 OID 246684)
-- Name: photo_link; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE photo_link (
    id integer NOT NULL,
    room_id integer NOT NULL,
    user_account_id integer NOT NULL,
    link character varying(300) NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 198 (class 1259 OID 246682)
-- Name: photo_link_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE photo_link_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3008 (class 0 OID 0)
-- Dependencies: 198
-- Name: photo_link_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE photo_link_id_seq OWNED BY photo_link.id;


--
-- TOC entry 221 (class 1259 OID 246791)
-- Name: post; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE post (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(1000) NOT NULL,
    supervisor_id integer,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 220 (class 1259 OID 246789)
-- Name: post_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE post_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3009 (class 0 OID 0)
-- Dependencies: 220
-- Name: post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE post_id_seq OWNED BY post.id;


--
-- TOC entry 197 (class 1259 OID 246671)
-- Name: room; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE room (
    id integer NOT NULL,
    number integer NOT NULL,
    floor integer NOT NULL,
    type character varying(50) NOT NULL,
    accomodation character varying(50) NOT NULL,
    view character varying(50) NOT NULL,
    actual_price numeric(12,2) NOT NULL,
    description character varying(500) NOT NULL,
    dirty boolean NOT NULL,
    broken boolean NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 196 (class 1259 OID 246669)
-- Name: room_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE room_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3010 (class 0 OID 0)
-- Dependencies: 196
-- Name: room_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE room_id_seq OWNED BY room.id;


--
-- TOC entry 211 (class 1259 OID 246738)
-- Name: task; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE task (
    id integer NOT NULL,
    to_do character varying(50) NOT NULL,
    description character varying(500) NOT NULL,
    priority character varying(50) NOT NULL,
    execution_time timestamp without time zone NOT NULL,
    answerable_id integer NOT NULL,
    executed boolean NOT NULL,
    creator_id integer NOT NULL,
    version integer NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 210 (class 1259 OID 246736)
-- Name: task_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE task_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3011 (class 0 OID 0)
-- Dependencies: 210
-- Name: task_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE task_id_seq OWNED BY task.id;


--
-- TOC entry 223 (class 1259 OID 246804)
-- Name: unstructured_object; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE unstructured_object (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    content bytea NOT NULL,
    user_account_id integer NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 222 (class 1259 OID 246802)
-- Name: unstructured_object_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE unstructured_object_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3012 (class 0 OID 0)
-- Dependencies: 222
-- Name: unstructured_object_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE unstructured_object_id_seq OWNED BY unstructured_object.id;


--
-- TOC entry 219 (class 1259 OID 246778)
-- Name: user_account; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE user_account (
    id integer NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(100) NOT NULL,
    role character varying(50) NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    birthday timestamp without time zone NOT NULL,
    address character varying(200) NOT NULL,
    phone character varying(50) NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 218 (class 1259 OID 246776)
-- Name: user_account_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE user_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3013 (class 0 OID 0)
-- Dependencies: 218
-- Name: user_account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE user_account_id_seq OWNED BY user_account.id;


--
-- TOC entry 2767 (class 2604 OID 246733)
-- Name: booked_maintenance id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY booked_maintenance ALTER COLUMN id SET DEFAULT nextval('booked_maintenance_id_seq'::regclass);


--
-- TOC entry 2766 (class 2604 OID 246725)
-- Name: booking id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY booking ALTER COLUMN id SET DEFAULT nextval('booking_id_seq'::regclass);


--
-- TOC entry 2769 (class 2604 OID 246752)
-- Name: booking_status id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY booking_status ALTER COLUMN id SET DEFAULT nextval('booking_status_id_seq'::regclass);


--
-- TOC entry 2770 (class 2604 OID 246762)
-- Name: comment id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY comment ALTER COLUMN id SET DEFAULT nextval('comment_id_seq'::regclass);


--
-- TOC entry 2763 (class 2604 OID 246697)
-- Name: employee id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY employee ALTER COLUMN id SET DEFAULT nextval('employee_id_seq'::regclass);


--
-- TOC entry 2764 (class 2604 OID 246705)
-- Name: guest id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY guest ALTER COLUMN id SET DEFAULT nextval('guest_id_seq'::regclass);


--
-- TOC entry 2765 (class 2604 OID 246715)
-- Name: guest_status id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY guest_status ALTER COLUMN id SET DEFAULT nextval('guest_status_id_seq'::regclass);


--
-- TOC entry 2771 (class 2604 OID 246773)
-- Name: maintenance id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY maintenance ALTER COLUMN id SET DEFAULT nextval('maintenance_id_seq'::regclass);


--
-- TOC entry 2775 (class 2604 OID 246818)
-- Name: message id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY message ALTER COLUMN id SET DEFAULT nextval('message_id_seq'::regclass);


--
-- TOC entry 2762 (class 2604 OID 246687)
-- Name: photo_link id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY photo_link ALTER COLUMN id SET DEFAULT nextval('photo_link_id_seq'::regclass);


--
-- TOC entry 2773 (class 2604 OID 246794)
-- Name: post id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY post ALTER COLUMN id SET DEFAULT nextval('post_id_seq'::regclass);


--
-- TOC entry 2761 (class 2604 OID 246674)
-- Name: room id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY room ALTER COLUMN id SET DEFAULT nextval('room_id_seq'::regclass);


--
-- TOC entry 2768 (class 2604 OID 246741)
-- Name: task id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY task ALTER COLUMN id SET DEFAULT nextval('task_id_seq'::regclass);


--
-- TOC entry 2774 (class 2604 OID 246807)
-- Name: unstructured_object id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY unstructured_object ALTER COLUMN id SET DEFAULT nextval('unstructured_object_id_seq'::regclass);


--
-- TOC entry 2772 (class 2604 OID 246781)
-- Name: user_account id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_account ALTER COLUMN id SET DEFAULT nextval('user_account_id_seq'::regclass);


--
-- TOC entry 2975 (class 0 OID 246730)
-- Dependencies: 209
-- Data for Name: booked_maintenance; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO booked_maintenance (id, user_account_id, maintenance_id, "time", price, created, updated) VALUES (1, 2, 2, NULL, 100.00, '2018-06-07 19:15:28.854', '2018-06-07 19:15:28.854');
INSERT INTO booked_maintenance (id, user_account_id, maintenance_id, "time", price, created, updated) VALUES (2, 3, 10, NULL, 0.00, '2018-06-07 19:17:27', '2018-06-07 19:19:34.52');
INSERT INTO booked_maintenance (id, user_account_id, maintenance_id, "time", price, created, updated) VALUES (3, 2, 10, '2018-06-09 16:20:00', 100.00, '2018-06-08 12:13:37.67', '2018-06-08 12:13:37.67');
INSERT INTO booked_maintenance (id, user_account_id, maintenance_id, "time", price, created, updated) VALUES (4, 2, 4, '2018-06-09 14:10:00', 150.00, '2018-06-08 12:14:18.948', '2018-06-08 12:14:18.948');


--
-- TOC entry 2973 (class 0 OID 246722)
-- Dependencies: 207
-- Data for Name: booking; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (16, 1, 5, 3, '2018-05-01 00:00:00', '2018-06-04 00:00:00', 0.00, 0, '2018-06-07 17:57:18.046', '2018-06-07 17:57:18.046');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (17, 1, 28, 3, '2018-05-16 00:00:00', '2018-06-04 00:00:00', 0.00, 0, '2018-06-07 17:57:38.201', '2018-06-07 17:57:38.201');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (22, 1, 2, 3, '2018-06-18 00:00:00', '2018-06-30 00:00:00', 0.00, 0, '2018-06-07 17:58:53.197', '2018-06-07 17:58:53.197');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (23, 1, 7, 3, '2018-06-11 00:00:00', '2018-06-14 00:00:00', 0.00, 0, '2018-06-07 17:59:14.033', '2018-06-07 17:59:14.033');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (15, 3, 1, 3, '2018-05-22 00:00:00', '2018-06-01 00:00:00', 0.00, 1, '2018-06-07 17:56:59.815', '2018-06-07 18:08:36.566');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (3, 2, 5, 5, '2018-06-10 00:00:00', '2018-06-16 00:00:00', 30.00, 1, '2018-06-07 17:51:33.086', '2018-06-07 18:16:45.815');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (21, 4, 5, 3, '2018-06-05 00:00:00', '2018-06-09 00:00:00', 0.00, 1, '2018-06-07 17:58:38.826', '2018-06-07 18:17:01.073');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (18, 3, 7, 3, '2018-05-29 00:00:00', '2018-06-06 00:00:00', 0.00, 1, '2018-06-07 17:57:57.846', '2018-06-07 18:17:13.168');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (19, 3, 8, 3, '2018-05-29 00:00:00', '2018-06-06 00:00:00', 0.00, 1, '2018-06-07 17:58:10.721', '2018-06-07 18:17:21.761');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (20, 4, 9, 3, '2018-05-31 00:00:00', '2018-06-09 00:00:00', 0.00, 1, '2018-06-07 17:58:26.416', '2018-06-07 18:17:33.122');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (24, 1, 31, 3, '2018-06-11 00:00:00', '2018-06-16 00:00:00', 0.00, 0, '2018-06-07 18:21:10.795', '2018-06-07 18:21:10.795');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (25, 1, 18, 3, '2018-06-10 00:00:00', '2018-06-22 00:00:00', 0.00, 0, '2018-06-07 18:21:40.792', '2018-06-07 18:21:40.792');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (26, 1, 23, 3, '2018-06-12 00:00:00', '2018-06-24 00:00:00', 0.00, 0, '2018-06-07 18:22:02.516', '2018-06-07 18:22:02.516');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (27, 1, 3, 3, '2018-06-18 00:00:00', '2018-06-26 00:00:00', 0.00, 0, '2018-06-07 18:22:21.939', '2018-06-07 18:22:21.939');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (28, 1, 29, 3, '2018-06-18 00:00:00', '2018-06-20 00:00:00', 0.00, 0, '2018-06-07 18:22:38.112', '2018-06-07 18:22:38.112');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (29, 1, 29, 3, '2018-05-01 00:00:00', '2018-05-15 00:00:00', 0.00, 0, '2018-06-07 18:22:58.519', '2018-06-07 18:22:58.519');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (30, 1, 23, 3, '2018-05-16 00:00:00', '2018-05-31 00:00:00', 0.00, 0, '2018-06-07 18:23:12.325', '2018-06-07 18:23:12.325');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (34, 1, 31, 2, '2018-06-19 00:00:00', '2018-06-30 00:00:00', 1100.00, 0, '2018-06-07 18:33:48.807', '2018-06-07 18:33:48.807');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (9, 1, 28, 2, '2018-06-11 00:00:00', '2018-06-23 00:00:00', 360.00, 1, '2018-06-07 17:53:49.938', '2018-06-07 18:47:18.262');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (10, 1, 1, 2, '2018-06-04 00:00:00', '2018-06-21 00:00:00', 170.00, 2, '2018-06-07 17:54:14.729', '2018-06-07 18:47:43.887');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (1, 1, 1, 2, '2018-05-02 00:00:00', '2018-05-13 00:00:00', 110.00, 1, '2018-06-07 17:50:12.939', '2018-06-07 18:47:47.014');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (2, 1, 2, 2, '2018-06-05 00:00:00', '2018-06-16 00:00:00', 165.00, 2, '2018-06-07 17:51:14.976', '2018-06-07 18:47:48.883');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (11, 1, 3, 2, '2018-06-03 00:00:00', '2018-06-12 00:00:00', 270.00, 2, '2018-06-07 17:54:31.382', '2018-06-07 18:47:51.279');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (12, 1, 4, 2, '2018-06-03 00:00:00', '2018-06-06 00:00:00', 54.00, 2, '2018-06-07 17:54:43.757', '2018-06-07 18:47:58.371');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (13, 1, 6, 2, '2018-06-03 00:00:00', '2018-06-19 00:00:00', 480.00, 2, '2018-06-07 17:54:56.877', '2018-06-07 18:49:52.882');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (35, 1, 8, 2, '2018-06-19 00:00:00', '2018-06-21 00:00:00', 80.00, 3, '2018-06-07 18:46:25.479', '2018-06-07 18:50:00.801');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (4, 1, 10, 2, '2018-06-17 00:00:00', '2018-06-23 00:00:00', 480.00, 1, '2018-06-07 17:51:48.675', '2018-06-07 18:50:05.046');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (14, 1, 4, 2, '2018-06-07 00:00:00', '2018-06-19 00:00:00', 216.00, 2, '2018-06-07 17:55:12.176', '2018-06-07 18:50:09.34');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (6, 1, 12, 2, '2018-06-26 00:00:00', '2018-06-30 00:00:00', 280.00, 1, '2018-06-07 17:52:48.635', '2018-06-07 18:50:27.202');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (31, 1, 24, 2, '2018-06-11 00:00:00', '2018-06-20 00:00:00', 630.00, 1, '2018-06-07 18:23:48.938', '2018-06-07 18:50:31.072');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (7, 1, 26, 2, '2018-06-19 00:00:00', '2018-06-27 00:00:00', 400.00, 3, '2018-06-07 17:53:04.57', '2018-06-07 18:50:38.468');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (33, 1, 32, 2, '2018-06-17 00:00:00', '2018-06-19 00:00:00', 100.00, 3, '2018-06-07 18:24:23.753', '2018-06-07 18:50:44.534');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (8, 4, 11, 2, '2018-06-05 00:00:00', '2018-06-15 00:00:00', 600.00, 3, '2018-06-07 17:53:29.309', '2018-06-07 19:20:03.905');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (5, 4, 12, 2, '2018-05-16 00:00:00', '2018-06-22 00:00:00', 2590.00, 3, '2018-06-07 17:52:16.27', '2018-06-07 19:20:15.712');
INSERT INTO booking (id, booking_status_id, room_id, user_account_id, check_in, check_out, room_price, version, created, updated) VALUES (32, 4, 30, 2, '2018-06-04 00:00:00', '2018-06-25 00:00:00', 2520.00, 2, '2018-06-07 18:24:06.978', '2018-06-07 19:20:31.429');


--
-- TOC entry 2979 (class 0 OID 246749)
-- Dependencies: 213
-- Data for Name: booking_status; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO booking_status (id, name, color, created, updated) VALUES (1, 'забронирован', 'rgb(17,96,159)', '2018-06-07 17:43:32.499', '2018-06-07 17:43:32.499');
INSERT INTO booking_status (id, name, color, created, updated) VALUES (2, 'ремонт', 'rgb(139,35,27)', '2018-06-07 17:43:52.48', '2018-06-07 17:43:52.48');
INSERT INTO booking_status (id, name, color, created, updated) VALUES (3, 'оплачен', 'rgb(29,104,51)', '2018-06-07 17:44:17.549', '2018-06-07 17:44:17.549');
INSERT INTO booking_status (id, name, color, created, updated) VALUES (4, 'проживание', 'rgb(122,96,194)', '2018-06-07 17:45:38.64', '2018-06-07 17:45:38.64');


--
-- TOC entry 2981 (class 0 OID 246759)
-- Dependencies: 215
-- Data for Name: comment; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO comment (id, room_id, user_account_id, comment, created, updated) VALUES (1, 1, 2, 'отличный номер, жил там', '2018-06-08 16:17:18.078', '2018-06-08 16:17:18.078');
INSERT INTO comment (id, room_id, user_account_id, comment, created, updated) VALUES (2, 3, 2, 'отличный номер, жил 2 раза', '2018-06-08 16:17:36.873', '2018-06-08 16:17:36.873');
INSERT INTO comment (id, room_id, user_account_id, comment, created, updated) VALUES (3, 4, 2, 'моря не видно из него, все не правда', '2018-06-08 16:17:59.313', '2018-06-08 16:17:59.313');
INSERT INTO comment (id, room_id, user_account_id, comment, created, updated) VALUES (4, 5, 2, 'ужасный номер, больше ни ногой', '2018-06-08 16:18:16.576', '2018-06-08 16:18:16.576');
INSERT INTO comment (id, room_id, user_account_id, comment, created, updated) VALUES (5, 7, 2, 'никак не для троих. третий еле влез', '2018-06-08 16:18:41.361', '2018-06-08 16:18:41.361');
INSERT INTO comment (id, room_id, user_account_id, comment, created, updated) VALUES (6, 1, 1, 'спасибо за ваш комментарий', '2018-06-08 16:26:23.318', '2018-06-08 16:26:23.318');
INSERT INTO comment (id, room_id, user_account_id, comment, created, updated) VALUES (7, 31, 3, 'отличный номер, всегда в нем живу', '2018-06-08 16:49:44.069', '2018-06-08 16:49:44.069');
INSERT INTO comment (id, room_id, user_account_id, comment, created, updated) VALUES (8, 25, 3, 'для ребенка места мало', '2018-06-08 16:50:09.288', '2018-06-08 16:50:09.288');
INSERT INTO comment (id, room_id, user_account_id, comment, created, updated) VALUES (9, 12, 3, 'бассейн шикарный', '2018-06-08 16:50:31.13', '2018-06-08 16:50:31.13');
INSERT INTO comment (id, room_id, user_account_id, comment, created, updated) VALUES (10, 23, 3, 'места третьему нету', '2018-06-08 16:50:50.084', '2018-06-08 16:50:50.084');
INSERT INTO comment (id, room_id, user_account_id, comment, created, updated) VALUES (11, 4, 3, 'ужасный номер. никогда сюда не поеду больше', '2018-06-08 16:51:34.542', '2018-06-08 16:51:34.542');
INSERT INTO comment (id, room_id, user_account_id, comment, created, updated) VALUES (12, 28, 3, 'не кровать, а раскладушка какая-то', '2018-06-08 16:52:15.607', '2018-06-08 16:52:15.607');


--
-- TOC entry 2967 (class 0 OID 246694)
-- Dependencies: 201
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO employee (id, hiring, layoff, post_id, created, updated) VALUES (1, '2018-06-06 00:00:00', NULL, 1, '2018-06-07 14:13:43.316', '2018-06-07 14:13:43.316');
INSERT INTO employee (id, hiring, layoff, post_id, created, updated) VALUES (4, '2018-06-06 00:00:00', NULL, 3, '2018-06-07 17:03:41.544', '2018-06-07 17:06:08.84');
INSERT INTO employee (id, hiring, layoff, post_id, created, updated) VALUES (5, '2018-06-06 00:00:00', NULL, 4, '2018-06-07 17:06:52.753', '2018-06-07 17:06:52.753');


--
-- TOC entry 2969 (class 0 OID 246702)
-- Dependencies: 203
-- Data for Name: guest; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO guest (id, verify_key, verified, credit, guest_status_id, created, updated) VALUES (2, 'sht52yhg4526tertwerte3w', true, 0.00, 1, '2018-06-07 17:00:42.058', '2018-06-07 17:00:42.058');
INSERT INTO guest (id, verify_key, verified, credit, guest_status_id, created, updated) VALUES (3, '67i6mn567ih354h756', true, 0.00, 2, '2018-06-07 17:01:54.408', '2018-06-07 17:02:14.17');
INSERT INTO guest (id, verify_key, verified, credit, guest_status_id, created, updated) VALUES (6, 'CBWwiWVU1dL02esG0LlU', false, 0.00, 1, '2018-06-08 16:58:01.886', '2018-06-08 16:58:01.886');


--
-- TOC entry 2971 (class 0 OID 246712)
-- Dependencies: 205
-- Data for Name: guest_status; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO guest_status (id, name, discount, created, updated) VALUES (1, 'гость', 0, '2018-06-07 16:59:39.157', '2018-06-07 16:59:39.157');
INSERT INTO guest_status (id, name, discount, created, updated) VALUES (2, 'VIP', 100, '2018-06-07 17:02:04.753', '2018-06-07 17:02:04.753');


--
-- TOC entry 2983 (class 0 OID 246770)
-- Dependencies: 217
-- Data for Name: maintenance; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO maintenance (id, name, actual_price, available, photo_link, created, updated) VALUES (1, 'бесплатная стоянка', 0.00, true, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1Hgl2XZNhsKLYOUPUbKGJv1naLM_xakgx', '2018-06-07 17:35:32.349', '2018-06-07 17:35:32.349');
INSERT INTO maintenance (id, name, actual_price, available, photo_link, created, updated) VALUES (2, 'аренда квадроциклов', 100.00, true, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=16eqm05S_GI4SMx2ij0wWR1b_zk-VoBls', '2018-06-07 17:36:05.703', '2018-06-07 17:36:05.703');
INSERT INTO maintenance (id, name, actual_price, available, photo_link, created, updated) VALUES (3, 'раскладушка по запросу', 0.00, true, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1zIs9qW4wNtDoGsr7J4GXtZhxSiZis0EU ', '2018-06-07 17:36:41.55', '2018-06-07 17:36:41.55');
INSERT INTO maintenance (id, name, actual_price, available, photo_link, created, updated) VALUES (4, 'свадебный лимузин', 150.00, true, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1hAf8KJ6npnTx92nGKQ2L77fW62mmKlrp ', '2018-06-07 17:37:10.013', '2018-06-07 17:37:10.013');
INSERT INTO maintenance (id, name, actual_price, available, photo_link, created, updated) VALUES (5, 'круглосуточный бар', 0.00, true, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1bsh-VIaX9UEultS6NMl8PE0RulgkWv3b ', '2018-06-07 17:37:37.62', '2018-06-07 17:37:37.62');
INSERT INTO maintenance (id, name, actual_price, available, photo_link, created, updated) VALUES (6, 'трансфер', 50.00, false, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1txFE8SvLJDklq-DKfjfSsLW5qQBngar_ ', '2018-06-07 17:39:13.412', '2018-06-07 17:39:13.412');
INSERT INTO maintenance (id, name, actual_price, available, photo_link, created, updated) VALUES (7, 'водные аттракционы', 0.00, true, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1MdNAKOAY5tNJfluyM8H2BQ5f4RpwNMYD ', '2018-06-07 17:39:42.39', '2018-06-07 17:39:42.39');
INSERT INTO maintenance (id, name, actual_price, available, photo_link, created, updated) VALUES (8, 'круглосуточная кухня', 10.00, true, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=14GEbbfYQuuNrY7QxtmqIXojTIdvjv500 ', '2018-06-07 17:40:35.152', '2018-06-07 17:40:35.152');
INSERT INTO maintenance (id, name, actual_price, available, photo_link, created, updated) VALUES (9, 'настольные игры', 0.00, true, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1J3_uWoD5gDclZBn7XzZIZQLHWqZbb-Gn ', '2018-06-07 17:41:09.161', '2018-06-07 17:41:09.161');
INSERT INTO maintenance (id, name, actual_price, available, photo_link, created, updated) VALUES (10, 'банкетный зал', 100.00, true, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1I-ElOtavox0T_EWp2qVlwPggs9_xmSIT ', '2018-06-07 17:41:48.32', '2018-06-07 17:41:48.32');
INSERT INTO maintenance (id, name, actual_price, available, photo_link, created, updated) VALUES (11, 'ужедневная уборка', 0.00, true, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1Dj7ktkBgf_IVx9xVFeCyvL5vLtgbqZbH ', '2018-06-07 17:42:25.658', '2018-06-07 17:42:25.658');


--
-- TOC entry 2991 (class 0 OID 246815)
-- Dependencies: 225
-- Data for Name: message; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2965 (class 0 OID 246684)
-- Dependencies: 199
-- Data for Name: photo_link; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (1, 1, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1Z1k_oWYmLLzunQSDgGNCJy7Dc95-FCBL', '2018-06-07 14:36:56.688', '2018-06-07 14:36:56.688');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (2, 1, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=12wnJ0jFyVRGgWHNRIOZdsjJ0vCuzhVa-', '2018-06-07 14:37:04.773', '2018-06-07 14:37:04.773');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (3, 1, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=13Igo0_3-2nrLNaQQJRNMbXYyCFrrIAf2', '2018-06-07 14:37:29.512', '2018-06-07 14:37:29.512');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (4, 2, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1Y7sVz9CbqcxkLlHYYBdQ-Zi0fBLFl6Au', '2018-06-07 14:37:45.616', '2018-06-07 14:37:45.616');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (5, 2, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1agzC-tfgAL1DvEP1EcsexHGHtEpeDXMG', '2018-06-07 14:37:57.195', '2018-06-07 14:37:57.195');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (6, 3, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1jYmu2j6SqB5UU_jNtSJiBkQd0KUZF9NH', '2018-06-07 14:38:09.596', '2018-06-07 14:38:09.596');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (7, 3, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1SFnlt4gPQNqfqY9RKNZRw5iPPTLY3C1w', '2018-06-07 14:38:18.62', '2018-06-07 14:38:18.62');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (8, 4, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=11bagBB5G0CwuJCxchCgf-L5bvIFfnJA0', '2018-06-07 14:38:28.25', '2018-06-07 14:38:28.25');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (9, 4, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=18kKVLrV-_cRLL6QLSdcYZyOYp-KEd0Kv', '2018-06-07 14:38:37.734', '2018-06-07 14:38:37.734');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (10, 4, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1pNpIbZafaQjSgqPK0AYEF2VOycLbgedV', '2018-06-07 14:38:47.731', '2018-06-07 14:38:47.731');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (11, 5, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1RvvvrRnNNBsanYDSuRLB4mS_-CV7PS99', '2018-06-07 14:38:57.679', '2018-06-07 14:38:57.679');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (12, 5, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1ReKz3WkEWb11VDR_zoPakTdSahGoAZ_W', '2018-06-07 14:39:17.825', '2018-06-07 14:39:17.825');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (13, 5, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1K29LDZukwHScE6-7imd61RP7ZVBtYzlP', '2018-06-07 14:39:26.703', '2018-06-07 14:39:26.703');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (14, 4, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1k5om5eeUkI_5oVVxowpm5Xm9Vs5apdHk', '2018-06-07 14:39:35.243', '2018-06-07 14:39:35.243');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (15, 5, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1isn2YFEfLm8BZ7I9PV6zYm6Ib2ndvNDN', '2018-06-07 14:39:48.658', '2018-06-07 14:39:48.658');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (16, 6, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1x-oX05Sp3vpZJ2pNl-M5bzhFn8DgdIOe', '2018-06-07 14:39:57.312', '2018-06-07 14:39:57.312');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (17, 7, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=12hZZq6alKPAvirsRSQMo5uHsi_5G9V2d', '2018-06-07 14:40:08.164', '2018-06-07 14:40:08.164');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (18, 8, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1DCbkSSTbyRo41_VCV1SEDI-NxDjj7Mf2', '2018-06-07 14:40:18.4', '2018-06-07 14:40:18.4');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (19, 7, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1R79rv42FosAxJkZaisYUx9tR6XC97EoS', '2018-06-07 14:40:27.992', '2018-06-07 14:40:27.992');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (20, 9, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1CLzkdY0S6vSC-Upiw6Hw3CZwQchV6mLJ', '2018-06-07 14:40:40.773', '2018-06-07 14:40:40.773');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (21, 10, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1RIedl6MsBoEjpx9_nMUR8uAFMS4zz2_q', '2018-06-07 14:45:05.291', '2018-06-07 14:45:05.291');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (22, 10, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1zBaGi2FcxibtxVNNaabKoKiBoPeRCLoD', '2018-06-07 14:45:15.637', '2018-06-07 14:45:15.637');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (23, 11, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1CoLG-YcHImAqLWVfU-T4qOmfcBr93kod', '2018-06-07 14:45:39.305', '2018-06-07 14:45:39.305');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (24, 12, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1fkwC84Hihm-A_ihuh2dYQ5GpZlluPo0q', '2018-06-07 14:45:52.87', '2018-06-07 14:45:52.87');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (25, 14, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1TICWb5VBw4JUHc3oVdBqIbyzWnpzA-B2', '2018-06-07 14:46:05.839', '2018-06-07 14:46:05.839');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (26, 15, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=10ZPgP9Ev4xsEoqX4TxP75zEv_1Bws2Nw', '2018-06-07 14:46:16.129', '2018-06-07 14:46:16.129');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (27, 17, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1xcIqODLcEOcwgAlizZS_yEYbWdJzRx_s', '2018-06-07 14:46:28.272', '2018-06-07 14:46:28.272');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (28, 18, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1J03rFmAldOUSCFDke_AK84QQJLwD3H8u', '2018-06-07 14:46:52.652', '2018-06-07 14:46:52.652');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (29, 23, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1qJ7zHl3cxbD6Di_BeiWDWqMdYB_j-Zbq', '2018-06-07 14:47:08.291', '2018-06-07 14:47:08.291');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (30, 24, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1Z9iCHpPYXz0w3-01mLS_Xxx91pAXZo1V', '2018-06-07 14:47:25.297', '2018-06-07 14:47:25.297');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (31, 25, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=10SRKNM_cncDh1zeB2TOGot6wo8UPOgmo', '2018-06-07 14:47:42.402', '2018-06-07 14:47:42.402');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (32, 26, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=12dVfRgkighLjzigooivcdUGqIZnvXzfJ', '2018-06-07 14:47:56.152', '2018-06-07 14:47:56.152');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (33, 27, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1uZFro1dzQq5n1hllRIPrna3M18c143UC', '2018-06-07 14:48:08.321', '2018-06-07 14:48:08.321');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (34, 28, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1m41maJp8kWSWfXBotvhwuDBespKyUmwY', '2018-06-07 14:48:21.826', '2018-06-07 14:48:21.826');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (35, 29, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1MkXe4a3G5FVe2GiowYu4RdBZeXTfrRok', '2018-06-07 14:48:35.57', '2018-06-07 14:48:35.57');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (36, 30, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1NW_LuYwwgWHC8791GRzmcASB3658lvtC', '2018-06-07 14:48:48.189', '2018-06-07 14:48:48.189');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (37, 32, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1jwjn1-D7hh7uY8pXZ6CzlpDJJWsauW1d', '2018-06-07 14:49:01.016', '2018-06-07 14:49:01.016');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (38, 31, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1xoKHPOWPEsspuKKzpmYcE1e9eSluN6bs', '2018-06-07 14:49:13.599', '2018-06-07 14:49:13.599');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (39, 1, 1, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1DoCSYd69zqpCbfePdfRb4OarMZ9hsOyO', '2018-06-07 23:00:05.09', '2018-06-07 23:00:05.09');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (41, 8, 2, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1ABzjxGjS9HCyDam9IJ4-N84xQ8kvn-hj', '2018-06-08 16:53:46.027', '2018-06-08 16:53:46.027');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (43, 3, 2, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1CTy6dLOx024xD7j4Tgm5QXzVHU9p7rVs', '2018-06-08 16:54:24.932', '2018-06-08 16:54:24.932');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (44, 6, 2, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1wwoFfyeROeeQxIjnN3HQjVFmaSrUkQtN', '2018-06-08 16:54:40.452', '2018-06-08 16:54:40.452');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (45, 9, 2, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1_eAVt-mDw8D2IkgCGJFfsjkgoXbMnKKt', '2018-06-08 16:54:56.983', '2018-06-08 16:54:56.983');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (46, 12, 2, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1jlBs-obNuVR8eH6ms3WSmcdzjupwLq50', '2018-06-08 16:55:13.298', '2018-06-08 16:55:13.298');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (47, 10, 2, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1litg14S_TFCj6mWZcCoQvmIEtP0OII_4', '2018-06-08 16:55:36.503', '2018-06-08 16:55:36.503');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (48, 28, 2, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1gsAQN1hiDteAJ-2AE3EJ9oT2JxNPe2HK', '2018-06-08 16:55:57.795', '2018-06-08 16:55:57.795');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (49, 26, 2, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1Ak3py3aLc_auBzHlIIh7dIGbEsycq89J', '2018-06-08 16:56:28.345', '2018-06-08 16:56:28.345');
INSERT INTO photo_link (id, room_id, user_account_id, link, created, updated) VALUES (50, 32, 2, 'https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1vspE9gkxcQDa9Mjw3Siyg9cr8sHDmFgL', '2018-06-08 16:56:46.638', '2018-06-08 16:56:46.638');


--
-- TOC entry 2987 (class 0 OID 246791)
-- Dependencies: 221
-- Data for Name: post; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO post (id, name, description, supervisor_id, created, updated) VALUES (1, 'admin', 'admin', NULL, '2018-06-07 14:12:10.785', '2018-06-07 14:12:10.785');
INSERT INTO post (id, name, description, supervisor_id, created, updated) VALUES (2, 'director', 'director', NULL, '2018-06-07 14:12:24.832', '2018-06-07 14:12:24.832');
INSERT INTO post (id, name, description, supervisor_id, created, updated) VALUES (3, 'reseptionist', 'reseption', 2, '2018-06-07 17:05:37.962', '2018-06-07 17:05:37.962');
INSERT INTO post (id, name, description, supervisor_id, created, updated) VALUES (4, 'employee', 'employee', 2, '2018-06-07 17:05:55.831', '2018-06-07 17:05:55.831');


--
-- TOC entry 2963 (class 0 OID 246671)
-- Dependencies: 197
-- Data for Name: room; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (2, 101, 1, 'Standard', 'Triple', 'CityView', 15.00, 'стандартный номер для троих человек', false, false, '2018-06-07 14:17:20.364', '2018-06-07 14:17:20.364');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (1, 100, 1, 'KingSuite', 'Double', 'CityView', 10.00, 'один из лучших наших номеров', false, false, '2018-06-07 14:16:47.32', '2018-06-07 14:17:37.275');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (3, 102, 1, 'Standard', 'Double', 'PoolView', 30.00, 'стандартный номер для двоих с видом на бассейн', true, false, '2018-06-07 14:18:12.354', '2018-06-07 14:18:12.354');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (4, 103, 1, 'Standard', 'Double', 'SeaView', 18.00, 'стандартный номер для двоих с видом на море', false, false, '2018-06-07 14:18:51.178', '2018-06-07 14:18:51.178');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (5, 104, 1, 'Standard', 'Double', 'InsideView', 30.00, 'стандартный номер для двоих', false, true, '2018-06-07 14:19:29.435', '2018-06-07 14:19:29.435');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (6, 105, 1, 'KingSuite', 'Single', 'CityView', 30.00, 'наш лучший номер для одного', false, false, '2018-06-07 14:20:05.849', '2018-06-07 14:20:05.849');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (7, 106, 1, 'Standard', 'Triple', 'PoolView', 40.00, 'стандартный номер для троих', true, false, '2018-06-07 14:20:36.9', '2018-06-07 14:20:36.9');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (8, 107, 1, 'Standard', 'Single', 'SeaView', 40.00, 'стандартный номер для одного с видом на море', false, false, '2018-06-07 14:21:07.251', '2018-06-07 14:21:07.251');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (9, 200, 2, 'KingSuite', 'ADL5', 'SeaView', 100.00, 'наш лучший номер для компании из пяти человек', false, false, '2018-06-07 14:21:49.625', '2018-06-07 14:21:49.625');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (10, 201, 2, 'PresidentSuite', 'Triple', 'PoolView', 80.00, 'президентский сьют для троих', true, false, '2018-06-07 14:22:21.554', '2018-06-07 14:22:21.554');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (11, 202, 2, 'Standard', 'ADL5', 'PoolView', 60.00, 'стандартный номер для пятерых', false, true, '2018-06-07 14:22:51.585', '2018-06-07 14:22:51.585');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (12, 203, 2, 'PoolAccess', 'Triple', 'PoolView', 70.00, 'номер для троих с выходом к бассейну', false, false, '2018-06-07 14:23:30.892', '2018-06-07 14:23:30.892');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (14, 204, 2, 'Standard', 'DBL_ЕХВ', 'CityView', 60.00, 'стандартный номер для двоих с ребенком', false, false, '2018-06-07 14:24:09.58', '2018-06-07 14:24:09.58');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (15, 205, 2, 'HoneymoonSuite', 'Double', 'SeaView', 100.00, 'номер для новобрачных', false, false, '2018-06-07 14:24:37.449', '2018-06-07 14:24:37.449');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (17, 206, 2, 'Standard', 'Double', 'CityView', 50.00, 'стандартный номер для двоих', false, false, '2018-06-07 14:25:15.682', '2018-06-07 14:25:15.682');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (18, 300, 3, 'Standard', 'Triple', 'CityView', 70.00, 'стандартный номер для троих', true, false, '2018-06-07 14:25:39.273', '2018-06-07 14:25:39.273');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (24, 302, 3, 'Standard', 'Triple', 'PoolView', 70.00, 'стандартный номер для троих', true, false, '2018-06-07 14:26:48.274', '2018-06-07 14:26:48.274');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (25, 303, 3, 'Standard', 'DBL_ЕХВ', 'PoolView', 70.00, 'стандартный номер для двоих с ребенком', false, false, '2018-06-07 14:27:20.201', '2018-06-07 14:27:20.201');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (26, 304, 3, 'Standard', 'Twin', 'GardenView', 50.00, 'стандартный номер для двоих', true, false, '2018-06-07 14:27:54.724', '2018-06-07 14:27:54.724');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (27, 305, 3, 'Standard', 'Twin', 'InsideView', 60.00, 'стандартный номер для двоих', false, true, '2018-06-07 14:28:17.53', '2018-06-07 14:28:17.53');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (28, 400, 4, 'Standard', 'Single', 'PoolView', 30.00, 'стандартный номер для одного', false, false, '2018-06-07 14:28:40.893', '2018-06-07 14:28:40.893');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (29, 401, 4, 'Standard', 'Single', 'CityView', 50.00, 'стандартный номер для одного', false, true, '2018-06-07 14:29:03.519', '2018-06-07 14:29:03.519');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (30, 402, 4, 'Standard', 'Quadriple', 'GardenView', 120.00, 'стандартный номер для четверых', false, false, '2018-06-07 14:29:40.332', '2018-06-07 14:29:40.332');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (31, 405, 4, 'PresidentSuite', 'ADL5', 'PoolView', 100.00, 'президетский сьют', false, false, '2018-06-07 14:30:19.615', '2018-06-07 14:30:19.615');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (32, 404, 4, 'Standard', 'Double', 'PoolView', 50.00, 'стандартный номер для двоих', true, false, '2018-06-07 14:30:40.608', '2018-06-07 14:30:40.608');
INSERT INTO room (id, number, floor, type, accomodation, view, actual_price, description, dirty, broken, created, updated) VALUES (23, 301, 3, 'Standard', 'Triple', 'CityView', 70.00, 'стандартный номер для троих', false, true, '2018-06-07 14:26:26.513', '2018-06-07 14:34:31.969');


--
-- TOC entry 2977 (class 0 OID 246738)
-- Dependencies: 211
-- Data for Name: task; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO task (id, to_do, description, priority, execution_time, answerable_id, executed, creator_id, version, created, updated) VALUES (1, 'ремонт 104', 'отремонтировать замок', 'high', '2018-06-08 22:50:00', 5, false, 1, 2, '2018-06-07 17:08:54.062', '2018-06-07 17:12:00.767');
INSERT INTO task (id, to_do, description, priority, execution_time, answerable_id, executed, creator_id, version, created, updated) VALUES (2, '200', 'заменить телевизор в 200', 'extra', '2018-06-08 17:10:00', 5, false, 1, 2, '2018-06-07 17:10:23.761', '2018-06-07 17:12:20.571');
INSERT INTO task (id, to_do, description, priority, execution_time, answerable_id, executed, creator_id, version, created, updated) VALUES (4, 'бассейн', 'убрать возле бассейна', 'normal', '2018-06-09 17:13:00', 5, false, 1, 1, '2018-06-07 17:13:07.142', '2018-06-07 17:13:07.142');
INSERT INTO task (id, to_do, description, priority, execution_time, answerable_id, executed, creator_id, version, created, updated) VALUES (5, 'будильник', 'разбудить гостя в 200', 'normal', '2018-06-09 08:15:00', 1, false, 5, 1, '2018-06-07 17:14:27.714', '2018-06-07 17:14:27.714');
INSERT INTO task (id, to_do, description, priority, execution_time, answerable_id, executed, creator_id, version, created, updated) VALUES (6, 'подстричь траву', 'подстричь траву вокруг отеля', 'normal', '2018-06-10 17:15:00', 5, false, 1, 1, '2018-06-07 17:15:35.577', '2018-06-07 17:15:35.577');
INSERT INTO task (id, to_do, description, priority, execution_time, answerable_id, executed, creator_id, version, created, updated) VALUES (7, 'заключить договор', 'на обслуживание свадьбы', 'high', '2018-06-08 17:16:00', 1, false, 1, 1, '2018-06-07 17:16:20.549', '2018-06-07 17:16:20.549');
INSERT INTO task (id, to_do, description, priority, execution_time, answerable_id, executed, creator_id, version, created, updated) VALUES (3, '202 ремонт', 'проверить кран после выезда гостей', 'low', '2018-06-15 17:24:00', 5, false, 1, 2, '2018-06-07 17:11:30.575', '2018-06-07 18:19:11.681');


--
-- TOC entry 2989 (class 0 OID 246804)
-- Dependencies: 223
-- Data for Name: unstructured_object; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO unstructured_object (id, name, content, user_account_id, created, updated) VALUES (1, 'locale', '\xaced0005737200106a6176612e7574696c2e4c6f63616c657ef811609c30f9ec03000649000868617368636f64654c0007636f756e7472797400124c6a6176612f6c616e672f537472696e673b4c000a657874656e73696f6e7371007e00014c00086c616e677561676571007e00014c000673637269707471007e00014c000776617269616e7471007e00017870ffffffff74000071007e0003740002656e71007e000371007e000378', 1, '2018-06-08 12:30:10.084', '2018-06-08 13:18:27.224');


--
-- TOC entry 2985 (class 0 OID 246778)
-- Dependencies: 219
-- Data for Name: user_account; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO user_account (id, email, password, role, first_name, last_name, birthday, address, phone, created, updated) VALUES (1, 'admin@tut.by', '$2a$10$eELNMDt83GioB.lYrytno.i9ulNZPudoiqp5/4fCzCzxIzZCGDame', 'ROLE_ADMIN', 'admin', 'admin', '2018-06-06 00:00:00', 'Grodno', '+375222222222', '2018-06-07 14:13:43.316', '2018-06-07 14:13:43.316');
INSERT INTO user_account (id, email, password, role, first_name, last_name, birthday, address, phone, created, updated) VALUES (2, 'guest@tut.by', '$2a$10$7u5c/qlmFPz6I5HugAAaWeSQbzrKvPTrWOzWIKXW0hxtai7K9ZtKu', 'ROLE_GUEST', 'guest', 'guest', '2018-06-06 00:00:00', 'Grodno', '2252525252', '2018-06-07 17:00:42.058', '2018-06-07 17:00:42.058');
INSERT INTO user_account (id, email, password, role, first_name, last_name, birthday, address, phone, created, updated) VALUES (3, 'guestVIP@tut.by', '$2a$10$VGO7NMbx2.zv0AdgeUxe3OJFFHPcYUM8di5.XaBOQGnRJJkQULmDW', 'ROLE_GUEST', 'guestVIP', 'guestVIP', '2018-06-06 00:00:00', 'Grodno', '14564645645', '2018-06-07 17:01:54.408', '2018-06-07 17:02:14.17');
INSERT INTO user_account (id, email, password, role, first_name, last_name, birthday, address, phone, created, updated) VALUES (4, 'reseption@tut.by', '$2a$10$rYOdKEoYwSnFaVoh9jIGq.bmiyOt/ZXxyFXGJhT9a/eYo5rbXLsyi', 'ROLE_RESEPTION', 'reseption', 'reseption', '2018-06-06 00:00:00', 'Grodno', '12341241', '2018-06-07 17:03:41.544', '2018-06-07 17:06:08.84');
INSERT INTO user_account (id, email, password, role, first_name, last_name, birthday, address, phone, created, updated) VALUES (5, 'employee@tut.by', '$2a$10$z4ae9Nzl7.EZwNaauMB7peJBMxJFPlG9QjQS7yTo2LxxSwpKcQwm6', 'ROLE_EMPLOYEE', 'employee', 'employee', '2018-06-06 00:00:00', 'Grodno', '123423423', '2018-06-07 17:06:52.753', '2018-06-07 17:06:52.753');
INSERT INTO user_account (id, email, password, role, first_name, last_name, birthday, address, phone, created, updated) VALUES (6, 'pashka-85@mail.ru', '$2a$10$HMeBKueTNu/s6xm7luxuhOJzU50cmJlQ83iTKWyBkJx2XDc3u749S', 'ROLE_GUEST', '1', '1', '2018-06-07 00:00:00', '1', '1', '2018-06-08 16:58:01.886', '2018-06-08 16:58:01.886');


--
-- TOC entry 3014 (class 0 OID 0)
-- Dependencies: 208
-- Name: booked_maintenance_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('booked_maintenance_id_seq', 4, true);


--
-- TOC entry 3015 (class 0 OID 0)
-- Dependencies: 206
-- Name: booking_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('booking_id_seq', 35, true);


--
-- TOC entry 3016 (class 0 OID 0)
-- Dependencies: 212
-- Name: booking_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('booking_status_id_seq', 5, true);


--
-- TOC entry 3017 (class 0 OID 0)
-- Dependencies: 214
-- Name: comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('comment_id_seq', 12, true);


--
-- TOC entry 3018 (class 0 OID 0)
-- Dependencies: 200
-- Name: employee_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('employee_id_seq', 1, false);


--
-- TOC entry 3019 (class 0 OID 0)
-- Dependencies: 202
-- Name: guest_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('guest_id_seq', 1, false);


--
-- TOC entry 3020 (class 0 OID 0)
-- Dependencies: 204
-- Name: guest_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('guest_status_id_seq', 2, true);


--
-- TOC entry 3021 (class 0 OID 0)
-- Dependencies: 216
-- Name: maintenance_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('maintenance_id_seq', 11, true);


--
-- TOC entry 3022 (class 0 OID 0)
-- Dependencies: 224
-- Name: message_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('message_id_seq', 1, true);


--
-- TOC entry 3023 (class 0 OID 0)
-- Dependencies: 198
-- Name: photo_link_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('photo_link_id_seq', 50, true);


--
-- TOC entry 3024 (class 0 OID 0)
-- Dependencies: 220
-- Name: post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('post_id_seq', 4, true);


--
-- TOC entry 3025 (class 0 OID 0)
-- Dependencies: 196
-- Name: room_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('room_id_seq', 32, true);


--
-- TOC entry 3026 (class 0 OID 0)
-- Dependencies: 210
-- Name: task_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('task_id_seq', 7, true);


--
-- TOC entry 3027 (class 0 OID 0)
-- Dependencies: 222
-- Name: unstructured_object_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('unstructured_object_id_seq', 1, true);


--
-- TOC entry 3028 (class 0 OID 0)
-- Dependencies: 218
-- Name: user_account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('user_account_id_seq', 6, true);


--
-- TOC entry 2799 (class 2606 OID 246735)
-- Name: booked_maintenance booked_maintenance_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY booked_maintenance
    ADD CONSTRAINT booked_maintenance_pk PRIMARY KEY (id);


--
-- TOC entry 2795 (class 2606 OID 246727)
-- Name: booking booking_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY booking
    ADD CONSTRAINT booking_pk PRIMARY KEY (id);


--
-- TOC entry 2797 (class 2606 OID 246910)
-- Name: booking booking_room_id_check_in_check_out_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY booking
    ADD CONSTRAINT booking_room_id_check_in_check_out_key UNIQUE (room_id, check_in, check_out);


--
-- TOC entry 2803 (class 2606 OID 246756)
-- Name: booking_status booking_status_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY booking_status
    ADD CONSTRAINT booking_status_name_key UNIQUE (name);


--
-- TOC entry 2805 (class 2606 OID 246754)
-- Name: booking_status booking_status_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY booking_status
    ADD CONSTRAINT booking_status_pk PRIMARY KEY (id);


--
-- TOC entry 2807 (class 2606 OID 246767)
-- Name: comment comment_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY comment
    ADD CONSTRAINT comment_pk PRIMARY KEY (id);


--
-- TOC entry 2785 (class 2606 OID 246699)
-- Name: employee employee_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY employee
    ADD CONSTRAINT employee_pk PRIMARY KEY (id);


--
-- TOC entry 2787 (class 2606 OID 246707)
-- Name: guest guest_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY guest
    ADD CONSTRAINT guest_pk PRIMARY KEY (id);


--
-- TOC entry 2791 (class 2606 OID 246719)
-- Name: guest_status guest_status_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY guest_status
    ADD CONSTRAINT guest_status_name_key UNIQUE (name);


--
-- TOC entry 2793 (class 2606 OID 246717)
-- Name: guest_status guest_status_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY guest_status
    ADD CONSTRAINT guest_status_pk PRIMARY KEY (id);


--
-- TOC entry 2789 (class 2606 OID 246709)
-- Name: guest guest_verify_key_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY guest
    ADD CONSTRAINT guest_verify_key_key UNIQUE (verify_key);


--
-- TOC entry 2809 (class 2606 OID 246775)
-- Name: maintenance maintenance_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY maintenance
    ADD CONSTRAINT maintenance_pk PRIMARY KEY (id);


--
-- TOC entry 2823 (class 2606 OID 246823)
-- Name: message message_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY message
    ADD CONSTRAINT message_pk PRIMARY KEY (id);


--
-- TOC entry 2819 (class 2606 OID 246912)
-- Name: unstructured_object name_user_account_id__key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY unstructured_object
    ADD CONSTRAINT name_user_account_id__key UNIQUE (name, user_account_id);


--
-- TOC entry 2781 (class 2606 OID 246691)
-- Name: photo_link photo_link_link_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY photo_link
    ADD CONSTRAINT photo_link_link_key UNIQUE (link);


--
-- TOC entry 2783 (class 2606 OID 246689)
-- Name: photo_link photo_link_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY photo_link
    ADD CONSTRAINT photo_link_pk PRIMARY KEY (id);


--
-- TOC entry 2815 (class 2606 OID 246801)
-- Name: post post_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY post
    ADD CONSTRAINT post_name_key UNIQUE (name);


--
-- TOC entry 2817 (class 2606 OID 246799)
-- Name: post post_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY post
    ADD CONSTRAINT post_pk PRIMARY KEY (id);


--
-- TOC entry 2777 (class 2606 OID 246681)
-- Name: room room_number_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY room
    ADD CONSTRAINT room_number_key UNIQUE (number);


--
-- TOC entry 2779 (class 2606 OID 246679)
-- Name: room room_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY room
    ADD CONSTRAINT room_pk PRIMARY KEY (id);


--
-- TOC entry 2801 (class 2606 OID 246746)
-- Name: task task_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY task
    ADD CONSTRAINT task_pk PRIMARY KEY (id);


--
-- TOC entry 2821 (class 2606 OID 246812)
-- Name: unstructured_object unstructured_object_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY unstructured_object
    ADD CONSTRAINT unstructured_object_pk PRIMARY KEY (id);


--
-- TOC entry 2811 (class 2606 OID 246788)
-- Name: user_account user_account_email_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_account
    ADD CONSTRAINT user_account_email_key UNIQUE (email);


--
-- TOC entry 2813 (class 2606 OID 246786)
-- Name: user_account user_account_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_account
    ADD CONSTRAINT user_account_pk PRIMARY KEY (id);


--
-- TOC entry 2833 (class 2606 OID 246869)
-- Name: booked_maintenance booked_maintenance_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY booked_maintenance
    ADD CONSTRAINT booked_maintenance_fk0 FOREIGN KEY (user_account_id) REFERENCES user_account(id);


--
-- TOC entry 2834 (class 2606 OID 246874)
-- Name: booked_maintenance booked_maintenance_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY booked_maintenance
    ADD CONSTRAINT booked_maintenance_fk1 FOREIGN KEY (maintenance_id) REFERENCES maintenance(id);


--
-- TOC entry 2830 (class 2606 OID 246854)
-- Name: booking booking_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY booking
    ADD CONSTRAINT booking_fk0 FOREIGN KEY (booking_status_id) REFERENCES booking_status(id);


--
-- TOC entry 2831 (class 2606 OID 246859)
-- Name: booking booking_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY booking
    ADD CONSTRAINT booking_fk1 FOREIGN KEY (room_id) REFERENCES room(id);


--
-- TOC entry 2832 (class 2606 OID 246864)
-- Name: booking booking_fk2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY booking
    ADD CONSTRAINT booking_fk2 FOREIGN KEY (user_account_id) REFERENCES user_account(id);


--
-- TOC entry 2837 (class 2606 OID 246889)
-- Name: comment comment_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY comment
    ADD CONSTRAINT comment_fk0 FOREIGN KEY (room_id) REFERENCES room(id);


--
-- TOC entry 2838 (class 2606 OID 246894)
-- Name: comment comment_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY comment
    ADD CONSTRAINT comment_fk1 FOREIGN KEY (user_account_id) REFERENCES user_account(id);


--
-- TOC entry 2826 (class 2606 OID 246834)
-- Name: employee employee_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY employee
    ADD CONSTRAINT employee_fk0 FOREIGN KEY (id) REFERENCES user_account(id);


--
-- TOC entry 2827 (class 2606 OID 246839)
-- Name: employee employee_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY employee
    ADD CONSTRAINT employee_fk1 FOREIGN KEY (post_id) REFERENCES post(id);


--
-- TOC entry 2828 (class 2606 OID 246844)
-- Name: guest guest_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY guest
    ADD CONSTRAINT guest_fk0 FOREIGN KEY (id) REFERENCES user_account(id);


--
-- TOC entry 2829 (class 2606 OID 246849)
-- Name: guest guest_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY guest
    ADD CONSTRAINT guest_fk1 FOREIGN KEY (guest_status_id) REFERENCES guest_status(id);


--
-- TOC entry 2824 (class 2606 OID 246824)
-- Name: photo_link photo_link_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY photo_link
    ADD CONSTRAINT photo_link_fk0 FOREIGN KEY (room_id) REFERENCES room(id);


--
-- TOC entry 2825 (class 2606 OID 246829)
-- Name: photo_link photo_link_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY photo_link
    ADD CONSTRAINT photo_link_fk1 FOREIGN KEY (user_account_id) REFERENCES user_account(id);


--
-- TOC entry 2839 (class 2606 OID 246899)
-- Name: post post_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY post
    ADD CONSTRAINT post_fk0 FOREIGN KEY (supervisor_id) REFERENCES post(id);


--
-- TOC entry 2835 (class 2606 OID 246879)
-- Name: task task_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY task
    ADD CONSTRAINT task_fk0 FOREIGN KEY (answerable_id) REFERENCES user_account(id);


--
-- TOC entry 2836 (class 2606 OID 246884)
-- Name: task task_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY task
    ADD CONSTRAINT task_fk1 FOREIGN KEY (creator_id) REFERENCES user_account(id);


--
-- TOC entry 2840 (class 2606 OID 246904)
-- Name: unstructured_object unstructured_object_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY unstructured_object
    ADD CONSTRAINT unstructured_object_fk0 FOREIGN KEY (user_account_id) REFERENCES user_account(id);


-- Completed on 2018-06-08 17:17:30

--
-- PostgreSQL database dump complete
--

