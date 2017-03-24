package edu.asu.spring.quadriga.qstore.async.impl;

import static edu.asu.spring.quadriga.qstore.ExecutionStatus.RUNNING;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.qstore.ExecutionStatus;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;
import edu.asu.spring.quadriga.qstore.async.IQStoreAsyncTaskManager;

@Service
public class QStoreAsyncTaskManager implements IQStoreAsyncTaskManager {

    @Autowired
    private IQStoreConnector qStoreConnector;

    private Future<String> publicNetworks;

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = "publicNetworksStatus")
    public ExecutionStatus startLoadingPublicNetworks() throws QStoreStorageException {
        publicNetworks = qStoreConnector.loadNetworkWithPopularTerms();
        return RUNNING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNetworkWithPopularTerms() throws QStoreStorageException {
        try {
            return publicNetworks.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new QStoreStorageException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getPublicNetworkStatus() {
        if (publicNetworks != null) {
            return publicNetworks.isDone();
        }
        return false;
    }
}
