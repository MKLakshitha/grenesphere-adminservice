package com.greensphereadminportalservice.service;


import com.greensphereadminportalservice.model.usersModel;
import com.greensphereadminportalservice.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements userService {

    @Autowired
    userRepository userRepo;

    @Override
    public usersModel insert(usersModel user) {
        if (userRepo.existsById(user.getId())) {
            throw new RuntimeException("User with ID " + user.getId() + " already exists.");
        }
        return userRepo.save(user);
    }

    @Override
    public usersModel update(usersModel user) {
        Optional<usersModel> existingUser = userRepo.findById(user.getId());
        if (existingUser.isPresent()) {
            usersModel updatedUser = existingUser.get();

            if (user.getUsername() != null && !user.getUsername().isEmpty()) {
                updatedUser.setUsername(user.getUsername());
            }
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String encodedPassword = encoder.encode(user.getPassword());
                updatedUser.setPassword(encodedPassword);
            }
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                updatedUser.setEmail(user.getEmail());
            }
            if (user.getFirst_name() != null && !user.getFirst_name().isEmpty()) {
                updatedUser.setFirst_name(user.getFirst_name());
            }
            if (user.getLast_name() != null && !user.getLast_name().isEmpty()) {
                updatedUser.setLast_name(user.getLast_name());
            }
            if (user.getAddress() != null && !user.getAddress().isEmpty()) {
                updatedUser.setAddress(user.getAddress());
            }
            if (user.getContact_info() != null && !user.getContact_info().isEmpty()) {
                updatedUser.setContact_info(user.getContact_info());
            }
            updatedUser.setStatus(user.isStatus()); // Always update status since it's a boolean

            return userRepo.save(updatedUser);
        } else {
            throw new RuntimeException("User not found with id: " + user.getId());
        }
    }

    @Transactional
    public boolean delete(String id) {
        Optional<usersModel> existingUser = userRepo.findById(id);
        if (existingUser.isPresent()) {
            // userRoleRepository.deleteByUserId(id);
            userRepo.delete(existingUser.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public usersModel fetchById(String id) {
        Optional<usersModel> user = userRepo.findById(id);
        return user.orElse(null);
    }

    @Override
    public usersModel fetchByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public usersModel fetchByEmail(String email) {
        return null;
    }

    @Override
    public usersModel updateStatus(usersModel user, int status) {
        boolean statusbool = (status == 1) ? true : false;
        Optional<usersModel> existingUser = userRepo.findById(user.getId());
        if (existingUser.isPresent()) {
            usersModel updateduser = existingUser.get();
            updateduser.setStatus(statusbool);
            return userRepo.save(updateduser);
        } else {
            throw new RuntimeException("User not found with id: " + user.getId());
        }
    }

    @Override
    public List<usersModel> fetchAllUsers(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return userRepo.findAll(pageable).getContent();
    }

    @Override
    public usersModel updateRole(usersModel user, String role) {
        List<String> allowedRoles = Arrays.asList("admin", "super admin", "supervisor", "analyst", "viewer", "none");

        // Check if the provided role is in the allowed roles list
        if (!allowedRoles.contains(role.toLowerCase())) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
        // Find the existing user by their ID
        Optional<usersModel> existingUser = userRepo.findById(user.getId());
        if (existingUser.isPresent()) {
            usersModel updatedUser = existingUser.get();
            updatedUser.setRole(role); // Assuming usersModel has a setRole method
            return userRepo.save(updatedUser);
        } else {
            throw new RuntimeException("User not found with id: " + user.getId());
        }
    }
}