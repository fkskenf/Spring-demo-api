package com.blog.dto.file.salary;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.blog.common.util.ByteUtil;

public class DataDto {
	private byte[] recordCode = new byte[] { 'D' }; // 1.Record 구분
	private byte[] bankCode = new byte[3]; // 2.은행코드
	private byte[] accountNo = new byte[15]; // 3.계좌번호
	private byte[] amount = new byte[11]; // 4.이체금액
	private byte[] transferResult = new byte[1]; // 5.이체결과
	private byte[] dCode = new byte[4]; // 6.불능코드
	private byte[] accountHistory = new byte[12]; // 7.통장인자 내역
	private byte[] clientInfo = new byte[29]; // 8.고객영역
	private byte[] listCode = new byte[2]; // 9.통장인자 구분
	private final byte[] crLf = new byte[] { 13, 10 }; // 10.cr&lf

	protected final Charset cs = Charset.forName("ksc5601");

	public DataDto() {
	}

	public byte[] getTelegram() {
		byte[] telegram = new byte[80];
		ByteBuffer bf = ByteBuffer.wrap(telegram);

		bf.put(this.recordCode);
		bf.put(this.bankCode);
		bf.put(this.accountNo);
		bf.put(this.amount);
		bf.put(this.transferResult);
		bf.put(this.dCode);
		bf.put(this.accountHistory);
		bf.put(this.clientInfo);
		bf.put(this.listCode);
		bf.put(this.crLf);

		return telegram;
	}

	public ByteBuffer setTelegram(byte[] inComingData) {
		ByteBuffer bf = ByteBuffer.wrap(inComingData);

		bf.get(this.recordCode);
		bf.get(this.bankCode);
		bf.get(this.accountNo);
		bf.get(this.amount);
		bf.get(this.transferResult);
		bf.get(this.dCode);
		bf.get(this.accountHistory);
		bf.get(this.clientInfo);
		bf.get(this.listCode);
		bf.get(this.crLf);

		return bf;
	}

	/* Mode = X(String)/ 9(int) */

	public String getRecordCode() {
		return new String(recordCode, cs);
	}

	public void setRecordCode(String recordCode) {
		try {
			this.recordCode = ByteUtil.toByteR(recordCode, this.recordCode.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getBankCode() {
		return Integer.parseInt(new String(bankCode, cs));
	}

	public void setBankCode(int bankCode) {
		try {
			this.bankCode = ByteUtil.toByteL(bankCode, this.bankCode.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getAccountNo() {
		return new String(accountNo, cs);
	}

	public void setAccountNo(String accountNo) {
		if (accountNo == null)
			return;
		try {
			this.accountNo = ByteUtil.toByteR(accountNo, this.accountNo.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getAmount() { // String?
		return Integer.parseInt(new String(amount, cs));
	}

	public void setAmount(int amount) {
		try {
			this.amount = ByteUtil.toByteL(amount, this.amount.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getTransferResult() {
		return new String(transferResult, cs);
	}

	public void setTransferResult(String transferResult) {
		if (transferResult == null)
			return;
		try {
			this.transferResult = ByteUtil.toByteR(transferResult, this.transferResult.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getDCode() {
		return Integer.parseInt(new String(dCode, cs));
	}

	public void setDCode(int dCode) {
		try {
			this.dCode = ByteUtil.toByteL(dCode, this.dCode.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getAccountHistory() {
		return new String(accountHistory, cs);
	}

	public void setAccountHistory(String accountHistory) {
		if (accountHistory == null)
			return;
		try {
			this.accountHistory = ByteUtil.toByteR(accountHistory, this.accountHistory.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getClientInfo() {
		return new String(clientInfo, cs);
	}

	public void setClientInfo(String clientInfo) {
		if (clientInfo == null)
			return;
		try {
			this.clientInfo = ByteUtil.toByteR(clientInfo, this.clientInfo.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getListCode() {
		return new String(listCode, cs);
	}

	public void setListCode(String listCode) {
		if (listCode == null)
			return;
		try {
			this.listCode = ByteUtil.toByteR(listCode, this.listCode.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

//    public String getCrLf() {
//        return new String(crLf, cs);
//    }
//
//    public void setCrLf(String crLf) {
//        if (crLf == null) return;
//        try {
//            this.crLf = ByteUtil.toByteR(crLf, this.crLf.length, cs);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
