Instrumentum

El sistema Instrumentum opera como una plataforma de gestión integral basada en una arquitectura de capas, diseñada para centralizar la información técnica de músicos y optimizar su rendimiento en vivo y en estudio. Su comportamiento se desglosa en los siguientes núcleos operativos:

Descripción del Comportamiento del Sistema:
* Gestión de Identidad y Aislamiento de Datos
  - El sistema implementa un entorno de seguridad basado en Spring Security y BCrypt para garantizar que cada músico trabaje en un espacio privado.
  - Las credenciales de acceso son procesadas mediante algoritmos de hashing antes de su almacenamiento en MySQL.
  - El comportamiento del software asegura que todas las consultas a la base de datos estén filtradas por el ID del usuario autenticado, impidiendo que los   datos de un inventario sean visibles para otros usuarios.

* Ciclo de Vida del Inventario (Gear Management)
  - El sistema presenta un comportamiento dinámico en la captura de datos: si el usuario registra un instrumento, el software habilita campos de maderas y    pastillas; si registra electrónica, habilita campos de voltaje y amperaje (mA).
  - Gestión de Estados: El sistema calcula automáticamente el tiempo transcurrido desde la última intervención técnica. Si el intervalo supera los 6 meses, el estado del equipo cambia visualmente para indicar una alerta de mantenimiento preventivo.

* Dinámica del Módulo de Mantenimiento
  - Este módulo funciona como una bitácora histórica vinculada de forma inmutable a cada activo del inventario.
  - Integridad de Datos: El sistema aplica reglas de borrado en cascada para asegurar que, al eliminar un equipo, sus registros de servicio asociados también desaparezcan, manteniendo la base de datos limpia y eficiente.

* Inteligencia del Rig Builder (Configuración de Canciones)
  - El núcleo del sistema permite gestionar la relación compleja entre el repertorio y el equipamiento.
  - Gestión de Cadena de Señal: El usuario define un orden lógico de conexión (Posición 1, 2, 3...) para sus equipos dentro de una canción específica.
  - Persistencia de Contexto: Por cada asignación, el sistema permite guardar notas sobre la técnica de ejecución y el seteo preciso de las perillas, funcionando como un manual de instrucciones para presentaciones en vivo.

* Lógica de Cálculo y Validación Eléctrica
  - El software integra un algoritmo de validación de carga para configuraciones de pedales.
  - Agregación Automática: Al cargar el setup de una canción, el sistema suma los valores de miliamperios (mA) de todos los dispositivos activos.
  - Alertas Preventivas: El total se contrasta con la capacidad de la fuente de poder registrada; si el consumo supera el límite teórico, el sistema dispara una advertencia visual para prevenir fallos eléctricos durante el uso.

* Procesamiento y Visualización de la Interfaz
  - El sistema utiliza el motor de plantillas Thymeleaf para el renderizado del lado del servidor, optimizando la entrega de contenido HTML ya procesado al navegador.
  - Comportamiento Responsive: Gracias a la integración de Bootstrap, la interfaz ajusta su disposición de forma automática, permitiendo que la consulta de los Technical Riders sea tan eficiente en un smartphone como en una estación de trabajo de estudio.

Requisitos Funcionales (RF)
- RF-01: Gestión de Inventario de Equipos: CRUD completo de equipos (instrumentos, amplificadores, pedales). Atributos específicos: maderas y pastillas para instrumentos; voltaje (9V/12V), consumo (mA) y circuito para electrónica.
- RF-02: Módulo de Mantenimiento Preventivo: Bitácora de servicios asociada a equipos específicos. Alerta visual para equipos sin mantenimiento en más de 6 meses.
- RF-03: Configuración por Canción (Rig Builder): CRUD de canciones con asignación N:N de equipos. Especificación de orden de cadena de señal, técnica de ejecución y seteo de perillas.
- RF-04: Exportación de Technical Rider: Generación de listas de equipos por canción o setlist en formato PDF/Texto.
- RF-05: Calculadora de Carga: Algoritmo para sumar consumo (mA) de pedales por canción y comparar contra el límite de la fuente de poder (con advertencia de exceso).
- RF-06: Gestión de Usuarios: Registro y autenticación mediante BCrypt para asegurar el aislamiento de datos entre usuarios.
- RF-07: Búsqueda y Filtros: Búsqueda por metadatos (nombre, tipo, marca) y filtrado de equipos en estado de mantenimiento pendiente.


