package com.koeiol.takin.framework.utils.zip;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by xuh on 16/9/12.
 */
public class TakinZipperImpl implements TakinZipper {

	@Override
	public void zip(String srcFile, String zipFile) throws IOException {
		zipToStream(zipFile, srcFile);
	}

	@Override
	public void unzip(String zipFile, String objPath) throws IOException {
		unzipFromStream(zipFile, objPath);
	}

	private OutputStream zipToStream(String zipFile, String... srcFiles) throws IOException {
		List<File> files = new ArrayList<>();
		for (String srcFile : srcFiles) {
			files.add(new File(srcFile));
		}
		return zipToStream(files, zipFile);
	}

	private OutputStream zipToStream(List<File> srcFiles, String zipFile) throws IOException {
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
		for (File srcFile : srcFiles) {
			InputStream inputStream = new FileInputStream(srcFile);
			zipOutputStream.putNextEntry(new ZipEntry(srcFile.getName()));
			int temp = 0;
			while ((temp = inputStream.read()) != -1) {
				zipOutputStream.write(temp);
			}
			inputStream.close();
		}
		zipOutputStream.close();
		return zipOutputStream;
	}

	private void unzipFromStream(String zipFileName, String objPath) throws IOException {
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFileName));
		ZipFile zipFile = new ZipFile(zipFileName);
		ZipEntry zipEntry;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			File outFile = new File(objPath + File.separator + zipEntry.getName());
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdir();
			}
			if (!outFile.exists()) {
				outFile.createNewFile();
			}
			InputStream input = zipFile.getInputStream(zipEntry);
			OutputStream output = new FileOutputStream(outFile);
			int temp = 0;
			while ((temp = input.read()) != -1) {
				output.write(temp);
			}
			input.close();
			output.close();
		}
	}
}
