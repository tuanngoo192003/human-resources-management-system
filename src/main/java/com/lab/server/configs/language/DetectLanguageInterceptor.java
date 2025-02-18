package com.lab.server.configs.language;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.lab.server.configs.security.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class DetectLanguageInterceptor implements HandlerInterceptor{

	private final JwtProvider jwtProvider;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod handlerMethod)) {
			return true;
		}

		DetectLanguage detectLanguage = handlerMethod.getMethodAnnotation(DetectLanguage.class);
		if(detectLanguage==null) return true;

		String token = jwtProvider.getJwtFromRequest(request);
		if (token != null) {
			try {
				String lang = jwtProvider.getLanguageFromToken(token);
				if (lang != null) {
					Locale locale = Locale.forLanguageTag(lang);
					LanguageContext.setLocale(locale);
				} else {
					LanguageContext.setLocale(Locale.forLanguageTag(detectLanguage.value().name())); // Default from annotation
				}
			} catch (Exception e) {
				LanguageContext.setLocale(Locale.ENGLISH);
			}
		} else {
			LanguageContext.setLocale(Locale.forLanguageTag(detectLanguage.value().name())); // Default from annotation
		}

        return true;
	}
}
