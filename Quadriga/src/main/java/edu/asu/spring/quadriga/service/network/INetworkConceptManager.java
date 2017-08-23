package edu.asu.spring.quadriga.service.network;

import java.util.List;

import javax.xml.bind.JAXBException;

import edu.asu.spring.quadriga.conceptpower.IConcept;

public interface INetworkConceptManager {

    List<IConcept> getConceptsOfStatements(List<String> topNodes) throws JAXBException;

}