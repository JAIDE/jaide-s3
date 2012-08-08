What is this?
-------------

This is a very simple wrapper around the Amazon S3 library. With this code you can:

* Easily upload/delete files to/from Amazon S3 with two lines of code... and one of those two lines is the import-package line at the top of your .java file :-)
* Make use of Spring as the library is Spring-enabled - but it's not a must. You're free to use the direct uploader OR the Spring service, whatever you prefer.
* You can now (version 1.1) specify the content type of the uploaded file

Directly uploading a file
-------------------------

Here's how to upload a file without using Spring (if you're making use of the Apache commons-io library):

    import de.jaide.s3.JaideS3Uploader;
    ...
    new JaideS3Uploader("MyS3AccessKey", "MyS3SecretKey", "my-s3-bucket-name").upload("my-file-path", "my-filename.ext",
      new ByteArrayInputStream(FileUtils.readFileToByteArray(new File("/some/path/my-filename.ext"))), JaideS3Uploader.CONTENT_TYPE_APPLICATION_OCTETSTREAM);

Uploading a file via Spring
---------------------------

* Copy and save the "aws-s3.properties.template" as "aws-s3.properties".
* In that file enter your Amazon S3 access credentials.
* Place that file somewhere in the classpath or just put it where the .template-file is.

Here's how to upload a file using Spring (if you're making use of the Apache commons-io library):

    import de.jaide.s3.JaideS3Uploader;
    ...
    JaideS3SpringService.getJaideS3Uploader().upload("my-file-path", "my-filename.ext",
      new ByteArrayInputStream(FileUtils.readFileToByteArray(new File("/some/path/my-filename.ext"))), JaideS3Uploader.CONTENT_TYPE_APPLICATION_OCTETSTREAM));

Integrating into your code
--------------------------

* You will need Java 6 and Maven 3 to build the code
* Choose one of the above two methods to use this library
* Build your code with Maven
* You will see that two tests fail - they fail because you didn't provide your S3 access credentials
* Put them where the two tests, at the top of the files, tell you to put them
* Build again, this time things should be fine
* Put the resulting .jar file into your application's library folder
