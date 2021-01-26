package dbVo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class FileVo {
	private int idx, bidx,downloadCount;
	private String filePath, ip,fileName, orifileName, uploadDateStr,hashCode;
	private Timestamp uploadDate;
	
	public FileVo() {}
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getBidx() {
		return bidx;
	}
	public void setBidx(int bidx) {
		this.bidx = bidx;
	}
	public int getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOrifileName() {
		return orifileName;
	}
	public void setOrifileName(String orifileName) {
		this.orifileName = orifileName;
	}
	
	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public String getUploadDateStr() {
		return uploadDateStr;
	}
	public void setUploadDateStr(Timestamp uploadDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
		String uploadDateStr = sdf.format(uploadDate);
		this.uploadDateStr = uploadDateStr;
	}
	public Timestamp getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	
}
