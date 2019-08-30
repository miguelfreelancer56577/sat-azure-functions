package com.github.mangelt.sat.azure.functions.util;

/**
 * Get the environment variables which are living on the azure resource
 */
public class SatConstants
{

	//	Environment variables set on azure resource

	public static final String HOST = System.getenv("HOST_API");
	public static final String INVOICE_MANAGEMENT_UPLOAD = System.getenv("INVOICE_MANAGEMENT_UPLOAD");

	private SatConstants() {}
}
