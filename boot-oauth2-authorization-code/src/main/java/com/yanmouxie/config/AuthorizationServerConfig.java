package com.yanmouxie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Override
	public void configure(ClientDetailsServiceConfigurer clients)
			throws Exception {
		clients.inMemory()
				.withClient("myclient")
				.secret("$2a$10$tEFtDKpFh6eAr07inuDYVOtVAArj1NK3ulnj8KuUp7rHjrjYPzzD.") // = mysecret
				.authorizedGrantTypes("authorization_code", "refresh_token")
				.scopes("all","resource-server-read", "resource-server-write")
				.redirectUris("http://127.0.0.1:8090/login/oauth2/code/callback")
				//.accessTokenValiditySeconds(300)
				.resourceIds("oauth2-server")

				.and()
				.withClient("ResourceServer")
				.secret("$2a$10$0rrAX0SkBmnQhkLjPmgJkuivkU.D5iUisgyeMFk8k0MQCLwHXw5kC")
				.authorizedGrantTypes("authorization_code", "implicit",
						"password", "client_credentials", "refresh_token")
				.authorities("ROLE_TRUSTED_CLIENT")
				.resourceIds("oauth2-server");
	}
    
	@Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess(
				"isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
				.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
    }

    //For http header
    public static void main(String[] args) {
        System.out.println(new org.apache.tomcat.util.codec.binary.Base64().encodeAsString("myclient:mysecret".getBytes()));
        System.out.println(java.util.Base64.getEncoder().encodeToString("myclient:mysecret".getBytes()));
    }
}