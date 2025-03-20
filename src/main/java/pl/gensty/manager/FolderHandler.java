package pl.gensty.manager;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigOther;
import pl.gensty.enums.MaterialType;
import pl.gensty.enums.Module;
import pl.gensty.utils.ExcelReader;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

import static pl.gensty.utils.Utils.isSingleCharNumber;

public class FolderHandler {
    private final JTextArea outputArea;
    private final PathHandler pathHandler;
    private final ExcelReader excelReader;

    public FolderHandler(PathHandler pathHandler, ExcelReader excelReader, JTextArea outputArea) {
        this.pathHandler = pathHandler;
        this.excelReader = excelReader;
        this.outputArea = outputArea;
    }

    public String createNewFolder(AbstractConfig abstractConfig, Module module) {
        String targetPath = pathHandler.getTargetPath();
        String folderName = setFolderName(abstractConfig, module);
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

    private String setFolderName(AbstractConfig abstractConfig, Module module) {
        String folderMaterial = MaterialType.DX51D.name().equals(abstractConfig.getMaterial()) ? "DX51D+S235" : abstractConfig.getMaterial();

        int quantity;
        String moduleName;
        if(abstractConfig instanceof ConfigOther) {
            quantity = abstractConfig.getDeviceQuantity();
            moduleName = " ";
        } else {
            quantity = excelReader.readModuleQuantity(module);
            moduleName = "_" + module.name() + " ";
        }

        return String.format("ZL_%s - %s%s%s (x%s)",
                abstractConfig.getOrder(),
                abstractConfig.getSize(),
                moduleName,
                folderMaterial,
                isSingleCharNumber(quantity));
    }
}