package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.ProductDetailDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Product;
import com.spring.usinsa.model.product.ProductDetail;
import com.spring.usinsa.repository.ProductDetailRepository;
import com.spring.usinsa.service.ProductDetailService;
import com.spring.usinsa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.usinsa.serviceImpl.MinioService;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final ProductService productService;
    private final MinioService minioService;

    private final String PRODUCT_DETAIL ="product_detail/";

    @Override
    public ProductDetail save(ProductDetailDto.Request productDetailDto) throws Exception {
        Product product = productService.findById(productDetailDto.getProductId());
        String uploadedImage = minioService.upsertFile(null, PRODUCT_DETAIL, productDetailDto.getContentImage());

        ProductDetail ProductDetail = productDetailDto.toProductDetailEntity(product, uploadedImage);

        return productDetailRepository.save(ProductDetail);
    }

    @Override
    public ProductDetail findById(long id) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.PRODUCT_NOT_FOUND));

        return productDetail;
    }

    @Override
    public ProductDetail updateContentImageById(ProductDetailDto.Request productDetailDto) throws Exception {
        ProductDetail productDetail = productDetailRepository.findById(productDetailDto.getProductId())
                .orElseThrow(() -> new ApiException(ApiErrorCode.PRODUCT_NOT_FOUND));

        String uploadedImage = minioService.upsertFile(null, PRODUCT_DETAIL, productDetailDto.getContentImage());
        minioService.removeFile(productDetail.getContentImage());

        productDetail.setContentImage(uploadedImage);

        return productDetail;
    }
}
