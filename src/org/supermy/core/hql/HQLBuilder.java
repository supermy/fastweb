package org.supermy.core.hql;

/**
 * @author my
 *
 */
public class HQLBuilder {
	private StringBuffer select;
	private StringBuffer from;
	private StringBuffer where;
	private StringBuffer having;
	private StringBuffer groupby;
	private StringBuffer orderby;

	public StringBuffer getSelect() {
		return select;
	}

	public HQLBuilder appendSelect(String select) {
		this.select.append(select);
		return this;
	}

	public StringBuffer getFrom() {
		return from;
	}

	public HQLBuilder appendFrom(StringBuffer from) {
		this.from.append(from);
		return this;
	}

	public StringBuffer getWhere() {
		return where;
	}

	public HQLBuilder apppendtWhere(String where) {
		this.where.append(where);
		return this;
	}

	public StringBuffer getOrderby() {
		return orderby;
	}

	public HQLBuilder appendOrderby(String orderby) {
		this.orderby.append(orderby);
		return this;
	}

	public StringBuffer getHaving() {
		return having;
	}

	public HQLBuilder setHaving(String having) {
		this.having.append(having);
		return this;
	}

	public StringBuffer getGroupby() {
		return groupby;
	}

	public HQLBuilder setGroupby(String groupby) {
		this.groupby.append(groupby);
		return this;
	}

	public String getHql() {
		return new StringBuffer(select).append(" ").append(from).append(" ")
				.append(where).append(" ").append(groupby).append(" ").append(
						having).append(" ").append(orderby).toString();
	}

}
