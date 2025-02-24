package pl.gensty.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.gensty.configuration.ConfigNPK;
import pl.gensty.configuration.ConfigOther;
import pl.gensty.enums.Module;

import javax.swing.*;

import java.util.HashMap;
import java.util.Map;

import static  org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PathHandlerTest {
    private PathHandler pathHandler;
    private JTextField excelPathField;
    private JTextField catalogPathField;
    private JTextArea outputArea;

    @BeforeEach
    void setUp() {
        excelPathField = mock(JTextField.class);
        catalogPathField = mock(JTextField.class);
        outputArea = mock(JTextArea.class);
        pathHandler = new PathHandler(excelPathField, catalogPathField, outputArea);
    }

    @Test
    void testGetTargetPath_shouldReturnValidPath() {
        when(catalogPathField.getText()).thenReturn("C:/target/path");

        String result = pathHandler.getTargetPath();

        assertEquals("C:/target/path", result);
    }

    @ParameterizedTest
    @CsvSource({
            ",",
            "''",
            "'   '"
    })
    void testGetTargetPath_shouldThrowExceptionWhenPathIsInvalid(String input) {
        when(catalogPathField.getText()).thenReturn(input);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> pathHandler.getTargetPath());

        assertEquals("Ścieżka do katalogu jest pusta.", exception.getMessage());
        verify(outputArea).append("Podaj ściężkę docelową dla paczek.\n");
    }

    @Test
    void testGetExcelPath_shouldReturnValidPath() {
        when(excelPathField.getText()).thenReturn("C:/excel/path");

        String result = pathHandler.getExcelPath();

        assertEquals("C:/excel/path", result);
    }

    @ParameterizedTest
    @CsvSource({
            ",",
            "''",
            "'   '"
    })
    void testGetExcelPath_shouldThrowExceptionWhenPathIsInvalid(String input) {
        when(excelPathField.getText()).thenReturn(input);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> pathHandler.getExcelPath());

        assertEquals("Ścieżka do katalogu jest pusta.", exception.getMessage());
        verify(outputArea).append("Podaj ściężkę do konfiguratora Excel.\n");
    }

    @Test
    void testGetSourcePath_ShouldReturnPathForConfigOther() {
        String size = "ZPR-050";
        String path = "/path/ZPR-050";
        ConfigOther config = mock(ConfigOther.class);
        when(config.getSize()).thenReturn(size);

        Map<String, String> paths = new HashMap<>();
        paths.put(size, path);

        String result = pathHandler.getSourcePath(config, Module.ZPR, paths);

        assertEquals(path, result);
    }

    @Test
    void testGetSourcePath_ShouldReturnPathForConfigNPK_100_RT() {
        String size = "NPK-100";
        Module module = Module.RT;
        String path = "/path/NPK-100_RT";
        ConfigNPK config = mock(ConfigNPK.class);
        when(config.getSize()).thenReturn(size);

        Map<String, String> paths = new HashMap<>();
        paths.put(size + "_" + module.name(), path);

        String result = pathHandler.getSourcePath(config, module, paths);

        assertEquals(path, result);
    }

    @Test
    void testGetSourcePath_ShouldThrowExceptionWhenPathIsNull() {
        ConfigOther config = mock(ConfigOther.class);
        when(config.getSize()).thenReturn("RDS-310");

        Map<String, String> paths = new HashMap<>();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> pathHandler.getSourcePath(config, Module.RDS, paths));


        assertEquals("Nie znaleziono ścieżki dla: RDS-310", exception.getMessage());
    }
}