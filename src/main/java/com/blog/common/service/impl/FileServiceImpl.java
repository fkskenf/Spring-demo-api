package com.blog.common.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.blog.common.service.FileService;

@Service("FileService")
public class FileServiceImpl implements FileService {

	public boolean createFile(String baseDirectory, String fileName) {
		if (baseDirectory == null || fileName == null)
			return false;

		String fullpathFilename;
		if (baseDirectory.endsWith("/"))
			fullpathFilename = baseDirectory + fileName;
		else
			fullpathFilename = baseDirectory + "/" + fileName;

		File file = null;
		try {
			file = new File(fullpathFilename);

			if (file.exists()) {
				// 이미 파일이 있으면...파일명_HHmmss_00 (00은 난수)
				FileUtils.moveFile(file,
						new File(fullpathFilename + LocalDateTime.now().format(DateTimeFormatter.ofPattern("_HHmmss_"))
								+ String.format("%2d", new Random().nextInt(100))));
			}
			file.setReadable(true, false);
			file.setWritable(true, false);

			FileUtils.touch(file);
		} catch (IOException e) {
			return false;
			// e.printStackTrace();
		}
		return true;
	}

	public boolean appendBytes(String baseDirectory, String fileName, byte[] b) {
		String fullpathFilename;
		if (baseDirectory.endsWith("/"))
			fullpathFilename = baseDirectory + fileName;
		else
			fullpathFilename = baseDirectory + "/" + fileName;

		File file = new File(fullpathFilename);
		try {
			FileUtils.writeByteArrayToFile(file, b, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}

	public boolean renameFile(String baseDirectory, String fileNameAsis, String fileNameTobe, boolean overwrite) {
		File fileOld = new File(baseDirectory + "/" + fileNameAsis);
		File fileNew = new File(baseDirectory + "/" + fileNameTobe);
		try {
			if (fileNew.exists()) {
				if (overwrite == false)
					return false;

				// 이미 파일이 있으면...파일명_HHmmss_00 (00은 난수)
				FileUtils.moveFile(fileNew, new File(
						baseDirectory + "/" + fileNameTobe + ".old_" + String.format("%3d", new Random().nextInt(1000))));
			}
			fileNew = new File(baseDirectory + "/" + fileNameTobe);

			FileUtils.moveFile(fileOld, fileNew);
		} catch (IOException e) {
			return false;
			// e.printStackTrace();
		}
		return true;
	}

	public byte[] readFileToByteArray(String baseDirectory, String fileName) {
		String fullpathFilename;
		if (baseDirectory.endsWith("/"))
			fullpathFilename = baseDirectory + fileName;
		else
			fullpathFilename = baseDirectory + "/" + fileName;

		try {
			File f = new File(fullpathFilename);

			if (f.exists() == false)
				return null;
			if (f.isDirectory())
				return null;

			return FileUtils.readFileToByteArray(f);
		} catch (Exception e) {
			return null;
		}
	}
	/*
	 * public byte[] readFilePart(String baseDir, String filename) { String
	 * fullpathFilename; if( baseDir.endsWith("/") ) fullpathFilename = baseDir+
	 * filename; else fullpathFilename = baseDir+"/"+filename;
	 * 
	 * byte[] b; try { FileInputStream fis = new FileInputStream(fullpathFilename);
	 * fis.read(b) } catch(Exception e) {
	 * 
	 * } }
	 */

	public ArrayList<String> getNoExtFilenameList(String baseDirectory, String pattern) {
		File dir = new File(baseDirectory);
		ArrayList<String> retval = new ArrayList<String>();

		File[] files = dir.listFiles();
		for (File f : files) {
			if (f.isDirectory())
				continue;
			// if (f.getName().contains(".")) continue;
			// 패턴에 맞는 파일만 리스트에 담아유~~
			if (f.getName().matches(pattern))
				retval.add(f.getName());
		}
		dir = null;
		files = null;
		return retval;
	}

}
