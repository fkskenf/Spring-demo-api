package com.blog.controller.filetransfer;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.controller.filetransfer.request.ResultTransferRequest;
import com.blog.controller.filetransfer.request.TransferRequest;
import com.blog.service.FileTransferService;

@RestController
@RequestMapping("/svc")
public class FileTransferController {

	protected Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private FileTransferService fileTransferService;

	@PostMapping("/reservation")
	public ResponseEntity<?> reservation(@Valid @RequestBody TransferRequest transferRequest) throws Exception {
		return fileTransferService.reservation(transferRequest);
	}

	@PostMapping("/result")
	public ResponseEntity<?> result(@Valid @RequestBody ResultTransferRequest resultTransferRequest) throws Exception {
		return fileTransferService.result(resultTransferRequest);
	}
}
