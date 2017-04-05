package com.zsoltfabok.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="services")
public class ServicesProperties {

    private Authservice authservice = new Authservice();


    public static class Authservice {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public Authservice getAuthservice() {
        return authservice;
    }

    public void setAuthservice(Authservice authservice) {
        this.authservice = authservice;
    }
}
