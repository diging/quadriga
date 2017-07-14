package edu.asu.spring.quadriga.qstore;

import javax.xml.bind.JAXBException;

import edu.asu.spring.quadriga.domain.network.impl.ElementEventsType;

public interface IMarshallingService {

    /**
     * This method shoud help in Unmarshalling XML {@link String} into {@link ElementEventsType} object.
     * @param xml                               XML in the form of {@link String}   
     * @return                                  Returns the {@link ElementEventsType} object for a particular QStore XML
     * @throws JAXBException                    Throws JAXB exception in case we have issues while unmarshalling.
     */
    public abstract ElementEventsType unMarshalXmlToElementEventsType(String xml)
            throws JAXBException;

}