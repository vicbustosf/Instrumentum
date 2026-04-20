Instrumentum

El sistema Instrumentum opera como una plataforma de gestión integral basada en una arquitectura de capas, diseñada para centralizar la información técnica de músicos y optimizar su rendimiento en vivo y en estudio. Su comportamiento se desglosa en los siguientes núcleos operativos:
------------------------------------------------------------------
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
-----------------------------------------------------------------
Requisitos Funcionales (RF)
- RF-01: Gestión de Inventario de Equipos: CRUD completo de equipos (instrumentos, amplificadores, pedales). Atributos específicos: maderas y pastillas para instrumentos; voltaje (9V/12V), consumo (mA) y circuito para electrónica.
- RF-02: Módulo de Mantenimiento Preventivo: Bitácora de servicios asociada a equipos específicos. Alerta visual para equipos sin mantenimiento en más de 6 meses.
- RF-03: Configuración por Canción (Rig Builder): CRUD de canciones con asignación N:N de equipos. Especificación de orden de cadena de señal, técnica de ejecución y seteo de perillas.
- RF-04: Exportación de Technical Rider: Generación de listas de equipos por canción o setlist en formato PDF/Texto.
- RF-05: Calculadora de Carga: Algoritmo para sumar consumo (mA) de pedales por canción y comparar contra el límite de la fuente de poder (con advertencia de exceso).
- RF-06: Gestión de Usuarios: Registro y autenticación mediante BCrypt para asegurar el aislamiento de datos entre usuarios.
- RF-07: Búsqueda y Filtros: Búsqueda por metadatos (nombre, tipo, marca) y filtrado de equipos en estado de mantenimiento pendiente.

---------------------------------------------------------------

Requisitos No Funcionales (RNF)
- RNF-01: Tecnología: Desarrollo en Java 21 con Spring Boot 4.0.5. Persistencia mediante Spring Data JPA sobre MySQL (gestionado vía XAMPP). Interfaz con Thymeleaf(Motor de plantillas Springboot Java del lado del servidor, para renderizar archivos HTML) y Bootstrap(framework front-end para el desarrollo web responsivo (adaptable a móviles)).
- RNF-02: Rendimiento: Operaciones CRUD con tiempo de respuesta inferior a 2 segundos. Cálculos de carga eléctrica ejecutados en menos de 1 segundo.
- RNF-03: Seguridad: Protección contra inyección SQL nativa de JPA. Encriptación de contraseñas con BCrypt y validación de datos de entrada (Bean Validation).
- RNF-04: Usabilidad: Interfaz responsive para visualización en distintos dispositivos y mensajes de error descriptivos.
- RNF-05: Mantenibilidad: Código organizado por capas (Controller, Service, Repository) y uso de Lombok para limpieza de código.
- RNF-06: Disponibilidad: Garantizada 24/7 para el entorno de desarrollo local y despliegues básicos.
- RNF-07: Integridad de Datos: Configuración de eliminación en cascada para mantenimientos y restricciones para proteger equipos asignados a canciones activas.



