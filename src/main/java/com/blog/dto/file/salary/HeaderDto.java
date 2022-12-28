package com.blog.dto.file.salary;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.blog.common.util.ByteUtil;

public class HeaderDto {
	private byte[] recordCode = new byte[] { 'S' }; // 1.Record 구분
	private byte[] fileCode = new byte[2]; // 2/화일구분코드
	private byte[] companyNum = new byte[8]; // 3.업체번호
	private byte[] bankCode = new byte[3]; // 4.은행코드
	private byte[] startTransferDate = new byte[8]; // 5.이체시작일자
	private byte[] endTransferDate = new byte[8]; // 6.이체종료일자
	private byte[] dAccountNo = new byte[15]; // 7.모계좌번호
	private byte[] resultInformCode = new byte[1]; // 8.결과통보구분
	private byte[] fundCode = new byte[1]; // 9.자금구분
	private byte[] reTransferCode = new byte[1]; // 10.재처리구분
	private byte[] isRealNum = new byte[1]; // 11.실명번호검증
	private byte[] accountId = new byte[6]; // 12.Account-id
	private byte[] userId = new byte[8]; // 13.User-Id
	private byte[] messageClass = new byte[7]; // 14.Message-Class
	private byte[] tmp = new byte[8];// 15.예비부분
	private byte[] crLf = new byte[] { 13, 10 }; // 16.cr&lf

	protected final Charset cs = Charset.forName("ksc5601");

	public HeaderDto() {
	}

	public byte[] getTelegram() {
		byte[] telegram = new byte[80];
		ByteBuffer bf = ByteBuffer.wrap(telegram);

		bf.put(this.recordCode);
		bf.put(this.fileCode);
		bf.put(this.companyNum);
		bf.put(this.bankCode);
		bf.put(this.startTransferDate);
		bf.put(this.endTransferDate);
		bf.put(this.dAccountNo);
		bf.put(this.resultInformCode);
		bf.put(this.fundCode);
		bf.put(this.reTransferCode);
		bf.put(this.isRealNum);
		bf.put(this.accountId);
		bf.put(this.userId);
		bf.put(this.messageClass);
		bf.put(this.tmp);
		bf.put(this.crLf);

		return telegram;
	}

	public ByteBuffer setTelegram(byte[] inComingData) {
		ByteBuffer bf = ByteBuffer.wrap(inComingData);

		bf.get(this.recordCode);
		bf.get(this.fileCode);
		bf.get(this.companyNum);
		bf.get(this.bankCode);
		bf.get(this.startTransferDate);
		bf.get(this.endTransferDate);
		bf.get(this.dAccountNo);
		bf.get(this.resultInformCode);
		bf.get(this.fundCode);
		bf.get(this.reTransferCode);
		bf.get(this.isRealNum);
		bf.get(this.accountId);
		bf.get(this.userId);
		bf.get(this.messageClass);
		bf.get(this.tmp);
		bf.get(this.crLf);

		return bf;
	}

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

	public String getFileCode() {
		return new String(fileCode, cs);
	}

	public void setFileCode(String fileCode) {
		try {
			this.fileCode = ByteUtil.toByteR(fileCode, this.fileCode.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getCompanyNum() {
		return new String(companyNum, cs);
	}

	public void setCompanyNum(String companyNum) {
		try {
			this.companyNum = ByteUtil.toByteR(companyNum, this.companyNum.length, cs);
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

	public int getStartTransferDate() {
		return Integer.parseInt(new String(startTransferDate, cs));
	}

	public void setStartTransferDate(int startTransferDate) {
		try {
			this.startTransferDate = ByteUtil.toByteL(startTransferDate, this.startTransferDate.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getEndTransferDate() {
		return Integer.parseInt(new String(endTransferDate, cs));
	}

	public void setEndTransferDate(int endTransferDate) {
		try {
			this.endTransferDate = ByteUtil.toByteL(endTransferDate, this.endTransferDate.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getdAccountNo() {
		return new String(dAccountNo, cs);
	}

	public void setdAccountNo(String dAccountNo) {
		try {
			this.dAccountNo = ByteUtil.toByteR(dAccountNo, this.dAccountNo.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getResultInformCode() {
		return Integer.parseInt(new String(resultInformCode, cs));
	}

	public void setResultInformCode(int resultInformCode) {
		try {
			this.resultInformCode = ByteUtil.toByteL(resultInformCode, this.resultInformCode.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getFundCode() {
		return Integer.parseInt(new String(fundCode, cs));
	}

	public void setFundCode(int fundCode) {
		try {
			this.fundCode = ByteUtil.toByteL(fundCode, this.fundCode.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getReTransferCode() {
		return new String(reTransferCode, cs);
	}

	public void setReTransferCode(String reTransferCode) {
		try {
			this.reTransferCode = ByteUtil.toByteR(reTransferCode, this.reTransferCode.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getIsRealNum() {
		return new String(isRealNum, cs);
	}

	public void setIsRealNum(String isRealNum) {
		try {
			this.isRealNum = ByteUtil.toByteR(isRealNum, this.isRealNum.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getAccountId() {
		return new String(accountId, cs);
	}

	public void setAccountId(String accountId) {
		try {
			this.accountId = ByteUtil.toByteR(accountId, this.accountId.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getUserId() {
		return new String(userId, cs);
	}

	public void setUserId(String userId) {
		try {
			this.userId = ByteUtil.toByteR(userId, this.userId.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getMessageClass() {
		return new String(messageClass, cs);
	}

	public void setMessageClass(String messageClass) {
		try {
			this.messageClass = ByteUtil.toByteR(messageClass, this.messageClass.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getTmp() {
		return new String(tmp, cs);
	}

	public void setTmp(String tmp) {
		try {
			this.tmp = ByteUtil.toByteR(tmp, this.tmp.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getCrLf() {
		return new String(crLf, cs);
	}

	public void setCrLf(String crLf) {
		try {
			this.crLf = ByteUtil.toByteR(crLf, this.crLf.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
