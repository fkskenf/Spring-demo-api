package com.blog.service;

import org.springframework.http.ResponseEntity;

import com.blog.controller.filetransfer.request.ResultTransferRequest;
import com.blog.controller.filetransfer.request.TransferRequest;

public interface FileTransferService {

	ResponseEntity<?> reservation(TransferRequest transferRequest) throws Exception;
	
	ResponseEntity<?> result(ResultTransferRequest resultTransferRequest) throws Exception ;

}
