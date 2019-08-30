package com.github.mangelt.sat.azure.functions.util;

import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

/**
 * Util Class.
 *
 */
@Slf4j
public class SatWebClient
{
	private static final WebClient SINGLE_INSTANCE = WebClient.create(SatConstants.HOST);


	private SatWebClient() {}

	/**
	 * Singleton instance of the {@link WebClient} to call API's
	 *
	 * @return {@link WebClient}
	 */
	public static WebClient getInstance() {
		return SINGLE_INSTANCE;
	}

}
