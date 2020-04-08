package com.example.loft;

import org.junit.jupiter.api.Test;

public class S3ExampleText {

    @Test
    void createBucketTest(){
        String bucketName = "test";
        new S3Example().createBucket(bucketName);
    }
}