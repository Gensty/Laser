package pl.gensty.manager;

import org.junit.jupiter.api.BeforeEach;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.Method;

class FileHandlerTest {
    private FileHandler fileHandler;
    private Method isFileTypeMethod;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        JTextField excelPathField = new JTextField();
        JTextField catalogPathField = new JTextField();
        JTextArea outputArea = new JTextArea();
        PathHandler pathHandler = new PathHandler(excelPathField, catalogPathField, outputArea);
        fileHandler = new FileHandler(pathHandler, outputArea);

        isFileTypeMethod = FileHandler.class.getDeclaredMethod("isFileType", File.class, String.class);
        isFileTypeMethod.setAccessible(true);
    }

}