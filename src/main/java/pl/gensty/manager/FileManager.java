package pl.gensty.manager;

import pl.gensty.configuration.*;
import pl.gensty.devicePart.*;
import pl.gensty.enums.*;
import pl.gensty.enums.Module;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static pl.gensty.manager.PathManager.*;
import static pl.gensty.utils.ExcelReader.readPartsFromConfig;

public class FileManager {
    public static void copyFiles(AbstractConfig abstractConfig, Module module, String targetPath, MaterialType materialType, JTextArea outputArea, JTextField excelPathField) {
        String excelPath = getExcelPath(outputArea, excelPathField);
        String sourcePath = getSourcePath(excelPath, abstractConfig, module);
        List<AbstractPart> parts = readPartsFromConfig(excelPath, abstractConfig, module);

        List<AbstractPart> configParts = getFiles(abstractConfig, parts, materialType.toString());

        File sourceFolder = new File(sourcePath);
        File targetFolder = new File(targetPath);

        if (!sourceFolder.isDirectory() || !targetFolder.exists()) {
//            System.out.println("Source directory does not exist or target directory does not exist.");
            return;
        }

        File[] files = sourceFolder.listFiles();
        if (files == null) {
            System.out.println("Source directory is empty or an error occurred while accessing it.");
            return;
        }

        for (AbstractPart part : configParts) {
            for (File file : files) {
                if (!file.isFile()) continue;

                Path targetFilePath;
                int signs = part.getNumberEDT().startsWith("ZM") ? 13 : 16;

//                if (part.getNumberEDT().startsWith("ZM")) {
//                    signs = 13;
//                } else if (part.getNumberEDT().startsWith("NR")) {
//                    signs =
//                }

                if (file.getName().endsWith("DWG") && file.getName().startsWith(part.getNumberEDT())) {
                    String configPartName = part.toString();
                    targetFilePath = Paths.get(targetFolder.getPath(), configPartName);
                } else if ((file.getName().endsWith("PDF") || file.getName().endsWith("pdf"))
                        && file.getName().startsWith(part.getNumberEDT().substring(0, signs))) {
                    targetFilePath = Paths.get(targetFolder.getPath(), file.getName());
                } else {
                    continue;
                }

                copyFile(file, targetFilePath, outputArea);
            }
        }
    }

    private static void copyFile(File file, Path targetFilePath, JTextArea outputArea) {
        try {
            Path sourceFilePath = file.toPath();

            if (!Files.exists(targetFilePath)) {
                Files.copy(sourceFilePath, targetFilePath);
                outputArea.append("Skopiowano plik: " + targetFilePath.getFileName() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Nie udało się skopiować pliku: " + file.getName());
            e.printStackTrace();
        }
    }

    private static List<AbstractPart> getFiles(AbstractConfig abstractConfig, List<AbstractPart> parts, String materialType) {
        List<AbstractPart> filteredByQuantity = filterByQuantity(parts);
        List<AbstractPart> filteredByMaterial = filterByMaterial(filteredByQuantity, materialType);

        if (MaterialType.A304.toString().equals(abstractConfig.getMaterial()) || MaterialType.A316.toString().equals(abstractConfig.getMaterial())) {
            return filteredByMaterial;
        } else {
            return filteredByMaterial.stream()
                    .filter(part -> !part.getNumberEDT().startsWith("ZM"))
                    .toList();
        }
    }

    private static List<AbstractPart> filterByQuantity(List<AbstractPart> parts) {
        return parts.stream()
                .filter(part -> part.getQuantity() != 0)
                .toList();
    }

    private static List<AbstractPart> filterByMaterial(List<AbstractPart> parts, String materialType) {
        if (MaterialType.SHEET.toString().equals(materialType)) {
            return parts.stream()
                    .filter(part -> MaterialType.S235.toString().equals(part.getMaterial()) ||
                            MaterialType.DX51D.toString().equals(part.getMaterial()) ||
                            MaterialType.A304.toString().equals(part.getMaterial()))
                    .toList();
        } else {
            return parts.stream()
                    .filter(part -> materialType.equals(part.getMaterial()))
                    .toList();
        }
    }
}
