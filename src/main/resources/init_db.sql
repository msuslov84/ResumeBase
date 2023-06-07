CREATE TABLE resume
(
    uuid      VARCHAR(36) PRIMARY KEY NOT NULL,
    full_name TEXT                    NOT NULL
);

CREATE TABLE contact
(
    id          SERIAL PRIMARY KEY,
    resume_uuid VARCHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT        NOT NULL,
    value       TEXT        NOT NULL
);

-- Add index for fast search by map of contacts
CREATE UNIQUE INDEX contact_uuid_type_index ON contact (resume_uuid, type);

CREATE TABLE section
(
    id          SERIAL PRIMARY KEY,
    resume_uuid VARCHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT        NOT NULL,
    content     TEXT        NOT NULL
);

-- Add index for fast search by map of sections
CREATE UNIQUE INDEX section_uuid_type_index ON section (resume_uuid, type);