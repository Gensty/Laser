package pl.gensty.manager;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.strategy.FactoryConfig;
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
            fileHandler.copyFiles(abstractConfig, module, targetPath, materialType);
            folderHandler.deleteFolder(targetPath);
        }
    }
}