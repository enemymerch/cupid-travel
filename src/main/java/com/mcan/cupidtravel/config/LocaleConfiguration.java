package com.mcan.cupidtravel.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class LocaleConfiguration implements WebMvcConfigurer {

	private final LocaleInterceptor localeInterceptor;

	@Bean
	public AcceptHeaderLocaleResolver acceptHeaderLocaleResolver() {
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.ENGLISH);
		return localeResolver;
	}

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public LocaleHolder localeHolder() {
		return new LocaleHolder();
	}

	@Override
	public void addInterceptors(InterceptorRegistry interceptorRegistry) {
		interceptorRegistry.addInterceptor(localeInterceptor);
	}

}
