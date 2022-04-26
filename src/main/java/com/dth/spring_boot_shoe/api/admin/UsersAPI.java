package com.dth.spring_boot_shoe.api.admin;

import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UsersAPI {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAll(@RequestParam("page") int page){
        return new ResponseEntity<>(userService.findAll(page),HttpStatus.OK);
    }
}
