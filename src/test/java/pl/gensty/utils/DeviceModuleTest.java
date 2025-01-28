package pl.gensty.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.gensty.configuration.ConfigNPK;
import pl.gensty.configuration.ConfigOther;
import pl.gensty.configuration.ConfigSPR;
import pl.gensty.manager.FileManager;
import pl.gensty.enums.Module;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DeviceModuleTest {
    private ConfigNPK configNPKwithAllModules;
    private ConfigNPK configNPKwithoutSOandPO;
    private ConfigSPR configSPR;
    private ConfigOther configZPR;
    private ConfigOther configNZZ;
    private ConfigOther configRDA;
    private ConfigOther configRTSP;

//    @BeforeEach
//    void setUp() {
//        configNPKwithAllModules = new ConfigNPK.Builder()
//                .type("NPK")
//                .order("001")
//                .size("NPK-100")
//                .material("DX51D")
//                .deviceQuantity(1)
//                .feetType("STS")
//                .filling("ZD")
//                .isVentingSegment(true)
//                .isMaintenancePlatform(true)
//                .build();
//
//        configNPKwithoutSOandPO = new ConfigNPK.Builder()
//                .type("NPK")
//                .order("001")
//                .size("NPK-100")
//                .material("DX51D")
//                .deviceQuantity(1)
//                .feetType("STS")
//                .filling("ZJ")
//                .isVentingSegment(false)
//                .isMaintenancePlatform(false)
//                .build();
//
//        // Prepare test data for ConfigSPR
//        configSPR = new ConfigSPR.Builder()
////                .type("SPR")
////                .order("002")
////                .size("size2")
////                .material("material2")
////                .deviceQuantity(3)
////                .chainSupport("ROL")
//                .build();
//
//        configZPR = new ConfigOther.Builder()
//                .type("ZPR")
//                .order("003")
//                .size("ZPR-030")
//                .material("DX51D")
//                .deviceQuantity(2)
//                .driveType("ELEKTRYCZNY")
//                .build();
//
//        configNZZ = new ConfigOther.Builder()
//                .type("NZZ")
//                .order("004")
//                .size("NZZ-150")
//                .material("DX51D")
//                .deviceQuantity(2)
//                .driveType("ELEKTRYCZNY")
//                .build();
//
//        configRDA = new ConfigOther.Builder()
//                .type("RDA")
//                .order("005")
//                .size("RDA-200")
//                .material("A304")
//                .deviceQuantity(10)
//                .driveType("PNEUMATYCZNY")
//                .build();
//
//        configRTSP = new ConfigOther.Builder()
//                .type("RTSP")
//                .order("006")
//                .size("RTSP-300")
//                .material("S235")
//                .deviceQuantity(15)
//                .driveType("MANUALNY")
//                .build();
//    }

//    @Test
//    void testDeviceModulesForConfigNPKwithAllModules() {
//        List<String> modules = FileManager.deviceModules(configNPKwithAllModules);
//
//        assertTrue(modules.contains(Module.GL.toString()));
//        assertTrue(modules.contains(Module.RT.toString()));
//        assertTrue(modules.contains(Module.RO.toString()));
//        assertTrue(modules.contains(Module.RWM_S.toString()));
//        assertTrue(modules.contains(Module.STS.toString()));
//        assertTrue(modules.contains(Module.SO.toString()));
//        assertTrue(modules.contains(Module.PO.toString()));
//    }
//
//    @Test
//    void testDeviceModulesForConfigNPKwithoutSOandPO() {
//        List<String> modules = FileManager.deviceModules(configNPKwithoutSOandPO);
//
//        assertTrue(modules.contains(Module.GL.toString()));
//        assertTrue(modules.contains(Module.RT.toString()));
//        assertTrue(modules.contains(Module.RO.toString()));
//        assertTrue(modules.contains(Module.RWM.toString()));
//        assertTrue(modules.contains(Module.STS.toString()));
//        assertTrue(modules.contains(Module.SO.toString()));
//        assertTrue(modules.contains(Module.PO.toString()));
//    }
//
//    @Test
//    void testDeviceModulesForConfigSPR() {
//        List<String> modules = FileManager.deviceModules(configSPR);
//
//        assertTrue(modules.contains(ModuleSPR.SN.toString()));
//        assertTrue(modules.contains(ModuleSPR.SZ.toString()));
//        assertTrue(modules.contains(ModuleSPR.KP.toString()));
//    }
//
//
//
//    @Test
//    void testDeviceModulesForConfigZPR() {
//        List<String> modules = FileManager.deviceModules(configZPR);
//
//        assertTrue(modules.contains(configZPR.getType()));
//    }
//
//    @Test
//    void testDeviceModulesForConfigNZZ() {
//        List<String> modules = FileManager.deviceModules(configNZZ);
//
//        assertTrue(modules.contains(configNZZ.getType()));
//    }
//
//    @Test
//    void testDeviceModulesForConfigRDA() {
//        List<String> modules = FileManager.deviceModules(configRDA);
//
//        assertTrue(modules.contains(configRDA.getType()));
//    }
//
//    @Test
//    void testDeviceModulesForConfigRTSP() {
//        List<String> modules = FileManager.deviceModules(configRTSP);
//
//        assertTrue(modules.contains(configRTSP.getType()));
//    }
}

