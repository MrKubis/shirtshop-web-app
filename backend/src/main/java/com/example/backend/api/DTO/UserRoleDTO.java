package com.example.backend.api.DTO;

import java.util.UUID;

public record UserRoleDTO(
        UUID user_id,
        UUID role_id
) {}
