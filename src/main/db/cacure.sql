--
-- PostgreSQL database dump
--

-- Dumped from database version 8.4.4
-- Dumped by pg_dump version 9.0.4
-- Started on 2011-09-08 11:53:14

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 7 (class 2615 OID 16492)
-- Name: cacure; Type: SCHEMA; Schema: -; Owner: cacure
--

CREATE SCHEMA cacure;


ALTER SCHEMA cacure OWNER TO cacure;

SET search_path = cacure, pg_catalog;

--
-- TOC entry 20 (class 1255 OID 16769)
-- Dependencies: 7 336
-- Name: convertskips(); Type: FUNCTION; Schema: cacure; Owner: cacure
--

CREATE FUNCTION convertskips() RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    r form_skip%rowtype;
BEGIN
    FOR r IN SELECT * FROM form_skip where ans_value is not null
    LOOP
	insert into skip_parts (id, parent_id, answer_value)
		values (nextval('"form_skip_id_seq"'), r.id, r.ans_value);

    END LOOP;
    RETURN 1;
END
$$;


ALTER FUNCTION cacure.convertskips() OWNER TO cacure;

--
-- TOC entry 21 (class 1255 OID 41553)
-- Dependencies: 7 336
-- Name: delete_module(integer); Type: FUNCTION; Schema: cacure; Owner: cacure
--

