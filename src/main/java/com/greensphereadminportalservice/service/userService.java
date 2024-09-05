package com.greensphereadminportalservice.service;



import com.greensphereadminportalservice.model.usersModel;

import java.util.List;

public interface userService {
    usersModel insert(usersModel user);

    usersModel update(usersModel user);

    boolean delete(String id);

    usersModel fetchById(String id);

    usersModel fetchByEmail(String email);

    usersModel updateStatus(usersModel user, int status);

    usersModel updateRole(usersModel user, String role);

    List<usersModel> fetchAllUsers(int offset, int limit);

    usersModel fetchByUsername(String username);

}
