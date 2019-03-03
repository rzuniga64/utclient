package com.ws.soap;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

public class UTPasswordCallback implements CallbackHandler {

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

		// We’ll loop through that array.  We will see if the username 
		// is ‘cxf’ then we are going to set the password as ‘cxf’.
		for (int i = 0; i < callbacks.length; i++)  {
			WSPasswordCallback wpc = (WSPasswordCallback) callbacks[i];
			if (wpc.getIdentifier().equals("cxf")) {
				wpc.setPassword("cxf");
				return;
			}
		}
	}
}
