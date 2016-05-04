package edu.asu.spring.quadriga.web.network;

/**
 * Interface to hold constants specifying the network status
 */
public interface INetworkStatus {

	// Network status constants
	public final static String PENDING = "PENDING";
	public final static String ASSIGNED = "ASSIGNED";
	public final static String APPROVED="APPROVED";
	public final static String REJECTED = "REJECTED";
	public final static String UNKNOWN = "UNKNOWN";
	
	// Status code of the network
	public final static int PENDING_CODE = 0;
	public final static int ASSIGNED_CODE = 1;
	public final static int APPROVED_CODE= 2;
	public final static int REJECTED_CODE = 3;
	public final static int UNKNOWN_CODE = -1;
	
	public final static int NOT_ARCHIVED = 0;
	public final static int ARCHIVE_LEVEL_ONE = 1;
	public final static int NOT_REACHABLE_ARCHIVE = 2;
	
	public final static String ANNOTATION_ID_PREFIX = "ANNOT_"; 
}
