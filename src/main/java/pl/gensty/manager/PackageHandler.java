package pl.gensty.manager;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.strategy.FactoryConfig;
import pl.gensty.devicePart.AbstractPart;
import pl.gensty.enums.DeviceType;
import pl.gensty.enums.MaterialType;
import pl.gensty.enums.Module;

import java.io.IOException;
import java.util.List;

public class PackageHandler {
    private final FolderHandler folderHandler;
    private final FileHandler fileHandler;
    private final PathHandler pathHandler;

    public PackageHandler(FolderHandler folderHandler, FileHandler fileHandler, PathHandler pathHandler) {
        this.folderHandler = folderHandler;
        this.fileHandler = fileHandler;
        this.pathHandler = pathHandler;
    }

    public void createPackage(DeviceType deviceType, MaterialType materialType) throws IOException {
        String excelPath = pathHandler.getExcelPath();
        AbstractConfig abstractConfig = FactoryConfig.createConfig(deviceType, excelPath);
        List<Module> modules = abstractConfig.getModules();

        for (Module module : modules) {
            String targetPath = folderHandler.createNewFolder(abstractConfig, module);

            String sourcePath = pathHandler.getSourcePath(abstractConfig, module);
            List<AbstractPart> parts = fileHandler.getElementFiles(abstractConfig, materialType.toString(), module);
            fileHandler.copyFiles(abstractConfig, sourcePath, targetPath, parts);

            if (MaterialType.A304.toString().equals(abstractConfig.getMaterial()) || MaterialType.A316.toString().equals(abstractConfig.getMaterial())) {
                String zmSourcePath = pathHandler.getSourcePath("ZM");
                List<AbstractPart> zmParts = fileHandler.getZM_NR_Files(abstractConfig, materialType.toString(), module);
                fileHandler.copyFiles(abstractConfig, zmSourcePath, targetPath, zmParts);
            }

            folderHandler.deleteFolder(targetPath);
        }
    }
}