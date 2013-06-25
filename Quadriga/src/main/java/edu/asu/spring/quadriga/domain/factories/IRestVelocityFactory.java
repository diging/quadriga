package edu.asu.spring.quadriga.domain.factories;

import org.apache.velocity.app.VelocityEngine;

public interface IRestVelocityFactory {

	public abstract VelocityEngine getVelocityEngine();

}