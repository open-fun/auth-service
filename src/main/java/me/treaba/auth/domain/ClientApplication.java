package me.treaba.auth.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
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
@Table(name = "client_apps")
public class ClientApplication implements ClientDetails {

  @Id
  private String clientId;

  @ElementCollection
  private Set<String> resourceIds;

  private String secret;

  private Boolean secretRequired;

  @ElementCollection
  private Set<String> scopes;

  private Boolean scoped;

  @ElementCollection
  private Set<String> autoApproveScopes;

  @ElementCollection
  private Set<String> authorizedGrantTypes;

  @ElementCollection
  private Set<String> redirectUris;

  @ElementCollection
  @Column(name = "authorities")
  private Set<String> authorityList;

  private Integer accessTokenValiditySeconds;

  private Integer refreshTokenValiditySeconds;

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
