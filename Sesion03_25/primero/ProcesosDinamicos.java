package Sesion03_25.primero;

//https://docs.oracle.com/javase/8/docs/api/java/lang/Process.html
import java.io.File;
import java.io.IOException;

public class ProcesosDinamicos {

    private static Process start;

    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("descifrar.exe", "./imagen/imagen2Encriptadaa.png",
                "./imagen/imagen2.png");

    }
}