package com.gogo.comix.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.supermy.core.domain.BaseDomain;

@Entity
@Table(name="MY_TIGER",catalog="fastweb")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MyTiger extends BaseDomain 
 {

    
    @Length(max = 200)
    @Column(name="my_tiger")
	private String myTiger;

    @Length(max = 100)
    @Column(name="my_email",nullable=false)
    @Email
	private String myEmail;






    
    public String getMyTiger() {
        return this.myTiger;
    }
    
    public void setMyTiger(String myTiger) {
        this.myTiger = myTiger;
    }



    /**
	 * @return the myEmail
	 */
	public String getMyEmail() {
		return myEmail;
	}

	/**
	 * @param myEmail the myEmail to set
	 */
	public void setMyEmail(String myEmail) {
		this.myEmail = myEmail;
	}

	public String toString() {
        "".toLowerCase().endsWith("email");
        "".endsWith("url");

        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
			sb.append("id").append("='").append(getId()).append("', ");
			sb.append("create").append("='").append(getCreate()).append("', ");
			sb.append("myTiger").append("='").append(getMyTiger()).append("'");
        sb.append("]");
      
        return sb.toString();
    }

}
