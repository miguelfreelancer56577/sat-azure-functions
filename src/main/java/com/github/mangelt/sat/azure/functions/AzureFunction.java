package com.github.mangelt.sat.azure.functions;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;

import com.github.mangelt.sat.azure.functions.util.MultiPartResource;
import com.github.mangelt.sat.azure.functions.util.SatConstants;
import com.github.mangelt.sat.azure.functions.util.SatWebClient;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.BlobTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.StorageAccount;

import lombok.extern.slf4j.Slf4j;

/**
 * This class lists the azure functions used by the sat application.
 */
@Slf4j
public class AzureFunction
{

	/**
	 * Function triggered detecting changes on the fwd-create-membership-migration blob container
	 *
	 * Call the API that creates a new record in cosmos db with the name of the blob file.
	 *
	 * @param content {@link byte[]}
	 * @param fileName {@link String}
	 * @param context {@link ExecutionContext}
	 */
	@FunctionName("UnzipInvoices")
	@StorageAccount("CONNECTION_STRING_MXDEVSATINVOICEBLOB")
	public void createMembership(
			@BlobTrigger(name = "content", path = "mx-dev-sat-invoice-container/{fileName}", dataType = "binary") byte[] content,
			@BindingName("fileName") String fileName,
			final ExecutionContext context
			) {

		// check if the file has information
		int blobSize = content.length;

		log.info("Trigger UnzipInvoices Function with blob file: {} and file's size {} bytes.",  fileName, blobSize);

		//if the file contains info then call the API
		if(blobSize > 0) {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			ByteArrayResource resource = new MultiPartResource(content, fileName);

			map.set("file", resource);

			//			call API to upzip, read and upload invoices from xml to cosmos db
			ClientResponse result = SatWebClient.getInstance().post()
					.uri(SatConstants.INVOICE_MANAGEMENT_UPLOAD)
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.body(BodyInserters.fromMultipartData(map))
					.exchange().block();

			//			check if the response was successful
			if(org.springframework.http.HttpStatus.OK == result.statusCode()){
				log.info("The file was uploaded successfully.");
			}else {
				log.error("There was a error calling the service: \n {}" , result.bodyToMono(String.class).block());
			}

		}else {
			log.info("Your {} file is empty", fileName);
		}

	}

}
