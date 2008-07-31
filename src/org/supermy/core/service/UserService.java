package org.supermy.core.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.Address;
import org.supermy.core.domain.BaseDomain;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-7-30 下午05:17:27
 * 
 */
@Transactional
@Service
public class UserService extends BaseService implements IUserService {
	static Log log = LogFactory.getLog(UserService.class);
	/*
	 * 
	 * @see org.supermy.core.service.IUserService#findUsers()
	 */
	@Transactional(readOnly = true)
		public Set<User> findUsers(int numPage, int sizePage) {
			// DetachedCriteria query = DetachedCriteria.forClass(User.class);
			// //query.createCriteria("address");
			// return findByPage(query, 1, 10);
			StringBuffer selectHql = new StringBuffer("");
			StringBuffer otherHql = new StringBuffer("from User");
			return findByPage(selectHql, otherHql, numPage, sizePage);
		};

	//	public void saveUser(User u) {
	//		super.save(u);
	//	}

	//	@Override
	//	public User loadUser(Long userId) {
	//		return (User) super.load(User.class, userId);
	//	}

	//	@Override
	//	public void delUser(Long userId) {
	//		super.delete(User.class, userId);
	//	}

	//	@Override
	//	public void saveUsers(Set<User> users) {
	//		super.saveAll(users);
	//		
	//	}

	//	@Override
	//	public void saveRoles(Set<Role> roles) {
	//		super.saveAll(roles);
	//		
	//	}

	//	@Override
	//	public void saveAddresss(Set<Address> address) {
	//		super.saveAll(address);
	//	}

	@Override
		public Set<Role> findRolesByUserId(long userId, int numPage, int sizePage) {
			StringBuffer selectHql = new StringBuffer("");
			StringBuffer otherHql = new StringBuffer("from Role r where r.user.id="+userId);
			return findByPage(selectHql, otherHql, numPage, sizePage);		
		}

	@Override
		public User getUserByName(String name) {
			List o = hibernateTemplate.findByNamedParam("from User u where u.name=:name", "name", name);
			if (o.size()==1) {
				return (User)o.get(0);
			} else {
				return null;
			}

		}

	@Override
		public Address getAddressByUserId(long userId) {
			List o = hibernateTemplate.findByNamedParam("from Address a where a.user.id=:id", "id", userId);
			if (o.size()>=1) {
				return (Address)o.get(0);
			} else {
				return null;
			}
		}


	public User login(String email,String passwd){
		List o = hibernateTemplate.findByNamedParam("from User u where u.email=:email and u.passwd=:passwd", new String[]{"email","passwd"}, new String[]{email,passwd});
		if (o.size()==1) {
			return (User)o.get(0);
		} else {
			return null;
		}

	}
	//	@Override
	//	public void saveAddress(Address a) {
	//		save(a);
	//	};

}
