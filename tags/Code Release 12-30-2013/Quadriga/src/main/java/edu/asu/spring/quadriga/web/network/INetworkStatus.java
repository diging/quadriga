package edu.asu.spring.quadriga.web.network;

public interface INetworkStatus {

	public final static String PENDING = "PENDING";
	public final static String ASSIGNED = "ASSIGNED";
	public final static String APPROVED="APPROVED";
	public final static String REJECTED = "REJECTED";
	
	public final static int NOT_ARCHIVED = 0;
	public final static int ARCHIVE_LEVEL_ONE = 1;
	public final static int NOT_REACHABLE_ARCHIVE = 2;
	
	public final static String ANNOTATION_ID_PREFIX = "ANNOT_"; 
}
