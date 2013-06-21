/*L
  Copyright HealthCare IT, Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/edct-collector/LICENSE.txt for details.
L*/

CREATE TABLE entity_tag_permission (entity_id character varying(40), tag_id character varying(500), permission character varying(10));

CREATE TABLE TAG (tag_id character varying(500));

ALTER TABLE forms ADD COLUMN tag_id character varying(500);

ALTER TABLe entity_tag_permission ADD CONSTRAINT entity_tag_permission_fk FOREIGN KEY (entity_id) REFERENCES core_entity (id) ON DELETE CASCADE;
