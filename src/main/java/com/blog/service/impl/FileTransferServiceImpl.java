package com.blog.service.impl;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.blog.common.service.FileService;
import com.blog.constants.CommCode;
import com.blog.controller.filetransfer.request.ResultTransferRequest;
import com.blog.controller.filetransfer.request.TransferRequest;
import com.blog.service.FileTransferService;
import com.blog.dto.file.salary.DacomHeaderDto;
import com.blog.dto.file.salary.HeaderDto;
import com.blog.dto.file.salary.DataDto;
import com.blog.dto.file.salary.TrailerDto;

@Service("fileTransferServiceImpl")
public class FileTransferServiceImpl implements FileTransferService {

	protected Logger logger = LogManager.getLogger(this.getClass());

	private static String ENC;
	private static String TO_PATN;
	private static String FROM_PATN;

	@Autowired
	private FileService fileService;

	private static final Charset cs = Charset.forName("ksc5601"); // 파일전송은 ksc5601 TODO

	@PostConstruct
	public void initialize() {
		try {
			ENC = "";
			TO_PATN = "";
			FROM_PATN = "";
		} catch (Exception e) {
		}
	}

	int n = 0;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity reservation(TransferRequest transferRequest) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String encKeyUrl = ENC;

		String fileName = getFileName();

		logger.debug("@@ salary transfer file(SEND) process...... : " + FROM_PATN + " [" + fileName + "]");

		int startEndDate = Integer.parseInt(transferRequest.getReqXferDate().replaceAll("-", ""));

		String dAccount = "";

		fileService.createFile(TO_PATN, fileName + ".tmp");

		// DacomHeader
		DacomHeaderDto dacomHeaderDto = new DacomHeaderDto();
		dacomHeaderDto.setHeaderCode("<<HEADER>>");
		dacomHeaderDto.setSenderCode("DDDD001");
		dacomHeaderDto.setCompanyCode("SH");
		dacomHeaderDto.setListInfoCode("200");
		dacomHeaderDto.setAutoFax("N");
		dacomHeaderDto.setBroadcastTransfer("N");
		dacomHeaderDto.setRecipientNum("001");
		dacomHeaderDto.setRecipientCode("SHBBANK");
		dacomHeaderDto.setReserve("");

		byte[] dacomHeader = dacomHeaderDto.getTelegram();
		dacomHeaderDto.setTelegram(dacomHeader);
		fileService.appendBytes(TO_PATN, fileName + ".tmp", dacomHeader);

		// Header
		HeaderDto headerDto = new HeaderDto();
		headerDto.setFileCode("A9");
		headerDto.setCompanyNum("DDDD001");
		headerDto.setBankCode(88);
		headerDto.setStartTransferDate(startEndDate);
		headerDto.setEndTransferDate(startEndDate);
		headerDto.setdAccountNo(dAccount); // TODO : 확인필요
		headerDto.setResultInformCode(1);
		headerDto.setFundCode(Integer.parseInt(transferRequest.getTransferType()));
		headerDto.setReTransferCode("");
		headerDto.setIsRealNum(transferRequest.getVerifyDeptAcctOwner());
		headerDto.setAccountId(""); // TODO : 확인필요
		headerDto.setUserId(""); // TODO : 확인필요
		headerDto.setMessageClass(""); // TODO : 확인필요
		headerDto.setTmp(""); // TODO : 확인필요
		byte[] header = headerDto.getTelegram();
		headerDto.setTelegram(header);
		fileService.appendBytes(TO_PATN, fileName + ".tmp", header);

		// Data
		List<TransferRequest.Accounts> accounts = transferRequest.getDeptAccounts();

