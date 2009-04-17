package org.supermy.core.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;
import org.supermy.core.service.Page;

/**
 * Struts2 Utils类.
 * 
 * 实现获取Request/Response/Session与绕过jsp/freemaker直接输出文本的简化函数.
 * 
 */
public class Struts2Utils {
	private static final Logger log = LoggerFactory
			.getLogger(Struts2Utils.class);
	// header 常量定义
	private static final String ENCODING_PREFIX = "encoding:";
	private static final String NOCACHE_PREFIX = "no-cache:";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;
	public static final String savePath = "upload";

	private static Logger logger = LoggerFactory.getLogger(Struts2Utils.class);

	private Struts2Utils() {
	}

	/**
	 * 取得HttpSession的简化方法.
	 */
	public static ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	/**
	 * 取得HttpSession的简化方法.
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 取得HttpRequest的简化方法.
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 取得HttpResponse的简化方法.
	 */
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 直接输出内容的简便函数.
	 * 
	 * eg. render("text/plain", "hello", "encoding:UTF-8"); render("text/plain",
	 * "hello", "no-cache:false"); render("text/plain", "hello",
	 * "encoding:UTF-8", "no-cache:false");
	 * 
	 * @param headers
	 *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",不设置时默认值分别为UTF-8和true
	 *            .
	 */
	public static void render(final String contentType, final String content,
			final String... headers) {
		try {
			// 分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName,
						NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
					throw new IllegalArgumentException(headerName
							+ "不是一个合法的header类型");
			}

			HttpServletResponse response = ServletActionContext.getResponse();

			// 设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			log.debug("fullContentType:{}", fullContentType);
			response.setContentType(fullContentType);

			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}

			response.getWriter().write(content);

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出文本.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderText(final String text, final String... headers) {
		render("text/plain", text, headers);
	}

	/**
	 * 直接输出HTML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(final String html, final String... headers) {
		render("text/html", html, headers);
	}

	/**
	 * 直接输出XML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(final String xml, final String... headers) {
		render("text/xml", xml, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param string
	 *            json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final String string, final String... headers) {
		render("application/json", string, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param map
	 *            Map对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	@SuppressWarnings("unchecked")
	public static void renderJson(final Map map, final String... headers) {
		String jsonString = JSONObject.fromObject(map).toString();
		log.debug("json:{}", jsonString);
		renderJson(jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param object
	 *            Java对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object object, final String[] filters,
			final String... headers) {
		// 虑掉部分不用的属性
		String jsonString = JSONObject.fromObject(object, getFilter(filters))
				.toString();

		// "invalid label"错误
		// String cb = getRequest().getParameter("callback");
		// if (cb != null) {
		// jsonString = "(" + jsonString + ")";
		// }
		// log.debug("json:{}", jsonString);
		renderJson(jsonString, headers);
	}

	/**
	 * 直接输出图片
	 * 
	 * @param bytes
	 */
	public static void renderImage(byte[] bytes) {
		OutputStream out;
		try {
			out = getResponse().getOutputStream();
			out.write(bytes);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 构造属性过滤器
	 * 
	 * @param filters
	 * @return
	 */
	private static JsonConfig getFilter(final String[] filters) {
		JsonConfig config = new JsonConfig();
		if (filters == null) {
			return config;
		}
		config.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				for (String fname : filters) {
					if (name.equals(fname)) {
						return true;
					}
				}
				return false;
			}
		});
		return config;
	}

	@Deprecated
	public static String generateFilePathName(String fileName) {
		return getUploadPath() + generateFileName(fileName);
	}

	public static String getFilePathName(String fileName) {
		return getUploadPath() + "/" + fileName;
	}

	public static void delete(String fileName) {
		File file = new File(getFilePathName(fileName));
		file.delete();
	}

	public static String getUploadPath() {
		return getServletContext().getRealPath(savePath);
	}

	public static String generateFileName(String fileName) {
		DateFormat dirf = new SimpleDateFormat("yyyyMMdd");
		String formatDir = dirf.format(new Date());

		DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		String formatDate = format.format(new Date());

		int random = new Random().nextInt(10000);

		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);

		return formatDir + "/" + formatDate + random + extension;
	}

	public static Page getPage(Page page) {
		String limit = getRequest().getParameter("limit");
		String start = getRequest().getParameter("start");
		limit = limit == null ? "0" : limit;
		start = start == null ? "0" : start;
		page.setPageSize(Integer.parseInt(limit));
		page.setPageNo(Integer.parseInt(start) / page.getPageSize() + 1);
		return page;
	}

	public static Map<String, Object> buildPropertyFilters(String filterPrefix) {
	
		HttpServletRequest request = getRequest();
	
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
