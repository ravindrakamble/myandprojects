
package com.ivd.http;

import java.lang.reflect.Type;

// TODO: Auto-generated Javadoc
/**
 * The Class ERestResponse.
 *
 * @author ravindra.kambale
 * @version 1.0
 */
public class RestResponse {

	/**
	 * The Enum StatusCode.
	 */
	public static enum StatusCode {

		/** The success. */
		SUCCESS,
		/** The failure. */
		FAILURE
	};

	/** The status code. */
	private StatusCode statusCode;

	/** The content. */
	private String content;

	/** The data. */
	private Object data;

	/** The output. */
	private Type output;


	/**
	 * Gets the status code.
	 *
	 * @return the status code
	 */
	public StatusCode getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets the status code.
	 *
	 * @param statusCode the new status code
	 */
	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public final String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param contentToSet            the content to set
	 */
	public final void setContent(final String contentToSet) {
		this.content = contentToSet;
	}


	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public final Object getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param dataToset the new data
	 */
	public final void setData(final Object dataToset) {
		this.data = dataToset;
	}


	/**
	 * Gets the output.
	 *
	 * @return the output
	 */
	public final Type getOutput() {
		return output;
	}

	/**
	 * Sets the output.
	 *
	 * @param outputToSet the new output
	 */
	public final void setOutput(final Type outputToSet) {
		this.output = outputToSet;
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
		return String.format("RestResponse {%n Status: %s %n Content: %s %n}", this.statusCode.name(), this.content);
	}
}
