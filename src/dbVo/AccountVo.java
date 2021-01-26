package dbVo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class AccountVo {
	private int idx,activation;
	private String id,pw,nick,email,tel,strBirth;
	private Timestamp birth;
	
	public AccountVo(){}
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Timestamp getBirth() {
		return birth;
	}
	public void setBirth(Timestamp birth) {
		this.birth = birth;
	}

	public int getActivation() {
		return activation;
	}

	public void setActivation(int activation) {
		this.activation = activation;
	}
	public String getStrBirth() {
		return strBirth;
	}

	public void setStrBirth(Timestamp birth) {
		String strBirth = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		strBirth = sdf.format(birth);
		this.strBirth = strBirth;
	}

	
}
