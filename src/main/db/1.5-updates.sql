/*L
  Copyright HealthCare IT, Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/edct-collector/LICENSE.txt for details.
L*/

CREATE TABLE sharing_group
(
  id character varying(100) NOT NULL,
  name character varying(500) NOT NULL,
  CONSTRAINT user_group_pk PRIMARY KEY (id)
);
ALTER TABLE sharing_group OWNER TO cacuredev;


ALTER TABLE entity_form
    RENAME TO sharing_group_form;

ALTER TABLE entity_module
    RENAME TO sharing_group_module;
    
    
alter table sharing_group_form add column sharing_group_id character varying(100);

alter table sharing_group_module add column sharing_group_id character varying(100);


alter table core_entity add column sharing_group_id character varying(100);


ALTER TABLE sharing_group_form DROP CONSTRAINT entity_form_pk;

ALTER TABLE sharing_group_form
  ADD CONSTRAINT entity_form_pk PRIMARY KEY(form_id, sharing_group_id);
    
ALTER TABLE sharing_group_module
  DROP CONSTRAINT entity_module_pk;

ALTER TABLE sharing_group_module
  ADD CONSTRAINT entity_module_pk PRIMARY KEY(module_id, sharing_group_id);
    
ALTER TABLE sharing_group_module ALTER COLUMN entity_id DROP NOT NULL;
ALTER TABLE sharing_group_form ALTER COLUMN entity_id DROP NOT NULL;