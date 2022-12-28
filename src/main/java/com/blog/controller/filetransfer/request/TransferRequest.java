package com.blog.controller.filetransfer.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TransferRequest {

	@NotBlank
	private String accountConnectKey;
	@Positive
	private int companyNo;
	@NotBlank
	private String reqXferDate;
	@NotBlank
	private String transferType;
	@NotBlank
	private String verifyDeptAcctOwner;

	private int totalXferCnt;

	private int totalXferAmt;

	@NotNull
	@Valid
	private List<Accounts> deptAccounts;

	@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
	public static class Accounts {
		@Positive
		private int employeeNo;
		@NotBlank
		private String bankCode;
		@NotBlank
		private String accountNo;
		
		private int transferAmt;
		@NotBlank
		private String printContent;
		@NotBlank
		private String printContentType;
		@NotBlank
		private String realNo;

		public int getEmployeeNo() {
			return employeeNo;
		}

		public String getBankCode() {
			return bankCode;
		}

		public String getAccountNo() {
			return accountNo;
		}

		public int getTransferAmt() {
			return transferAmt;
		}

		public String getPrintContent() {
			return printContent;
		}

		public String getPrintContentType() {
			return printContentType;
		}

		public String getRealNo() {
			return realNo;
		}
	}

	public String getAccountConnectKey() {
		return accountConnectKey;
	}

	public void setAccountConnectKey(String accountConnectKey) {
		this.accountConnectKey = accountConnectKey;
	}

	public int getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(int companyNo) {
		this.companyNo = companyNo;
	}

	public String getReqXferDate() {
		return reqXferDate;
	}

	public void setReqXferDate(String reqXferDate) {
		this.reqXferDate = reqXferDate;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getVerifyDeptAcctOwner() {
		return verifyDeptAcctOwner;
	}

	public void setVerifyDeptAcctOwner(String verifyDeptAcctOwner) {
		this.verifyDeptAcctOwner = verifyDeptAcctOwner;
	}

	public int getTotalXferCnt() {
		return totalXferCnt;
	}

	public void setTotalXferCnt(int totalXferCnt) {
		this.totalXferCnt = totalXferCnt;
	}

	public int getTotalXferAmt() {
		return totalXferAmt;
	}

	public void setTotalXferAmt(int totalXferAmt) {
		this.totalXferAmt = totalXferAmt;
	}

	public List<Accounts> getDeptAccounts() {
		return deptAccounts;
	}

	public void setDeptAccounts(List<Accounts> deptAccounts) {
		this.deptAccounts = deptAccounts;
	}
}
