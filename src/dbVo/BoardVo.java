package dbVo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardVo {
	private int bidx,memberwrite;
	private String id, pw, ip,tag, title, content,writedateStr,ipView;
	private int hits, recommend;
	private Timestamp writedate;
	
	
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
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
		SimpleDateFormat sdf1 = new SimpleDateFormat("yy.MM.dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		Date now = new Date();
		String nowStr = sdf1.format(now);
		
		if(sdf1.format(writedate).equals(nowStr)) {
			this.writedateStr = sdf2.format(writedate);
		}
		else {
			this.writedateStr = sdf1.format(writedate);
		}
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public int getMemberwrite() {
		return memberwrite;
	}
	public void setMemberwrite(int memberwrite) {
		this.memberwrite = memberwrite;
	}
	
	
	
}
