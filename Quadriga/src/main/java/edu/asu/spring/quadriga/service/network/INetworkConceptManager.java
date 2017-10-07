package edu.asu.spring.quadriga.service.network;

import java.util.List;

import javax.xml.bind.JAXBException;

import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.exceptions.NoCacheEntryForKeyException;

public interface INetworkConceptManager {

    void getConceptsOfStatements(int key, List<String> topNodes, List<String> typeIdList) throws JAXBException;

    List<IConcept> getQueryResult(int key) throws NoCacheEntryForKeyException;

}