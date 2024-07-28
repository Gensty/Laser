package pl.gensty.Utils;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pl.gensty.Configuration.AbstractConfig;
import pl.gensty.Configuration.ConfigNPK;
import pl.gensty.Configuration.ConfigSPR;
import pl.gensty.Enums.MaterialType;

import javax.swing.*;
import java.io.File;

public class CreateNewFolderTest {
    private JTextArea outputArea;
    private JTextField excelPathField;
    private JTextField catalogPathField;
    private AbstractConfig abstractConfig;
    private ConfigNPK configNPK;
    private ConfigSPR configSPR;

    @BeforeEach
    public void setUp() {
        outputArea = new JTextArea();
        excelPathField = new JTextField();
        catalogPathField = new JTextField("test/catalog");

        abstractConfig = mock(AbstractConfig.class);
        configNPK = mock(ConfigNPK.class);
        configSPR = mock(ConfigSPR.class);
    }

    @Test
    public void testCreateNewFolderConfigNPK() {
        when(configNPK.setFolderName()).thenReturn("moduleFolder");

        String result = FileUtils.createNewFolder(configNPK, "testModule", MaterialType.SHEET, outputArea, excelPathField, catalogPathField);

        assertTrue(result.contains("test/catalog/moduleFolder-testModule"));
        assertTrue(new File(result).exists());
        assertTrue(outputArea.getText().contains("Folder utworzony: " + result));
    }

    @Test
    public void testCreateNewFolderWithNonSheetAndConfigSPR() {
//        when(configSPR.setFolderName(MaterialType.OTHER)).thenReturn("moduleFolder");
//
//        String result = FileUtils.createNewFolder(configSPR, "testModule", MaterialType.OTHER, outputArea, excelPathField, catalogPathField);
//
//        assertTrue(result.contains("test/catalog/moduleFolder_testModule"));
//        assertTrue(new File(result).exists());
//        assertTrue(outputArea.getText().contains("Folder utworzony: " + result));
    }

    @Test
    public void testFolderAlreadyExists() {
        File existingFolder = new File("test/catalog/existingFolder");
        existingFolder.mkdirs();

        when(abstractConfig.setFolderName()).thenReturn("existingFolder");

        String result = FileUtils.createNewFolder(abstractConfig, "testModule", MaterialType.SHEET, outputArea, excelPathField, catalogPathField);

        assertTrue(result.contains("test/catalog/existingFolder"));
        assertTrue(outputArea.getText().contains("Folder o nazwie existingFolder już istnieje."));

        // Clean up
        existingFolder.delete();
    }
}