		for (int i = 0; i < accounts.size(); i++) {
			DataDto dataDto = new DataDto();
			dataDto.setBankCode(Integer.parseInt(accounts.get(i).getBankCode()));
			dataDto.setAccountNo(accounts.get(i).getAccountNo());
			dataDto.setAmount(accounts.get(i).getTransferAmt());
			dataDto.setTransferResult("");
			dataDto.setDCode(0000);
			dataDto.setAccountHistory(accounts.get(i).getPrintContent());
			dataDto.setClientInfo(accounts.get(i).getRealNo());
			dataDto.setListCode("");

			byte[] data = dataDto.getTelegram();
			dataDto.setTelegram(data);
			fileService.appendBytes(TO_PATN, fileName + ".tmp", data);
		}

		// Trailer
		TrailerDto trailerDto = new TrailerDto();
		int recodeNum = accounts.size() + 2; // header + data + trailer
		trailerDto.setRecodeCount(recodeNum);// 임시 계산후 해야되는지?

		byte[] trailer = trailerDto.getTelegram();
		headerDto.setTelegram(trailer);
		fileService.appendBytes(TO_PATN, fileName + ".tmp", trailer);

		fileService.renameFile(TO_PATN, fileName + ".tmp", fileName, true);

		logger.debug("@@ salary transfer file(SEND) processed!!!! : " + FROM_PATN + " [" + fileName + "]");

		return new ResponseEntity("OK", HttpStatus.OK);
	}

	private String getFileName() {
		String fileName = "DD" + String.format("%02d", n);

		File fileNew = new File(TO_PATN + "/" + fileName);

		if (fileNew.exists()) {
			n++;
			fileName = "DD" + String.format("%02d", n);
		}
		return fileName;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity result(ResultTransferRequest resultTransferSalarRequest) throws Exception {

		List<HashMap<String, Object>> shSalaryResultList = new ArrayList<HashMap<String, Object>>();

		String fileName = resultTransferSalarRequest.getFileName();

		logger.debug(
				"@@ salary transfer file(RECEIVED) process...... : " + FROM_PATN + " [" + fileName + "]");

		// Read 파일
		byte[] fcon = fileService.readFileToByteArray(FROM_PATN, fileName);
		if (fcon == null || fcon.length == 0) {
			return new ResponseEntity("NOT_FOUND", HttpStatus.NOT_FOUND);
		}

		// Read 데이터
		int dataCnt = (fcon.length - 240) / 80; // 부분별 80 바이트 (dacomHeader + Header + Trailer = 240)
		int dataSize = 0;
		TransferSalaryLayout transferSalaryLayout = new TransferSalaryLayout();

//		HashMap<String, Object> dacomHeader = transferSalaryLayout.getMapFromTR(CommCode.DACOM_HEADER, fileName, 0, fcon);
//		HashMap<String, Object> headerRecord = transferSalaryLayout.getMapFromTR(CommCode.HEADER_RECORD, fileName, 80, fcon);
//		shSalaryResultList.add(dacomHeader);
//		shSalaryResultList.add(headerRecord);
//		
//		for (int i = 0; i < dataCnt; i++) {
//			dataSize = 160 + (80 * i);
//			
//			HashMap<String,Object> dataRecord = transferSalaryLayout.getMapFromTR("salaryTransfer", fileName, dataSize, fcon);
//			shSalaryResultList.add(dataRecord);
//		}
//		HashMap<String, Object> trailerRecrd = transferSalaryLayout.getMapFromTR(CommCode.TRAILER_RECORD, fileName, fcon.length - 80, fcon);
//		shSalaryResultList.add(trailerRecrd);

		for (int i = 0; i < dataCnt; i++) {
			dataSize = 160 + (80 * i);

			HashMap<String, Object> record = transferSalaryLayout.getMapFromTR("salaryTransfer", fileName, dataSize,
					fcon);
			shSalaryResultList.add(record);

//			String data = new String(fcon, dataSize, 80, cs);
//			HashMap<String, Object> resultMap = new HashMap<String, Object>();
//			resultMap.put(CommCode.ACCOUNT_NO, data.substring(4, 19));
//			resultMap.put(CommCode.TRANSFER_AMT, data.substring(19, 30));
//			resultMap.put(CommCode.TRANSFER_RESULT, data.substring(30, 31));
//			resultMap.put(CommCode.ACCOUNT_TRANSFER_CODE, data.substring(31, 35));
//			resultMap.put(CommCode.CLIENT_INFO, data.substring(47, 76));
//			shSalaryResultList.add(resultMap);
		}

		// Move 수신 완료한 결과파일 이동
		fileService.renameFile(FROM_PATN, fileName, "processed/" + fileName, true);

		logger.debug(
				"@@ salary transfer file(RECEIVED) processed!!!! : " + FROM_PATN + " [" + fileName + "]");

		return new ResponseEntity("OK", HttpStatus.OK);
	}
}

