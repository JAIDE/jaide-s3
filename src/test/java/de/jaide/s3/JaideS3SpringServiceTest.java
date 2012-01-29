package de.jaide.s3;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.io.FileUtils;

/**
 * Call the Uploader by using the Spring framework. For this to work you need to modify the existing "aws-s3.properties.template" file by
 * putting in your S3 access credentials and saving it somewhere in the classpath under the filename "aws-s3.properties".
 */
public class JaideS3SpringServiceTest extends TestCase {
  /**
   * Create the test case.
   * 
   * @param testName Name of the test case
   */
  public JaideS3SpringServiceTest(String testName) {
    super(testName);
  }

  /**
   * Return the Test suite.
   * 
   * @return The suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(JaideS3SpringServiceTest.class);
  }

  /**
   * Run the Uploader via the Spring framework.
   */
  public void testJaideS3SpringService() throws IOException {
    ByteArrayInputStream bin = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File("/tmp/BabyOngBak.jpg")));

    /*
     * Call the Uploader by using the Spring framework. For this to work you need to modify the existing "aws-s3.properties.template" file
     * by putting in your S3 access credentials and saving it somewhere in the classpath under the filename "aws-s3.properties".
     */
    boolean upload = JaideS3SpringService.getJaideS3Uploader().upload(String.valueOf(System.currentTimeMillis()), "BabyOngBak.jpg", bin);
    assertTrue(upload);
  }
}
