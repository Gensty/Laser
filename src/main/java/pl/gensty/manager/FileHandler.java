package pl.gensty.manager;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import pl.gensty.configuration.*;
import pl.gensty.devicePart.*;
import pl.gensty.devicePart.strategy.FactoryPart;
import pl.gensty.enums.*;
import pl.gensty.enums.Module;
import pl.gensty.utils.ExcelReader;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static pl.gensty.utils.Utils.isRowEmpty;

public class FileHandler {
    private final ExcelReader excelReader;
    private final JTextArea outputArea;

    public FileHandler(ExcelReader excelReader, JTextArea outputArea) {
        this.excelReader = excelReader;
        this.outputArea = outputArea;
    }

    public void copyFiles(AbstractConfig abstractConfig, String sourcePath, String targetPath, List<AbstractPart> parts) throws IOException {
        File sourceFolder = new File(sourcePath);
        File targetFolder = new File(targetPath);

        if (!isValidSourceAndTargetFolders(sourceFolder, targetFolder)) {
            return;
        }

        copyMatchingFiles(abstractConfig, sourceFolder, targetFolder, parts);
        excelReader.closeWorkbook();
    }

    public List<AbstractPart> getElementFiles(AbstractConfig abstractConfig, String materialType, Module module) {
        List<AbstractPart> parts = getAllParts(abstractConfig, module);
        List<AbstractPart> filteredByMaterial = filterByMaterial(parts, materialType);

        return filteredByMaterial.stream()
                .filter(part -> !part.getNumberEDT().startsWith("ZM") && !part.getNumberEDT().startsWith("NR"))
                .toList();
    }

    public List<AbstractPart> getZM_NR_Files(AbstractConfig abstractConfig, String materialType, Module module) {
        List<AbstractPart> parts = getAllParts(abstractConfig, module);
        List<AbstractPart> filteredByMaterial = filterByMaterial(parts, materialType);

        return filteredByMaterial.stream()
                .filter(part -> part.getNumberEDT().startsWith("ZM") || part.getNumberEDT().startsWith("NR"))
                .toList();
    }

    private List<AbstractPart> getAllParts(AbstractConfig config, Module module) {
        List<AbstractPart> allParts = new ArrayList<>();
        if (config == null) return allParts;

        Sheet sheet = excelReader.getWorkbook().getSheet(module.toString());
        for (Row row : sheet) {
            if (row.getRowNum() < 10 || isRowEmpty(row)) continue;
            AbstractPart part = createPart(row);
            allParts.add(part);
        }
        return filterByQuantity(allParts);
    }

    private AbstractPart createPart(Row row) {
        return FactoryPart.createPart(excelReader.getPartParams(row));
    }

    private List<AbstractPart> filterByQuantity(List<AbstractPart> parts) {
        return parts.stream()
                .filter(part -> part.getQuantity() != 0)
                .toList();
    }

    private List<AbstractPart> filterByMaterial(List<AbstractPart> parts, String materialType) {
        boolean isSteelPart = MaterialType.STEEL.name().equals(materialType);
        return parts.stream()
                .filter(part -> isSteelPart
                    ? isSteelMaterial(part.getMaterial())
                    : materialType.equals(part.getMaterial()))
                .toList();
    }

    private boolean isSteelMaterial(String materialType) {
        return switch (materialType) {
            case "A304", "A316", "DX51D", "S235" -> true;
            default -> false;
        };
    }

    private void copyMatchingFiles(AbstractConfig abstractConfig, File sourceFolder, File targetFolder, List<AbstractPart> parts) {
        File[] files = Objects.requireNonNullElse(sourceFolder.listFiles(),new File[0]);

        for (AbstractPart part : parts) {
            for (File file : files) {
                if (!file.isFile()) continue;

                Path targetFilePath = getTargetFilePath(abstractConfig, file, part, targetFolder);
                if (targetFilePath == null) continue;

                copyFile(file, targetFilePath);
            }
        }
    }

    private Path getTargetFilePath(AbstractConfig abstractConfig, File file, AbstractPart part, File targetFolder) {
        Path path = null;
        String fileName = file.getName();
        String partNumber = part.getNumberEDT();

        int signs;

        if (partNumber.startsWith("ZM") || partNumber.startsWith("NR")) {
            signs = 13;
        } else {
            signs = fileNameLength(abstractConfig);
        }

        if (isMatchingDrawingFile(fileName, partNumber)) {
            path = Paths.get(targetFolder.getPath(), part.toString());
        }

        if (isMatchingPdfFile(fileName, partNumber, signs)) {
            path = Paths.get(targetFolder.getPath(), fileName);
        }

        return path;
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

    private boolean isMatchingDrawingFile(String fileName, String partNumber) {
        return (isFileExtension(fileName, "dwg") || isFileExtension(fileName, "dxf")) && fileName.startsWith(partNumber);
    }

    private boolean isMatchingPdfFile(String fileName, String partNumber, int signs) {
        return isFileExtension(fileName, "pdf") && fileName.startsWith(partNumber.substring(0, signs));
    }

    private boolean isFileExtension(String fileName, String fileExtension) {
        return (fileName.endsWith(fileExtension.toUpperCase()) || fileName.endsWith(fileExtension.toLowerCase()));
    }

    private int fileNameLength(AbstractConfig abstractConfig) {
        String config = abstractConfig.getType();
        switch (config) {
            case "ZPR", "NPK" -> {return 16;}
            default -> {return 15;}
        }
    }
}
