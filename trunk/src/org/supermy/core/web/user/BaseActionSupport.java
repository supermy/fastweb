package org.supermy.core.web.user;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

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

	/* 关闭校验提高页面构造速度
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {
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

	public Map<String, Object> buildPropertyFilters(String filterPrefix) {

		HttpServletRequest request = Struts2Utils.getRequest();

		// 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map filterParamMap = WebUtils.getParametersStartingWith(request,
				filterPrefix);

		StringBuffer wherehql = new StringBuffer();
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		// 分析参数Map,构造PropertyFilter列表
		for (Object filterName : filterParamMap.keySet()) {
			Object value = filterParamMap.get(filterName);

			// 如果value值为空,则忽略此filter.
			boolean omit = StringUtils.isBlank((String) value);
			if (!omit) {

				// 分析filterName,获取matchType与propertyName
				String matchTypeCode = StringUtils.substringBefore(
						(String) filterName, "_");

				if ("eq like".contains(matchTypeCode)) {
					throw new IllegalArgumentException(
							"filter名称没有按规则编写,无法得到属性比较类型." + matchTypeCode);
				}

				if (matchTypeCode.equalsIgnoreCase("eq")) {
					matchTypeCode = " = ";
				}
				if (matchTypeCode.equalsIgnoreCase("like")) {
					matchTypeCode = " like ";
					value = value + "%";
				}

				String propertyName = StringUtils.substringAfter(
						(String) filterName, "_");

				if (wherehql.length() > 0) {
					wherehql.append(" and ");
				}

				if (propertyName.contains("|")) {
					String[] split = StringUtils.split(propertyName, "|");
					wherehql.append("(");
					for (String string : split) {

						wherehql.append(" obj.").append(string).append(
								matchTypeCode).append(" ").append(" ? ")
								.append(" or ");

						result.put(string + '1', value);

					}
					wherehql.delete(wherehql.length() - 3, wherehql.length());
					wherehql.append(")");
				} else {
					wherehql.append(" obj.").append(propertyName).append(
							matchTypeCode).append(" ").append(" ? ")
							.append(" ");

					result.put(propertyName, value);
				}
			}
		}
		if (result.size() > 0) {
			// wherehql.delete( wherehql.length()-3,wherehql.length() );
			result.put("hql", new StringBuffer(" where ").append(wherehql));
		}
		return result;

	}
}
