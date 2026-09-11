## 🆕 Hoja de Trabajo 2 — Cambios realizados

**Escenario de negocio:** Retail — Catálogo de Productos (adaptado a una tienda de videojuegos).

### 🐛 Bugs corregidos del proyecto original (HT1)
1. **`nbproject/project.properties`**: `src.dir` y `src.resources.dir` apuntaban a
   `src/java` y `src/resources` (carpetas que no existen). Se corrigieron a
   `src/main/java` y `src/main/resources`, que es donde realmente está tu código.
2. **`fx:controller` en los FXML** (`LoginView`, `RegistroView`, `MainMenuView`):
   tenían el paquete incompleto `com.auratheriagames.controller...` en vez de
   `com.auratheria.auratheriaformulario.auratheriagames.controller...`. Esto
   habría lanzado un error al cargar cualquier vista.
3. **Rutas de `SceneManager.switchScene(...)`**: apuntaban a
   `/com/auratheriagames/view/...`, una carpeta que no existe dentro de tus
   recursos. Se corrigieron a `/view/...` (la ruta real).

Con estos 3 arreglos, tu Login → Registro → Menú ya debería funcionar
correctamente en NetBeans.

### ✨ Módulo nuevo: Gestión de Catálogo de Videojuegos (CRUD completo)
- `model/Videojuego.java` — POJO con: código, título, plataforma, categoría,
  precio de costo, precio de venta y stock.
- `dao/VideojuegoDAO.java` — Create, Read, Update, Delete usando `PreparedStatement`,
  con manejo de excepción para código duplicado.
- `controller/CatalogoController.java` — valida campos obligatorios, valida que
  costo/venta/stock sean numéricos y no negativos, y aplica la regla de negocio
  **"precio de venta nunca menor al costo"**. Usa `Alert` para confirmar
  guardado/actualización/eliminación.
- `view/CatalogoView.fxml` — formulario + `TableView`, mismo tema oscuro
  morado/cian del resto de la app.
- Se conectó el botón **"Catálogo / Ventas"** del Menú Principal (antes solo
  mostraba un mensaje de "módulo en construcción") para que abra esta pantalla.
- `sql/auratheria_games_ht2.sql` — script actualizado con la tabla `usuarios`
  (ya existente) + la nueva tabla `videojuegos`.
- `der/DER.svg` — diagrama con ambas entidades.

---

# Auratheria Games (ebarillas)

Proyecto JavaFX (MVC) para NetBeans, **proyecto tipo "Java con Ant"** (no Maven),
con la misma estructura de carpetas que viste en tu video: `build`, `dist`,
`nbproject`, `src`, `test`, `build.xml`, `manifest.mf`.

Dentro de `src` hay dos carpetas fuente:

- `src/java` → todo el código `.java` (paquetes `main`, `model`, `controller`, `dao`, `config`, `util`)
- `src/resources` → los `.fxml`, el `.css` y las imágenes

```
auratheria-games-ebarillas/
├─ build.xml
├─ manifest.mf
├─ nbproject/
│  ├─ project.xml
│  └─ project.properties
├─ lib/                     ← aquí van los .jar de JavaFX y MySQL (ver paso 2)
├─ sql/
│  └─ auratheria_games.sql
├─ src/
│  ├─ java/com/auratheriagames/
│  │  ├─ main/App.java
│  │  ├─ model/Usuario.java
│  │  ├─ controller/LoginController.java, RegistroController.java, MainMenuController.java
│  │  ├─ dao/UsuarioDAO.java
│  │  ├─ config/Credentials.java, DatabaseConnection.java   ← conexión MySQL
│  │  └─ util/SceneManager.java, Session.java
│  └─ resources/com/auratheriagames/
│     ├─ view/LoginView.fxml, RegistroView.fxml, MainMenuView.fxml
│     ├─ css/styles.css
│     └─ images/logo.png
└─ test/java/
```

---

## 1. Abrir el proyecto en NetBeans

1. NetBeans → **File → Open Project...**
2. Selecciona la carpeta `auratheria-games-ebarillas` (la que tiene `build.xml`).
3. Ábrelo normal. Como es un proyecto Ant, NetBeans regenerará automáticamente
   `nbproject/build-impl.xml` la primera vez que lo abras (es normal, no lo edites tú).

## 2. Descargar las librerías (JavaFX y MySQL Connector)

Como es un proyecto Ant "a mano", las librerías no se descargan solas (a diferencia de Maven).
Debes descargarlas UNA vez y ponerlas dentro de la carpeta `lib/`:

1. **JavaFX SDK 21** → descárgalo de https://gluonhq.com/products/javafx/ (elige tu sistema operativo).
   Descomprime el `.zip` dentro de `lib/` para que quede así:
   `lib/javafx-sdk-21.0.2/lib/javafx-controls.jar` (y los demás .jar de javafx).
2. **MySQL Connector/J** → descárgalo de https://dev.mysql.com/downloads/connector/j/
   y coloca el archivo `mysql-connector-j-9.1.0.jar` directo dentro de `lib/`.

> Si tu versión descargada tiene otro número (ej. `javafx-sdk-21.0.3` o
> `mysql-connector-j-9.2.0.jar`), solo edita las rutas en
> `nbproject/project.properties` (las líneas que empiezan con `file.reference.`)
> para que coincidan con el nombre real de tu carpeta/archivo.

Después de esto, clic derecho en el proyecto → **Clean and Build** para confirmar que compila.

## 3. Base de datos MySQL

1. Abre tu gestor de MySQL (Workbench, phpMyAdmin, consola, etc.).
2. Ejecuta completo el script `sql/auratheria_games.sql`. Esto crea la base
   `auratheria_games_db` y la tabla `usuarios`, con un usuario de prueba:
   - usuario: `admin`
   - contraseña: `123`
3. La conexión vive en `src/java/com/auratheriagames/config/`:
   - **`Credentials.java`** → aquí cambias host, nombre de la BD, usuario y password de tu MySQL.
   - **`DatabaseConnection.java`** → abre la conexión usando esas credenciales (no necesitas tocarlo).
   - **`dao/UsuarioDAO.java`** → aquí están las consultas SQL (login, registro, validar usuario existente).

Si al iniciar sesión te sale "Error de conexión", normalmente es porque:
- MySQL no está corriendo, o
- el usuario/password/puerto en `Credentials.java` no coinciden con tu instalación.

## 4. Cómo trabajar las vistas en Scene Builder

Vi la imagen que mandaste (Scene Builder vacío, "Drag Library items here..."). Así se usa con estos archivos:

1. En NetBeans: **Tools → Options → Java → JavaFX** → en "Scene Builder Home" apunta a
   donde instalaste Scene Builder (el `.exe` en Windows). Esto hace que NetBeans lo abra directo.
2. En el árbol de archivos, ve a `src/resources/com/auratheriagames/view/`, clic derecho sobre
   por ejemplo `LoginView.fxml` → **Open** (se abre en Scene Builder) o **Edit** (código XML en NetBeans).
3. En Scene Builder:
   - Panel izquierdo **Library**: ahí arrastras los controles (Button, Label, TextField, etc.) hacia el
     canvas central donde dice "Drag Library items here...".
   - Panel derecho **Inspector**: seleccionando un control, en la sección **Code** le pones el
     `fx:id` (para que el Controller lo reconozca con `@FXML`) y el `On Action` (el método que se
     ejecuta al hacer clic, ej. `#handleIngresar`).
   - Los `fx:id` y métodos `onAction` que ya usan los controllers (`txtUsuario`, `txtPassword`,
     `handleIngresar`, `handleCrearCuenta`, etc.) ya están escritos en los `.fxml` que te dejé, así
     que si abres esos archivos en Scene Builder ya deberías ver los controles acomodados y
     conectados — puedes moverlos, cambiarles el texto o el estilo desde ahí sin romper la conexión
     con el controller, siempre que no borres el `fx:id` ni el `onAction`.
4. Guarda desde Scene Builder (Ctrl+S) y el archivo `.fxml` se actualiza solo.

## 5. Ejecutar la app

Clic derecho en el proyecto → **Run**. Debe abrir la ventana de **Login**:
- `admin` / `123` → entra al **Menú Principal** (BorderPane con topbar, sidebar y área central).
- "¿No tienes cuenta? Crear cuenta" → va a **Registro** (guarda en MySQL y regresa al Login).
- "Cerrar Sesión" en el Dashboard → destruye la sesión y regresa al Login.

## 6. Cumplimiento de los criterios de evaluación

- **MVC limpio**: `model` (POJO `Usuario`), `view` (FXML puro, sin lógica), `controller`
  (un controller por vista, inyectado con `@FXML`), `dao`/`config` separando el acceso a datos.
- **Navegación**: `SceneManager` reutiliza el mismo `Stage` (no acumula ventanas) y gestiona
  Login ↔ Registro ↔ Dashboard.
- **CSS externo**: `css/styles.css`, enlazado desde los tres FXML con `stylesheets="@../css/styles.css"`.
- **Buenas prácticas**: paquetes separados (`main`, `model`, `controller`, `dao`, `config`, `util`),
  nombres descriptivos, sin imports sin usar.