CREATE FUNCTION delete_module(mod_id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    this_form RECORD;
    this_skip RECORD;
begin
	FOR this_form IN SELECT * FROM "cacure".forms WHERE module_id = mod_id LOOP

		-- delete skips
		FOR this_skip IN SELECT * FROM "cacure".form_skip WHERE form_id = this_form.id LOOP
			
			delete from "cacure".skip_parts where parent_id =this_skip.id;

		END LOOP;
		delete from "cacure".form_skip where form_id = this_form.id;
		
		-- delete entity forms
		DELETE FROM "cacure".entity_form WHERE form_id = this_form.id;

	END LOOP; -- forms loop
	-- delete all forms
	DELETE FROM "cacure".forms WHERE module_id = mod_id;

	-- delete module
	DELETE FROM "cacure".entity_module WHERE module_id = mod_id;
	DELETE FROM "cacure".modules WHERE id = mod_id;
	
	
	RETURN 1;
end
$$;


ALTER FUNCTION cacure.delete_module(mod_id integer) OWNER TO cacure;

--
-- TOC entry 22 (class 1255 OID 41556)
-- Dependencies: 336 7
-- Name: delete_module(character varying); Type: FUNCTION; Schema: cacure; Owner: cacure
--

CREATE FUNCTION delete_module(mod_id character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    this_form RECORD;
    this_skip RECORD;
begin
	FOR this_form IN SELECT * FROM "cacure".forms WHERE module_id = mod_id LOOP

		-- delete skips
		FOR this_skip IN SELECT * FROM "cacure".form_skip WHERE form_id = this_form.id LOOP
			
			delete from "cacure".skip_parts where parent_id =this_skip.id;

		END LOOP;
		delete from "cacure".form_skip where form_id = this_form.id;
		
		-- delete entity forms
		DELETE FROM "cacure".entity_form WHERE form_id = this_form.id;

	END LOOP; -- forms loop
	-- delete all forms
	DELETE FROM "cacure".forms WHERE module_id = mod_id;

	-- delete module
	DELETE FROM "cacure".entity_module WHERE module_id = mod_id;
	DELETE FROM "cacure".modules WHERE id = mod_id;
	
	
	RETURN 1;
end
$$;


ALTER FUNCTION cacure.delete_module(mod_id character varying) OWNER TO cacure;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1523 (class 1259 OID 16594)
-- Dependencies: 7
-- Name: core_entity; Type: TABLE; Schema: cacure; Owner: cacure; Tablespace: 
--

CREATE TABLE core_entity (
    id character varying(100) NOT NULL,
    sharing_group_id character varying(100)
);


ALTER TABLE cacure.core_entity OWNER TO cacure;

--
-- TOC entry 1526 (class 1259 OID 16603)
-- Dependencies: 7
-- Name: form_skip; Type: TABLE; Schema: cacure; Owner: cacure; Tablespace: 
--

CREATE TABLE form_skip (
    id bigint NOT NULL,
    form_id character varying(100) NOT NULL,
    question_id character varying(100) NOT NULL,
    rule character varying(50) NOT NULL,
    logical_op character varying(3),
    question_owner_form_id character varying(150) NOT NULL
);


ALTER TABLE cacure.form_skip OWNER TO cacure;

--
-- TOC entry 1527 (class 1259 OID 16609)
-- Dependencies: 7
-- Name: form_skip_id_seq; Type: SEQUENCE; Schema: cacure; Owner: cacure
--

CREATE SEQUENCE form_skip_id_seq
    START WITH 4
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cacure.form_skip_id_seq OWNER TO cacure;

--
-- TOC entry 1528 (class 1259 OID 16611)
-- Dependencies: 7
-- Name: forms; Type: TABLE; Schema: cacure; Owner: cacure; Tablespace: 
--

CREATE TABLE forms (
    id character varying(100) NOT NULL,
    description character varying(300),
    author character varying(50),
    question_count integer NOT NULL,
    xform_location character varying(400) NOT NULL,
    module_id character varying(100) NOT NULL,
    form_order integer NOT NULL,
    form_name character varying(100) NOT NULL
);


ALTER TABLE cacure.forms OWNER TO cacure;

--
-- TOC entry 1529 (class 1259 OID 16617)
-- Dependencies: 1814 7
-- Name: modules; Type: TABLE; Schema: cacure; Owner: cacure; Tablespace: 
--

CREATE TABLE modules (
    id character varying(100) NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(300),
    status character varying(10) NOT NULL,
    deploy_date timestamp with time zone DEFAULT now() NOT NULL,
    estimated_completion_time character varying(500),
    context character varying(20)
);


ALTER TABLE cacure.modules OWNER TO cacure;

--
-- TOC entry 1530 (class 1259 OID 16623)
-- Dependencies: 1815 7
-- Name: patients; Type: TABLE; Schema: cacure; Owner: cacure; Tablespace: 
--

CREATE TABLE patients (
    modified_date date,
    additional_info character varying(4000),
    phr_first_time_completed_date date,
    user_id bigint NOT NULL,
    status character varying(50) DEFAULT 'NOT SUBMITTED'::character varying,
    id bytea NOT NULL
);


ALTER TABLE cacure.patients OWNER TO cacure;

--
-- TOC entry 1531 (class 1259 OID 16630)
-- Dependencies: 7
-- Name: security_questions; Type: TABLE; Schema: cacure; Owner: cacure; Tablespace: 
--

CREATE TABLE security_questions (
    value character varying(60),
    id bigint NOT NULL
);


ALTER TABLE cacure.security_questions OWNER TO cacure;

--
-- TOC entry 1532 (class 1259 OID 16633)
-- Dependencies: 7 1531
-- Name: security_questions_id_seq; Type: SEQUENCE; Schema: cacure; Owner: cacure
--

CREATE SEQUENCE security_questions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cacure.security_questions_id_seq OWNER TO cacure;

--
-- TOC entry 1851 (class 0 OID 0)
-- Dependencies: 1532
-- Name: security_questions_id_seq; Type: SEQUENCE OWNED BY; Schema: cacure; Owner: cacure
--

ALTER SEQUENCE security_questions_id_seq OWNED BY security_questions.id;


--
-- TOC entry 1536 (class 1259 OID 58963)
-- Dependencies: 7
-- Name: sharing_group; Type: TABLE; Schema: cacure; Owner: cacure; Tablespace: 
--

CREATE TABLE sharing_group (
    id character varying(100) NOT NULL,
    name character varying(500) NOT NULL
);


ALTER TABLE cacure.sharing_group OWNER TO cacure;

--
-- TOC entry 1524 (class 1259 OID 16597)
-- Dependencies: 7
-- Name: sharing_group_form; Type: TABLE; Schema: cacure; Owner: cacure; Tablespace: 
--

CREATE TABLE sharing_group_form (
    form_id character varying(100) NOT NULL,
    entity_id character varying(100),
    status character varying(20) NOT NULL,
    lastupdated timestamp with time zone,
    sharing_group_id character varying(100) NOT NULL
);


ALTER TABLE cacure.sharing_group_form OWNER TO cacure;

--
-- TOC entry 1525 (class 1259 OID 16600)
-- Dependencies: 7
-- Name: sharing_group_module; Type: TABLE; Schema: cacure; Owner: cacure; Tablespace: 
--

CREATE TABLE sharing_group_module (
    module_id character varying(100) NOT NULL,
    entity_id character varying(100),
    status character varying(20) NOT NULL,
    datesubmitted timestamp with time zone,
    sharing_group_id character varying(100) NOT NULL
);


ALTER TABLE cacure.sharing_group_module OWNER TO cacure;

--
-- TOC entry 1535 (class 1259 OID 16764)
-- Dependencies: 7
-- Name: skip_parts; Type: TABLE; Schema: cacure; Owner: cacure; Tablespace: 
--

CREATE TABLE skip_parts (
    id bigint NOT NULL,
    parent_id bigint NOT NULL,
    answer_value character varying(150) NOT NULL
);


ALTER TABLE cacure.skip_parts OWNER TO cacure;

--
-- TOC entry 1533 (class 1259 OID 16635)
-- Dependencies: 7
-- Name: users; Type: TABLE; Schema: cacure; Owner: cacure; Tablespace: 
--

CREATE TABLE users (
    username character varying(25),
    password character varying(130),
    password_hint character varying(50),
    last_login_date date,
    mustchangepassword boolean,
    system_usage_consent boolean,
    system_usage_consent_date date,
    email_addr character varying(25),
    security_question_id bigint,
    security_question_answer character varying(50),
    id bigint NOT NULL
);


ALTER TABLE cacure.users OWNER TO cacure;

--
-- TOC entry 1534 (class 1259 OID 16638)
-- Dependencies: 1533 7
-- Name: users_id_seq; Type: SEQUENCE; Schema: cacure; Owner: cacure
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cacure.users_id_seq OWNER TO cacure;

--
-- TOC entry 1852 (class 0 OID 0)
-- Dependencies: 1534
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: cacure; Owner: cacure
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 1816 (class 2604 OID 16640)
-- Dependencies: 1532 1531
-- Name: id; Type: DEFAULT; Schema: cacure; Owner: cacure
--

ALTER TABLE security_questions ALTER COLUMN id SET DEFAULT nextval('security_questions_id_seq'::regclass);


--
-- TOC entry 1817 (class 2604 OID 16641)
-- Dependencies: 1534 1533
-- Name: id; Type: DEFAULT; Schema: cacure; Owner: cacure
--

ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- TOC entry 1819 (class 2606 OID 16643)
-- Dependencies: 1523 1523
-- Name: core_entity_pk; Type: CONSTRAINT; Schema: cacure; Owner: cacure; Tablespace: 
--

ALTER TABLE ONLY core_entity
    ADD CONSTRAINT core_entity_pk PRIMARY KEY (id);


--
-- TOC entry 1821 (class 2606 OID 59011)
-- Dependencies: 1524 1524 1524
-- Name: entity_form_pk; Type: CONSTRAINT; Schema: cacure; Owner: cacure; Tablespace: 
--

ALTER TABLE ONLY sharing_group_form
    ADD CONSTRAINT entity_form_pk PRIMARY KEY (form_id, sharing_group_id);


--
-- TOC entry 1823 (class 2606 OID 59013)
-- Dependencies: 1525 1525 1525
-- Name: entity_module_pk; Type: CONSTRAINT; Schema: cacure; Owner: cacure; Tablespace: 
--

ALTER TABLE ONLY sharing_group_module
    ADD CONSTRAINT entity_module_pk PRIMARY KEY (module_id, sharing_group_id);


--
-- TOC entry 1827 (class 2606 OID 16649)
-- Dependencies: 1528 1528
-- Name: forms_pkey; Type: CONSTRAINT; Schema: cacure; Owner: cacure; Tablespace: 
--

ALTER TABLE ONLY forms
    ADD CONSTRAINT forms_pkey PRIMARY KEY (id);


--
-- TOC entry 1829 (class 2606 OID 16651)
-- Dependencies: 1529 1529
-- Name: module_pkey; Type: CONSTRAINT; Schema: cacure; Owner: cacure; Tablespace: 
--

ALTER TABLE ONLY modules
    ADD CONSTRAINT module_pkey PRIMARY KEY (id);


--
-- TOC entry 1832 (class 2606 OID 16653)
-- Dependencies: 1530 1530
-- Name: patients_pkey; Type: CONSTRAINT; Schema: cacure; Owner: cacure; Tablespace: 
--

ALTER TABLE ONLY patients
    ADD CONSTRAINT patients_pkey PRIMARY KEY (id);


--
-- TOC entry 1825 (class 2606 OID 16655)
-- Dependencies: 1526 1526
-- Name: pk_id; Type: CONSTRAINT; Schema: cacure; Owner: cacure; Tablespace: 
--

ALTER TABLE ONLY form_skip
    ADD CONSTRAINT pk_id PRIMARY KEY (id);


--
-- TOC entry 1834 (class 2606 OID 16657)
-- Dependencies: 1531 1531
-- Name: pk_security_questions_pkey; Type: CONSTRAINT; Schema: cacure; Owner: cacure; Tablespace: 
--

ALTER TABLE ONLY security_questions
    ADD CONSTRAINT pk_security_questions_pkey PRIMARY KEY (id);


--
-- TOC entry 1837 (class 2606 OID 16659)
-- Dependencies: 1533 1533
-- Name: pk_users_pkey; Type: CONSTRAINT; Schema: cacure; Owner: cacure; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users_pkey PRIMARY KEY (id);


--
-- TOC entry 1839 (class 2606 OID 16768)
-- Dependencies: 1535 1535
-- Name: skip_parts_pkey; Type: CONSTRAINT; Schema: cacure; Owner: cacure; Tablespace: 
--

ALTER TABLE ONLY skip_parts
    ADD CONSTRAINT skip_parts_pkey PRIMARY KEY (id);


--
-- TOC entry 1841 (class 2606 OID 58970)
-- Dependencies: 1536 1536
-- Name: user_group_pk; Type: CONSTRAINT; Schema: cacure; Owner: cacure; Tablespace: 
--

ALTER TABLE ONLY sharing_group
    ADD CONSTRAINT user_group_pk PRIMARY KEY (id);


--
-- TOC entry 1835 (class 1259 OID 16660)
-- Dependencies: 1533
-- Name: fki_security_questions_fkey; Type: INDEX; Schema: cacure; Owner: cacure; Tablespace: 
--

CREATE INDEX fki_security_questions_fkey ON users USING btree (security_question_id);


--
-- TOC entry 1830 (class 1259 OID 16661)
-- Dependencies: 1530
-- Name: fki_users_fkey; Type: INDEX; Schema: cacure; Owner: cacure; Tablespace: 
--

CREATE INDEX fki_users_fkey ON patients USING btree (user_id);


--
-- TOC entry 1844 (class 2606 OID 16662)
-- Dependencies: 1818 1523 1525
-- Name: core_entity_fk; Type: FK CONSTRAINT; Schema: cacure; Owner: cacure
--

ALTER TABLE ONLY sharing_group_module
    ADD CONSTRAINT core_entity_fk FOREIGN KEY (entity_id) REFERENCES core_entity(id) ON DELETE CASCADE;


--
-- TOC entry 1842 (class 2606 OID 16667)
-- Dependencies: 1524 1818 1523
-- Name: core_entity_fk; Type: FK CONSTRAINT; Schema: cacure; Owner: cacure
--

ALTER TABLE ONLY sharing_group_form
    ADD CONSTRAINT core_entity_fk FOREIGN KEY (entity_id) REFERENCES core_entity(id) ON DELETE CASCADE;


--
-- TOC entry 1847 (class 2606 OID 16672)
-- Dependencies: 1533 1531 1833
-- Name: fk_security_questions_fkey; Type: FK CONSTRAINT; Schema: cacure; Owner: cacure
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_security_questions_fkey FOREIGN KEY (security_question_id) REFERENCES security_questions(id);


--
-- TOC entry 1846 (class 2606 OID 16677)
-- Dependencies: 1836 1533 1530
-- Name: fk_users_fkey; Type: FK CONSTRAINT; Schema: cacure; Owner: cacure
--

ALTER TABLE ONLY patients
    ADD CONSTRAINT fk_users_fkey FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 1843 (class 2606 OID 16682)
-- Dependencies: 1524 1528 1826
-- Name: forms_pk; Type: FK CONSTRAINT; Schema: cacure; Owner: cacure
--

ALTER TABLE ONLY sharing_group_form
    ADD CONSTRAINT forms_pk FOREIGN KEY (form_id) REFERENCES forms(id) ON DELETE CASCADE;


--
-- TOC entry 1845 (class 2606 OID 16687)
-- Dependencies: 1525 1529 1828
-- Name: modules_pk; Type: FK CONSTRAINT; Schema: cacure; Owner: cacure
--

ALTER TABLE ONLY sharing_group_module
    ADD CONSTRAINT modules_pk FOREIGN KEY (module_id) REFERENCES modules(id) ON DELETE CASCADE;


--
-- TOC entry 1850 (class 0 OID 0)
-- Dependencies: 7
-- Name: cacure; Type: ACL; Schema: -; Owner: cacure
--

REVOKE ALL ON SCHEMA cacure FROM PUBLIC;
REVOKE ALL ON SCHEMA cacure FROM cacure;
GRANT ALL ON SCHEMA cacure TO cacure;
GRANT ALL ON SCHEMA cacure TO PUBLIC;


-- Completed on 2011-09-08 11:53:14

--
-- PostgreSQL database dump complete
--

