alter table entity_module add column datesubmitted timestamp with time zone;
alter table modules add column estimated_completion_time character varying(100);