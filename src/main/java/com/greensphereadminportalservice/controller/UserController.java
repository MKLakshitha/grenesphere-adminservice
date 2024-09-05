package com.greensphereadminportalservice.controller;

import com.greensphereadminportalservice.model.CustomUserDetails;
import com.greensphereadminportalservice.model.usersModel;
import com.greensphereadminportalservice.service.SystemLogService;
import com.greensphereadminportalservice.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin")
public class UserController {

    @Autowired
    userService UserService;

    @Autowired
    SystemLogService systemLogservice;

    // Retrieve the authenticated user's ID
    String authUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String authenticatedUserId = userDetails.getId();
        return authenticatedUserId;
    }

    @PostMapping("/insert")
    usersModel insertUser(@RequestBody usersModel user) {
        usersModel createdUser = UserService.insert(user);
        systemLogservice.logAction(authUserId(), "CREATE USER",
                "Created User with id: " + createdUser.getId());
        return createdUser;
    }

    @PutMapping("/update")
    public usersModel updateUser(@RequestBody usersModel user) {
        systemLogservice.logAction(authUserId(), "EDIT USER", "Edited User with id: " + user.getId());
        return UserService.update(user);
    }

    @PutMapping("/status/{status}")
    public usersModel updateStatus(@RequestBody usersModel user, @PathVariable int status) {
        systemLogservice.logAction(authUserId(), "CHECKED STATUS", "Checked status of User: " + user.getId());
        return UserService.updateStatus(user, status);
    }

    @PutMapping("/role/{role}")
    public usersModel updateRole(@RequestBody usersModel user, @PathVariable String role) {
        systemLogservice.logAction(authUserId(), "UPDATED ROLE ", "Updated role of User: " + user.getId());
        return UserService.updateRole(user, role);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteUser(@PathVariable String id) {
        boolean isDeleted = UserService.delete(id);
        try {
            if (isDeleted) {
                systemLogservice.logAction(authUserId(), "DELETED USER", "Deleted User with ID: " + id);
                return ResponseEntity.ok("User deleted successfully.") != null;
            } else {
                systemLogservice.logAction(authUserId(), "ATTEMPTED TO DELETE USER",
                        "Attempted to delete User with ID: " + id);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user.") != null;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user.") != null;
        }
    }

    @GetMapping("/get/{id}")
    public usersModel fetchUserById(@PathVariable String id) {
        systemLogservice.logAction(authUserId(), "GET USER DETAILS", "GET User with ID: " + id);
        return UserService.fetchById(id);
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<usersModel>> fetchAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "25") int limit) {
        List<usersModel> allusers = UserService.fetchAllUsers(offset, limit);
        systemLogservice.logAction(authUserId(), "FETCH USER DATA", "GET User data range 25");
        return new ResponseEntity<>(allusers, HttpStatus.OK);
    }

    @GetMapping(value = "/allusers", produces = "application/json")
    public ResponseEntity<List<usersModel>> fetchAll() {
        List<usersModel> allUsers = UserService.fetchAllUsers(0, 1000);
        systemLogservice.logAction(authUserId(), "FETCH ALL USER DATA", "GET ALL USER DATA");
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public usersModel fetchUserByEmail(@PathVariable String email) {
        return UserService.fetchByEmail(email);
    }
}
