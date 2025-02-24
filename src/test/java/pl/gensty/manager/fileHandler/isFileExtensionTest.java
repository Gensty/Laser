package pl.gensty.manager.fileHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.gensty.manager.FileHandler;
import pl.gensty.manager.PathHandler;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

class isFileExtensionTest {
    private FileHandler fileHandler;
    private Method isFileExtensionMethod;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        JTextField excelPathField = new JTextField();
        JTextField catalogPathField = new JTextField();
        JTextArea outputArea = new JTextArea();
        PathHandler pathHandler = new PathHandler(excelPathField, catalogPathField, outputArea);
        fileHandler = new FileHandler(pathHandler, outputArea);

        isFileExtensionMethod = FileHandler.class.getDeclaredMethod("isFileExtension", File.class, String.class);
        isFileExtensionMethod.setAccessible(true);
    }

    @ParameterizedTest
    @CsvSource({
            "testFile.PDF, pdf, true",
            "testFile.pdf, PDF, true",
            "testFile.DWG, dwg, true",
            "testFile.dwg, DWG, true",
            "testFile.DXF, dxf, true",
            "testFile.dxf, DXF, true",
            "testFile.txt, pdf, false"
    })

    public void testIsExtensionType(String fileName, String extension, boolean expectedResult) throws InvocationTargetException, IllegalAccessException {
        File file = new File(fileName);
        boolean result = invokeIsFileExtension(file, extension);
        assertEquals(expectedResult, result, "Test failed for file: " + fileName + " with extension: " + extension);
    }

    private boolean invokeIsFileExtension(File file, String extension) throws InvocationTargetException, IllegalAccessException {
        return (boolean) isFileExtensionMethod.invoke(fileHandler, file, extension);
    }
}