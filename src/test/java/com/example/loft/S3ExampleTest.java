package com.example.loft;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class S3ExampleTest {

    private static final Logger logger = LoggerFactory.getLogger(S3ExampleTest.class);

    //@Test
    //void createBucketActual(){
    //    String bucketName = "test4";
    //    boolean result = new S3Example(null).createBucket(bucketName);
    //}

    @Test
    void createBucketTest() {

        String bucketName = "test1";
        String bucketLocation = "test-location";

        Bucket bucket = mock(Bucket.class);
        when(bucket.getName()).thenReturn(bucketName);
        AmazonS3 client = mock(AmazonS3.class);
        when(client.doesBucketExistV2(bucketName)).thenReturn(false);
        when(client.createBucket(bucketName)).thenReturn(bucket);
        when(client.getBucketLocation(bucketName)).thenReturn(bucketLocation);

        S3Example s3Example = new S3Example(client);
        boolean result = s3Example.createBucket(bucketName);
        verify(client, times(1)).doesBucketExistV2(bucketName);
        verify(client, times(1)).createBucket(bucketName);
        verify(client, times(1)).getBucketLocation(bucketName);
        assertEquals(true, result);
    }

    @Test
    void not_createBucketTest() {

        String bucketName = "test1";
        String bucketLocation = "test-location";

        Bucket bucket = mock(Bucket.class);
        when(bucket.getName()).thenReturn(bucketName);
        AmazonS3 client = mock(AmazonS3.class);
        when(client.doesBucketExistV2(bucketName)).thenReturn(true);
        when(client.createBucket(bucketName)).thenReturn(bucket);
        when(client.getBucketLocation(bucketName)).thenReturn(bucketLocation);

        S3Example s3Example = new S3Example(client);
        boolean result = s3Example.createBucket(bucketName);
        verify(client, times(1)).doesBucketExistV2(bucketName);
        verify(client, times(0)).createBucket(bucketName);
        verify(client, times(0)).getBucketLocation(bucketName);
        assertEquals(false, result);
    }
}