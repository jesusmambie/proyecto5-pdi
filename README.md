# pdi
- Mostrar información sobre la imagen: dimensiones, bits por pixel, cantidad única de colores and dpi (Dots Per Inch).
La informacion sale desplegada por pantalla

- Cálculo y despliegue del histograma de la imagen.
El histograma se guarda como imagen .png en el directorio del proyecto.

- Modificación del brillo y contraste.
Al seleccionar modificacion de brillo y contraste se habilitan los botones de "+" y "-" de la interfaz para poder manejar los cambios

- Umbralización de la imagen (umbral dinámico).
Al igual que el brillo y el contraste, se usa el boton de "+" y "-" para aumentar o disminuir respectivamente el umbral a ser utilizado

- Escalamiento y rotación libre de la imagen.
- Acercamiento y alejamiento (Zoom in/Zoom out).

- Cálculo del gradiente con los filtros de Sobel, Roberts y Prewitt.
Estan las 2 versiones (relieve y blanco y negro) para los 3 filtros, los trabajan con kernel 3x3

- Filtro del promedio, mediana y Laplaciano del Gaussiano.
Se debe seleccionar la opcion deseada, para usar otro kernel, se debe seleccionar primero el tipo de kernel en el combobox y luego el fltro a aplicar, si se quiere aplicar
mas de una vez, se debe volver a seleccionar la opcion asi ya este seleccionada, con el kernel deseado previamente seleccionado.

- Aplicar un kernel arbitrario a la imagen. Para ello debe haber una forma simple y práctica de asignar los valores a cada
posición del kernel.

- Se debe poder especificar el tamaño del kernel a utilizar para cada uno de los filtros. Cualquier combinación de filas y/o
columnas es válida partiendo desde un mínimo de 2x1 (vertical) ó 1x2 (horizontal) hasta un máximo de 7x7.
En el combobox.
