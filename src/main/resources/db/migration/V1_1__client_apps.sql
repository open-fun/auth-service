CREATE TABLE client_application (
  client_id                      VARCHAR(256) PRIMARY KEY,
  secret                         VARCHAR(256),
  secret_required                BOOLEAN NOT NULL DEFAULT FALSE,
  scoped                         BOOLEAN NOT NULL DEFAULT TRUE,
  access_token_validity_seconds  INTEGER NOT NULL DEFAULT 3600,
  refresh_token_validity_seconds INTEGER NOT NULL DEFAULT 3600
);

CREATE TABLE client_application_resource_ids (
  client_application_client_id VARCHAR(256) NOT NULL,
  resource_ids                 VARCHAR(256) NOT NULL,
  CONSTRAINT client_application_resourceIds_uq UNIQUE (client_application_client_id, resource_ids)
);

CREATE TABLE client_application_scopes (
  client_application_client_id VARCHAR(256) NOT NULL,
  scopes                       VARCHAR(256) NOT NULL,
  CONSTRAINT client_application_scopes_uq UNIQUE (client_application_client_id, scopes)
);

CREATE TABLE client_application_auto_approve_scopes (
  client_application_client_id VARCHAR(256) NOT NULL,
  auto_approve_scopes          VARCHAR(256) NOT NULL,
  CONSTRAINT client_application_autoApproveScopes_uq UNIQUE (client_application_client_id, auto_approve_scopes)
);

CREATE TABLE client_application_authorized_grant_types (
  client_application_client_id VARCHAR(256) NOT NULL,
  authorized_grant_types       VARCHAR(256) NOT NULL,
  CONSTRAINT client_application_authorizedGrantTypes_uq UNIQUE (client_application_client_id, authorized_grant_types)
);

CREATE TABLE client_application_redirect_uris (
  client_application_client_id VARCHAR(256) NOT NULL,
  redirect_uris                VARCHAR(256) NOT NULL,
  CONSTRAINT client_application_redirectUris_uq UNIQUE (client_application_client_id, redirect_uris)
);

CREATE TABLE client_application_authority_list (
  client_application_client_id VARCHAR(256) NOT NULL,
  authority_list               VARCHAR(256) NOT NULL,
  CONSTRAINT client_application_authorities_uq UNIQUE (client_application_client_id, authority_list)
);
