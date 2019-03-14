package com.entrego.weather.application.util;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.function.Consumer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.entrego.weather.application.exception.RestCallException;

@Service
public class RestCallHelperImpl implements RestCallHelper{

	@Override
	public <T, V> ResponseEntity<T> doPostWithReturnCode(final String url, final Class<T> responseType, final V requestObject,
														 final MediaType mediaType, final Consumer<HttpHeaders> httpHeadersAdaptor) {
		return doCallWithReturnCode(url, HttpMethod.POST, responseType, requestObject, mediaType, httpHeadersAdaptor);
	}

	@Override
	public <T, V> ResponseEntity<T> doGetWithReturnCode(final String url, final Class<T> responseType, final V requestObject,
														final MediaType mediaType, final Consumer<HttpHeaders> httpHeadersAdaptor) {
		return doCallWithReturnCode(url, HttpMethod.GET, responseType, requestObject, mediaType, httpHeadersAdaptor);
	}

	private RestTemplate createRestTemplate() {
		final ClientHttpRequestFactory factory = createHttpRequestFactory();

		final RestTemplate template = new RestTemplate(factory);
		template.getMessageConverters()
			.add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

		return template;
	}

	protected ClientHttpRequestFactory createHttpRequestFactory() {
		return new SimpleClientHttpRequestFactory();
	}

	private RestCallException getRestCallException(final HttpStatusCodeException e) {
		return new RestCallException(
			"HttpServerErrorException: " + e.toString() + ". Response Body:" + e.getResponseBodyAsString(), e);
	}


	private <T, V> ResponseEntity<T> doCall(final RestTemplate restTemplate, final String url, final Class<T> responseType,
											final HttpEntity<V> httpEntity,	final HttpMethod method) {
		try {
			return restTemplate.exchange(url, method, httpEntity, responseType);
		} catch (final HttpServerErrorException | HttpClientErrorException e) {
			throw getRestCallException(e);
		}
	}

	private <T, V> ResponseEntity<T> doCallWithReturnCode(final String url, final HttpMethod method, final Class<T> responseType,
														  final V requestObject, final MediaType mediaType,
														  final Consumer<HttpHeaders> httpHeadersAdaptor) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		if (!Objects.isNull(mediaType)) {
			httpHeaders.setContentType(mediaType);
		}

		if (!Objects.isNull(httpHeadersAdaptor)) {
			httpHeadersAdaptor.accept(httpHeaders);
		}

		final HttpEntity<V> httpEntity = new HttpEntity<>(requestObject, httpHeaders);

		final RestTemplate restTemplate = createRestTemplate();
		return doCall(restTemplate, url, responseType, httpEntity, method);
	}
}
