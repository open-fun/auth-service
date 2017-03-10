package me.treaba.auth;

import me.treaba.auth.service.ClientApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * Created by Stanislav on 07.03.17.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  @Value("${resource.id:spring-boot-application}")
  private String resourceId;

  @Value("${access_token.validity_period:3600}")
  private int accessTokenValiditySeconds = 3600;

  @Value("${key.store.resource:classpath:sample.jks}")
  private String keyStoreResource;

  @Value("${key.store.password:secret}")
  private String keyStorePassword;

  @Value("${key.store.key_pair:testkey}")
  private String keyStoreKeyPair;

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private ClientApplicationService clientApplicationService;

  @Bean
  public TokenStore tokenStore(@Autowired JwtAccessTokenConverter accessTokenConverter) {
    return new JwtTokenStore(accessTokenConverter);
  }

  @Bean
  public KeyStoreKeyFactory keyStoreKeyFactory() {
    return new KeyStoreKeyFactory(applicationContext.getResource(keyStoreResource), keyStorePassword.toCharArray());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
    KeyStoreKeyFactory keyStoreKeyFactory = applicationContext.getBean(KeyStoreKeyFactory.class);
    accessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair(keyStoreKeyPair));
    return accessTokenConverter;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .tokenStore(applicationContext.getBean(TokenStore.class))
        .tokenEnhancer(applicationContext.getBean(JwtAccessTokenConverter.class))
        .authenticationManager(this.authenticationManager);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    oauthServer.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
        .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(clientApplicationService);
  }


}
