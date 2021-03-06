package com.dth.spring_boot_shoe.constant;

import com.dth.spring_boot_shoe.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

public class MessageAdmin {
    public static ApiException UPDATED_SUCCESS=new ApiException("Cập nhật thành công!", HttpStatus.OK, LocalDate.now());
    public static ApiException CREATED_SUCCESS=new ApiException("Thêm mới thành công!", HttpStatus.CREATED, LocalDate.now());
    public static ApiException DELETED_SUCCESS=new ApiException("Xóa thành công!",HttpStatus.OK,LocalDate.now());
    public static ApiException UNAUTHORIZED=new ApiException("Bạn chưa đăng nhập!", HttpStatus.UNAUTHORIZED, LocalDate.now());
    public static ApiException LOCKED=new ApiException("Sản phẩm đã được khóa!",HttpStatus.OK,LocalDate.now());
    public static ApiException UNLOCKED=new ApiException("Sản phẩm đã được mở khóa!",HttpStatus.OK,LocalDate.now());
    public static ApiException ZERO=new ApiException("Sản phẩm đã được xóa!",HttpStatus.NOT_FOUND,LocalDate.now());
    public static ApiException SAVE_DIS=new ApiException("Danh sách áp dụng khuyến mãi đã được cập nhật!",HttpStatus.NOT_FOUND,LocalDate.now());
}
