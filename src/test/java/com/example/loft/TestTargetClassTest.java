package com.example.loft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTargetClassTest {

    private static final Logger logger = LoggerFactory.getLogger(TestTargetClassTest.class);

    @Disabled("モックを注入していないため、実際にS3にアクセスしてしまう困ったテストの例")
    @Test
    void createBucketActual(){

        AmazonS3 client = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(new EndpointConfiguration("endpoint", "region"))
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("accessKey", "secretAccessKey")))
            .build();
        boolean result = new TestTargetClass(client).testTargetMethod("testBucketName");
        assertEquals(true, result);
    }

    @Test
    void create_bucket() {

        String bucketName = "testBucketName";
        String bucketLocation = "testBucketLocation";

        // define mock
        Bucket bucket = mock(Bucket.class);
        when(bucket.getName()).thenReturn(bucketName);
        AmazonS3 client = mock(AmazonS3.class);
        when(client.doesBucketExistV2(bucketName)).thenReturn(false);
        when(client.createBucket(bucketName)).thenReturn(bucket);
        when(client.getBucketLocation(bucketName)).thenReturn(bucketLocation);

        // test target
        TestTargetClass testTarget = new TestTargetClass(client);
        boolean result = testTarget.testTargetMethod(bucketName);

        // verify
        verify(client, times(1)).doesBucketExistV2(bucketName);
        verify(client, times(1)).createBucket(bucketName);
        verify(client, times(1)).getBucketLocation(bucketName);
        assertEquals(true, result);
    }

    @Test
    void not_create_bucket() {

        String bucketName = "testBucketName";

        // define mock
        Bucket bucket = mock(Bucket.class);
        when(bucket.getName()).thenReturn(bucketName);
        AmazonS3 client = mock(AmazonS3.class);
        when(client.doesBucketExistV2(bucketName)).thenReturn(true);

        // test target
        TestTargetClass testTarget = new TestTargetClass(client);
        boolean result = testTarget.testTargetMethod(bucketName);

        // verify
        verify(client, times(1)).doesBucketExistV2(bucketName);
        verify(client, times(0)).createBucket(bucketName);
        verify(client, times(0)).getBucketLocation(bucketName);
        assertEquals(false, result);
    }
}