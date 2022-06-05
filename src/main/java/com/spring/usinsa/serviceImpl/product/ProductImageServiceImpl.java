package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.ProductImageDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.ProductDetail;
import com.spring.usinsa.model.product.ProductImage;
import com.spring.usinsa.repository.ProductImageRepository;
import com.spring.usinsa.service.ProductImageService;
import com.spring.usinsa.serviceImpl.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    private final MinioService minioService;

    private final String PRODUCT_SUBIMAGE_FOLDER ="product/sub/";

    @Override
    public void save(MultipartFile image, Long productDetailId) {
        try{
            String uploadedImage = minioService.upsertFile(null, PRODUCT_SUBIMAGE_FOLDER, image);
            productImageRepository.save(ProductImage.builder()
                    .image(uploadedImage).productDetailId(productDetailId).build());
        }catch (Exception e) {
            new ApiException(ApiErrorCode.MINIO_INVALID_UPLOAD_REQUEST);
        }
    }

    @Override
    public void deleteByProductDetailId(Long productDetailId) {
        productImageRepository.deleteByProductDetailId(productDetailId);
    }
}
