package com.dth.spring_boot_shoe.api;

import com.dth.spring_boot_shoe.constant.MessageAdmin;
import com.dth.spring_boot_shoe.dto.BillDTO;
import com.dth.spring_boot_shoe.dto.CommentDTO;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.exception.RequestException;
import com.dth.spring_boot_shoe.request.ChangePWRequest;
import com.dth.spring_boot_shoe.request.InfoRequest;
import com.dth.spring_boot_shoe.service.BillService;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.transform.OutputKeys;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InfoAPI {

    private final UserService userService;
    private final BillService billService;

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

    @PutMapping("/info/bill/{id}")
    public ResponseEntity<?> cancelBill(@PathVariable("id") String id,
                                        HttpServletRequest request){
        billService.updateBill(Long.valueOf(id),"cancel",request);
        return new ResponseEntity<>(MessageAdmin.UPDATED_SUCCESS,HttpStatus.OK);
    }

    @GetMapping("/info/cancelBill")
    public ResponseEntity<?> getCancelBill(){
        UserEntity user=userService.profile();
        List<BillDTO> cancelOrder=billService.findAllCancelBillByUser(user.getId());
        return new ResponseEntity<>(cancelOrder,HttpStatus.OK);
    }
}
