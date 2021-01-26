package dbControl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CreateSHA256 {
	public String hashCode = "";
	
	public String getHashCode(String fileName) throws NoSuchAlgorithmException,UnsupportedEncodingException {
		
		//SHA-256 MessageDigest의 생성
		MessageDigest mdSHA256 = MessageDigest.getInstance("SHA-256");
		
		//fileName의 문자열을 바이트로 MessageDigest갱신
		mdSHA256.update(fileName.getBytes("UTF-8"));
		
		//해시 계산 반환값은 바이트배열
		byte[] sha256Hash = mdSHA256.digest();
		
		//바이트배열을 16진수 문자열로 변환하여 표시
		StringBuilder hexSHA256Hash = new StringBuilder();
		for(byte b: sha256Hash) {
			String hexString = String.format("%02x", b);
			hexSHA256Hash.append(hexString);
		}
		
		hashCode = hexSHA256Hash.toString();
		
		return hashCode; 
	}
}
