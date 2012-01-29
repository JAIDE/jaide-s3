package de.jaide.s3;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.io.FileUtils;

/**
 * Call the Uploader by directly instantiating it using our S3 access credentials.
 */
public class JaideS3UploaderTest extends TestCase {
  /**
   * Create the test case.
   * 
   * @param testName Name of the test case
   */
  public JaideS3UploaderTest(String testName) {
    super(testName);
  }

  /**
   * Return the Test suite.
   * 
   * @return The suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(JaideS3UploaderTest.class);
  }

  /**
   * Directly run the Uploader.
   */
  public void testJaideS3Uploader() throws IOException {
    ByteArrayInputStream bin = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File("/tmp/BabyOngBak.jpg")));

    /*
     * Call the Uploader by directly instantiating it using our S3 access credentials.
     */
    boolean upload = new JaideS3Uploader("MyS3AccessKey", "MyS3SecretKey", "my-s3-bucket-name").upload(
        String.valueOf(System.currentTimeMillis()), "BabyOngBak.jpg", bin);
    assertTrue(upload);
  }
}
