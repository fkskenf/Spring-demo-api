package com.blog.common.service;

import java.util.ArrayList;

public interface FileService {

	boolean createFile(String baseDir, String filename);

	boolean appendBytes(String baseDir, String filename, byte[] b);

	boolean renameFile(String baseDir, String filename_asis, String filename_tobe, boolean overwrite);

	byte[] readFileToByteArray(String bASEDIR_FROM_ORG, String string);

	ArrayList<String> getNoExtFilenameList(String baseDir, String pattern);

}
