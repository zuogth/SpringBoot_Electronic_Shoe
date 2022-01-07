package com.dth.spring_boot_shoe.api;

import com.dth.spring_boot_shoe.dto.CommentDTO;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.exception.RequestException;
import com.dth.spring_boot_shoe.request.ChangePWRequest;
import com.dth.spring_boot_shoe.request.InfoRequest;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.transform.OutputKeys;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InfoAPI {

    private final UserService userService;

    @GetMapping("/info/detail")
    public ResponseEntity<UserEntity> detail(){
        UserEntity user=userService.profileDetail();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/info/detail")
    public Map<String,String> update(@Valid @RequestBody InfoRequest request){
        userService.update(request);
        HashMap<String,String> map=new HashMap<>();
        map.put("success","Cập nhật thành công, tải lại trang sau 2s");
        return map;
    }

    @PutMapping("/info/password")
    public Map<String,String> change(@Valid @RequestBody ChangePWRequest request){
        return userService.changePass(request);
    }

    @PostMapping("/info/comment")
    public Map<String,String> addComment(@Valid @RequestBody CommentDTO commentDTO){
        return userService.createComment(commentDTO);
    }
}
