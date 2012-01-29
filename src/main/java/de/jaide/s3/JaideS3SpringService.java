/**
 * 
 */
package de.jaide.s3;

import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Initializes the Spring Context and provides static methods for returning services.
 * 
 * @author Rias A. Sherzad (rias@sherzad.com)
 */
public class JaideS3SpringService {
  /**
   * Initializes the Spring Application Context when this class is loaded.
   */
  private static GenericXmlApplicationContext applicationContext = null;

  private static String UPLOADER_SERVICE = "de_jaide_s3_JaideS3Uploader";

  /**
   * Returns the JaideS3Uploader Spring bean.
   * 
   * @param The JaideS3Uploader Spring bean.
   */
  public static JaideS3Uploader getJaideS3Uploader() {
    /*
     * Lazily initialized as we don't know if the Spring service is even used.
     */
    if (applicationContext == null)
      applicationContext = new GenericXmlApplicationContext("applicationContext.xml");

    return (JaideS3Uploader) applicationContext.getBean(UPLOADER_SERVICE);
  }
}
