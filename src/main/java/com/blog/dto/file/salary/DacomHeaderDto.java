package com.blog.dto.file.salary;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.blog.common.util.ByteUtil;

public class DacomHeaderDto {

	private byte[] headerCode = new byte[10]; // 1.Header구분
	private byte[] senderCode = new byte[8]; // 2.송신자코드
	private byte[] companyCode = new byte[2]; // 3.업체구분코드
	private byte[] listInfoCode = new byte[3]; // 4.정보구분코즈
	private byte[] autoFax = new byte[1]; // 5.Fax자동착식
	private byte[] broadcastTransfer = new byte[1]; // 6.통보통신
	private byte[] recipientNum = new byte[3]; // 7.수신자 수
	private byte[] recipientCode = new byte[8]; // 8.수신자코드
	private byte[] reserve = new byte[42]; // 9.예비
	private final byte[] crLf = new byte[] { 13, 10 }; // 10.cr&lf

	protected final Charset cs = Charset.forName("ksc5601");

	public DacomHeaderDto() {
	}

	public String getHeaderCode() {
		return new String(headerCode, cs);
	}

	public byte[] getTelegram() {
		byte[] telegram = new byte[80];
		ByteBuffer bf = ByteBuffer.wrap(telegram);

		bf.put(this.headerCode);
		bf.put(this.senderCode);
		bf.put(this.companyCode);
		bf.put(this.listInfoCode);
		bf.put(this.autoFax);
		bf.put(this.broadcastTransfer);
		bf.put(this.recipientNum);
		bf.put(this.recipientCode);
		bf.put(this.reserve);
		bf.put(this.crLf);

		return telegram;
	}

	public ByteBuffer setTelegram(byte[] inComingData) {
		ByteBuffer bf = ByteBuffer.wrap(inComingData);

		bf.get(this.headerCode);
		bf.get(this.senderCode);
		bf.get(this.companyCode);
		bf.get(this.listInfoCode);
		bf.get(this.autoFax);
		bf.get(this.broadcastTransfer);
		bf.get(this.recipientNum);
		bf.get(this.recipientCode);
		bf.get(this.reserve);
		bf.get(this.crLf);

		return bf;
	}

	public void setHeaderCode(String headerCode) {
		try {
			this.headerCode = ByteUtil.toByteR(headerCode, this.headerCode.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getSenderCode() {
		return new String(senderCode, cs);
	}

	public void setSenderCode(String senderCode) {
		try {
			this.senderCode = ByteUtil.toByteR(senderCode, this.senderCode.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getCompanyCode() {
		return new String(companyCode, cs);
	}

	public void setCompanyCode(String companyCode) {
		try {
			this.companyCode = ByteUtil.toByteR(companyCode, this.companyCode.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getListInfoCode() {
		return new String(listInfoCode, cs);
	}

	public void setListInfoCode(String listInfoCode) {
		try {
			this.listInfoCode = ByteUtil.toByteR(listInfoCode, this.listInfoCode.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getAutoFax() {
		return new String(autoFax, cs);
	}

	public void setAutoFax(String autoFax) {
		try {
			this.autoFax = ByteUtil.toByteR(autoFax, this.autoFax.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getBroadcastTransfer() {
		return new String(broadcastTransfer, cs);
	}

	public void setBroadcastTransfer(String broadcastTransfer) {
		try {
			this.broadcastTransfer = ByteUtil.toByteR(broadcastTransfer, this.broadcastTransfer.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getRecipientNum() {
		return new String(recipientNum, cs);
	}

	public void setRecipientNum(String recipientNum) {
		try {
			this.recipientNum = ByteUtil.toByteR(recipientNum, this.recipientNum.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getRecipientCode() {
		return new String(recipientCode, cs);
	}

	public void setRecipientCode(String recipientCode) {
		try {
			this.recipientCode = ByteUtil.toByteR(recipientCode, this.recipientCode.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getReserve() {
		return new String(reserve, cs);
	}

	public void setReserve(String reserve) {
		try {
			this.reserve = ByteUtil.toByteR(reserve, this.reserve.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
