package edu.asu.spring.quadriga.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Iterator;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.RestAccessPolicies;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.factories.IDspaceKeysFactory;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataBitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataBundleEntity;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataItemEntity;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceMetadataBitStream;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.workspace.ICheckWSSecurity;
import edu.asu.spring.quadriga.web.login.RoleNames;

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
	private IRestMessage restMessage;

	@Autowired
	private IDspaceManager dspaceManager;

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	@Resource(name = "dspaceStrings")
	private Properties dspaceProperties;

	@Autowired
	private IRestVelocityFactory restVelocityFactory;

	@Autowired
	private ICheckWSSecurity securityCheck;
	
	@Autowired
	private IDspaceKeysFactory dspaceKeysFactory;
	

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
			List<IBitStream> bitstreams = dspaceManager.getBitstreamsInWorkspace(workspaceid, principal.getName());
			
			Iterator<IBitStream> bitStreamIterator = bitstreams.iterator();
			while(bitStreamIterator.hasNext()){
				IBitStream bit = bitStreamIterator.next();
				logger.info(bit.getId());
				logger.info(bit.getName());
				logger.info(bit.getItemName());
				logger.info(bit.getMimeType());
				logger.info(bit.getSize());
				logger.info(""+ bit.getLoadStatus());
			}

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
	 * Rest Api to validate a dspace credential. When the combination of both email+password and public key + private key are provided,
	 * the keys take precedence over the traditional email-password authentication. 
	 * 
	 * @param email				The dspace username of the user.
	 * @param password			The dspace password of the user account.
	 * @param publicKey			The dspace public key of the user.
	 * @param privateKey		The dspace private key of the user.
	 * @param response			The response of this rest call. If the dspace credentials are valid then the response code is 200 else its 401.
	 */
	@RequestMapping(value = "rest/dspace/validate", method = RequestMethod.GET)
	public void validateDspaceCredentials(@RequestParam(value="email", required=false) String email, @RequestParam(value="password", required=false) String password, @RequestParam(value="public_key", required=false) String publicKey, @RequestParam(value="private_key", required=false) String privateKey, ModelMap model, Principal principal, HttpServletResponse response)
	{
		IDspaceKeys dspaceKeys = null;
		if(privateKey != null && !privateKey.equals("") && publicKey != null && !publicKey.equals(""))
		{
			dspaceKeys = dspaceKeysFactory.createDspaceKeysObject();
			dspaceKeys.setPrivateKey(privateKey);
			dspaceKeys.setPublicKey(publicKey);
		}
		
		if(dspaceManager.validateDspaceCredentials(email, password, dspaceKeys))
			response.setStatus(200);
		else 
			response.setStatus(401);
		
	}

	/**
	 * This controller downloads the requested file from Dspace and returns the file as a response.
	 * 
	 * Any mode of Dspace authentication(email+password or keys) can be used by the system invoking this interface. If both of them are set then the keys 
	 * take precedence over the email/password. If none is provided then the keys stored in Quadriga will be used to access the Dspace. 
	 * If no keys are found in Quadriga, then Dspace will be accessed as an anonymous user.
	 * 
	 * @param fileid			The id of the file to be downloaded
	 * @param email				The dspace email id of the user.
	 * @param password			The dspace password of the user.
	 * @param publicKey			The dspace public key of the user.
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
	 * This method is used to generate the Dspace url for downloading the file.
	 * Based on what authentication details are provided, the url will vary for each case.
	 * 
	 * Any mode of Dspace authentication(email+password or keys) can be used by the system invoking this interface. If both of them are set then 
	 * the keys take precedence over the email/password. If none is provided then the keys stored in Quadriga will be used to access the Dspace. 
	 * If no keys are found in Quadriga, then Dspace will be accessed as an anonymous user.
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
			String stringToHash = dspaceProperties.getProperty("dspace.bitstream_url") + fileid + privateKey;
			MessageDigest messageDigest = MessageDigest.getInstance(dspaceProperties.getProperty("dspace.algorithm"));
			messageDigest.update(stringToHash.getBytes());
			String digestKey = bytesToHex(messageDigest.digest()).substring(0, 8);

			return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid+dspaceProperties.getProperty("dspace.?")+
					dspaceProperties.getProperty("dspace.api_key")+publicKey+dspaceProperties.getProperty("dspace.&")+
					dspaceProperties.getProperty("dspace.api_digest")+digestKey;
		}
		else if(email!=null && password!=null && !email.equals("") && !password.equals(""))
		{				
			//Authenticate based on email and password
			return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid+dspaceProperties.getProperty("dspace.?")+
					dspaceProperties.getProperty("dspace.email")+email+
					dspaceProperties.getProperty("dspace.&")+dspaceProperties.getProperty("dspace.password")+password;
		}

		//Try to get the keys from the database
		IDspaceKeys dspacekey = dspaceManager.getDspaceKeys(quadrigaUsername);
		if(dspacekey != null)
		{
			//User has keys stored in quadriga database
			//Authenticate based on public and private key
			String stringToHash = dspaceProperties.getProperty("dspace.bitstream_url") + fileid + dspacekey.getPrivateKey();
			MessageDigest messageDigest = MessageDigest.getInstance(dspaceProperties.getProperty("dspace.algorithm"));
			messageDigest.update(stringToHash.getBytes());
			String digestKey = bytesToHex(messageDigest.digest()).substring(0, 8);

			return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid+dspaceProperties.getProperty("dspace.?")+
					dspaceProperties.getProperty("dspace.api_key")+dspacekey.getPublicKey()+dspaceProperties.getProperty("dspace.&")+
					dspaceProperties.getProperty("dspace.api_digest")+digestKey;
		}

		//No authentication provided
		return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid;
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

	/**
	 * This method is used to add a Dspace bitstream to a Quadriga workspace. If all the fields community id, collection id, item id and bitstream id are provided
	 * then only a check for authorized access to the bitstream is performed and if its successful, the bistream is added to the workspace. If any of those fields are
	 * missing then the methods finds the name of all dependent communities, collections and items and returns them in a list.
	 * 
	 * Any mode of Dspace authentication(email+password or keys) can be used by the system invoking this interface. If both of them are set then 
	 * the keys take precedence over the email/password. If none is provided then the keys stored in Quadriga will be used to access the Dspace. 
	 * If no keys are found in Quadriga, then Dspace will be accessed as an anonymous user. 
	 * 
	 * @param workspaceid			The id of the Quadriga workspace to which the file is to be added.
	 * @param fileid				The Dspace id of the file to be added. 
	 * @param quadrigaUsername		The Quadriga username of the user trying to add the file.
	 * @param email					The Dspace email of the user.
	 * @param password				The Dspace password of the user.
	 * @param publicKey				The Dspace public key of the user.
	 * @param privateKey			The Dspace private key of the user.
	 * @return
	 * @throws RestException
	 */
	@RequestMapping(value = "rest/workspace/{workspaceid}/uploadfile", method = RequestMethod.POST, produces = "application/xml")
	@ResponseBody
	@RestAccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE_REST,paramIndex = 1, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN,RoleNames.ROLE_WORKSPACE_COLLABORATOR_CONTRIBUTOR} )})
	public String addFileToWorkspace(@PathVariable("workspaceid") String workspaceid, @RequestParam("fileid") String fileid, @RequestParam(value="username") String quadrigaUsername, @RequestParam(value="email", required=false) String email, @RequestParam(value="password", required=false) String password, @RequestParam(value="public_key", required=false) String publicKey, @RequestParam(value="private_key", required=false) String privateKey, ModelMap model, Principal principal, HttpServletRequest request, HttpServletResponse response) throws RestException
	{
		IDspaceKeys dspaceKeys = null;
		
		List<ICommunity> communityList = null;


		/**
		 * If the workspaceid and fileid are valid. Then add the file to the workspace. 
		 */
		if(workspaceid != null && !workspaceid.equals("") && quadrigaUsername != null && !quadrigaUsername.equals("") && fileid!=null && !fileid.equals(""))
		{
			try {
				//Make a rest call to check if the user has access to dspace fileid.
				String restURLPath = getBitstreamMetadataPath(fileid, email, password, publicKey, privateKey, quadrigaUsername);
				IDspaceMetadataBitStream metadataBitstream = (IDspaceMetadataBitStream)restTemplate.getForObject(restURLPath, DspaceMetadataBitStream.class);

				String itemHandle = null;
				// get item handle for bitstream
				if (metadataBitstream.getBundles() != null && metadataBitstream.getBundles().getBundleEntity() != null) {
					IDspaceMetadataBundleEntity entry = metadataBitstream.getBundles().getBundleEntity();
					if (entry != null && entry.getItems() != null && entry.getItems().getItementities() != null) {
						if (entry.getItems().getItementities().size() > 0) {
							// we only get the first item, this might be wrong and needs to be checked.
							IDspaceMetadataItemEntity itemEntry = entry.getItems().getItementities().get(0);
							itemHandle = itemEntry.getHandle();
						}
					}
				}
				
				//Add the file to the workspace
				dspaceManager.addBitStreamsToWorkspaceThroughRestInterface(workspaceid, fileid, itemHandle, quadrigaUsername);
				response.setStatus(201);
				return restMessage.getSuccessMsg(dspaceProperties.getProperty("dspace.file_add_success"),request);
			} 
			catch(HttpClientErrorException e){
				//Thrown when the dspace username and password or dspace keys do not have access to the file. 
				logger.error("Exception: ",e);
				throw new RestException(401);
			}
			catch(HttpServerErrorException e){
				//No such file name was found. 
				logger.error("Exception: ",e);
				throw new RestException(404);
			}
			catch (NoSuchAlgorithmException e) {
				//System error
				e.printStackTrace();
				throw new RestException(406);
			}
			catch (QuadrigaStorageException e) {
				//Error in the database
				e.printStackTrace();
				throw new RestException(404);
			} catch (QuadrigaAccessException e) {
				//User not allowed to access the workspace
				e.printStackTrace();
				throw new RestException(403);
			}
		}

		return restMessage.getErrorMsg(dspaceProperties.getProperty("dspace.file_add_error"),request);

	}

	private String getBitstreamMetadataPath(String fileid, String email, String password, String publicKey, String privateKey, String quadrigaUsername) throws NoSuchAlgorithmException, QuadrigaStorageException
	{
		if(publicKey!=null && privateKey!=null && !publicKey.equals("") && !privateKey.equals(""))
		{
			//Authenticate based on public and private key
			String stringToHash = dspaceProperties.getProperty("dspace.bitstream_url") + fileid + dspaceProperties.getProperty(".xml") + privateKey;
			MessageDigest messageDigest = MessageDigest.getInstance(dspaceProperties.getProperty("dspace.algorithm"));
			messageDigest.update(stringToHash.getBytes());
			String digestKey = bytesToHex(messageDigest.digest()).substring(0, 8);

			return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid+dspaceProperties.getProperty("dspace.xml")+dspaceProperties.getProperty("dspace.?")+
					dspaceProperties.getProperty("dspace.api_key")+publicKey+dspaceProperties.getProperty("dspace.&")+
					dspaceProperties.getProperty("dspace.api_digest")+digestKey;
		}
		else if(email!=null && password!=null && !email.equals("") && !password.equals(""))
		{				
			//Authenticate based on email and password
			return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid+dspaceProperties.getProperty("dspace.xml")+dspaceProperties.getProperty("dspace.?")+
					dspaceProperties.getProperty("dspace.email")+email+
					dspaceProperties.getProperty("dspace.&")+dspaceProperties.getProperty("dspace.password")+password;
		}

		//Try to get the keys from the database
		IDspaceKeys dspacekey = dspaceManager.getDspaceKeys(quadrigaUsername);
		if(dspacekey != null)
		{
			//User has keys stored in quadriga database
			//Authenticate based on public and private key
			String stringToHash = dspaceProperties.getProperty("dspace.bitstream_url") + fileid + dspaceProperties.getProperty("dspace.xml") + dspacekey.getPrivateKey();
			MessageDigest messageDigest = MessageDigest.getInstance(dspaceProperties.getProperty("dspace.algorithm"));
			messageDigest.update(stringToHash.getBytes());
			String digestKey = bytesToHex(messageDigest.digest()).substring(0, 8);

			return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid+dspaceProperties.getProperty("dspace.xml")+dspaceProperties.getProperty("dspace.?")+
					dspaceProperties.getProperty("dspace.api_key")+dspacekey.getPublicKey()+dspaceProperties.getProperty("dspace.&")+
					dspaceProperties.getProperty("dspace.api_digest")+digestKey;
		}

		//No authentication provided
		return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid+dspaceProperties.getProperty("dspace.xml");
	}
}
