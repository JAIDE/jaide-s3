package de.jaide.s3;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Call the Uploader/deletion by using the Spring framework. For this to work you need to modify the existing "aws-s3.properties.template"
 * file by
 * putting in your S3 access credentials and saving it somewhere in the classpath under the filename "aws-s3.properties".
 */
public class JaideS3SpringServiceTest {

  /**
   * Run the Uploader via the Spring framework.
   * 
   * @throws IOException Thrown in case the file to be uploaded could not be found.
   */
  @Test
  public void testJaideS3SpringServiceUpload() throws IOException {
    ByteArrayInputStream bin = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File("target/test-classes/BabyOngBak.jpg")));

    /*
     * Call the Uploader by using the Spring framework. For this to work you need to modify the existing "aws-s3.properties.template" file
     * by putting in your S3 access credentials and saving it somewhere in the classpath under the filename "aws-s3.properties".
     */
    boolean upload = JaideS3SpringService.getJaideS3Uploader().upload(null, "BabyOngBak.jpg", bin, JaideS3Uploader.CONTENT_TYPE_IMAGE_JPEG);
    Assert.assertTrue(upload);
  }

  /**
   * Runs the deletion via the Spring framework.
   */
  @Test
  public void testJaideS3SpringServiceDelete() {
    /*
     * Run the deletion by using the Spring framework. For this to work you need to modify the existing "aws-s3.properties.template" file
     * by putting in your S3 access credentials and saving it somewhere in the classpath under the filename "aws-s3.properties".
     */
    boolean delete = JaideS3SpringService.getJaideS3Uploader().delete("BabyOngBak.jpg");
    Assert.assertTrue(delete);
  }
}
