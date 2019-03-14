package com.entrego.weather.application.util;

import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface RestCallHelper {

	/*
	 * Make a POST call to the given url.
	 *
	 * @param url - resource to call
	 * @param responseType - the type of the return value
	 * @param requestObject - object to put into the request body
	 * @param mediaType - the mediaType of the requestObject (final if it is not null)
	 * @param httpHeadersAdaptor - a consumer which can adapt the http header
	 * @return the response
	 */
	public <T, V> ResponseEntity<T> doPostWithReturnCode(final String url, final Class<T> responseType,
														 final V requestObject, final MediaType mediaType,
														 final Consumer<HttpHeaders> httpHeadersAdaptor);

	/*
	 * Make a GET call to the given url.
	 *
	 * @param url - resource to call
	 * @param responseType - the type of the return value
	 * @param requestObject - object to put into the request body
	 * @param mediaType - the mediaType of the requestObject (final if it is not null)
	 * @param httpHeadersAdaptor - a consumer which can adapt the http header
	 * @return the response
	 */
	public <T, V> ResponseEntity<T> doGetWithReturnCode(final String url, final Class<T> responseType,
														final V requestObject, final MediaType mediaType,
														final Consumer<HttpHeaders> httpHeadersAdaptor);

}
