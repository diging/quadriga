package edu.asu.spring.quadriga.service.impl;

import java.util.UUID;

public class BaseManager {

    public String generateUniqueID()
    {
        return UUID.randomUUID().toString();
    }
}