Requisitos No Funcionales (RNF)
- RNF-01: Tecnología: Desarrollo en Java 21 con Spring Boot 4.0.5. Persistencia mediante Spring Data JPA sobre MySQL (gestionado vía XAMPP). Interfaz con Thymeleaf(Motor de plantillas Springboot Java del lado del servidor, para renderizar archivos HTML) y Bootstrap(framework front-end para el desarrollo web responsivo (adaptable a móviles)).
- RNF-02: Rendimiento: Operaciones CRUD con tiempo de respuesta inferior a 2 segundos. Cálculos de carga eléctrica ejecutados en menos de 1 segundo.
- RNF-03: Seguridad: Protección contra inyección SQL nativa de JPA. Encriptación de contraseñas con BCrypt y validación de datos de entrada (Bean Validation).
- RNF-04: Usabilidad: Interfaz responsive para visualización en distintos dispositivos y mensajes de error descriptivos.
- RNF-05: Mantenibilidad: Código organizado por capas (Controller, Service, Repository) y uso de Lombok para limpieza de código.
- RNF-06: Disponibilidad: Garantizada 24/7 para el entorno de desarrollo local y despliegues básicos.
- RNF-07: Integridad de Datos: Configuración de eliminación en cascada para mantenimientos y restricciones para proteger equipos asignados a canciones activas.


Riesgos Críticos (Impacto Alto)

R01 | Incompatibilidad de Versión (Spring Boot 4.0.5)
* Categoría: Técnico
* Probabilidad: Media
* Impacto: Alto
* Mitigación: Realizar una prueba de concepto inicial (PoC) para validar que las dependencias de **Jakarta Persistence** y el driver de **MySQL** operen sin conflictos en la nueva versión de Spring.

R02 | Desconexión del Motor de Base de Datos (MySQL/XAMPP)**
* Categoría: Infraestructura Local
* Probabilidad: Media
* Impacto: Alto
* Mitigación: Configurar un HealthCheck en Spring para monitorear el estado de la conexión y asegurar que el servicio de MySQL en el panel de XAMPP esté iniciado antes del despliegue del .jar.

R03 | Brecha de Seguridad en Aislamiento de Datos
* Categoría: Seguridad
* Probabilidad:** Baja
* Impacto:** Crítico
* Mitigación: Implementar cláusulas WHERE user_id = :current_user en todos los métodos del repositorio de Spring Data JPA para evitar que un usuario visualice el inventario de otro.

---

Riesgos Moderados (Impacto Medio)

R04 | Error en Algoritmo de Cálculo de Carga (RF-05)**
* Categoría: Funcional
* Probabilidad: Baja
* Impacto: Medio
* Mitigación: Desarrollar una suite de pruebas unitarias con JUnit 5 (framework de pruebas) que testee casos de borde (ej: pedales sin amperaje definido o sumas que exceden el límite de la fuente).

R05 | Inconsistencia por Eliminación en Cascada (RNF-07)
* Categoría: Integridad de Datos
* Probabilidad: Baja
* Impacto: Alto
* Mitigación: Aplicar la anotación @OnDelete(action = OnDeleteAction.CASCADE) únicamente en los logs de mantenimiento, protegiendo con restricciones de clave foránea los equipos vinculados a canciones.

---

Riesgos Operacionales (Impacto Bajo)

R06 | Latencia en el Motor de Plantillas (Thymeleaf)
* Categoría: Rendimiento
* Probabilidad: Media
* Impacto: Bajo
* Mitigación: Evitar el procesamiento de bucles anidados complejos dentro de los archivos .html y asignar el procesamiento de datos pesado a la capa de Service en Java.

R07 | Fallas en Generación de Technical Rider (RF-04)
* Categoría: Funcional
* Probabilidad: Media
* Impacto: Medio
* Mitigación: Validar los campos de texto antes de la exportación para evitar caracteres especiales que corrompan el formato del archivo final (PDF/Texto).
