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
import java.util.ArrayList;
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
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataBitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataCollectionEntity;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataCommunityEntity;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataItemEntity;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceKeys;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceMetadataBitStream;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.workspace.ICheckWSSecurity;

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
	@Qualifier("listWSManagerDAO")
	private IDBConnectionListWSManager dbConnect;

	@Autowired
	//	@Qualifier("dspaceManagerDAO")
	private IDBConnectionDspaceManager dbDspaceManager;

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
	 * Any mode of Dspace authentication(email+password or keys) can be used by the system invoking this interface. If both of them are set then the keys 
	 * take precedence over the email/password. If none is provided then the keys stored in Quadriga will be used to access the Dspace. 
	 * If no keys are found in Quadriga, then Dspace will be accessed as an anonymous user.
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
		IDspaceKeys dspacekey = dbDspaceManager.getDspaceKeys(quadrigaUsername);
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
	 * @param communityid			The Dspace id of the community under which the file is stored.
	 * @param collectionid			The Dspace id of the collection under which the file is stored.
	 * @param itemid				The Dspace id of the item under which the file is stored.
	 * @param quadrigaUsername		The Quadriga username of the user trying to add the file.
	 * @param email					The Dspace email of the user.
	 * @param password				The Dspace password of the user.
	 * @param publicKey				The Dspace public key of the user.
	 * @param privateKey			The Dspace private key of the user.
	 * @return
	 * @throws RestException
	 */
	@RequestMapping(value = "rest/workspace/{workspaceid}", method = RequestMethod.POST, produces = "application/xml")
	@ResponseBody
	public String addFileToWorkspace(@PathVariable("workspaceid") String workspaceid, @RequestParam("fileid") String fileid,  @RequestParam(value="communityid", required=false) String communityid, @RequestParam(value="collectionid", required=false) String collectionid, @RequestParam(value="itemid", required=false) String itemid, @RequestParam(value="username", required=false) String quadrigaUsername, @RequestParam(value="email", required=false) String email, @RequestParam(value="password", required=false) String password, @RequestParam(value="public_key", required=false) String publicKey, @RequestParam(value="private_key", required=false) String privateKey, ModelMap model, Principal principal, HttpServletRequest request, HttpServletResponse response) throws RestException
	{
		IDspaceKeys dspaceKeys = null;
		if(privateKey != null && !privateKey.equals("") && publicKey != null && !publicKey.equals(""))
		{
			dspaceKeys = new DspaceKeys();
			dspaceKeys.setPrivateKey(privateKey);
			dspaceKeys.setPublicKey(publicKey);
		}
		List<ICommunity> communityList = null;


		/**
		 * If the workspaceid, fileid, communityid, collectionid, itemid and quadriga username are provided, 
		 * then add the file to the workspace 
		 */
		if(workspaceid != null && !workspaceid.equals("") && quadrigaUsername != null && !quadrigaUsername.equals(""))
		{
			//Check if its a proper workspace and also check if the user has access to it
			if(communityid!=null && !communityid.equals("") && collectionid!=null && !collectionid.equals("") && itemid!=null && !itemid.equals("") && fileid!=null && !fileid.equals(""))
			{
				try {
					//Check if the file id is a valid bitstream id that the user has access
					String restURLPath = getBitstreamMetadataPath(fileid, email, password, publicKey, privateKey, quadrigaUsername);
					IDspaceMetadataBitStream metadataBitstream = (IDspaceMetadataBitStream)restTemplate.getForObject(restURLPath, DspaceMetadataBitStream.class);

					//Check if the user has access to modify the quadriga workspace
					if(securityCheck.chkModifyWorkspaceAccess(quadrigaUsername, workspaceid))
					{
						//Add the file to the workspace
						dspaceManager.addBitStreamsToWorkspaceThroughRestInterface(workspaceid, communityid, collectionid, itemid, fileid, quadrigaUsername);
						return restMessage.getSuccessMsg(dspaceProperties.getProperty("dspace.file_add_success"),request);
					}
					else
					{
						//User not allowed to modify the workspace
						throw new RestException(403);
					}					
				} 
				catch(HttpClientErrorException e){
					//Thrown when the dspace username and password or dspace keys do not have access to the file. 
					logger.error("Exception: ",e);
					throw new RestException(401);
				}
				catch (QuadrigaStorageException e) {
					//Error in the database
					e.printStackTrace();
					throw new RestException(404);
				} catch (QuadrigaAccessException e) {
					//User not allowed to access the workspace
					e.printStackTrace();
					throw new RestException(403);
				} catch (NoSuchAlgorithmException e) {
					//System error
					e.printStackTrace();
					throw new RestException(406);
				}
			}
		}

		/**
		 * Find out the dependent community id, collection id and item id for the given file.
		 */

		try
		{
			//Build the xml containing the community, collection and item details
			String restURLPath = getBitstreamMetadataPath(fileid, email, password, publicKey, privateKey, quadrigaUsername);

			IDspaceMetadataBitStream metadataBitstream = (IDspaceMetadataBitStream)restTemplate.getForObject(restURLPath, DspaceMetadataBitStream.class);
			List<IDspaceMetadataItemEntity> itemEntities = metadataBitstream.getBundles().getBundleEntity().getItems().getItementities();
			communityList = new ArrayList<ICommunity>();

			for(IDspaceMetadataItemEntity itemEntity : itemEntities)
			{
				List<IDspaceMetadataCommunityEntity> communityIds = itemEntity.getCommunities().getCommunityEntitites();
				List<IDspaceMetadataCollectionEntity> collectionIds  = itemEntity.getCollections().getCollectionEntitites();
				for(int i=0;i<communityIds.size();i++)
				{
					IDspaceMetadataCommunityEntity communityMetadata = communityIds.get(i);

					ICommunity community = dspaceManager.getCommunity(dspaceKeys, email, password, true, communityMetadata.getId());

					//Load all the collections of the particular community
					dspaceManager.getAllCollections(dspaceKeys, email, password, community.getId());	
					
					IDspaceMetadataCollectionEntity collectionMetadata = collectionIds.get(i);
					//Get the particular collection and add it to the community
					ICollection collection = dspaceManager.getCollection(collectionMetadata.getId(),community.getId());
					//Wait for the collection to load
					while(collection.getLoadStatus() == false)
					{
						Thread.sleep(1000);
					};
					IItem item = collection.getItem(itemEntity.getId());
					community.setCollections(null);
					collection.setItems(null);
					community.addCollection(collection);
					collection.addItem(item);

					communityList.add(community);

				}
			}

			//Setup the xml response with the bitstream dependency details
			VelocityEngine engine = restVelocityFactory.getVelocityEngine(request);
			Template template = null;
			engine.init();
			template = engine.getTemplate("velocitytemplates/bitstreamdependencylist.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());

			context.put("fileid", fileid);
			context.put("workspaceid", workspaceid);
			context.put("list", communityList);

			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		}
		catch(HttpClientErrorException e)
		{
			logger.error("Exception: ",e);
			throw new RestException(401);
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
		IDspaceKeys dspacekey = dbDspaceManager.getDspaceKeys(quadrigaUsername);
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
