package com.ws.soap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.dom.WSConstants;

import com.bharath.ws.soap.PaymentProcessor;
import com.bharath.ws.soap.PaymentProcessorRequest;
import com.bharath.ws.soap.PaymentProcessorResponse;
import com.bharath.ws.soap.PaymentProcessorImplService;

public class PaymentWSClient {

	public static void main(String[] args) throws MalformedURLException {

		// Create the service stub and pass it the WSDL URL.
		PaymentProcessorImplService service = new PaymentProcessorImplService(
				new URL("http://localhost:8080/javafirstws/paymentProcessor?wsdl"));
		// Create a port type out of the service that wraps all the operations.
		PaymentProcessor port = service.getPaymentProcessorImplPort();
		// Get the client from ClientProxy
		Client client = ClientProxy.getClient(port);
		// Using the client we can get the endpoint.
		Endpoint endpoint = client.getEndpoint();
		
		// Our goal is to configure an out interceptor. Use the WSS4JoutInterceptor that takes a Map of String and Object arguments.
		// props (properties) we'll configure in the next section. They are the username, username token profile action, the password callback and all that.
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(ConfigurationConstants.ACTION, ConfigurationConstants.USERNAME_TOKEN);
		props.put(ConfigurationConstants.USER, "cxf");
		props.put(ConfigurationConstants.PASSWORD_TYPE, WSConstants.PW_TEXT );
		
		props.put(ConfigurationConstants.PW_CALLBACK_CLASS, UTPasswordCallback.class.getName());
		
		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(props);
		// Finally we need to add this interceptor to the endpoint itself. 
		endpoint.getOutInterceptors().add(wssOut);
		
		// Get a response back by passing in a payment processor request.
		PaymentProcessorResponse response = port.processPayment(new PaymentProcessorRequest());
		System.out.println(response.isResult());
	}
}