class TransferSalaryLayout {
	public HashMap<String, Object> getMapFromTR(String trType, String fileName, int startPos, byte[] data) {
		Map<String, FileTrPos> layout;

		switch (trType) {
		case "salaryTransfer":
			layout = LayoutSalaryTransfer.layout;
			break;
		case CommCode.DACOM_HEADER:
			layout = LayoutDacomHeader.layout;
			break;
		case CommCode.HEADER_RECORD:
			layout = LayoutHeaderRecord.layout;
			break;
		case CommCode.DATA_RECORD:
			layout = LayoutDataRecord.layout;
			break;
		case CommCode.TRAILER_RECORD:
			layout = LayoutTrailerRecord.layout;
			break;
		default:
			return null;
		}

		HashMap<String, Object> resultValue = new HashMap<String, Object>();
//		resultValue.put("file_name", fileName);
//		resultValue.put("file_type", trType);

		for (String key : layout.keySet()) {
			resultValue.put(key, new String(data, startPos + layout.get(key).idxStart, layout.get(key).length).trim());
		}
		return resultValue;
	}
}

class LayoutSalaryTransfer {
	public final static Map<String, FileTrPos> layout;
	static {
		Map<String, FileTrPos> Layout = new HashMap<String, FileTrPos>();
		Layout.put(CommCode.ACCOUNT_NO, new FileTrPos(4, 15)); // 3. 계좌번호
		Layout.put(CommCode.TRANSFER_AMT, new FileTrPos(19, 9)); // 4, 이체금액
		Layout.put(CommCode.TRANSFER_RESULT, new FileTrPos(30, 1)); // 5. 이체결과
		Layout.put(CommCode.ACCOUNT_TRANSFER_CODE, new FileTrPos(31, 4)); // 6. 불능코드
		Layout.put(CommCode.CLIENT_INFO, new FileTrPos(47, 29)); // 7. 고객영역
		layout = Collections.unmodifiableMap(Layout);
	}
}

class LayoutDacomHeader {
	public final static Map<String, FileTrPos> layout;
	static {
		Map<String, FileTrPos> Layout = new HashMap<String, FileTrPos>();
		Layout.put("header_code", new FileTrPos(0, 10)); // 1. header구분
		Layout.put("sender_code", new FileTrPos(10, 8)); // 2. 송신자코드
		Layout.put("company_code", new FileTrPos(18, 2)); // 3. 업체구분코드
		Layout.put("list_info_code", new FileTrPos(20, 3)); // 4. 정보구분코드
		Layout.put("auto_fax", new FileTrPos(23, 1)); // 5. Fax자동착신
		Layout.put("broadcast_transfer", new FileTrPos(24, 1)); // 6. 통보통신
		Layout.put("recipient_num", new FileTrPos(25, 3)); // 7. 수신자 수
		Layout.put("recipient_code", new FileTrPos(28, 8)); // 8. 수신자 코드
		Layout.put("reserve", new FileTrPos(36, 42)); // 9. 예비
		layout = Collections.unmodifiableMap(Layout);
	}
}

