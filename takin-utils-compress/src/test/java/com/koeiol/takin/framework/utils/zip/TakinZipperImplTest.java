package com.koeiol.takin.framework.utils.zip;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;

/**
 * Created by koeiol@github.com on 16/9/12.
 */
public class TakinZipperImplTest {

    File srcFile1;
    File zipFile1;
    TakinZipper takinZipper;

    @Before
    public void setUp() throws Exception {
        srcFile1 = File.createTempFile("TakinZipperImplTest_testZip", ".tmp");
        FileWriter fileWriter = new FileWriter(srcFile1);
        fileWriter.write("hello world.");
        fileWriter.close();
        zipFile1 = File.createTempFile("TakinZipperImplTest_testZip", ".zip");
        takinZipper = new TakinZipperImpl();
    }

    @After
    public void tearDown() {
        srcFile1.deleteOnExit();
        zipFile1.deleteOnExit();
    }

    @Test
    public void testZip() throws Exception {

        String srcFile = srcFile1.getAbsolutePath();
        String zipFile = zipFile1.getAbsolutePath();

        long t1 = System.currentTimeMillis();
        takinZipper.zip(srcFile, zipFile);
        long t2 = System.currentTimeMillis();
        System.out.printf("TakinZipperImplTest.testZip() total cost: %d%n", t2 - t1);

        File result = new File(zipFile);
        Assert.assertTrue(result.isFile() && result.length() > 0);

    }

    @Test
    public void testUnzip() throws Exception {

        String zipFile = zipFile1.getAbsolutePath();
        String objPath = zipFile1.getPath();

        long t1 = System.currentTimeMillis();
        takinZipper.zip(zipFile, objPath);
        long t2 = System.currentTimeMillis();
        System.out.printf("TakinZipperImplTest.testZip() total cost: %d%n", t2 - t1);

        File result = new File(zipFile);
        Assert.assertTrue(result.isFile() && result.length() > 0);

    }
}
