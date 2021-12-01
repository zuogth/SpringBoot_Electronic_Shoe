package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.entity.RoleEntity;
import com.dth.spring_boot_shoe.repository.RoleRepository;
import com.dth.spring_boot_shoe.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public RoleEntity findByCode(String code) {
        return roleRepository.findByCode(code).get();
    }
}
