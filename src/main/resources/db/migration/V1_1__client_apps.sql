CREATE TABLE client_apps (
  clientId                    VARCHAR(256) PRIMARY KEY,
  secret                      VARCHAR(256),
  secretRequired              BOOLEAN,
  scoped                      BOOLEAN,
  accessTokenValiditySeconds  INTEGER DEFAULT 3600,
  refreshTokenValiditySeconds INTEGER DEFAULT 3600
);

CREATE TABLE client_apps_resourceIds (
  clientId    VARCHAR(256) NOT NULL,
  resourceIds VARCHAR(256) NOT NULL,
  CONSTRAINT client_apps_resourceIds_uq UNIQUE (clientId, resourceIds)
);

CREATE TABLE client_apps_scopes (
  clientId VARCHAR(256) NOT NULL,
  scopes   VARCHAR(256) NOT NULL,
  CONSTRAINT client_apps_scopes_uq UNIQUE (clientId, scopes)
);

CREATE TABLE client_apps_autoApproveScopes (
  clientId          VARCHAR(256) NOT NULL,
  autoApproveScopes VARCHAR(256) NOT NULL,
  CONSTRAINT client_apps_autoApproveScopes_uq UNIQUE (clientId, autoApproveScopes)
);

CREATE TABLE client_apps_authorizedGrantTypes (
  clientId             VARCHAR(256) NOT NULL,
  authorizedGrantTypes VARCHAR(256) NOT NULL,
  CONSTRAINT client_apps_authorizedGrantTypes_uq UNIQUE (clientId, authorizedGrantTypes)
);

CREATE TABLE client_apps_redirectUris (
  clientId     VARCHAR(256) NOT NULL,
  redirectUris VARCHAR(256) NOT NULL,
  CONSTRAINT client_apps_redirectUris_uq UNIQUE (clientId, redirectUris)
);

CREATE TABLE client_apps_authorities (
  clientId    VARCHAR(256) NOT NULL,
  authorities VARCHAR(256) NOT NULL,
  CONSTRAINT client_apps_authorities_uq UNIQUE (clientId, authorities)
);
