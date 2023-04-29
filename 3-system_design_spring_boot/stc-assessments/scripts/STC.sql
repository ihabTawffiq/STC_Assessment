
BEGIN;

SET client_encoding = 'LATIN1';

CREATE TABLE permission_groups (
    id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    group_name varchar NOT NULL,
    CONSTRAINT permission_groups_pk PRIMARY KEY (id)
);

CREATE TABLE permissions (
     id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
     user_email varchar NOT NULL,
     permission_level varchar NOT NULL,
     group_id int8 NULL,
     CONSTRAINT permissions_pk PRIMARY KEY (id),
     CONSTRAINT permissions_un UNIQUE (user_email),
     CONSTRAINT permissions_fk FOREIGN KEY (group_id) REFERENCES permission_groups(id)
);

CREATE TABLE items (
     id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
     "type" varchar NOT NULL,
     "name" varchar NOT NULL,
     permission_group_id int8 NULL,
     CONSTRAINT item_pk PRIMARY KEY (id),
     CONSTRAINT item_fk FOREIGN KEY (permission_group_id) REFERENCES permission_groups(id)
);

CREATE TABLE files (
     id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
     "binary" bytea NOT NULL,
     item_id int8 NOT NULL,
     CONSTRAINT files_pk PRIMARY KEY (id),
     CONSTRAINT files_fk FOREIGN KEY (item_id) REFERENCES items(id)
);

INSERT INTO public.permission_groups (group_name) VALUES ('ADMIN');
INSERT INTO public.permission_groups (group_name) VALUES ('USER');

INSERT INTO public.permissions (user_email,permission_level,group_id) VALUES ('view@gmail.com','VIEW',1);
INSERT INTO public.permissions (user_email,permission_level,group_id) VALUES ('edit@gmail.com','EDIT',1);

COMMIT;

ANALYZE permission_groups;
ANALYZE permissions;
ANALYZE items;
ANALYZE files;