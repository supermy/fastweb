package org.supermy.core.service;

import org.supermy.core.domain.Resource;

public interface IResourceService {

	/**
	 * @return the resourceUtil
	 */
	public abstract FastwebTemplate<Resource, Long> getResourceUtil();

}