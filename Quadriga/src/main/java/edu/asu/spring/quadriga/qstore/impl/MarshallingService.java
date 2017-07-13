package edu.asu.spring.quadriga.qstore.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.impl.ElementEventsType;
import edu.asu.spring.quadriga.qstore.IMarshallingService;

/**
 * Class provides marshalling services for QStore responses.
 * 
 * @author  Lohith Dwaraka, jdamerow
 *
 */
@Service
public class MarshallingService implements IMarshallingService {

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.qstore.impl.IMarshallingService#unMarshalXmlToElementEventsType(java.lang.String)
     */
    @Override
    public ElementEventsType unMarshalXmlToElementEventsType(String xml) throws JAXBException {
        ElementEventsType elementEventType = null;

        // Try to unmarshall the XML got from QStore to an ElementEventsType
        // object
        JAXBContext context = JAXBContext.newInstance(ElementEventsType.class);
        Unmarshaller unmarshaller1 = context.createUnmarshaller();
        unmarshaller1.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        JAXBElement<ElementEventsType> response1 = unmarshaller1.unmarshal(new StreamSource(is),
                ElementEventsType.class);
        elementEventType = response1.getValue();

        return elementEventType;
    }
}
