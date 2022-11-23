package com.example.roleservice.service;

import com.example.roleservice.model.Role;
import com.example.roleservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;


    @Transactional
    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public String deleteRole(int roleId) {
        roleRepository.deleteById(roleId);
        return "xoa thanh cong";
    }

    @Override
    public Role updateRole(Role role) {
        Role role1= roleRepository.saveAndFlush(role);
        return role1;
    }

    @Override
    public Role getRoleById(int id) {
        Role role= roleRepository.findById(id).get();
        return role;
    }

    @Override
    public List<Role> getListRole() {
        List<Role> roleList= roleRepository.findAll();
        return roleList;
    }
}
