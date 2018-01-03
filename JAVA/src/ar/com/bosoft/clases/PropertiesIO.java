package ar.com.bosoft.clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesIO {

    public static Properties read(java.net.URL url) throws IOException {
        Properties props;
        try (InputStream is = url.openStream()) {
            props = new Properties();
            props.load(is);
        }
        return props;
    }

    public static Properties read(File file) throws IOException {
        return read(file.toURI().toURL());
    }

    public static Properties readXML(java.net.URL url) throws IOException {
        Properties props;
        try (InputStream is = url.openStream()) {
            props = new Properties();
            props.loadFromXML(is);
        }
        return props;
    }

    public static Properties readXML(File file) throws IOException {
        return readXML(file.toURI().toURL());
    }

    public static void write(Properties props, File file) throws FileNotFoundException, IOException {
        write(props, file, null);
    }

    public static void write(Properties props, File file, String comments) throws IOException {
        //El parámetro false es para que el flujo remplace el contenido del archivo, 
        //si fuese true se escribiría al final de este, es decir, iniciando en la última línea 
        //sin remplazar las propiedades que ya existan.
        try (FileOutputStream ops = new FileOutputStream(file, false)) {
            props.store(ops, comments);
        }
    }

    public static void writeXML(Properties props, File file, String comments) throws IOException {
        //El parámetro false es para que el flujo remplace el contenido del archivo, 
        //si fuese true se escribiría al final de este, es decir, iniciando en la última línea 
        //sin remplazar las propiedades que ya existan.
        try (OutputStream os = new FileOutputStream(file, false)) {
            props.storeToXML(os, comments);
        }
    }

    public static void writeXML(Properties props, File file) throws IOException {
        writeXML(props, file, null);
    }
}
