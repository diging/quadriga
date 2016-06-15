package edu.asu.spring.quadriga.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Locale;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Controller
public class TrialController {

	public static void trustSelfSignedSSL() {

		try
		{
			String keystoreType = "JKS";
			InputStream keystoreLocation = null;
			char [] keystorePassword = null;
			char [] keyPassword = null;

			KeyStore keystore = KeyStore.getInstance(keystoreType);
			keystore.load(keystoreLocation, keystorePassword);
			KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmfactory.init(keystore, keyPassword);

			InputStream truststoreLocation = null;
			char [] truststorePassword = null;
			String truststoreType = "JKS";

			KeyStore truststore = KeyStore.getInstance(truststoreType);
			truststore.load(truststoreLocation, truststorePassword);
			TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

			KeyManager [] keymanagers = kmfactory.getKeyManagers();
			TrustManager [] trustmanagers =  tmfactory.getTrustManagers();

			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(keymanagers, trustmanagers, new SecureRandom());
			SSLContext.setDefault(sslContext);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}



	public String callRestService(String request) {
		String line="";
		
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(request);
		
		try
		{			
			// Send GET request
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("Method failed: " + method.getStatusLine());
			}
			InputStream rstream = null;

			// Get the response body
			rstream = method.getResponseBodyAsStream();

			// Process the response 
			BufferedReader br = new BufferedReader(new InputStreamReader(rstream));

			while ((line = br.readLine()) != null) {
			}
			br.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

		return line;
	} 

	/**
	 * Simply selects the home view to render by returning its name.
	 */
//	@RequestMapping(value = "/workspace", method = RequestMethod.GET)
	public String home(Locale locale, Model model, Principal principal) {
		// Get the LDAP-authenticated userid
		String sUserId = principal.getName();		
		model.addAttribute("username", sUserId);


		System.out.println("Controller request received..");
		//		RestTemplate rest = new RestTemplate();
		//		Map<String, String> params = new HashMap<String, String>();
		//		params.put("email", "ramk@asu.edu");
		//		params.put("password", "123456");
		//		Source result = rest.getForObject("https://import.hps.ubio.org/rest/communities.xml?email={email}&password={password}", Source.class, params);


		String output = callRestService("https://import.hps.ubio.org/rest/communities.xml?email=ramk@asu.edu&password=123456");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.print(output);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("Hurrahhhh");
		return "auth/home";
	}


}
