package com.exteso.bruno.configuration;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

import com.exteso.bruno.model.UserIdentifier;
import com.exteso.bruno.repository.UserRepository;


// based on https://github.com/spring-guides/tut-spring-boot-oauth2/blob/master/github/src/main/java/com/example/SocialApplication.java
// licensed as
/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



@EnableOAuth2Sso
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Autowired
    OAuth2ClientContext oauth2ClientContext;
    
    @Autowired
    UserRepository userRepository;


    @Bean
    @ConfigurationProperties("github")
    ClientResources github() {
        return new ClientResources();
    }
    
    @Bean
    @ConfigurationProperties("google")
    ClientResources google() {
        return new ClientResources();
    }
    
    @Bean
    @ConfigurationProperties("facebook")
    ClientResources facebook() {
        return new ClientResources();
    }
    
    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
            .authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**", "/app/**", "/icons/**", "/img/**").permitAll()
                .anyRequest().authenticated()
            .and().exceptionHandling()
                .accessDeniedHandler((req, resp, exc) -> {resp.sendError(HttpServletResponse.SC_FORBIDDEN);})
                .authenticationEntryPoint((req, resp, exc) -> {resp.sendError(HttpServletResponse.SC_FORBIDDEN);})
            .and().logout().logoutSuccessUrl("/").permitAll()
            .and().csrf().disable()// FIXME
            //.and().csrf().csrfTokenRepository(csrfTokenRepository())
            //.and().addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
            .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }
    
    
    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(ssoFilter(github(), "/login/github", "github", detailExtractor("login")));
        filters.add(ssoFilter(google(), "/login/google", "google", detailExtractor("email")));
        filters.add(ssoFilter(facebook(), "/login/facebook", "facebook", detailExtractor("id")));
        filter.setFilters(filters);
        return filter;
    }
    
    
    @SuppressWarnings("unchecked")
    private Function<Authentication, String> detailExtractor(String key) {
        return a -> (String) ((Map<String, Object>) a.getDetails()).get(key);
    }

    private Filter ssoFilter(ClientResources client, String path, String prefix, Function<Authentication, String> nameExtractor) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        filter.setAuthenticationSuccessHandler((req, res, auth) -> {
            // ensure user presence
            UserIdentifier userIdentifier = UserIdentifier.from(auth);
            if(userRepository.count(userIdentifier.getProvider(), userIdentifier.getUsername()) == 0) {
                userRepository.create(userIdentifier.getProvider(), userIdentifier.getUsername());
            }
            //
            res.sendRedirect("/");
        });
        filter.setRestTemplate(template);
        filter.setTokenServices(new PrefixUserInfoTokenServices(prefix, new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId()), nameExtractor));
        return filter;
    }
    
    static class PrefixUserInfoTokenServices implements ResourceServerTokenServices {
        
        private final String prefix;
        private final UserInfoTokenServices userInfoTokenServices;
        private final Function<Authentication, String> nameExtractor;
        
        PrefixUserInfoTokenServices(String prefix, UserInfoTokenServices userInfoTokenServices, Function<Authentication, String> nameExtractor) {
            this.prefix = prefix;
            this.userInfoTokenServices = userInfoTokenServices;
            this.nameExtractor = nameExtractor;
        }

        @Override
        public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
            OAuth2Authentication loadAuthentication = userInfoTokenServices.loadAuthentication(accessToken);
            return new OAuth2Authentication(loadAuthentication.getOAuth2Request(), new PrefixedAuthentication(prefix, loadAuthentication.getUserAuthentication(), nameExtractor));
        }

        @Override
        public OAuth2AccessToken readAccessToken(String accessToken) {
            return userInfoTokenServices.readAccessToken(accessToken);
        }
        
    }
    
    public static class PrefixedAuthentication implements Authentication {
        
        private static final long serialVersionUID = 7539865983634393978L;
        
        private final String prefix;
        private final Authentication authentication;
        private final Function<Authentication, String> nameExtractor;
        
        PrefixedAuthentication(String prefix, Authentication authentication, Function<Authentication, String> nameExtractor) {
            this.prefix = prefix;
            this.authentication = authentication;
            this.nameExtractor = nameExtractor;
        }

        @Override
        public String getName() {
            return ((Principal) getPrincipal()).getName();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authentication.getAuthorities();
        }

        @Override
        public Object getCredentials() {
            return authentication.getCredentials();
        }

        @Override
        public Object getDetails() {
            return authentication.getDetails();
        }

        @Override
        public Object getPrincipal() {
            return new Principal() {
                @Override
                public String getName() {
                    return prefix + ":" + nameExtractor.apply(authentication);
                }
            };
        }

        @Override
        public boolean isAuthenticated() {
            return authentication.isAuthenticated();
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            authentication.setAuthenticated(isAuthenticated);
        }
    }

    
    static class ClientResources {
        private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();
        private ResourceServerProperties resource = new ResourceServerProperties();
        
        public AuthorizationCodeResourceDetails getClient() {
            return client;
        }

        public ResourceServerProperties getResource() {
            return resource;
        }
    }
}
