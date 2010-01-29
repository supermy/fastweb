package com.lottery.ssq.web.ssq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.service.Page;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;

import com.lottery.ssq.domain.DoubleColorBall;
import com.lottery.ssq.service.IDoubleColorBallService;

@Results( { @Result(name = BaseActionSupport.RELOAD, location = "double-color-ball.action?pagedoublecolorball.pageRequest=${pagedoublecolorball.pageRequest}", type = "redirect") })
@Namespace("/ssq")
public class DoubleColorBallAction extends BaseActionSupport<DoubleColorBall> {


	@Autowired
	private IDoubleColorBallService doubleColorBallService;

	// 基本属性
	private DoubleColorBall doubleColorBall;
	private Long id;
	private Page<DoubleColorBall> pagedoublecolorball = new Page<DoubleColorBall>(
			30);

	// 基本属性访问函数 //
	public DoubleColorBall getModel() {
		return doubleColorBall;
	}

	/**
	 * @return the pageDoubleColorBall
	 */
	public Page<DoubleColorBall> getPagedoublecolorball() {
		return pagedoublecolorball;
	}

	public void setPagedoublecolorball(Page<DoubleColorBall> pagedoublecolorball) {
		this.pagedoublecolorball = pagedoublecolorball;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			doubleColorBall = doubleColorBallService.getDoubleColorBallUtil()
					.get(id);
		} else {
			doubleColorBall = new DoubleColorBall();
		}
	}

	@Override
	protected void prepareModelSave() throws Exception {
		prepareModel();

	}

	public void setId(Long id) {
		this.id = id;
	}

	// CRUD Action 函数 //
	// 其他属性访问函数与Action函数 //

	@Override
	public String list() throws Exception {
		pagedoublecolorball = doubleColorBallService.getDoubleColorBallUtil()
				.get(pagedoublecolorball);
		log.debug("find :{}", pagedoublecolorball.getResult());
		log.debug("find doubleColorBall by page:"
				+ pagedoublecolorball.getResult().size());
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	public String save() throws Exception {

		doubleColorBallService.getDoubleColorBallUtil().save(doubleColorBall);
		addActionMessage(getText("doubleColorBall.updated"));
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			doubleColorBallService.getDoubleColorBallUtil().delete(id);
			addActionMessage(getText("doubleColorBall.deleted"));
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
			addActionMessage(e.getMessage());
		}
		return RELOAD;
	}

	// 其他属性访问函数与Action函数 //

	/**
	 * 根据属性过滤条件搜索.
	 */
	public String search() throws Exception {

		// 因为搜索时不保存分页参数,因此将页面大小设到最大.
		pagedoublecolorball.setPageSize(Page.MAX_PAGESIZE);

		Map<String, Object> filters = Struts2Utils
				.buildPropertyFilters("filter_");
		if (filters.size() <= 0) {
			addActionMessage(getText("doubleColorBall.searchtxt"));
		}
		pagedoublecolorball = doubleColorBallService.getDoubleColorBallUtil()
				.search(pagedoublecolorball, filters);
		return SUCCESS;
	}

	/**
	 * solr方式全文检索对象的String属性
	 * 
	 * @return
	 * @throws Exception
	 */
	public String fulltext() throws Exception {

		// 因为搜索时不保存分页参数,因此将页面大小设到最大.
		pagedoublecolorball.setPageSize(Page.MAX_PAGESIZE);

		String q = Struts2Utils.getRequest().getParameter("q");
		if (StringUtils.isBlank(q)) {
			addActionMessage(getText("fulltext.query.notblank"));
			return RELOAD;
		}
		addActionMessage(getText("common.domain.fulltext") + " [" + q + "] ");

		pagedoublecolorball = doubleColorBallService.getDoubleColorBallUtil()
				.fullltext(pagedoublecolorball, q, getClient());

		return SUCCESS;
	}

	public static final String TRENDLINE = "trendline";
	public List<Map<String, String>> trendlines = new ArrayList<Map<String, String>>(
			30);
	public List<Map<String, String>> getTrendline() {
		return trendlines;
	}
	/**
	 * 趋势线分析
	 */
	public String showtrendline() throws Exception {
		// double-color-ball!trendline.action?lcation=1&limit=20
		String location = Struts2Utils.getRequest().getParameter("location");
		String limit = Struts2Utils.getRequest().getParameter("limit");
		trendlines = doubleColorBallService.trendline(
				Integer.valueOf(location), Integer.valueOf(limit));
		return DoubleColorBallAction.TRENDLINE;
	}
	

	public static final String TRENDLINEXML = "trendlinexml";
	public List<Map<String, Object>> trendlinexmls = new ArrayList<Map<String, Object>>(
			30);
	public List<Map<String, Object>> getTrendlinexml() {
		return trendlinexmls;
	}
	/**
	 * 趋势线分析 xml 
	 */
	public String showtrendlinexml() throws Exception {
		// double-color-ball!showtrendlinexml.action?lcation=1&limit=20
		String location = Struts2Utils.getRequest().getParameter("location");
		String limit = Struts2Utils.getRequest().getParameter("limit");
		trendlinexmls = doubleColorBallService.trendlinexml(
				Integer.valueOf(location), Integer.valueOf(limit));
		return DoubleColorBallAction.TRENDLINEXML;
	}
	
}