
package com.gogo.comix.service;

import com.gogo.comix.domain.MyTiger;
import org.supermy.core.service.FastwebTemplate;
import javax.jws.WebService;

@WebService
public interface IMyTigerService {

	/**
	 * @return the $(pojoNameLower)Util
	 */
	public abstract FastwebTemplate<MyTiger, Long> getMyTigerUtil();

}