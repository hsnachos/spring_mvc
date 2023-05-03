package com.hsnachos.config;

import com.hsnachos.security.CustomUserDetailsService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.sql.DataSource;

/**
 * packageName    : com.hsnachos.config
 * fileName       : SecurityConfig
 * author         : banghansol
 * date           : 2023/04/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/27        banghansol       최초 생성
 */
@EnableWebSecurity
@Configuration
@Log4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /*
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
        return http.build();
    }
*/

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new CharacterEncodingFilter("utf-8", true), CsrfFilter.class);

        http.authorizeRequests()
                .antMatchers("/sample/admin").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/sample/member").access("hasRole('ROLE_MEMBER')");

        http.formLogin().loginPage("/member/login");
        http.logout().invalidateHttpSession(true).logoutUrl("/member/logout").logoutSuccessUrl("/");

        http.rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(604800);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
        // auth.inMemoryAuthentication().withUser("member").password("{noop}member").roles("ADMIN");
/*
        String users_by_username_query="select userid username, userpw password, enabled from tbl_member where userid=?";
        String authorities_by_username_query="select userid username, auth authority from tbl_auth where userid=?";

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(users_by_username_query)
                .authoritiesByUsernameQuery(authorities_by_username_query);
        */

        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repositoryImpl = new JdbcTokenRepositoryImpl();
        repositoryImpl.setDataSource(dataSource);
        return repositoryImpl;
    }
}
