# Instrumentum

#Requisitos funcionales no funcionales.
#Modelo entidad relacion.
#Planilla de riesgo.
# ETS = Equisitos Transmitidos x Sufrimiento




Requisitos Funcionales (RF)

RF-01: Gestión de Inventario de Equipos

· CRUD completo de equipos (instrumentos, amplificadores, pedales)
· Atributos específicos por tipo:
  · Instrumentos: marca, modelo, año, tipo de madera, configuración de pastillas, calibre de cuerdas
  · Amplificación y pedales: voltaje (9V/12V), consumo (mA), tipo de circuito

RF-02: Módulo de Mantenimiento Preventivo

· Registrar bitácora de servicios (calibración, cambio de cuerdas, limpieza electrónica)
· Asociar cada mantenimiento a un equipo específico
· Visualizar alerta de equipos sin mantenimiento en los últimos 6 meses

RF-03: Configuración por Canción (Rig Builder)

· CRUD de canciones (título, artista, duración, notas generales)
· Asignar múltiples equipos a una canción (relación N:N)
· Especificar por asignación: notas de ejecución (técnica, seteo de perillas) y orden lógico de la cadena de señal

RF-04: Exportación de Technical Rider

· Generar lista de equipos por canción o setlist en formato PDF/texto

RF-05: Calculadora de Carga

· Sumar consumo total (mA) de pedales asignados a una canción
· Comparar con límite de fuente de poder y mostrar advertencia si excede

RF-06: Gestión de Usuarios (opcional recomendado)

· Registro y autenticación (BCrypt)
· Aislamiento de datos por usuario

RF-07: Búsqueda y Filtros

· Buscar equipos por nombre, tipo, marca
· Filtrar equipos que requieren mantenimiento

---

Requisitos No Funcionales (RNF)

RNF-01: Tecnología

· Backend: Java 17+ con Spring Boot 3
· Persistencia: Spring Data JPA + MariaDB
· Frontend: Thymeleaf (integrado) o React (API desacoplada)

RNF-02: Rendimiento

· Operaciones CRUD < 2 segundos
· Calculadora de carga < 1 segundo

RNF-03: Seguridad

· Protección contra inyección SQL (JPA)
· Contraseñas encriptadas (BCrypt)
· Validación de datos de entrada

RNF-04: Usabilidad

· Interfaz responsive (Bootstrap)
· Mensajes de error claros

RNF-05: Mantenibilidad

· Código comentado y por capas (Controller, Service, Repository)
· Convenciones Java

RNF-06: Disponibilidad

· Aplicación 24/7 (entorno local o nube básica)

RNF-07: Integridad de datos

· Eliminación en cascada configurada (equipo → mantenimientos)
· Restricción para no borrar equipos asignados a canciones
