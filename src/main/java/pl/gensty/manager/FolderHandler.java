package pl.gensty.manager;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigOther;
import pl.gensty.enums.MaterialType;
import pl.gensty.enums.Module;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

import static pl.gensty.utils.ExcelReader.*;

public class FolderHandler {
    private final JTextArea outputArea;
    private final PathHandler pathHandler;

    public FolderHandler(PathHandler pathHandler, JTextArea outputArea) {
        this.pathHandler = pathHandler;
        this.outputArea = outputArea;
    }

    public String createNewFolder(AbstractConfig abstractConfig, Module module, MaterialType materialType) {
        String targetPath = pathHandler.getTargetPath();
        String folderName = setFolderName(abstractConfig, module, materialType);
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

    public void deleteFolder(String folderPath) {
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

    private String setFolderName(AbstractConfig abstractConfig, Module module, MaterialType materialType) {
        //TODO: nie działa metoda readModuleQuantity
//        Integer moduleQuantity = readModuleQuantity(excelPath, abstractConfig, module);
        Integer moduleQuantity = 1;
        String replacement = (abstractConfig instanceof ConfigOther) ? " " : "_" + module.toString() + " ";

        String temporaryFolderName = (materialType == MaterialType.SHEET)
                ? abstractConfig.setFolderName().replace("module", replacement)
                : abstractConfig.setFolderName(materialType).replace("module", replacement);

        int quantity = (abstractConfig instanceof ConfigOther) ? abstractConfig.getDeviceQuantity() : moduleQuantity;

        return temporaryFolderName.replace("quantity", isSingleCharNumber(quantity));


    }
}