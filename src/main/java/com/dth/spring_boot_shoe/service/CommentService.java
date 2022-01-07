package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.entity.CommentEntity;

import java.util.List;

public interface CommentService {

    List<CommentEntity> findByProductId(Long productId);
}
