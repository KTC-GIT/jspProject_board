package webMessage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class WebMessageVo {
	private int idx;
	private String title;
	private String content;
	private String sendId;
	private String sendSw;
	private Timestamp sendDate;
	private String sendDateStr;
	private String receiveId;
	private String receiveSw;
	private Timestamp receiveDate;
	private String receiveDateStr;
	
	public WebMessageVo() {}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
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

	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

	public String getSendSw() {
		return sendSw;
	}

	public void setSendSw(String sendSw) {
		this.sendSw = sendSw;
	}

	public Timestamp getSendDate() {
		return sendDate;
	}

	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendDateStr() {
		return sendDateStr;
	}

	public void setSendDateStr(Timestamp sendDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(sendDate);
		this.sendDateStr = str;
	}

	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	public String getReceiveSw() {
		return receiveSw;
	}

	public void setReceiveSw(String receiveSw) {
		this.receiveSw = receiveSw;
	}

	public Timestamp getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Timestamp receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getReceiveDateStr() {
		return receiveDateStr;
	}

	public void setReceiveDateStr(Timestamp receiveDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(receiveDate);
		
		this.receiveDateStr = str;
	}
	
	
}
