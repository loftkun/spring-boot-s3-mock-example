package com.example.loft;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTargetClass {

    private static final Logger logger = LoggerFactory.getLogger(TestTargetClass.class);

    private AmazonS3 client = null;

    public TestTargetClass(AmazonS3 client) {
        this.client = client;
	}

	public boolean testTargetMethod(String bucketName){
        if (client.doesBucketExistV2(bucketName)) {
            logger.info("doesBucketExistV2");
            return false;
        }
        Bucket bucket = client.createBucket(bucketName);
        logger.info(bucket.toString());
        logger.info(client.getBucketLocation(bucketName));
        return true;
    }
}