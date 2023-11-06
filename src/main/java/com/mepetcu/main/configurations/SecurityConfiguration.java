package com.mepetcu.main.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsManager userDetailsManager() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withUsername("admin").password("1234").authorities("Admin").build());
        userDetailsManager.createUser(User.withUsername("manager").password("1234").authorities("Manager").build());
        userDetailsManager.createUser(User.withUsername("user").password("1234").authorities("User").build());
        return userDetailsManager;
    }

    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().and().logout().logoutSuccessUrl("/")
                .and().authorizeRequests()
                .antMatchers("/tools/**").hasAnyAuthority("Admin", "Manager", "User")
                .antMatchers("/products/**").hasAnyAuthority("Admin", "Manager")
                .antMatchers("/others/**").hasAnyAuthority("Admin")
                .anyRequest().permitAll()
                .and().headers().frameOptions().sameOrigin()
                .and().exceptionHandling().accessDeniedPage("/denied")
                .and().csrf().disable();
//        http.authorizeRequests().anyRequest().permitAll().and().headers().frameOptions().sameOrigin().and().csrf().disable();
    }

}
