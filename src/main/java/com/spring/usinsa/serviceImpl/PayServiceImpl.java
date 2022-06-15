package com.spring.usinsa.serviceImpl;

import com.spring.usinsa.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {
    @Value("${iamport.imp_key}")
    private String impKey;

    @Value("${iamport.imp_secret}")
    private String impSecret;


    @Override
    public String getToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
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
}
