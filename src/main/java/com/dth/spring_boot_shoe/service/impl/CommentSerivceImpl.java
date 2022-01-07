package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.entity.CommentEntity;
import com.dth.spring_boot_shoe.repository.CommentRepository;
import com.dth.spring_boot_shoe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentSerivceImpl implements CommentService{

    private final CommentRepository commentRepository;

    @Override
    public List<CommentEntity> findByProductId(Long productId) {
        return commentRepository.findByProductId(productId);
    }
}
