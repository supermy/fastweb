package org.supermy.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * Struts2中典型CRUD Action的规范基类.
 * 
 * 声明对Preparable,ModelDriven接口的使用,并规范了CRUD函数和返回值的命名.
 * 
 * @param <T>
 *            CRUD所管理的对象类型.
 * 
 */

public abstract class BaseActionSupport<T> extends ActionSupport implements
		ModelDriven<T>, Preparable {

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
	}

	/**
	 * 在save()前执行二次绑定.
	 */
	public void prepareSave() throws Exception {
		log.debug("save before 绑定 ");
		prepareModel();
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
}
