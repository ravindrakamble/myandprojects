
package com.ivd.http;

import java.util.Map;


/**
 * The Class RestRequest.
 */
public class RestRequest {

	/** The target url. */
	private String targetUrl;

	/** The query string. */
	private String queryString;

	/** The headers. */
	private Map<String, String> headers;

	/** The content type. */
	private String contentType;

	/** The char encoding. */
	private String charEncoding;

	/** The payload. */
	private String payload;

	/**  The output class. */
	private Class<?> output;

	/** The method type. */
	private String methodType;


	/**
	 * Gets the target url.
	 *
	 * @return the targetUrl
	 */
	public final String getTargetUrl() {
		return targetUrl;
	}

	/**
	 * Sets the target url.
	 *
	 * @param rTargetUrl            The target URL to set
	 */
	public final void setTargetUrl(final String rTargetUrl) {
		this.targetUrl = rTargetUrl;
	}

	/**
	 * Gets the query string.
	 *
	 * @return the queryString
	 */
	public final String getQueryString() {
		return queryString;
	}

	/**
	 * Sets the query string.
	 *
	 * @param rQueryString            The Query String to set
	 */
	public final void setQueryString(final String rQueryString) {
		this.queryString = rQueryString;
	}

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	public final Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * Sets the headers.
	 *
	 * @param rHeaders            the list of headers to set
	 */
	public final void setHeaders(final Map<String, String> rHeaders) {
		this.headers = rHeaders;
	}

	/**
	 * Gets the content type.
	 *
	 * @return the contentType
	 */
	public final String getContentType() {
		return contentType;
	}

	/**
	 * Sets the content type.
	 *
	 * @param rContentType            the contentType to set
	 */
	public final void setContentType(final String rContentType) {
		this.contentType = rContentType;
	}

	/**
	 * Gets the char encoding.
	 *
	 * @return the charEncoding
	 */
	public final String getCharEncoding() {
		return charEncoding;
	}

	/**
	 * Sets the char encoding.
	 *
	 * @param rCharEncoding            the Character Encoding to set
	 */
	public final void setCharEncoding(final String rCharEncoding) {
		this.charEncoding = rCharEncoding;
	}

	/**
	 * Gets the payload.
	 *
	 * @return the payload
	 */
	public final String getPayload() {
		return payload;
	}

	/**
	 * Sets the payload.
	 *
	 * @param rPayload            the payload to set in json format
	 */
	public final void setPayload(final String rPayload) {
		this.payload = rPayload;
	}


	/**
	 * Gets the output.
	 *
	 * @return the output
	 */
	public final Class<?> getOutput() {
		return output;
	}

	/**
	 * Sets the output.
	 *
	 * @param rOutput the new output
	 */
	public final void setOutput(final Class<?> rOutput) {
		this.output = rOutput;
	}


	/**
	 * Gets the method type.
	 *
	 * @return the method type
	 */
	public final String getMethodType() {
		return methodType;
	}

	/**
	 * Sets the method type.
	 *
	 * @param rMethodType the new method type
	 */
	public final void setMethodType(final String rMethodType) {
		this.methodType = rMethodType;
	}


	/**
	 * (non-Javadoc).
	 *
	 * @return the string
	 * @see java.lang.Object#toString()
	 *
	 *      <p>
	 *      </p>
	 */
	@Override
	public final String toString() {
		String restResquest = String.format(
				"RestResquest {%n Target Url: %s %n Query String: %s %n Content Type: %s %n"
				+ " Char Encoding: %s %n Payload: %s %n}",
				this.targetUrl, this.queryString, this.contentType, this.charEncoding, this.payload);
		return restResquest;
	}
}
