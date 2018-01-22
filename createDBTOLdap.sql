CREATE TABLE sec_group
(
  id         UUID NOT NULL
    CONSTRAINT sec_group_pkey
    PRIMARY KEY,
  create_ts  TIMESTAMP,
  created_by VARCHAR(50),
  version    INTEGER,
  update_ts  TIMESTAMP,
  updated_by VARCHAR(50),
  delete_ts  TIMESTAMP,
  deleted_by VARCHAR(50),
  name       VARCHAR(255),
  parent_id  UUID
    CONSTRAINT sec_group_parent
    REFERENCES sec_group
);

CREATE TABLE df_organization
(
  id              UUID NOT NULL
    CONSTRAINT df_organization_pkey
    PRIMARY KEY,
  create_ts       TIMESTAMP,
  created_by      VARCHAR(50),
  version         INTEGER,
  update_ts       TIMESTAMP,
  updated_by      VARCHAR(50),
  delete_ts       TIMESTAMP,
  deleted_by      VARCHAR(50),
  name            VARCHAR(200),
  full_name       VARCHAR(200),
  inn             VARCHAR(12),
  okpo            VARCHAR(10),
  kpp             VARCHAR(9),
  postal_address  VARCHAR(300),
  legal_address   VARCHAR(300),
  phone           VARCHAR(100),
  fax             VARCHAR(100),
  email           VARCHAR(100),
  comment         VARCHAR(1000),
  code            VARCHAR(20),
  secretary_id    UUID,
  has_attachments BOOLEAN
);

CREATE TABLE sec_user
(
  id                       UUID        NOT NULL
    CONSTRAINT sec_user_pkey
    PRIMARY KEY,
  create_ts                TIMESTAMP,
  created_by               VARCHAR(50),
  version                  INTEGER,
  update_ts                TIMESTAMP,
  updated_by               VARCHAR(50),
  delete_ts                TIMESTAMP,
  deleted_by               VARCHAR(50),
  login                    VARCHAR(50) NOT NULL,
  login_lc                 VARCHAR(50) NOT NULL,
  password                 VARCHAR(255),
  name                     VARCHAR(255),
  first_name               VARCHAR(255),
  last_name                VARCHAR(255),
  middle_name              VARCHAR(255),
  position_                VARCHAR(400),
  email                    VARCHAR(100),
  language_                VARCHAR(20),
  time_zone                VARCHAR(50),
  time_zone_auto           BOOLEAN,
  active                   BOOLEAN,
  group_id                 UUID
    CONSTRAINT sec_user_group
    REFERENCES sec_group,
  ip_mask                  VARCHAR(200),
  change_password_at_logon BOOLEAN,
  organization_id          UUID
    CONSTRAINT fk_sec_user_organization
    REFERENCES df_organization,
  is_mobile                BOOLEAN,
  department_code          VARCHAR(20),
  active_directory_id      VARCHAR(255)
);

CREATE UNIQUE INDEX idx_sec_user_uniq_login
  ON sec_user (login_lc)
  WHERE (delete_ts IS NULL);


ALTER TABLE df_organization
  ADD
  CONSTRAINT fk_df_organization_secretary
  FOREIGN KEY (secretary_id)
  REFERENCES sec_user
