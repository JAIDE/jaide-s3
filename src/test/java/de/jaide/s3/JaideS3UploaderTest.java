package de.jaide.s3;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Call the Uploader by directly instantiating it using our S3 access credentials. Put in your own S3 access credentials in the line below.
 */
public class JaideS3UploaderTest {

  /**
   * Directly run the Uploader.
   */
  @Test
  public void testJaideS3Uploader() throws IOException {
    ByteArrayInputStream bin = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File("/tmp/BabyOngBak.jpg")));

    /*
     * Call the Uploader by directly instantiating it using our S3 access credentials.
     */
    boolean upload = new JaideS3Uploader("MyS3AccessKey", "MyS3SecretKey", "my-s3-bucket-name").upload(
        String.valueOf(System.currentTimeMillis()), "BabyOngBak.jpg", bin, JaideS3Uploader.CONTENT_TYPE_IMAGE_JPEG);
    Assert.assertTrue(upload);
  }
}
