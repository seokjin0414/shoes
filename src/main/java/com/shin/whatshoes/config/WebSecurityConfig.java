package com.shin.whatshoes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/shoes/list", "/shoes/getResale/**", "/account/register", "/css/**", "/api/**", "/what/**", "/shoes/detail/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/account/signin")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
//  쿼리문 공백 주의(구분 기능)
                .usersByUsernameQuery("select userId,userPw,enabled "
                        + "from user "
                        + "where userId = ?")
                .authoritiesByUsernameQuery("select u.userId, a.authName "
                        + "from userauth ua inner join user u on ua.userId = u.userId "
                        + "inner join auth a on ua.authId = a.authId "
                        + "where u.userId = ?");
    }

    @Bean
//  static 유무차이 BeanCurrentlyInCreationException
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}