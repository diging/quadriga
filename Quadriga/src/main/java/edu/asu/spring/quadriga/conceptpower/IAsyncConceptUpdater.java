package edu.asu.spring.quadriga.conceptpower;

import org.springframework.scheduling.annotation.Async;

public interface IAsyncConceptUpdater {

    void updateConcept(String uri);

}