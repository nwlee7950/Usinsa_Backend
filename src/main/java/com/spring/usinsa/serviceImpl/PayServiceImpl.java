package com.spring.usinsa.serviceImpl;

import com.spring.usinsa.dto.PaymentCancelDto;
import com.spring.usinsa.dto.PaymentDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.Payment;
import com.spring.usinsa.model.User;
import com.spring.usinsa.model.product.Product;
import com.spring.usinsa.repository.PayRepository;
import com.spring.usinsa.repository.ProductRepository;
import com.spring.usinsa.repository.UserRepository;
import com.spring.usinsa.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {
    @Value("${iamport.imp_key}")
    private String impKey;

    @Value("${iamport.imp_secret}")
    private String impSecret;

    private final ProductRepository productRepository;
    private final PayRepository payRepository;

    @Override
    public String getToken() throws Exception{
        String _token = "";
        String requestURL = "https://api.iamport.kr/users/getToken";
        String imp_key = URLEncoder.encode(impKey, "UTF-8");
        String imp_secret = URLEncoder.encode(impSecret, "UTF-8");
        try{
            String requestString = "";
            JSONObject json = new JSONObject();
            json.put("imp_key", imp_key);
            json.put("imp_secret", imp_secret);
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            OutputStream os= connection.getOutputStream();
            os.write(json.toString().getBytes());
            connection.connect();
            StringBuilder sb = new StringBuilder();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line = null;

                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                requestString = sb.toString();
            }
            os.flush();
            connection.disconnect();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(requestString);

            if((Long)jsonObj.get("code") == 0){
                JSONObject getToken = (JSONObject) jsonObj.get("response");
                log.info("getToken==>"+getToken.get("access_token"));
                _token = (String)getToken.get("access_token");
            }
        }catch(Exception e){
            e.printStackTrace();
            _token = "";
        }
        return _token;
    }

    @Override
    @Transactional
    public String savePayment(User user, PaymentDto.Request paymentDto) throws Exception {
        String merchantUid = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyyMMdd");
            Date time = new Date();
            String result = dateFormat.format(time);
            merchantUid = "ORD" + result + "-" + UUID.randomUUID().toString();

            Product product = productRepository.findById(paymentDto.getProductId())
                    .orElseThrow(() -> new ApiException(ApiErrorCode.PRODUCT_NOT_FOUND));
            paymentDto.setStatus("ready");
            paymentDto.setMerchantUid(merchantUid);
            Payment payment = paymentDto.toPaymentEntity(product, user);
            payRepository.save(payment);
        } catch(Exception e){
            e.printStackTrace();
        }
        return merchantUid;
    }

    @Override
    @Transactional
    public PaymentDto.Response updateByMerchantUid(PaymentDto.UpdateRequest paymentDto) throws Exception {
        Payment payment = payRepository.findByMerchantUid(paymentDto.getMerchantUid())
                .orElseThrow(() -> new ApiException(ApiErrorCode.PAYMENT_NOT_FOUND));

        payment.setImpUid(paymentDto.getImpUid());
        payment.setStatus("paid");
        return PaymentDto.Response.toPaymentDtoResponse(payment);
    }

    @Override
    @Transactional
    public void cancelPayment(PaymentCancelDto.Request paymentCancelDto) throws Exception {
        String _token = getToken();
        String requestURL = "https://api.iamport.kr/payments/cancel";
        try{
            String requestString = "";
            JSONObject json = new JSONObject();
            json.put("imp_uid", paymentCancelDto.getImp_uid());
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", _token);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            OutputStream os= connection.getOutputStream();
            os.write(json.toString().getBytes());
            connection.connect();
            StringBuilder sb = new StringBuilder();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line = null;

                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                requestString = sb.toString();
            }
            os.flush();
            connection.disconnect();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(requestString);

            if((Long)jsonObj.get("code") == 0){
                JSONObject jsonResponse = (JSONObject) jsonObj.get("response");
                log.info("imp_uid==>"+jsonResponse.get("imp_uid"));
                String imp_uid = (String)jsonResponse.get("imp_uid");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
