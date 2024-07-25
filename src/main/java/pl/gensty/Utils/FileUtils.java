package pl.gensty.Utils;

import pl.gensty.Configuration.*;
import pl.gensty.DevicePart.*;
import pl.gensty.Enums.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pl.gensty.Utils.ExcelUtils.*;
import static pl.gensty.Utils.PathUtils.*;


public class FileUtils {
    public static void prepareLaser(DeviceType deviceType, MaterialType materialType, JTextArea outputArea, JTextField excelPathField, JTextField catalogPathField) {
        String excelPath = getExcelPath(outputArea, excelPathField);
        Optional<AbstractConfig> configOpt = readLaserConfigFromExcel(excelPath, deviceType);

        if (configOpt.isEmpty()) {
            throw new RuntimeException("Konfiguracja niedostępna!");
        }

        AbstractConfig abstractConfig = configOpt.get();
        List<String> modules = deviceModules(abstractConfig);

        for (String module : modules) {
            String targetPath = createNewFolder(abstractConfig, module, materialType, outputArea, excelPathField, catalogPathField);
            copyFiles(deviceType, abstractConfig, module, targetPath, MaterialType.SHEET, outputArea, excelPathField);
        }
    }

    private static List<String> deviceModules(AbstractConfig abstractConfig) {
        List<String> modules = new ArrayList<>();
        if (abstractConfig instanceof ConfigSPR) {
            modules.add(ModuleSPR.SN.toString());
            modules.add(ModuleSPR.SZ.toString());
            modules.add(ModuleSPR.KP.toString());
        } else if (abstractConfig instanceof ConfigNPK) {
            modules.add(ModuleNPK.GL.toString());
            modules.add(ModuleNPK.RT.toString());
            modules.add(ModuleNPK.RO.toString());
            modules.add(ModuleNPK.RWM.toString());

            if (ModuleNPK.STS.toString().equals(((ConfigNPK) abstractConfig).getFeetType())) {
                modules.add(ModuleNPK.STS.toString());
            } else {
                modules.add(ModuleNPK.STZ.toString());
            }

            if (((ConfigNPK) abstractConfig).isVentingSegment()) {
                modules.add(ModuleNPK.SO.toString());
            }
            if (((ConfigNPK) abstractConfig).isMaintenancePlatform()) {
                modules.add(ModuleNPK.PO.toString());
            }
        } else {
            modules.add(abstractConfig.getType());
        }

        return modules;
    }

    public static String createNewFolder(AbstractConfig abstractConfig, String module, MaterialType materialType, JTextArea outputArea, JTextField excelPathField, JTextField catalogPathField) {
        String targetPath = getTargetPath(outputArea, catalogPathField);

        String folderName;
        String replacement = (materialType == MaterialType.SHEET) ? "" : "_";
        replacement = (abstractConfig instanceof ConfigNPK || abstractConfig instanceof ConfigSPR) ? replacement + module : replacement;

        if (materialType == MaterialType.SHEET) {
            folderName = abstractConfig.setFolderName().replace("module", replacement);
        } else {
            folderName = abstractConfig.setFolderName(materialType).replace("module", replacement);
        }

        String folderPath = targetPath + "\\" + folderName;

        File newFolder = new File(folderPath);
        if (!newFolder.exists()) {
            if (newFolder.mkdir()) {
                outputArea.append("Folder utworzony: " + folderPath + "\n");
            } else {
                outputArea.append("Nie udało się utworzyć folderu: " + folderPath);
            }
        } else {
            outputArea.append("Folder o nazwie " + folderName + " już istnieje.");
        }

        return folderPath;
    }

    public static void copyFiles(DeviceType deviceType, AbstractConfig abstractConfig, String module, String targetPath, MaterialType materialType, JTextArea outputArea, JTextField excelPathField) {
        String excelPath = getExcelPath(outputArea, excelPathField);
        String sourcePath = getSourcePath(excelPath, abstractConfig);
        List<AbstractPart> parts = readLaserFilesFromExcel(excelPath, deviceType, abstractConfig, module).orElseGet(ArrayList::new);

        List<AbstractPart> configParts = getFilesAccToFullConfig(abstractConfig, parts, materialType.toString());
    //sprawdzić czy przenieść to do createFolder

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
                if (!file.isFile()) {
                    continue;
                }

                Path targetFilePath;
                if (file.getName().endsWith("DWG") && file.getName().startsWith(part.getNumberEDT())) {
                    String configPartName = part.toString();
                    targetFilePath = Paths.get(targetFolder.getPath(), configPartName);
                } else if (file.getName().endsWith("PDF") && file.getName().startsWith(part.getNumberEDT().substring(0, 16))) {
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

    private static List<AbstractPart> getFilesAccToFullConfig(AbstractConfig abstractConfig, List<AbstractPart> parts, String materialType) {
        List<AbstractPart> filteredByConfigParts = filterByConfig(abstractConfig, parts);
        List<AbstractPart> filteredParts = filterByMaterial(filteredByConfigParts, materialType);
        return filteredParts;
    }

    private static List<AbstractPart> filterByConfig(AbstractConfig abstractConfig, List<AbstractPart> parts) {
        if (abstractConfig instanceof ConfigNPK) {
            ConfigNPK configNPK = (ConfigNPK) abstractConfig;

            return parts.stream()
                    .filter(part -> {
                        if (part instanceof PartFeet) {
                            PartFeet partFeet = (PartFeet) part;
                            return switch (configNPK.getFilling()) {
                                case "ZJ" -> partFeet.filling1Way();
                                case "ZD" -> partFeet.filling2Way();
                                case "ZB" -> partFeet.fillingNoWay();
                                default -> false;
                            };
                        }
                        return false;
                    })
                    .toList();
        } else if (abstractConfig instanceof ConfigSPR) {
            ConfigSPR configSPR = (ConfigSPR) abstractConfig;

            return parts.stream()
                    .filter(part -> {
                        if (part instanceof PartSPR) {
                            PartSPR partSPR = (PartSPR) part;
                            return switch (configSPR.getChainSupport()) {
                                case "ROL" -> partSPR.rolls();
                                case "GDS" -> partSPR.uppedDeck();
                                case "GDT" -> partSPR.transportingUpperDeck();
                                case "LPR" -> partSPR.guideBar();
                                default -> false;
                            };
                        }
                        return false;
                    })
                    .toList();
        } else {
            ConfigOther configOther = (ConfigOther) abstractConfig;

            return parts.stream()
                    .filter(part -> {
                        if (part instanceof PartDriveType) {
                            PartDriveType partDriveType = (PartDriveType) part;
                            return switch (configOther.getDriveType()) {
                                case "ELEKTRYCZNY" -> partDriveType.isElectric();
                                case "PNEUMATYCZNY" -> partDriveType.isPneumatic();
                                case "RECZNY" -> partDriveType.isManual();
                                default -> false;
                            };
                        }
                        return false;
                    })
                    .toList();
        }
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
