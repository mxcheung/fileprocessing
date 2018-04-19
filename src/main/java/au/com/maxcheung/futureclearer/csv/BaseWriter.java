package au.com.maxcheung.futureclearer.csv;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

abstract class BaseWriter {

    private static final int BUFFER_SIZE = 1024;

    public Writer getOutPutStreamWriter(String filename) throws FileNotFoundException, UnsupportedEncodingException {
        OutputStream outputStream = getOuputStream(filename);
        Writer writer = getWriter(outputStream);
        return writer;
    }

    public OutputStream getOuputStream(String filename) throws FileNotFoundException {
        File tempFile = getFile(filename);
        FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
        OutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, BUFFER_SIZE);
        return bufferedOutputStream;
    }

    public Writer getWriter(OutputStream out) throws UnsupportedEncodingException {
        Writer writer = new OutputStreamWriter(out, "UTF-8");
        return writer;
    }

    public File getFile(String fileName) {
        return new File(fileName);
    }

}
