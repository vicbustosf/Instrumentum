package cl.instrumentum.service_auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
@Schema(description = "Entidad que representa un rol de permisos para un usuario dentro del sistema")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del rol", example = "1")
    private Long id;

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Schema(description = "Nombre descriptivo del rol", example = "MUSICO")
    private String nombreRol;
}