package com.example.demo;

import org.springframework.context.annotation.Profile;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.openid.OpenIDLoginConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebSecurity
@Profile("working")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  //need to post openid_identifier with crowd url https://crowd.example.com/openidserver/op
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/**").hasRole("USER")
        .and()
        .apply(new OpenIDLoginConfigurer())
        .sRegAttributes()
        .attribute("nickname")
        .attribute("email")
        .and()
        .authenticationUserDetailsService(new AutoProvisioningUserDetailsService())
        .permitAll();
  }

  public class AutoProvisioningUserDetailsService implements
      AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    public UserDetails loadUserDetails(OpenIDAuthenticationToken token)
        throws UsernameNotFoundException {
      return new User(token.getsRegAttributes().getOrDefault("nickname", token.getName()), "NOTUSED",
          AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
  }

}
