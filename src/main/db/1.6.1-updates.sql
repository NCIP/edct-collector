/*L
  Copyright HealthCare IT, Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/edct-collector/LICENSE.txt for details.
L*/

ALTER TABLE users
   ALTER COLUMN mustchangepassword SET DEFAULT false;
ALTER TABLE users
   ALTER COLUMN system_usage_consent SET DEFAULT false;
ALTER TABLE users ADD COLUMN "role" character varying(25);
INSERT INTO users(
            username, "password", mustchangepassword, 
            system_usage_consent, system_usage_consent_date, id, "role")
    VALUES ('admin', '0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e',
	    FALSE, TRUE, now(), nextval('users_id_seq'), 'ROLE_ADMIN');
INSERT INTO tag values('TAG1');
UPDATE forms set tag_id='TAG1';
INSERT INTO entity_tag_permission (entity_id, tag_id, permission) select id, 'TAG1', 'READ' from core_entity;
INSERT INTO entity_tag_permission (entity_id, tag_id, permission) select id, 'TAG1', 'WRITE' from core_entity;
INSERT INTO entity_tag_permission (entity_id, tag_id, permission) select id, 'TAG1', 'APPROVE' from core_entity;