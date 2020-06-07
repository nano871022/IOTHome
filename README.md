# IOTHome

Contiene el código de IOT para el hogar, conectando dispositivos creados con arduino/nodeMCU para tener diferente información del hogar,estos dispositivos almacenan la información sobre una hoja de calculo de sheet de google, por lo tanto lo que se realizá es bajar la información de esta hoja de calculo y se muestra en el dispositivo.

El desarrollo tiene las siguientes paginas :
 * La principal muestra las alertas que son enviadas al dispositivo, de cada uno de los dispositivos.
 * La siguiente es la página que indica todos los dispositivos que se encuentra configurados, esta configuración se indica la hoja dentro de la hoja de calculos que tiene la lectura de los sensores.
 * La siguiente es la página que miestra la información del sensor seleccionado.
 * La configuración de la aplicación contiene el nombre , correo y key de conexion a la hoja de calculo, todo esto se almacena sobre una base de datos local del dispositivo.
 * La configuración de los dispositivos, es donde agrega el nombre del campo y agrega la hoja de calculo donde lee la información.

