package edu.asu.spring.quadriga.domain.implementation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataBitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataItemEntity;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceMetadataBitStream;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * The class representation of the ByteStream got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */
public class BitStream implements IBitStream{

	private String id;
	private String name;
	private String size;
	private String mimeType;
	private String communityid;
	private String collectionid;
	private String itemid;
	private String communityName;
	private String collectionName;
	private String itemName;
	private boolean isloaded;
	
	private RestTemplate restTemplate;
	private Properties dspaceProperties;
	private String userName;
	private String password;
	private IDspaceKeys dspaceKeys;
	
	public BitStream()
	{
		isloaded = false;
	}
	
	public BitStream(String id, RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String userName, String password)
	{
		this.id = id;
		this.restTemplate = restTemplate;
		this.dspaceProperties = dspaceProperties;
		this.userName = userName;
		this.password = password;
		this.dspaceKeys = dspaceKeys;
		this.isloaded = false;
	}
	
	private String getBitstreamMetadataPath(Properties dspaceProperties, String fileid, String email, String password, IDspaceKeys dspaceKeys) throws NoSuchAlgorithmException, QuadrigaStorageException
	{
		if(dspaceKeys != null)
		{
			if(dspaceKeys.getPublicKey()!=null && dspaceKeys.getPrivateKey()!=null && !dspaceKeys.getPublicKey().equals("") && !dspaceKeys.getPrivateKey().equals(""))
			{
				//Authenticate based on public and private key
				String stringToHash = dspaceProperties.getProperty("dspace.bitstream_url") + fileid + dspaceProperties.getProperty(".xml") + dspaceKeys.getPrivateKey();
				MessageDigest messageDigest = MessageDigest.getInstance(dspaceProperties.getProperty("dspace.algorithm"));
				messageDigest.update(stringToHash.getBytes());
				String digestKey = bytesToHex(messageDigest.digest()).substring(0, 8);

				return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid+dspaceProperties.getProperty("dspace.xml")+dspaceProperties.getProperty("dspace.?")+
						dspaceProperties.getProperty("dspace.api_key")+dspaceKeys.getPublicKey()+dspaceProperties.getProperty("dspace.&")+
						dspaceProperties.getProperty("dspace.api_digest")+digestKey;
			}
		}
		else if(email!=null && password!=null && !email.equals("") && !password.equals(""))
		{				
			//Authenticate based on email and password
			return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid+dspaceProperties.getProperty("dspace.xml")+dspaceProperties.getProperty("dspace.?")+
					dspaceProperties.getProperty("dspace.email")+email+
					dspaceProperties.getProperty("dspace.&")+dspaceProperties.getProperty("dspace.password")+password;
		}

		//No authentication provided
		return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid+dspaceProperties.getProperty("dspace.xml");
	}
	
	@Override
	public boolean getLoadStatus() {
		return isloaded;
	}

	@Override
	public void setLoadStatus(boolean isloaded) {
		this.isloaded = isloaded;
	}
	
	@Override
	public String getCommunityid() {
		return communityid;
	}

	@Override
	public void setCommunityid(String communityid) {
		this.communityid = communityid;
	}

	@Override
	public String getCollectionid() {
		return collectionid;
	}

	@Override
	public void setCollectionid(String collectionid) {
		this.collectionid = collectionid;
	}

	@Override
	public String getItemid() {
		return itemid;
	}

	@Override
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	@Override
	public String getCommunityName() {
		return communityName;
	}

	@Override
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	@Override
	public String getCollectionName() {
		return collectionName;
	}

	@Override
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	@Override
	public String getItemName() {
		return itemName;
	}

	@Override
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getMimeType() {
		return mimeType;
	}

	@Override
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;		
	}

	@Override
	public String getSize() {
		return this.size;
	}

	@Override
	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public void run() {
		
		try {
			String restURLPath = getBitstreamMetadataPath(dspaceProperties, id, userName, password, dspaceKeys);
			IDspaceMetadataBitStream metadataBitstream = (IDspaceMetadataBitStream)restTemplate.getForObject(restURLPath, DspaceMetadataBitStream.class);
			List<IDspaceMetadataItemEntity> itemEntities = metadataBitstream.getBundles().getBundleEntity().getItems().getItementities();
			
			for(IDspaceMetadataItemEntity itemEntity : itemEntities)
			{
				System.out.println("---------------------------------------------");
				System.out.println(itemEntity.getId());
				System.out.println(itemEntity.getName());
				System.out.println("---------------------------------------------");
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
