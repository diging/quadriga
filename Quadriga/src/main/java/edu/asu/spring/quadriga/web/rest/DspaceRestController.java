package edu.asu.spring.quadriga.web.rest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.autotag.core.runtime.annotation.Parameter;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;


@Controller
public class DspaceRestController {


	private static final Logger logger = LoggerFactory
			.getLogger(DspaceRestController.class);

	@Autowired
	private IDBConnectionListWSManager dbConnect;
	
	@Autowired
	private IDBConnectionDspaceManager dbDspaceManager;

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	@Resource(name = "dspaceStrings")
	private Properties dspaceProperties;
	
	@Autowired
	private IRestVelocityFactory restVelocityFactory;


	@RequestMapping(value = "rest/workspace/{workspaceid}/files", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listWorkspaceFiles(@PathVariable("workspaceid") String workspaceid, ModelMap model, Principal principal, HttpServletRequest request) throws RestException
	{
		VelocityEngine engine = null;
		Template template = null;

		try {
			engine = restVelocityFactory.getVelocityEngine(request);
			engine.init();

			// Retrieve the list of bitstreams for this workspace
			List<IBitStream> bitstreams = dbConnect.getBitStreams(workspaceid, principal.getName());

			template = engine.getTemplate("velocitytemplates/dspacefiles.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("list", bitstreams);

			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (ResourceNotFoundException e) {
			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (ParseErrorException e) {

			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (MethodInvocationException e) {

			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (QuadrigaStorageException e) {
			logger.error("Exception:", e);
			throw new RestException(405);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RestException(403);
		}
	}

	@RequestMapping(value = "rest/file/{fileid}", method = RequestMethod.GET)
	public void getWorkspaceFile(@PathVariable("fileid") String fileid, @RequestParam(value="email", required=false) String email, @RequestParam(value="password", required=false) String password, @RequestParam(value="public_key", required=false) String publicKey, @RequestParam(value="private_key", required=false) String privateKey, ModelMap model, Principal principal, HttpServletResponse response) throws RestException
	{
		ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
		InputStream inputStream = null;

		try {
			URL downloadUrl = new URL(getDspaceDownloadURLPath(fileid, email, password, publicKey, privateKey, principal.getName()));
			
			//Retrieve file from Dspace
			HttpURLConnection httpConnection = (HttpURLConnection) downloadUrl.openConnection();
			inputStream = httpConnection.getInputStream();
			byte[] byteChunk = new byte[4096];
			int sizeToBeRead;

			while ( (sizeToBeRead = inputStream.read(byteChunk)) > 0 ) {
				fileOutputStream.write(byteChunk, 0, sizeToBeRead);
			}
			fileOutputStream.flush();
			
			//Pass the file to response
			response.reset();
			response.setHeader("Content-Disposition",httpConnection.getHeaderField("Content-Disposition"));
			response.setContentType(httpConnection.getContentType());
			response.getOutputStream().write(fileOutputStream.toByteArray());
			logger.info("The user "+principal.getName()+" successfully downloaded the file "+httpConnection.getHeaderField("Content-Disposition")+" with fileid: "+fileid+" from Dspace");
		}
		catch(IOException ioe)
		{
			logger.info("Access exception occurred during file download. User: "+principal.getName()+" Fileid: "+fileid, ioe);
			throw new RestException(403);
		} catch (NoSuchAlgorithmException e) {
			logger.error("The algorithm used for dspace hashing is causing exception", e);
		} catch (QuadrigaStorageException e) {
			logger.error("Exception:", e);
			throw new RestException(405);
		}
		finally{
			if(inputStream != null)
			{
				try {
					inputStream.close();
				} catch (IOException ioe) {
				}
			}
		}
	}
	
	private String getDspaceDownloadURLPath(String fileid, String email, String password, String publicKey, String privateKey, String quadrigaUsername) throws NoSuchAlgorithmException, QuadrigaStorageException
	{
		if(publicKey!=null && privateKey!=null && !publicKey.equals("") && !privateKey.equals(""))
		{
			//Authenticate based on public and private key
			String stringToHash = dspaceProperties.getProperty("bitstream_url") + fileid + privateKey;
			MessageDigest messageDigest = MessageDigest.getInstance(dspaceProperties.getProperty("algorithm"));
			messageDigest.update(stringToHash.getBytes());
			String digestKey = bytesToHex(messageDigest.digest()).substring(0, 8);
			
			return dspaceProperties.getProperty("bitstream_download_url")+fileid+dspaceProperties.getProperty("?")+
					dspaceProperties.getProperty("api_key")+publicKey+dspaceProperties.getProperty("&")+
					dspaceProperties.getProperty("api_digest")+digestKey;
		}
		else if(email!=null && password!=null && !email.equals("") && !password.equals(""))
		{				
			//Authenticate based on email and password
			return dspaceProperties.getProperty("bitstream_download_url")+fileid+dspaceProperties.getProperty("?")+
					dspaceProperties.getProperty("email")+email+
					dspaceProperties.getProperty("&")+dspaceProperties.getProperty("password")+password;
		}
		
		//Try to get the keys from the database
		IDspaceKeys dspacekey = dbDspaceManager.getDspaceKeys(quadrigaUsername);
		if(dspacekey != null)
		{
			//User has keys stored in quadriga database
			//Authenticate based on public and private key
			String stringToHash = dspaceProperties.getProperty("bitstream_url") + fileid + dspacekey.getPrivateKey();
			MessageDigest messageDigest = MessageDigest.getInstance(dspaceProperties.getProperty("algorithm"));
			messageDigest.update(stringToHash.getBytes());
			String digestKey = bytesToHex(messageDigest.digest()).substring(0, 8);
			
			return dspaceProperties.getProperty("bitstream_download_url")+fileid+dspaceProperties.getProperty("?")+
					dspaceProperties.getProperty("api_key")+dspacekey.getPublicKey()+dspaceProperties.getProperty("&")+
					dspaceProperties.getProperty("api_digest")+digestKey;
		}
		
		//No authentication provided
		return dspaceProperties.getProperty("bitstream_download_url")+fileid;
	}

	private String bytesToHex(byte[] b) {
	      char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
	                         '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	      StringBuffer buf = new StringBuffer();
	      for (int j=0; j<b.length; j++) {
	         buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
	         buf.append(hexDigit[b[j] & 0x0f]);
	      }
	      return buf.toString();
	   }
}
