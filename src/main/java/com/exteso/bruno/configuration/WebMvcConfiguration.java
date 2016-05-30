package com.exteso.bruno.configuration;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.exteso.bruno.model.UserIdentifier;
import com.exteso.bruno.repository.UserRepository;
import com.exteso.bruno.web.ControllersMarker;

@Configuration
@ComponentScan(basePackageClasses = { ControllersMarker.class })
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public LocaleResolver localeResolver(UserRepository userRepository) {
        return new PersistedLocaleResolver(userRepository);
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
    
    
    static class PersistedLocaleResolver implements LocaleResolver {
    	
    	private final UserRepository userRepository;
    	
    	public PersistedLocaleResolver(UserRepository userRepository) {
    		this.userRepository = userRepository;
		}

		@Override
		public Locale resolveLocale(HttpServletRequest request) {
			if (UserIdentifier.isAuthenticated(request.getUserPrincipal())) {
				return userRepository.getUserLocale(UserIdentifier.from(request.getUserPrincipal()));
			} else {
				return Locale.ITALIAN.toLanguageTag().equals(request.getLocale().toLanguageTag()) ? Locale.ITALIAN : Locale.ENGLISH;
			}
		}

		@Override
		public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
			if (UserIdentifier.isAuthenticated(request.getUserPrincipal())) {
				userRepository.setUserLocale(UserIdentifier.from(request.getUserPrincipal()), locale);
			}
		}
    	
    }
}
