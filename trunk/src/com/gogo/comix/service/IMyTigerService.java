
package com.gogo.comix.service;

import javax.jws.WebService;

import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.service.FastwebTemplate;

import com.gogo.comix.domain.MyTiger;

@WebService
@Transactional
public interface IMyTigerService {

	/**
	 * @return the $(pojoNameLower)Util
	 */
	
	public abstract FastwebTemplate<MyTiger, Long> getMyTigerUtil();

}