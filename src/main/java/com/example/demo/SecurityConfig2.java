package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;

@Configuration
@EnableWebSecurity
@Profile("not-working")
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/**").hasRole("USER")
        .and()
        .openidLogin()
        .attributeExchange("https://crowd.example.com/openidserver/.*")
        .attribute("email")
        .type("https://schema.openid.net/contact/email")
        .required(true)
        .and()
        .and()
        .authenticationUserDetailsService(new AutoProvisioningUserDetailsService())
        .permitAll();
  }

  public class AutoProvisioningUserDetailsService implements
      AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    public UserDetails loadUserDetails(OpenIDAuthenticationToken token)
        throws UsernameNotFoundException {
      return new User(token.getName(), "NOTUSED",
          AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
  }

}
