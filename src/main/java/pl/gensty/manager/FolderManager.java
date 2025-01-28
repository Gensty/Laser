package pl.gensty.manager;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigOther;
import pl.gensty.enums.MaterialType;
import pl.gensty.enums.Module;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

import static pl.gensty.utils.ExcelReader.*;

public class FolderManager {
    public static String createNewFolder(String excelPath, AbstractConfig abstractConfig, Module module, MaterialType materialType, JTextArea outputArea, JTextField catalogPathField) {
        String targetPath = PathManager.getTargetPath(outputArea, catalogPathField);
        Integer moduleQuantity = readModuleQuantity(excelPath, abstractConfig, module);

        String folderName;
        String replacement = (abstractConfig instanceof ConfigOther) ? " " : "_" + module.toString() + " ";

        String temporaryFolderName = (materialType == MaterialType.SHEET)
                ? abstractConfig.setFolderName().replace("module", replacement)
                : abstractConfig.setFolderName(materialType).replace("module", replacement);

        int quantity = (abstractConfig instanceof ConfigOther) ? abstractConfig.getDeviceQuantity() : moduleQuantity;

        folderName = temporaryFolderName.replace("quantity", isSingleCharNumber(quantity));

        String folderPath = targetPath + "\\" + folderName;

        File newFolder = new File(folderPath);
        if (!newFolder.exists()) {
            if (newFolder.mkdir()) {
                outputArea.append("\n" + "Folder utworzony: " + folderPath + "\n");
            } else {
                outputArea.append("Nie udało się utworzyć folderu: " + folderPath);
            }
        } else {
            outputArea.append("Folder o nazwie " + folderName + " już istnieje.");
        }

        return folderPath;
    }

    public static void deleteFolder(String folderPath, JTextArea outputArea) {
        File folder = new File(folderPath);

        if (folder.isDirectory() && Objects.requireNonNull(folder.list()).length == 0) {
            if (folder.delete()) {
                outputArea.append("Pusty folder usunięty: " + folderPath + "\n");
            } else {
                outputArea.append("Nie udało się usunąć folderu: " + folderPath + "\n");
            }
        } else {
            outputArea.append("\n");
        }
    }
}