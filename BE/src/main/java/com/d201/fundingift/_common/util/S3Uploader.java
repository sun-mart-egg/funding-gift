package com.d201.fundingift._common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.d201.fundingift._common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static com.d201.fundingift._common.response.ErrorType.IMAGE_FILE_UPLOAD_FAIL;
import static com.d201.fundingift._common.response.ErrorType.INVALID_FILE_FORMAT;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile) throws IOException {
        // 이미지 형식의 파일인지 확인
        if(!Objects.requireNonNull(multipartFile.getContentType()).contains("image")) {
            throw new CustomException(INVALID_FILE_FORMAT);
        }

        // 새로운 파일 이름 생성 (중복 방지)
        String filename = createFilename(multipartFile);

        // 파일 업로드
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3.putObject(bucket, filename, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new CustomException(IMAGE_FILE_UPLOAD_FAIL);
        }

        return amazonS3.getUrl(bucket, filename).toString();
    }

    private String createFilename(MultipartFile multipartFile) {
        return UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
    }

}
