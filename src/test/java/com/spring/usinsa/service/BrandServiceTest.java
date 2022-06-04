package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.BrandBySubCategoryDto;
import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.model.product.Brand;
import com.spring.usinsa.repository.BrandRepository;
import com.spring.usinsa.serviceImpl.product.BrandServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.spring.usinsa.serviceImpl.MinioService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)     // JUnit5 일때 사용
public class BrandServiceTest {

    @InjectMocks
    private BrandServiceImpl brandService;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private MinioService minioService;

    private static String title = "나이키1";
    private static String enTitle = "nike1";
    private static String info = " 브랜드 소개글1 ";
    private static String image = "image1.png";

    @Test
    void save() throws Exception {
        //given
        Brand brand = buildBrand();
        doReturn(brand).when(brandRepository).save(any(Brand.class));
        doReturn(image).when(minioService).upsertFile(any(), anyString(), any());

        //when
        final BrandDto.Response savedBrand = brandService.save(buildBrandRequestDto());

        //then
        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getTitle()).isEqualTo(title);
        assertThat(savedBrand.getEnTitle()).isEqualTo(enTitle);
        assertThat(savedBrand.getImage()).isEqualTo(image);
    }

    @Test
    void findByTitleContains(){
        //given
        List<Brand> brands = new ArrayList<>();
        brands.add(buildBrand());
        doReturn(brands).when(brandRepository).findByTitleContainsOrEnTitleContains(anyString(), anyString());

        //when
        List<BrandDto.Response> findBrandDtoList = brandService.findByTitleContains(title);

        //then
        assertThat(findBrandDtoList).isNotNull();
        assertThat(findBrandDtoList.size()).isGreaterThan(0);
        assertThat(findBrandDtoList.get(0).getTitle()).isEqualTo(title);
    }

    @Test
    void findBySubCategory(){
        //given
        doReturn(bulidBrandBySubCategoryDtoList()).when(brandRepository).findBrandListBySubCategory(anyLong());

        //when
        List<BrandBySubCategoryDto> result = brandService.findBySubCategory(1l);

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isGreaterThan(0);
        assertThat(result.get(0).getProductTotal()).isEqualTo(10l);
    }

    private Brand buildBrand(){
        return  Brand.builder()
                .id(1L)
                .title(title)
                .enTitle(enTitle)
                .image(image)
                .info(info).build();
    }

    private BrandDto.Request buildBrandRequestDto(){
        BrandDto.Request brandDto = new BrandDto.Request();
        brandDto.setTitle(title);
        brandDto.setEnTitle(enTitle);
        brandDto.setInfo(info);
        brandDto.setImage(new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        });
        return brandDto;
    }

    private List<BrandBySubCategoryDto> bulidBrandBySubCategoryDtoList(){
        List<BrandBySubCategoryDto> result = new ArrayList<>();
        result.add(new BrandBySubCategoryDto(1l, "usinsa", 10l));

        return result;
    }
}
