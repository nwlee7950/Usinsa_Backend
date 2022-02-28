//package com.spring.usinsa.controller.api.v1;
//
//import com.spring.usinsa.exception.ApiErrorCode;
//import com.spring.usinsa.exception.ApiException;
//import com.spring.usinsa.serviceImpl.ApiResponseService;
//import com.spring.usinsa.serviceImpl.MinioService;
//import io.minio.StatObjectResponse;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.compress.utils.IOUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//
//@Api(tags = {"5. Get Image From Minio "})
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/v1/images")
//public class ImageRestController {
//
//    private final MinioService minioService;
//    private final ApiResponseService apiResponseService;
//
//    @ApiOperation(value = "이미지 출력", notes = "이미지 출력")
//    @GetMapping
//    public byte[] getImage(@ApiParam(value = "사용자 프로필 사진", required = true)
//                                                       @RequestParam String imagePath, HttpServletResponse response) throws Exception {
//
//        System.out.println("imagePath = " + imagePath);
//        if(imagePath == null || "".equals(imagePath)) {
//            return null;
//        }
//        // 사진 존재 유무 확인
//        StatObjectResponse stat = minioService.getObjectStat(imagePath);
//        if (stat == null) {
//            throw new ApiException(ApiErrorCode.MINIO_INVALID_GET_REQUEST);
//        }
//
//        // response 헤더 작업
//        response.setContentType(stat.contentType());
//        response.setCharacterEncoding("UTF-8");
//
//        // response 의 outputStream
//        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
//        // 이미지 파일
//        BufferedInputStream bis = new BufferedInputStream(minioService.getFile(imagePath));
//
//        // response 의 OutputStream 에 가져온 이미지 파일을 write.
//        IOUtils.copy(bis, bos);
//
//        return IOUtils.toByteArray(bis);
//    }
//}
