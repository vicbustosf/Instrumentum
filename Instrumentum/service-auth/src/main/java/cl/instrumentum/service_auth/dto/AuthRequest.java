package cl.instrumentum.service_auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de transferencia de datos para procesar la solicitud de inicio de sesión (Login)")
public class AuthRequest {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Schema(description = "Nombre de usuario registrado", example = "jhendrix")
    private String nombreUsuario;

    @NotBlank(message = "Debe ingresar su contraseña")
    @Schema(description = "Contraseña de la cuenta", example = "guitarra123")
    private String contrasena;
}