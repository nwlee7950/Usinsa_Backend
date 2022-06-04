package com.spring.usinsa.serviceImpl;

import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String BUCKET;

    public void uploadFile(String filePath, String extension, InputStream inputStream) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(BUCKET)
                    .object(filePath)
                    .stream(inputStream, -1, 10485760)
                    .contentType(extension)
                    .build());
        } catch (Exception e) {
            throw new ApiException(ApiErrorCode.MINIO_INVALID_UPLOAD_REQUEST);
        }
    }

    public InputStream getFile(String filePath) {
        InputStream file = null;
        try {
            InputStream obj = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(BUCKET)
                    .object(filePath)
                    .build());
            file = obj;
        } catch (Exception e) {
            log.error("getFile - 해당 경로의 파일 다운로드에 실패하였습니다.");
//            throw new ApiException(ApiErrorCode.MINIO_INVALID_GET_REQUEST);
        }
        return file;
    }

    public StatObjectResponse getObjectStat(String filePath) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        StatObjectResponse statObject = null;
        try {
            statObject = minioClient.statObject(StatObjectArgs.builder()
                            .bucket(BUCKET)
                            .object(filePath)
                            .build());

        } catch (Exception e) {
            log.error("getObjectStat - 해당 경로에 파일이 존재하지 않습니다.");
//            throw new ApiException(ApiErrorCode.MINIO_INVALID_GET_REQUEST);
        }
        return statObject;
    }

    public void removeFile(String filePath) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(BUCKET)
                    .object(filePath)
                    .build());
        } catch (Exception e) {
            log.error("removeFile - 파일 삭제에 실패하였습니다.");
//            throw new ApiException(ApiErrorCode.MINIO_INVALID_REMOVE_REQUEST);
        }
    }

    public void validateContentType(String contentType) {
        if(!("image/png".equals(contentType) || "image/jpg".equals(contentType) || "image/jpeg".equals(contentType))) {
            throw new ApiException(ApiErrorCode.MINIO_INVALID_FILE_TYPE);
        }

    }

    public String upsertFile(String existImage, String folder, MultipartFile multipartFile) throws Exception {

        // 업로드한 File이 없을 때
        if(multipartFile.isEmpty())
            return "";

        // 업로드 파일의 ContentType
        String contentType = multipartFile.getContentType();

        // 확장자 Validation
        validateContentType(contentType);
        // DB 에 이미 이미지 값이 있을 때
        if(existImage != null) {
            // MINIO 에도 해당 이미지가 있다면
            if(getObjectStat(existImage) != null) {
                // 기존 이미지 삭제
                removeFile(existImage);
            }
        }

        // 임의의 코드 생성 및 확장자 추가 (업로드할 이미지 경로, 파일명으로 사용)
        String image = folder + UUID.randomUUID().toString();

        // MINIO 에 중복된 파일명이 있을 경우, 중복이 발생하지 않을 때까지 재생성
/*        while(getObjectStat(image) != null) {
            image = folder + UUID.randomUUID().toString();
        }*/

        // 새로운 프로필 이미지 업로드
        uploadFile(image, contentType, multipartFile.getInputStream());

        return image;
    }
}
