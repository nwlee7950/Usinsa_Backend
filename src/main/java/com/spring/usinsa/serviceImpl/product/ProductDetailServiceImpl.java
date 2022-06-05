package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.ProductDetailDto;
import com.spring.usinsa.dto.product.ProductImageDto;
import com.spring.usinsa.dto.product.ProductSizeDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.ProductDetail;
import com.spring.usinsa.repository.ProductDetailRepository;
import com.spring.usinsa.service.ProductDetailService;
import com.spring.usinsa.service.ProductImageService;
import com.spring.usinsa.service.ProductSizeService;
import com.spring.usinsa.serviceImpl.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final ProductImageService productImageService;
    private final ProductSizeService productSizeService;
    private final MinioService minioService;

    private final String PRODUCT_DETAIL = "product_detail/";

    @Override
    public ProductDetail save(ProductDetailDto.Request productDetailDto) throws Exception {

        String uploadedImage = minioService.upsertFile(null, PRODUCT_DETAIL, productDetailDto.getContentImage());

        ProductDetail productDetail = productDetailRepository.save(productDetailDto.toProductDetailEntity(uploadedImage));

        for (ProductImageDto.Request productImage : productDetailDto.getProductImageList()) {
            productImageService.save(productImage.getImage(), productDetail.getId());
        }

        for (ProductSizeDto productSize : productDetailDto.getProductSizeList()) {
            productSizeService.save(productSize, productDetail.getId());
        }

        return productDetail;
    }

    @Override
    public ProductDetailDto.Response findById(Long id) {
        ProductDetail productDetail = productDetailRepository.findByProductId(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.PRODUCT_NOT_FOUND));

        return ProductDetailDto.Response.toProductDetailDtoResponse(productDetail);
    }

    @Override
    public void deleteById(Long id) {
        productImageService.deleteByProductDetailId(id);

        productDetailRepository.deleteById(id);
    }

    @Override
    public void deleteByProductId(Long id) {
        ProductDetail productDetail = productDetailRepository.findByProductId(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.PRODUCT_NOT_FOUND));

        productImageService.deleteByProductDetailId(productDetail.getId());

        productDetailRepository.deleteById(productDetail.getId());
    }
}
