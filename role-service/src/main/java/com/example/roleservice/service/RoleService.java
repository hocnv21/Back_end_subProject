package com.example.roleservice.service;

import com.example.roleservice.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface RoleService {
    public Role addRole(Role role);

    public String deleteRole(int roleId);

    public Role updateRole(Role role);

    public Role getRoleById(int id);

    public List<Role> getListRole();
}
