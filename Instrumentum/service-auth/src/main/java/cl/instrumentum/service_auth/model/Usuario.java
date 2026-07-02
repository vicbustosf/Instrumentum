package cl.instrumentum.service_auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa a un usuario que se autentica en el sistema")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Column(unique = true)
    @Schema(description = "Nombre de usuario único para el inicio de sesión", example = "jhendrix")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Schema(description = "Contraseña encriptada del usuario", example = "guitarra123")
    private String contrasena;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe proporcionar un formato de correo electrónico válido")
    @Schema(description = "Correo electrónico de contacto del usuario", example = "jimi@instrumentum.cl")
    private String correo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    @Schema(description = "Conjunto de roles asignados al usuario")
    private Set<Rol> roles = new HashSet<>();
}