package edu.asu.spring.quadriga.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.xml.transform.Source;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;


import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;

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

		//To overcome certification validation
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
		    public X509Certificate[] getAcceptedIssuers(){return null;}
		    public void checkClientTrusted(X509Certificate[] certs, String authType){}
		    public void checkServerTrusted(X509Certificate[] certs, String authType){}
		}};

		// Install the all-trusting trust manager
		try {
		    SSLContext sc = SSLContext.getInstance("TLS");
		    sc.init(null, trustAllCerts, new SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		    System.out.println("Error");
		}
		
		
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
	@RequestMapping(value = "/workspace", method = RequestMethod.GET)
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
