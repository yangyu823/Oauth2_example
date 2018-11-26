package com.plura.secureServer;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class ServiceConfig extends GlobalAuthenticationConfigurerAdapter {

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        //.passwordEncoder(NoOpPasswordEncoder.getInstance())  This is for password Encoder!!

        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("agoldberg").password("pass1").roles("USER").and()
                .withUser("bgoldberg").password("pass2").roles("USER", "OPERATOR");
    }

}
