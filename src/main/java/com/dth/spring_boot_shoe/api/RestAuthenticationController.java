package com.dth.spring_boot_shoe.api;

import com.dth.spring_boot_shoe.constant.MessageAdmin;
import com.dth.spring_boot_shoe.exception.ApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/api")
public class RestAuthenticationController {
    public HttpHeaders getJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }

    @GetMapping("/login-page")
    public ResponseEntity<ApiException> apiLoginPage() {
        return new ResponseEntity<>(MessageAdmin.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/login-fail")
    public ResponseEntity<String> apiLoginFail(){
        return new ResponseEntity<>("{\"success\" : false, \"message\" : \"authentication-failure\"}",getJsonHeaders(),HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/default")
    public ResponseEntity<String> apiDefault(){
        return new ResponseEntity<>("Login thanh cong",getJsonHeaders(),HttpStatus.OK);
    }
}
