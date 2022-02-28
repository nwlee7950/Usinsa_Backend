package com.spring.usinsa.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.url}")
    private String URL;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;
    @Value("${minio.bucket}")
    private String BUCKET;

    @Bean
    public MinioClient generateMinioClient() {
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            // port 443
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(URL)
                            .credentials(ACCESS_KEY, SECRET_KEY)
                            .build();

            // Make 'usinsa' bucket if not exist.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET).build());
            if (!found) {
                // Make a new bucket called 'usinsa'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET).build());
            } else {
                System.out.println("Bucket 'usinsa' already exists.");
            }

            return minioClient;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
