package cl.instrumentum.service_auth.controller;

import cl.instrumentum.service_auth.dto.AuthRequest;
import cl.instrumentum.service_auth.model.Usuario;
import cl.instrumentum.service_auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints de seguridad para registro de usuarios y generación de Tokens JWT")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @PostMapping("/registrar")
    @Operation(summary = "Registrar un nuevo usuario", description = "Recibe los datos del usuario, valida el formato, encripta la contraseña con BCrypt y lo persiste en la base de datos.")
    public ResponseEntity<Map<String, Object>> registrar(@Valid @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = authService.registrar(usuario);
        // Ocultamos la contraseña en la respuesta JSON por seguridad
        nuevoUsuario.setContrasena("[PROTEGIDA]");
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Usuario registrado exitosamente.", "usuario", nuevoUsuario));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión en el sistema", description = "Valida las credenciales del usuario y genera un JSON Web Token (JWT) válido por 2 horas.")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AuthRequest request){
        String token = authService.login(request.getNombreUsuario(), request.getContrasena());
        
        return ResponseEntity.ok(Map.of(
            "mensaje", "Login exitoso. Bienvenido a Instrumentum.", 
            "token", token
        ));
    }

    // ==============================================================
    // MANEJO GLOBAL DE EXCEPCIONES PARA ESTE CONTROLADOR
    // ==============================================================

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
        String msg = e.getMessage();
        // Si el error tiene que ver con login, retornamos código 401 Unauthorized
        if (msg.equals("Credenciales inválidas") || msg.equals("Usuario no encontrado")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensaje", "Error de autenticación: " + msg));
        }
        // Para errores generales como "usuario duplicado", retornamos código 400
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("mensaje", msg));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        // Recoge todas las validaciones fallidas de Jakarta y las une en un texto
        String errores = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining(" | "));
                
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("mensaje", "Error de validación", "detalles", errores));
    }
}