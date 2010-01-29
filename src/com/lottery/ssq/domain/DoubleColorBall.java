package com.lottery.ssq.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.cfg.annotations.Comment;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.supermy.core.domain.BaseDomain;
@Entity
@Table(name = "double_color_ball")//数据库名称
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)//动态更新
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//HQL 查询 缓存数据
// @Proxy(lazy=false)//因为缓存数据所以去掉此项
@Comment(value = "双色球", desc = "开奖数据")//代码生成的时候可以生成多国语言，页面的数据和校验时用到的数据
public class DoubleColorBall extends BaseDomain {//BaseDomain中放入了 主键、日期等公用的属性

	// @Field("num_t")//fulltext  自动solr全文检索
	@Comment("期号")//生成页面的多国语言数据和js校验提示信息。
	@Length(min = 4, max = 5)//自动生成页面校验
	@NotEmpty//自动生成页面校验
	@Index(name = "i_num")//自动生成数据索引
	@Column(name = "num_", nullable = false, unique = true, length = 5)//nullable=false 允许为空; unique = true 生成数据库约束，必须唯一；
	private String num;


	@Comment("开奖日期")
	@Index(name = "i_lottery_dates")
	@Column(name = "lottery_dates")
	private Date lotteryDates;

	@Comment("1")
	@Length(min = 2)
	@NotEmpty
	@Index(name = "i_sequence_1")
	@Column(name = "sequence_1", nullable = false, length = 2)
	private String sequence1;

	@Comment("2")
	@Length(min = 2)
	@NotEmpty
	@Index(name = "i_sequence_2")
	@Column(name = "sequence_2", nullable = false, length = 2)
	private String sequence2;

	@Comment("3")
	@Length(min = 2)
	@NotEmpty
	@Index(name = "i_sequence_3")
	@Column(name = "sequence_3", nullable = false, length = 2)
	private String sequence3;

	@Comment("4")
	@Length(min = 2)
	@NotEmpty
	@Index(name = "i_sequence_4")
	@Column(name = "sequence_4", nullable = false, length = 2)
	private String sequence4;

	@Comment("5")
	@Length(min = 2)
	@NotEmpty
	@Index(name = "i_sequence_5")
	@Column(name = "sequence_5", nullable = false, length = 2)
	private String sequence5;

	@Comment("6")
	@Length(min = 2)
	@NotEmpty
	@Index(name = "i_sequence_6")
	@Column(name = "sequence_6", nullable = false, length = 2)
	private String sequence6;

	@Comment("1")
	@Length(min = 2)
	@NotEmpty
	@Index(name = "i_size_1")
	@Column(name = "size_1", nullable = false, length = 2)
	private String size1;

	@Comment("2")
	@Length(min = 2)
	@NotEmpty
	@Index(name = "i_size_2")
	@Column(name = "size_2", nullable = false, length = 2)
	private String size2;

	@Comment("3")
	@Length(min = 2)
	@NotEmpty
	@Index(name = "i_size_3")
	@Column(name = "size_3", nullable = false, length = 2)
	private String size3;

	@Comment("4")
	@Length(min = 2)
	@NotEmpty
	@Index(name = "i_size_4")
	@Column(name = "size_4", nullable = false, length = 2)
	private String size4;

	@Comment("5")
	@Length(min = 2)
	@NotEmpty
	@Index(name = "i_size_5")
	@Column(name = "size_5", nullable = false, length = 2)
	private String size5;

	@Comment("6")
	@Length(min = 2)
	@NotEmpty
	@Index(name = "i_size_6")
	@Column(name = "size_6", nullable = false, length = 2)
	private String size6;

	@Comment("蓝球")
	@Length(min = 2)
	@NotEmpty
	@Index(name = "i_blue_ball")
	@Column(name = "blue_ball", nullable = false, length = 2)
	private String blueBall;

	@Comment("兑奖号码")
	@Length(min = 14)
	@Index(name = "i_red_blue_ball")
	@Column(name = "red_blue_ball", nullable = true, length = 14)
	private String redBlueBall;

