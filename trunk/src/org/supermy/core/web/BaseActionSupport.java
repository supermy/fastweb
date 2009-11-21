package org.supermy.core.web;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Set;

import junit.framework.Assert;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.service.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * Struts2中典型CRUD Action的规范基类.
 * 
 * 声明对Preparable,ModelDriven接口的使用,并规范了CRUD函数和返回值的命名.
 * 
 * @param <T>
 *<br/>CRUD所管理的对象类型.
 * 
 */

public abstract class BaseActionSupport<T> extends ActionSupport implements
		ModelDriven<T>, Preparable {

	@Autowired
	private SolrServer client;

	
	public SolrServer getClient() {
		return client;
	}

	@Autowired
	public SessionFactory sessionFactory;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 进行增删改操作后,以redirect方式重新打开action默认页的result名.
	 */
	public static final String RELOAD = "reload";

	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Action函数,默认action函数，默认指向list函数.
	 */
	@Override
	public String execute() throws Exception {
		return list();
	}

	/**
	 * 建议return SUCCESS.
	 */
	public abstract String list() throws Exception;

	/**
	 * 建议return RELOAD.
	 */
	public abstract String save() throws Exception;

	/**
	 * 建议return RELOAD.
	 */
	public abstract String delete() throws Exception;

	// Preparable函数 //

	/**
	 * 实现空的prepare()函数,屏蔽所有Action函数公共的二次绑定.
	 */
	public void prepare() throws Exception {
		log.debug("公共的二次绑定，什么也不做！");
	}

	/**
	 * 在save()前执行二次绑定.
	 */
	public void prepareSave() throws Exception {
		log.debug("save before 绑定 ");
		prepareModelSave();
	}

	/**
	 * 在input()前执行二次绑定.
	 */
	public void prepareInput() throws Exception {
		log.debug("input before 绑定 ");
		prepareModel();
	}

	/**
	 * 等同于prepare()的内部函数,供prepardMethodName()函数调用.
	 */
	protected abstract void prepareModel() throws Exception;

	protected abstract void prepareModelSave() throws Exception;


	/**
	 * 一对多和多对多的关系的详细列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String mtolist() throws Exception {
		Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		String className = Struts2Utils.getRequest().getParameter("class");
		String objid = Struts2Utils.getRequest().getParameter("id");
		String property = Struts2Utils.getRequest().getParameter("property");
		// 参数不能为空
		Assert.assertNotNull(className);
		Assert.assertNotNull(objid);
		Assert.assertNotNull(property);

		Class<?> forName = Class.forName(className);
		// 数据库查询
		Object obj = sessionFactory.getCurrentSession().get(forName,
				new Long(objid));
		// Object obj=null;
		// 取出关联属性
		Object p = PropertyUtils.getProperty(obj, property);
		log.debug("relation list :{}", p);
		// 将数据注入到Action
		Page<T> page = new Page<T>(5);
		page.setResult(new ArrayList(((Set<T>) p)));
		PropertyUtils.setProperty(this, "page"
				+ entityClass.getSimpleName().toLowerCase(), page);
		// 跳转到对应的列表页面
		return SUCCESS;
	}

	/**
	 * 
	 * 直接跳转到View页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String ntoinput() throws Exception {
		Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		String objid = Struts2Utils.getRequest().getParameter("id");

		// 参数不能为空
		Assert.assertNotNull(objid);

		// 数据库查询
		Object obj = sessionFactory.getCurrentSession().get(entityClass,
				new Long(objid));
		// (T)obj;
		// 跳转到对应的列表页面
		return INPUT;// VIEW
	}

}
