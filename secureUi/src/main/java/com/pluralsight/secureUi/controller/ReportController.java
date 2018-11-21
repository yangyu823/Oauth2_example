package com.pluralsight.secureUi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@EnableOAuth2Sso
public class ReportController extends WebSecurityConfigurerAdapter {

    @Qualifier("oauth2ClientContext")
    @Autowired
    private OAuth2ClientContext clientContext;

    @Autowired
    private OAuth2RestTemplate oauth2RestTemplate;

    @RequestMapping("/")
    public String loadHome(){
        return "home";
    }

    @RequestMapping("/reports")
    public String loadReports(Model model){

        OAuth2AccessToken t = clientContext.getAccessToken();
        System.out.println("Token "+ t.getValue());

        ResponseEntity<ArrayList<TollUsage>> tolls =
                oauth2RestTemplate.exchange(
                        "http://localhost:9002/services/tolldata",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ArrayList<TollUsage>>() {});

        model.addAttribute("tolls", tolls.getBody());

        return "reports";

    }

    public static class TollUsage{
        public String Id;
        public String stationId;
        public String licensePlate;
        public String timestamp;

        public TollUsage() {}

        public TollUsage(String id, String stationId, String licensePlate, String timestamp) {
            this.Id = id;
            this.stationId = stationId;
            this.licensePlate = licensePlate;
            this.timestamp = timestamp;
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/" , "/login**")
                .permitAll()
            .anyRequest()
                .authenticated();
    }
}
