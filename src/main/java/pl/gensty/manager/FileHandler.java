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
import java.util.Map;
import java.util.Objects;

import static pl.gensty.utils.ExcelReader.readPartsFromConfig;
import static pl.gensty.utils.ExcelReader.readPaths;

public class FileHandler {
    private final PathHandler pathHandler;
    private final JTextArea outputArea;

    public FileHandler(PathHandler pathHandler, JTextArea outputArea) {
        this.pathHandler = pathHandler;
        this.outputArea = outputArea;
    }

    public void copyFiles(AbstractConfig abstractConfig, Module module, String targetPath, MaterialType materialType) {
        String excelPath = pathHandler.getExcelPath();
        Map<String, String> paths = readPaths(excelPath);
        String sourcePath = pathHandler.getSourcePath(abstractConfig, module, paths);
        List<AbstractPart> parts = readPartsFromConfig(excelPath, abstractConfig, module);

        List<AbstractPart> configParts = getFiles(abstractConfig, parts, materialType.toString());

        File sourceFolder = new File(sourcePath);
        File targetFolder = new File(targetPath);

        if (!isValidSourceAndTargetFolders(sourceFolder, targetFolder)) {
            return;
        }

        File[] files = Objects.requireNonNullElse(sourceFolder.listFiles(),new File[0]);


        for (AbstractPart part : configParts) {
            processPartFiles(part, files, targetFolder);
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
        boolean isSheetMaterial = MaterialType.SHEET.name().equals(materialType);
        return parts.stream()
                .filter(part -> isSheetMaterial
                    ? isSheetMaterial(part.getMaterial())
                    : materialType.equals(part.getMaterial()))
                .toList();


//        if (MaterialType.SHEET.toString().equals(materialType)) {
//            return parts.stream()
//                    .filter(part -> MaterialType.S235.toString().equals(part.getMaterial()) ||
//                            MaterialType.DX51D.toString().equals(part.getMaterial()) ||
//                            MaterialType.A304.toString().equals(part.getMaterial()))
//                    .toList();
//        } else {
//            return parts.stream()
//                    .filter(part -> materialType.equals(part.getMaterial()))
//                    .toList();
//        }
    }

    private static boolean isSheetMaterial(String materialType) {
        return switch (materialType) {
            case "A304", "A316", "DX51D", "S235" -> true;
            default -> false;
        };
    }

    private void processPartFiles(AbstractPart part, File[] files, File targetFolder) {
        for (File file : files) {
            if (!file.isFile()) continue;

            Path targetFilePath = getTargetFilePath(file, part, targetFolder);
            if (targetFilePath == null) continue;

            copyFile(file, targetFilePath);
        }
    }

    private Path getTargetFilePath(File file, AbstractPart part, File targetFolder) {
        int signs = part.getNumberEDT().startsWith("ZM") ? 13 : 16;

        if ((isFileExtension(file, "dwg") || isFileExtension(file, "dxf")) && file.getName().startsWith(part.getNumberEDT())) {
            return Paths.get(targetFolder.getPath(), part.toString());
        } else if (isFileExtension(file, "pdf") && file.getName().startsWith(part.getNumberEDT().substring(0, signs))) {
            return Paths.get(targetFolder.getPath(), file.getName());
        }

        return null;
    }

    private void copyFile(File file, Path targetFilePath) {
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

    private boolean isValidSourceAndTargetFolders(File sourceFolder, File targetFolder) {
        return sourceFolder.isDirectory() && targetFolder.exists();
    }

    private boolean isFileExtension(File file, String fileExtension) {
        return (file.getName().endsWith(fileExtension.toUpperCase()) || file.getName().endsWith(fileExtension.toLowerCase()));
    }
}
