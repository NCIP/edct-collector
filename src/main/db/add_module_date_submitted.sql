/*L
  Copyright HealthCare IT, Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/edct-collector/LICENSE.txt for details.
L*/

alter table entity_module add column datesubmitted timestamp with time zone;
alter table modules add column estimated_completion_time character varying(100);