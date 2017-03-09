package me.treaba.auth.domain;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Stanislav on 08.03.17.
 */
@Entity
@Data
@Table(name = "client_application")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientApplication implements ClientDetails {

  @Id
  @Size(min = 5, max = 256, message = "error.client_id.size")
  private String clientId;

  @NotNull(message = "error.resources_ids.required")
  @ElementCollection
  private Set<String> resourceIds;

  @JsonIgnore
  private String secret;

  private Boolean secretRequired = false;

  @NotNull(message = "error.scopes.required")
  @ElementCollection
  private Set<String> scopes;

  private Boolean scoped = true;

  @ElementCollection
  private Set<String> autoApproveScopes;

  @NotNull(message = "error.grant_types.required")
  @ElementCollection
  private Set<String> authorizedGrantTypes;

  @NotNull(message = "error.redirect_uris.required")
  @ElementCollection
  private Set<String> redirectUris;

  @NotNull(message = "error.authorities.required")
  @ElementCollection
  private Set<String> authorityList;

  private Integer accessTokenValiditySeconds = 3600;

  private Integer refreshTokenValiditySeconds = 3600;

  @Override
  public boolean isSecretRequired() {
    return secretRequired;
  }

  @Transient
  @Override
  public String getClientSecret() {
    return secret;
  }

  @Override
  public boolean isScoped() {
    return scoped;
  }

  @Transient
  @Override
  public Set<String> getScope() {
    return scopes;
  }

  @Transient
  @Override
  public Set<String> getRegisteredRedirectUri() {
    return redirectUris;
  }

  @Transient
  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    if (authorityList == null)
      return Collections.emptyList();
    return authorityList.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Transient
  @Override
  public boolean isAutoApprove(String scope) {
    if (autoApproveScopes == null)
      return false;
    return autoApproveScopes.contains(scope);
  }

  @Transient
  @Override
  public Map<String, Object> getAdditionalInformation() {
    return Collections.emptyMap();
  }
}
