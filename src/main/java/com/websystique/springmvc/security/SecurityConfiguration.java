package com.websystique.springmvc.security;

import com.websystique.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	UserService userService;

	@Autowired
	PersistentTokenRepository tokenRepository;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/userlist").access("hasRole('ADMIN')")
				.antMatchers("/newuser/**", "/delete-user-*").access("hasRole('ADMIN')")
				.antMatchers("/edit-user-*").access("hasRole('ADMIN')")
				.antMatchers("/autolist").access("hasRole('CUSTOMER') or hasRole('ADMIN')")
				.antMatchers("/newauto/**", "/delete-auto-*").access("hasRole('ADMIN')")
				.antMatchers("/edit-auto-*").access("hasRole('ADMIN')")
				.antMatchers("/edit-profile").access("hasRole('CUSTOMER')")
				.antMatchers("/prenotazionelist").access("hasRole('CUSTOMER') or hasRole('ADMIN')")
				.antMatchers("/prenotazioni-user-*").access("hasRole('CUSTOMER') or hasRole('ADMIN')")
				.antMatchers("/newprenotazione/**", "/delete-prenotazione-*","/edit-prenotazione-*").access("hasRole('CUSTOMER')")
				.antMatchers("/approve-prenotazione-*", "/disapprove-prenotazione-*").access("hasRole('ADMIN')")
				.and().formLogin().loginPage("/login").loginProcessingUrl("/login").successHandler(myAuthenticationSuccessHandler())
				.usernameParameter("username").passwordParameter("password").and()
				.rememberMe().rememberMeParameter("remember-me").tokenRepository(tokenRepository)
				.tokenValiditySeconds(86400).and().csrf().and().exceptionHandling().accessDeniedPage("/Access_Denied");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
		PersistentTokenBasedRememberMeServices tokenBasedservice = new PersistentTokenBasedRememberMeServices(
				"remember-me", userDetailsService, tokenRepository);
		return tokenBasedservice;
	}

	@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}

	@Bean
	public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
		return new MySimpleUrlAuthenticationSuccessHandler();
	}
}
