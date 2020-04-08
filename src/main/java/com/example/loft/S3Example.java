package com.example.loft;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class S3Example {

    private static final String endpoint = "http://192.168.3.2:9000";
    private static final String region = "ap-northeast-1";
    private static final String accessKey = "minio";
    private static final String secretAccessKey = "miniotest";

    private static final Logger logger = LoggerFactory.getLogger(S3Example.class);

    public void createBucket(String bucketName){
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new EndpointConfiguration(endpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretAccessKey)))
                .build();
        if (s3Client.doesBucketExistV2(bucketName)) {
            logger.info("doesBucketExistV2");
            return;
        }
        s3Client.createBucket(new CreateBucketRequest(bucketName));
        logger.info("Bucket location: " + s3Client.getBucketLocation(new GetBucketLocationRequest(bucketName)));
    }
}