/**
 * 
 */
package de.jaide.s3;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * Handles uploading to the S3 server.
 * This class may either be instantiated programmatically or via a Spring configuration.
 * 
 * @author Rias A. Sherzad (rias@sherzad.com)
 */
public class JaideS3Uploader {

  /**
   * Connection details to Amazon S3.
   */
  private AmazonS3 amazonS3;
  private String accessKey;
  private String secretKey;
  private String bucket;

  /**
   * Make sure the connection is established the first time a call is attempted.
   */
  private boolean refreshConnection = true;

  /**
   * Connects to S3 with the given accessKey and secretKey and also sets the default bucket to upload stuff to.
   * 
   * @param accessKey The access key to connect to S3 with.
   * @param secretKey The secret key to connect to S3 with.
   * @param bucket The default bucket to use for default.
   */
  public JaideS3Uploader(String accessKey, String secretKey, String bucket) {
    setAccessKey(accessKey);
    setSecretKey(secretKey);
    setBucket(bucket);
    doRefreshConnection();
  }

  /**
   * Refresh the connection to S3.
   */
  private void doRefreshConnection() {
    /*
     * Refresh the connection to S3.
     */
    amazonS3 = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));
    setRefreshConnection(false);
  }

  /**
   * Uploads the given resource to the pre-specified bucket.
   * 
   * @param path The path where to store the resource inside the bucket.
   * @param filename The name to store the resource as.
   * @param file The resource to store inside the bucket.
   * @return True, if the upload succeeded.
   */
  public boolean upload(String path, String filename, InputStream file) {
    return upload(bucket, path, filename, file);
  }

  /**
   * Uploads the given resource to the specified bucket. Does not replace the pre-specified bucket as this is a one-time operation.
   * 
   * @param bucket The target bucket inside S3. Needs to exist.
   * @param path The path where to store the resource inside the bucket.
   * @param filename The name to store the resource as.
   * @param file The resource to store inside the bucket.
   * @return True, if the upload succeeded.
   */
  public boolean upload(String bucket, String path, String filename, InputStream file) {
    /*
     * Refresh the connection, if necessary.
     */
    if (isRefreshConnection())
      doRefreshConnection();

    /*
     * The content length HAS to be set because we're not providing a File to the PutObjectRequest but a stream.
     * Uploading stuff to S3 requires the amount of data to be specified before the upload. If that is not done then we'd have to buffer the
     * entire stream (=File) before the upload. Setting the content length circumvents that.
     */
    ObjectMetadata meta = new ObjectMetadata();
    meta.setContentLength(((ByteArrayInputStream) file).available());

    /*
     * Proceed with the actual upload.
     */
    try {
      amazonS3.putObject(new PutObjectRequest(bucket, sanitizePath(path) + filename, file, meta));
      return true;
    } catch (AmazonServiceException ase) {
      ase.printStackTrace();
    } catch (AmazonClientException ace) {
      ace.printStackTrace();
    }

    return false;
  }

  /**
   * Sanitizes the path and makes sure there is no "/" at the front and there is a "/" at the end of the path.
   * 
   * @param path The path to clean and fix.
   * @return The cleaned up path.
   */
  private String sanitizePath(String path) {
    if (path.startsWith("/"))
      path = path.substring(1);

    return path + "/";
  }

  /**
   * @param accessKey the accessKey to set
   */
  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
    setRefreshConnection(true);
  }

  /**
   * @param secretKey the secretKey to set
   */
  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
    setRefreshConnection(true);
  }

  /**
   * @param bucket the bucket to set
   */
  public void setBucket(String bucket) {
    this.bucket = bucket;
    setRefreshConnection(true);
  }

  /**
   * @return the refreshConnection
   */
  public boolean isRefreshConnection() {
    return refreshConnection;
  }

  /**
   * @param refreshConnection the refreshConnection to set
   */
  public void setRefreshConnection(boolean refreshConnection) {
    this.refreshConnection = refreshConnection;
  }
}
