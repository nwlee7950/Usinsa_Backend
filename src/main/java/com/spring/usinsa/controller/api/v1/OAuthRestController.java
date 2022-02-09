package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.oauth2.GoogleProfile;
import com.spring.usinsa.dto.oauth2.KakaoProfile;
import com.spring.usinsa.dto.oauth2.KakaoTokenDto;
import com.spring.usinsa.dto.oauth2.NaverProfile;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.Social;
import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.OAuthService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"1. SNS Sign In / Up - Kakao, Naver, Google"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/oauth")
public class OAuthRestController {

    private final ApiResponseService apiResponseService;
    private final OAuthService oAuthService;

    // 사실 프론트가 인가코드를 통해 OAuth Access Token 을 얻고 REST API 서버는 이 토큰을 이용해서 사용자 정보만 가져오면 되지만
    // 인가코드를 얻는 과정을 보여주기 위해 카카오 로그인만 인가코드 - Oauth Access Token - 사용자 정보 모든 과정을 백엔드에서 진행
    @ApiOperation(value = "카카오 로그인 리다이렉트", notes = "카카오 로그인 성공 후 리다이렉트되는 URL 입니다.")
    @GetMapping("/kakao/login/redirect")
    public SingleResponse<Object> authorization(@RequestParam String code) {

        // 인가코드로 토큰 받아오기
        KakaoTokenDto kakaoTokenDto = oAuthService.getKakaoTokenInfo(code);

        // 토큰으로 사용자 정보 가져오기
        KakaoProfile kakaoProfile = oAuthService.getKakaoProfile(kakaoTokenDto.getAccess_token());

        if(kakaoProfile == null) {
            throw new ApiException(ApiErrorCode.OAUTH_ERROR);
        }

        // 사용자 구분 (신규 회원인지, 기존 회원인지)
        Object result = oAuthService.checkProfile(Social.SOCIAL_KAKAO.getValue(),
                kakaoProfile.getId(),
                kakaoProfile.getKakao_account().getEmail(),
                kakaoProfile);

        return apiResponseService.getSingleResult(result);
    }

    // 네이버 로그인 성공 시 리다이렉트
    @ApiOperation(value = "네이버 로그인 리다이렉트", notes = "네이버 로그인 성공 후 리다이렉트되는 URL 입니다.")
    @GetMapping(value = "/naver/login/redirect")
    public SingleResponse<Object> redirectNaverLogin(@RequestParam String accessToken) {

        // 토큰으로 사용자 정보 가져오기
        NaverProfile naverProfile = oAuthService.getNaverProfile(accessToken);

        if(naverProfile == null) {
            throw new ApiException(ApiErrorCode.OAUTH_ERROR);
        }

        // 사용자 구분 (신규 회원인지, 기존 회원인지)
        Object result = oAuthService.checkProfile(Social.SOCIAL_NAVER.getValue(),
                naverProfile.getResponse().getId(),
                naverProfile.getResponse().getEmail(),
                naverProfile);

        return apiResponseService.getSingleResult(result);
    }
    
    // 구글 로그인 창 호출
    @ApiOperation(value = "구글 로그인창 호출", notes = "구글 로그인 창을 호출합니다.")
    @GetMapping("/google/login")
    public ResponseEntity<Object> moveGoogleInitUrl() {
        return oAuthService.getGoogleLoginForm();
    }

    
    // 구글 로그인 성공 시 리다이렉트
    @ApiOperation(value = "구글 로그인 리다이렉트", notes = "구글 로그인 성공 후 리다이렉트되는 URL 입니다.")
    @GetMapping(value = "/google/login/redirect")
    public SingleResponse<Object> redirectGoogleLogin(@RequestParam String idToken) {
        Object result = null;

        try {
            // 인가 코드로 토큰 발급 후, 토큰으로 사용자 정보 가져오기
            GoogleProfile googleProfile = oAuthService.getGoogleProfile(idToken);

            // 사용자 구분 (신규 회원인지, 기존 회원인지)
            result = oAuthService.checkProfile(Social.SOCIAL_GOOGLE.getValue(),
                    googleProfile.getSub(),
                    googleProfile.getEmail(),
                    googleProfile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return apiResponseService.getSingleResult(result);
    }
}
