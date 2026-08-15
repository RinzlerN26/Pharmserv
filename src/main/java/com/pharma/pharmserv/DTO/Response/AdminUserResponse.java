package com.pharma.pharmserv.DTO.Response;

import com.pharma.pharmserv.Enums.Role;

public class AdminUserResponse extends UserResponse {
    private Role role;

    public AdminUserResponse() {
        super();
    }

    public AdminUserResponse(Integer id, String userName, String userEmail, String userId, Role role) {
        super(id, userName, userEmail, userId);
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
