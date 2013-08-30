package edu.asu.spring.quadriga.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

/**
 * This class handles the rest requests for file downloads
 * belonging to a workspace.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
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


	/**
	 * Produce an xml listing all the files in a workspace
	 * 
	 * @param workspaceid		The id of the workspace.
	 * @return					An xml template listing all the files in the workspace
	 * @throws RestException	Exception with corresponding error keys set. Thrown when the operation encounters any errors.
	 */
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

	/**
	 * This controller downloads the requested file from Dspace and returns the file as a response.
	 * 
	 * NOTE: If the public and private keys were stored in the quadriga database then no authentication details need to be set.
	 * Else either the key fields or the email/password fields need to be set. If both of them are set then the keys take precedence over the email/password.
	 * 
	 * @param fileid			The id of the file to be downloaded
	 * @param email				The dspace email id of the user.
	 * @param password			The dspace password of the user.
	 * @param publicKey			The dspae public key of the user.
	 * @param privateKey		The dspace private key of the user.
	 * @param principal			This is used to retrieve the logged in username.
	 * @param response			The response of this method which will contain the file and its metadata.
	 * @throws RestException	Thrown when the user tries to access an unauthorized file.
	 */
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
	
	/**
	 * This method is used to generate the dspace url for downloading the file.
	 * Based on what authentication details are provided, the url will vary for each case.
	 * 
	 * NOTE: If all the authentication fields are empty then this function will try to pull the keys from the quadriga database.
	 * When even that returns empty, then the url returned will contain no authentication details.
	 * 
	 * @param fileid						The id of the file.
	 * @param email							The dspace email of the user.
	 * @param password						The dspace password of the user.
	 * @param publicKey						The dspace public key of the user.
	 * @param privateKey					The dspace private key of the user.
	 * @param quadrigaUsername				The quadriga username of the person trying to download the file.
	 * 
	 * @return								The dspace download url for the given file based on the authentication details.
	 * @throws NoSuchAlgorithmException		When the algorithm provided to hash is not supported by message digest in java.
	 * @throws QuadrigaStorageException		When an error occurs in the quadriga database.
	 */
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
