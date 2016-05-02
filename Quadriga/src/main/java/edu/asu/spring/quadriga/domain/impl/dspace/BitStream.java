package edu.asu.spring.quadriga.domain.impl.dspace;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.dspace.IBitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataBitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataCollectionEntity;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataCommunityEntity;
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
	private String itemHandle;
	private String name;
	private String size;
	private String mimeType;
	private boolean isloaded;
	private String itemName;
	private List<String> communityIds;
	private List<String> collectionIds;

	private RestTemplate restTemplate;
	private Properties dspaceProperties;
	private String userName;
	private String password;
	private IDspaceKeys dspaceKeys;

	public BitStream()
	{
		setCommunityIds(Collections.synchronizedList(new ArrayList<String>()));
		setCollectionIds(Collections.synchronizedList(new ArrayList<String>()));
		isloaded = false;
	}

	public BitStream(String communityid, String collectionid, String itemid, String bitstreamid, RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String userName, String password)
	{
		this.id = bitstreamid;
		this.restTemplate = restTemplate;
		this.dspaceProperties = dspaceProperties;
		this.userName = userName;
		this.password = password;
		this.dspaceKeys = dspaceKeys;
		this.isloaded = false;
		setCommunityIds(new ArrayList<String>());
		setCollectionIds(new ArrayList<String>());

		if(communityid == null)
			setCommunityIds(Collections.synchronizedList(new ArrayList<String>()));
		if(communityid != null)
			this.communityIds.add(communityid);

		if(collectionid == null)
			setCollectionIds(Collections.synchronizedList(new ArrayList<String>()));
		if(collectionid != null)
			this.collectionIds.add(collectionid);

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
	
	public String getItemHandle() {
		return itemHandle;
	}

	public void setItemHandle(String itemHandle) {
		this.itemHandle = itemHandle;
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
	public List<String> getCommunityIds() {
		return communityIds;
	}

	@Override
	public void setCommunityIds(List<String> communityIds) {
		this.communityIds = communityIds;
	}

	@Override
	public void addCommunityId(String communityId) {
		if(this.communityIds == null)
			this.communityIds = Collections.synchronizedList(new ArrayList<String>());
		this.communityIds.add(communityId);
	}

	@Override
	public List<String> getCollectionIds() {
		return collectionIds;
	}

	@Override
	public void setCollectionIds(List<String> collectionIds) {
		this.collectionIds = collectionIds;
	}

	@Override
	public void addCollectionId(String collectionId) {
		if(this.collectionIds == null)
			this.collectionIds = Collections.synchronizedList(new ArrayList<String>());
		this.collectionIds.add(collectionId);
	}

	public boolean copy(IDspaceMetadataBitStream metadataBitstream)
	{
		if(metadataBitstream != null)
		{		
			this.id = metadataBitstream.getId();
			this.name = metadataBitstream.getName();
			this.size = metadataBitstream.getSize();
			List<IDspaceMetadataItemEntity> itemEntities = metadataBitstream.getBundles().getBundleEntity().getItems().getItementities();

			//Assumption: Bitstream belongs to only one item
			if(itemEntities != null && itemEntities.size() > 0)
			{
				IDspaceMetadataItemEntity itemEntity = itemEntities.get(0);
				this.itemName = itemEntity.getName();

				//Get all the dependent community ids
				List<IDspaceMetadataCommunityEntity> communityIds = itemEntity.getCommunities().getCommunityEntitites();
				for(IDspaceMetadataCommunityEntity communityEntity: communityIds)
					this.communityIds.add(communityEntity.getId());

				//Get all the dependent collection ids
				List<IDspaceMetadataCollectionEntity> collectionIds  = itemEntity.getCollections().getCollectionEntitites();
				for(IDspaceMetadataCollectionEntity collectionEntity: collectionIds)
					this.collectionIds.add(collectionEntity.getId());

			}

			return true;
		}	

		return false;
	}

	@Override
	public void run() {

		try {
			String restURLPath = getBitstreamMetadataPath(dspaceProperties, id, userName, password, dspaceKeys);
			IDspaceMetadataBitStream metadataBitstream = (IDspaceMetadataBitStream)restTemplate.getForObject(restURLPath, DspaceMetadataBitStream.class);
			this.copy(metadataBitstream);

		} catch (Exception e){
			//Can be caused for multiple reasons. Dspace is down or wrong authentication details.
		}
		finally
		{
			isloaded = true;
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

    @Override
    public String toString() {
        return "BitStream [getLoadStatus()=" + getLoadStatus() + ", getId()=" + getId() + ", getMimeType()="
                + getMimeType() + ", getName()=" + getName() + ", getSize()=" + getSize() + ", getItemName()="
                + getItemName() + "]";
    }

}
