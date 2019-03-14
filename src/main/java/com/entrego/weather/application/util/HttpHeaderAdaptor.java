package com.entrego.weather.application.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/*Created but not used*/
@Component
public class HttpHeaderAdaptor {

	public void generateHeaders(final HttpHeaders httpHeaders) {

		httpHeaders.set(HttpHeaders.CACHE_CONTROL, "no-cache");
		httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	}
}
