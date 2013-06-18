/**
 * 
 */
package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;


/**
 * 
 * 
 * Interface that places restraints on the ConceptCollectionManager class to implement
 * the required behaviors.
 * 
 *
 * @author satyaswaroop
 *
 */
public interface IConceptCollectionManager {

	/**
	 * Method is used to get list of concepts owned by the user. 
	 * Input: UserId
	 * Output: List of conceptcollections
	 * 
	 */
	public abstract List<IConceptCollection> getCollectionsOwnedbyUser(String sUserId);
	
	/**
	 * Method is used to get list of concepts collaborated by the user. 
	 * Input: UserId
	 * Output: List of conceptcollections
	 * 
	 */
	public abstract List<IConceptCollection> getUserCollaborations(String sUserId);
	
	/**
	 * Method is used to update concept collections data and items list 
	 * Input: ConceptCollection
	 * Output: String - message of success or failure
	 * 
	 */
	public abstract String updateConceptCollection(ConceptCollection conceptCollection);
	
	/**
	 * Method is used to delete a item from the list. 
	 * Input: ConceptcollecitonId
	 * Output: Success/failure
	 * 
	 */
	public abstract int deleteConceptCollection(String id);	
	
	/**
	 * Method is used to search the concept power rest api
	 * Input: item and pos of the word
	 * Output: ConceptpowerReply which contains a list of items
	 * 
	 */
	public ConceptpowerReply search(String item, String pos);
	
	/**
	 * Method is used to get collection details like description and  items list
	 * Input: IConceptCollection 
	 * Output: void. We place the result in the same input object
	 * 
	 */
	public abstract void getCollectionDetails(IConceptCollection concept);
	
	/**
	 * Method is used to add new items to items list of a conceptcollection
	 * Input: lemma, id, pos, description, string
	 * Result: We just update the backend
	 * 
	 */
	void addItems(String lemmma, String id, String pos, String desc,
			int conceptId);
	
	/**
	 * Method is used to add new  conceptcollection
	 * Input: Collection object
	 * Result: We just update the backend and return the success/failure
	 * 
	 */
	public abstract  String addConceptCollection(IConceptCollection collection);

	/**
	 * Method is used to delete items from the collection list of a particular user.
	 * Input: collectionname and id of the concept to be deleted
	 * 
	 */
	public abstract void deleteItem(String id, int i);
	/**
	 * Method is used to update the items list with the latest values from the conceptpower
	 * @param values 
	 * @param concept
	 */
	public abstract void update(String[] values, IConceptCollection concept);

	
}
