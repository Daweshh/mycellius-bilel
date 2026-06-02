package fr.mycellius.web.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Le nom d'utilisateur est obligatoire")
        @Size(min = 3, max = 64, message = "Le nom d'utilisateur doit faire entre 3 et 64 caractères")
        String username,
        @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 8, max = 128, message = "Le mot de passe doit faire entre 8 et 128 caractères")
        String password
) {}