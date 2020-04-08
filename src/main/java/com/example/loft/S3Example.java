package com.example.loft;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
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

    private AmazonS3 client = null;

    public S3Example(AmazonS3 client) {
        this.client = client;
	}

	public boolean createBucket(String bucketName){
        if ( this.client == null ){
            client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new EndpointConfiguration(endpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretAccessKey)))
                .build();
        }
        if (client.doesBucketExistV2(bucketName)) {
            logger.info("doesBucketExistV2");
            return false;
        }
        //Bucket bucket = client.createBucket(new CreateBucketRequest(bucketName));
        Bucket bucket = client.createBucket(bucketName);
        logger.info(bucket.toString());

        //return client.getBucketLocation(new GetBucketLocationRequest(bucketName));
        logger.info(client.getBucketLocation(bucketName));
        return true;
    }
}