	/**
	 * 设定中奖号码
	 */
	public void setRedBlueBall() {
		this.redBlueBall = this.size1 + this.size2 + this.size3 + this.size4
				+ this.size5 + this.size6 + this.blueBall;
	}

	/**
	 * @return the num
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(String num) {
		this.num = num;
	}

	/**
	 * @return the lotteryDates
	 */
	public Date getLotteryDates() {
		return lotteryDates;
	}

	/**
	 * @param lotteryDates
	 *            the lotteryDates to set
	 */
	public void setLotteryDates(Date lotteryDates) {
		this.lotteryDates = lotteryDates;
	}

	/**
	 * @return the sequence1
	 */
	public String getSequence1() {
		return sequence1;
	}

	/**
	 * @param sequence1
	 *            the sequence1 to set
	 */
	public void setSequence1(String sequence1) {
		this.sequence1 = sequence1;
	}

	/**
	 * @return the sequence2
	 */
	public String getSequence2() {
		return sequence2;
	}

	/**
	 * @param sequence2
	 *            the sequence2 to set
	 */
	public void setSequence2(String sequence2) {
		this.sequence2 = sequence2;
	}

	/**
	 * @return the sequence3
	 */
	public String getSequence3() {
		return sequence3;
	}

	/**
	 * @param sequence3
	 *            the sequence3 to set
	 */
	public void setSequence3(String sequence3) {
		this.sequence3 = sequence3;
	}

	/**
	 * @return the sequence4
	 */
	public String getSequence4() {
		return sequence4;
	}

	/**
	 * @param sequence4
	 *            the sequence4 to set
	 */
	public void setSequence4(String sequence4) {
		this.sequence4 = sequence4;
	}

	/**
	 * @return the sequence5
	 */
	public String getSequence5() {
		return sequence5;
	}

	/**
	 * @param sequence5
	 *            the sequence5 to set
	 */
	public void setSequence5(String sequence5) {
		this.sequence5 = sequence5;
	}

	/**
	 * @return the sequence6
	 */
	public String getSequence6() {
		return sequence6;
	}

	/**
	 * @param sequence6
	 *            the sequence6 to set
	 */
	public void setSequence6(String sequence6) {
		this.sequence6 = sequence6;
	}

	/**
	 * @return the size1
	 */
	public String getSize1() {
		return size1;
	}

	/**
	 * @param size1
	 *            the size1 to set
	 */
	public void setSize1(String size1) {
		this.size1 = size1;
	}

	/**
	 * @return the size2
	 */
	public String getSize2() {
		return size2;
	}

	/**
	 * @param size2
	 *            the size2 to set
	 */
	public void setSize2(String size2) {
		this.size2 = size2;
	}

	/**
	 * @return the size3
	 */
	public String getSize3() {
		return size3;
	}

	/**
	 * @param size3
	 *            the size3 to set
	 */
	public void setSize3(String size3) {
		this.size3 = size3;
	}

	/**
	 * @return the size4
	 */
	public String getSize4() {
		return size4;
	}

	/**
	 * @param size4
	 *            the size4 to set
	 */
	public void setSize4(String size4) {
		this.size4 = size4;
	}

	/**
	 * @return the size5
	 */
	public String getSize5() {
		return size5;
	}

	/**
	 * @param size5
	 *            the size5 to set
	 */
	public void setSize5(String size5) {
		this.size5 = size5;
	}

	/**
	 * @return the size6
	 */
	public String getSize6() {
		return size6;
	}

	/**
	 * @param size6
	 *            the size6 to set
	 */
	public void setSize6(String size6) {
		this.size6 = size6;
	}

	/**
	 * @return the blueBall
	 */
	public String getBlueBall() {
		return blueBall;
	}

	/**
	 * @param blueBall
	 *            the blueBall to set
	 */
	public void setBlueBall(String blueBall) {
		this.blueBall = blueBall;
	}

	/**
	 * @return the redBlueBall
	 */
	public String getRedBlueBall() {
		return redBlueBall;
	}

	/**
	 * @param redBlueBall
	 *            the redBlueBall to set
	 */
	public void setRedBlueBall(String redBlueBall) {
		this.redBlueBall = redBlueBall;
	}

}
