package pl.gensty.manager;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.strategy.FactoryConfig;
import pl.gensty.enums.DeviceType;
import pl.gensty.enums.MaterialType;
import pl.gensty.enums.Module;

import javax.swing.*;
import java.util.List;

public class PackageManager {
    public static void preparePackage(
            DeviceType deviceType,
            MaterialType materialType,
            JTextArea outputArea,
            JTextField excelPathField,
            JTextField catalogPathField) {

        String excelPath = PathManager.getExcelPath(outputArea, excelPathField);
        AbstractConfig abstractConfig = FactoryConfig.createConfig(deviceType, excelPath);

        List<Module> modules = abstractConfig.getModules();

        for (Module module : modules) {
            String targetPath = FolderManager.createNewFolder(excelPath, abstractConfig, module, materialType, outputArea, catalogPathField);
            FileManager.copyFiles(abstractConfig, module, targetPath, materialType, outputArea, excelPathField);
            FolderManager.deleteFolder(targetPath, outputArea);
        }
    }
}