class LayoutHeaderRecord {
	public final static Map<String, FileTrPos> layout;
	static {
		Map<String, FileTrPos> Layout = new HashMap<String, FileTrPos>();
		Layout.put("record_code", new FileTrPos(0, 1)); // 1. record 구분
		Layout.put("file_code", new FileTrPos(1, 2)); // 2. 화일구분코드
		Layout.put("company_num", new FileTrPos(3, 8)); // 3. 업체번호
		Layout.put("bank_code", new FileTrPos(11, 3)); // 4. 은행코드
		Layout.put("start_transfer_date", new FileTrPos(14, 8)); // 5. 이체시작일자
		Layout.put("end_transfer_date", new FileTrPos(22, 8)); // 6. 이체종료일자
		Layout.put("daccount_no", new FileTrPos(30, 15)); // 7. 모계좌번호
		Layout.put("result_info_code", new FileTrPos(45, 1)); // 8. 결과통보구분
		Layout.put("fund_code", new FileTrPos(46, 1)); // 9. 자금구분
		Layout.put("retransfer_code", new FileTrPos(47, 1)); // 10. 재처리구분
		Layout.put("is_real_num", new FileTrPos(48, 1)); // 11. 실명번호 검증
		Layout.put("account_id", new FileTrPos(49, 6)); // 12. Account-Id
		Layout.put("user_id", new FileTrPos(55, 8)); // 13. User-Id
		Layout.put("message_class", new FileTrPos(63, 7)); // 14. Message-Class
		Layout.put("tmp", new FileTrPos(70, 8)); // 15. 예비부분
		layout = Collections.unmodifiableMap(Layout);
	}
}

class LayoutDataRecord {
	public final static Map<String, FileTrPos> layout;
	static {
		Map<String, FileTrPos> Layout = new HashMap<String, FileTrPos>();
		Layout.put("record_code", new FileTrPos(0, 1)); // 1. record 구분
		Layout.put("bank_code", new FileTrPos(1, 3)); // 2. 은행코드
		Layout.put("account_no", new FileTrPos(4, 15)); // 3. 계좌번호
		Layout.put("amount", new FileTrPos(19, 11)); // 4. 이체금액
		Layout.put("transfer_result", new FileTrPos(30, 1)); // 5. 이체결과
		Layout.put("d_code", new FileTrPos(31, 4)); // 6. 불능코드
		Layout.put("account_history", new FileTrPos(35, 12)); // 7. 통장인자 내역
		Layout.put("client_info", new FileTrPos(47, 29)); // 8. 고객영역
		Layout.put("list_code", new FileTrPos(76, 2)); // 9. 통장인자구분
		layout = Collections.unmodifiableMap(Layout);
	}
}

class LayoutTrailerRecord {
	public final static Map<String, FileTrPos> layout;
	static {
		Map<String, FileTrPos> Layout = new HashMap<String, FileTrPos>();
		Layout.put("record_code", new FileTrPos(0, 1)); // 1. record 구분
		Layout.put("record_count", new FileTrPos(1, 7)); // 2. 전송 Record 수
		Layout.put("request_transfer_no", new FileTrPos(8, 7)); // 3. 이체의뢰분건수
		Layout.put("request_transfer_amt", new FileTrPos(15, 13)); // 4. 이체의뢰금액
		Layout.put("complete_transfer_no", new FileTrPos(28, 7)); // 5. 이체정상분건수
		Layout.put("complete_transfer_amt", new FileTrPos(35, 13)); // 6. 이체정상금액
		Layout.put("fail_transfer_no", new FileTrPos(38, 7)); // 7. 이체불능분건수
		Layout.put("fail_transfer_amt", new FileTrPos(45, 13)); // 8. 이체불능금액
		Layout.put("tmp", new FileTrPos(68, 10)); // 9. 예비부분
		layout = Collections.unmodifiableMap(Layout);
	}
}

class FileTrPos {
	public int idxStart;
	public int length;

	public FileTrPos(int i, int l) {
		this.idxStart = i;
		this.length = l;
	}
}
