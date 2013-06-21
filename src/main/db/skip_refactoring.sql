/*L
  Copyright HealthCare IT, Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/edct-collector/LICENSE.txt for details.
L*/

CREATE TABLE "cacure".skip_parts
(
  id bigint NOT NULL,
  parent_id bigint NOT NULL,
  answer_value character varying(150) NOT NULL,
  CONSTRAINT skip_parts_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "cacure".skip_parts OWNER TO cacuredev;

CREATE OR REPLACE FUNCTION "cacure".convertSkips() returns integer as $$
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
$$ LANGUAGE 'plpgsql' ;

select convertSkips();

alter table form_skip add column logical_op character varying(3);
alter table form_skip drop column ans_value;