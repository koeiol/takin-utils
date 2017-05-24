package com.koeiol.takin.framework.utils.zip;

import java.io.IOException;

/**
 * Created by koeiol@github.com on 16/9/12.
 */
public interface TakinZipper {

	void zip(String srcFile, String zipFile) throws IOException;

	void unzip(String zipFile, String objPath) throws IOException;

}
