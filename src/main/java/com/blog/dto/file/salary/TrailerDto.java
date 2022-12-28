package com.blog.dto.file.salary;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.blog.common.util.ByteUtil;

public class TrailerDto {

	private final byte[] recodeCode = new byte[] { 'E' }; // 1. record 구분
	private byte[] recodeCount = new byte[7]; // 2. 전송 Record 수
	private byte[] requestTransferNo = new byte[7]; // 3. 이체의뢰분건수
	private byte[] requestTransferAmt = new byte[13]; // 4. 이체의뢰금액
	private byte[] completeTransferNo = new byte[7]; // 5. 이체정상분건수
	private byte[] completeTransferAmt = new byte[13]; // 6. 이체정상금액
	private byte[] failTransferNo = new byte[7]; // 7. 이체불능분건수
	private byte[] failTransferAmt = new byte[13]; // 8. 이체불능금액
	private byte[] tmp = new byte[10]; // 9. 예비부분

	private final byte[] crLf = new byte[] { 13, 10 };

	protected final Charset cs = Charset.forName("ksc5601");

	public TrailerDto() {
	}

	public byte[] getTelegram() {
		byte[] telegram = new byte[80];
		ByteBuffer bf = ByteBuffer.wrap(telegram);

		bf.put(this.recodeCode);
		bf.put(this.recodeCount);
		bf.put(this.requestTransferNo);
		bf.put(this.requestTransferAmt);
		bf.put(this.completeTransferNo);
		bf.put(this.completeTransferAmt);
		bf.put(this.failTransferNo);
		bf.put(this.failTransferAmt);
		bf.put(this.tmp);
		bf.put(this.crLf);

		return telegram;
	}

	public ByteBuffer setTelegram(byte[] inComingData) {
		ByteBuffer bf = ByteBuffer.wrap(inComingData);

		bf.get(this.recodeCode);
		bf.get(this.recodeCount);
		bf.get(this.requestTransferNo);
		bf.get(this.requestTransferAmt);
		bf.get(this.completeTransferNo);
		bf.get(this.completeTransferAmt);
		bf.get(this.failTransferNo);
		bf.get(this.failTransferAmt);
		bf.get(this.tmp);
		bf.get(this.crLf);

		return bf;
	}

	public int getRecodeCount() {
		return Integer.parseInt(new String(recodeCount, cs));
	}

	public void setRecodeCount(int recodeCount) {
		try {
			this.recodeCount = ByteUtil.toByteL(recodeCount, this.recodeCount.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getRequestTransferNo() {
		return Integer.parseInt(new String(requestTransferNo, cs));
	}

	public void setRequestTransferNo(int requestTransferNo) {
		try {
			this.requestTransferNo = ByteUtil.toByteL(requestTransferNo, this.requestTransferNo.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getRequestTransferAmt() {
		return Integer.parseInt(new String(requestTransferAmt, cs));
	}

	public void setRequestTransferAmt(int requestTransferAmt) {
		try {
			this.requestTransferAmt = ByteUtil.toByteL(requestTransferAmt, this.requestTransferAmt.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getCompleteTransferNo() {
		return Integer.parseInt(new String(completeTransferNo, cs));
	}

	public void setCompleteTransferNo(int completeTransferNo) {
		try {
			this.completeTransferNo = ByteUtil.toByteL(completeTransferNo, this.completeTransferNo.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getCompleteTransferAmt() {
		return Integer.parseInt(new String(completeTransferAmt, cs));
	}

	public void setCompleteTransferAmt(int completeTransferAmt) {
		try {
			this.completeTransferAmt = ByteUtil.toByteL(completeTransferAmt, this.completeTransferAmt.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getFailTransferNo() {
		return Integer.parseInt(new String(failTransferNo, cs));
	}

	public void setFailTransferNo(int failTransferNo) {
		try {
			this.failTransferNo = ByteUtil.toByteL(failTransferNo, this.failTransferNo.length, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getFailTransferAmt() {
		return Integer.parseInt(new String(failTransferAmt, cs));
	}

	public void setFailTransferAmt(int failTransferAmt) {
		try {
			this.failTransferAmt = ByteUtil.toByteL(failTransferAmt, this.failTransferAmt.length, cs);
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

}
