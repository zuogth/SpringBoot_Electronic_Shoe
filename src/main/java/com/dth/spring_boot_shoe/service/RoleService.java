package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.entity.RoleEntity;

public interface RoleService {
    RoleEntity findByCode(String code);
}
