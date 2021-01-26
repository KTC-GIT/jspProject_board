package dbVo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ReplyVo {
	private int ridx, bidx, memberwrite;
	private String id,pw,ip,content,ipView,writedateStr;
	private Timestamp writedate;
	
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIpView() {
		return ipView;
	}
	public void setIpView(String ip) {
		int idx = ip.lastIndexOf(".");
		String str = ip.substring(0,idx);
		idx = str.lastIndexOf(".");
		str = str.substring(0,idx);
		this.ipView = str;
	}
	public int getRidx() {
		return ridx;
	}
	public void setRidx(int ridx) {
		this.ridx = ridx;
	}
	public int getBidx() {
		return bidx;
	}
	public void setBidx(int bidx) {
		this.bidx = bidx;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getWritedate() {
		return writedate;
	}
	public void setWritedate(Timestamp writedate) {
		this.writedate = writedate;
	}
	
	public String getWritedateStr() {
		return writedateStr;
	}
	public void setWritedateStr(Timestamp writedate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd  HH:mm:ss");
		String date = sdf.format(writedate);
		
		this.writedateStr = date;
	}
	public int getMemberwrite() {
		return memberwrite;
	}
	public void setMemberwrite(int memberwrite) {
		this.memberwrite = memberwrite;
	}
	
	
	
	
}
