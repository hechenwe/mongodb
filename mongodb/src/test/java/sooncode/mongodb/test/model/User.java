package sooncode.mongodb.test.model;

import java.util.Date;

import org.bson.types.ObjectId;

public class User {
	
	
	private ObjectId _id;

	private String username;

	private String pwd;

	private Double score;

	private Boolean sex;

	private Date birthDay;

	 

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	@Override
	public String toString() {
		return "User [_id=" + _id + ", username=" + username + ", pwd=" + pwd + ", score=" + score + ", sex=" + sex + ", birthDay=" + birthDay + "]";
	}

	 

}
