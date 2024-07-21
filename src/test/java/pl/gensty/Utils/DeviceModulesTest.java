package pl.gensty.Utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.gensty.Configuration.ConfigNPK;
import pl.gensty.Configuration.ConfigOther;
import pl.gensty.Configuration.ConfigSPR;
import pl.gensty.Enums.ModuleNPK;
import pl.gensty.Enums.ModuleSPR;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DeviceModulesTest {
    private ConfigNPK configNPKwithAllModules;
    private ConfigNPK configNPKwithoutSOandPO;
    private ConfigSPR configSPR;
    private ConfigOther configZPR;
    private ConfigOther configNZZ;
    private ConfigOther configRDA;
    private ConfigOther configRTSP;

    @BeforeEach
    void setUp() {
        configNPKwithAllModules = new ConfigNPK.Builder()
                .type("NPK")
                .order("001")
                .size("NPK-100")
                .material("DX51D")
                .deviceQuantity(1)
                .feetType("STS")
                .isVentingSegment(true)
                .isMaintenancePlatform(true)
                .build();

        configNPKwithoutSOandPO = new ConfigNPK.Builder()
                .type("NPK")
                .order("001")
                .size("NPK-100")
                .material("DX51D")
                .deviceQuantity(1)
                .feetType("STS")
                .isVentingSegment(false)
                .isMaintenancePlatform(false)
                .build();

        // Prepare test data for ConfigSPR
        configSPR = new ConfigSPR.Builder()
//                .type("SPR")
//                .order("002")
//                .size("size2")
//                .material("material2")
//                .deviceQuantity(3)
//                .chainSupport("ROL")
                .build();

        configZPR = new ConfigOther.Builder()
                .type("ZPR")
                .order("003")
                .size("ZPR-030")
                .material("DX51D")
                .deviceQuantity(2)
                .driveType("ELEKTRYCZNY")
                .build();

        configNZZ = new ConfigOther.Builder()
                .type("NZZ")
                .order("004")
                .size("NZZ-150")
                .material("DX51D")
                .deviceQuantity(2)
                .driveType("ELEKTRYCZNY")
                .build();

        configRDA = new ConfigOther.Builder()
                .type("RDA")
                .order("005")
                .size("RDA-200")
                .material("A304")
                .deviceQuantity(10)
                .driveType("PNEUMATYCZNY")
                .build();

        configRTSP = new ConfigOther.Builder()
                .type("RTSP")
                .order("006")
                .size("RTSP-300")
                .material("S235")
                .deviceQuantity(15)
                .driveType("MANUALNY")
                .build();
    }

    @Test
    void testDeviceModulesForConfigNPKwithAllModules() {
        List<String> modules = FileUtils.deviceModules(configNPKwithAllModules);

        assertTrue(modules.contains(ModuleNPK.GL.toString()));
        assertTrue(modules.contains(ModuleNPK.RT.toString()));
        assertTrue(modules.contains(ModuleNPK.RO.toString()));
        assertTrue(modules.contains(ModuleNPK.RWM.toString()));
        assertTrue(modules.contains(ModuleNPK.STS.toString()));
        assertTrue(modules.contains(ModuleNPK.SO.toString()));
        assertTrue(modules.contains(ModuleNPK.PO.toString()));
    }

    @Test
    void testDeviceModulesForConfigNPKwithoutSOandPO() {
        List<String> modules = FileUtils.deviceModules(configNPKwithoutSOandPO);

        assertTrue(modules.contains(ModuleNPK.GL.toString()));
        assertTrue(modules.contains(ModuleNPK.RT.toString()));
        assertTrue(modules.contains(ModuleNPK.RO.toString()));
        assertTrue(modules.contains(ModuleNPK.RWM.toString()));
        assertTrue(modules.contains(ModuleNPK.STS.toString()));
        assertTrue(modules.contains(ModuleNPK.SO.toString()));
        assertTrue(modules.contains(ModuleNPK.PO.toString()));
    }

    @Test
    void testDeviceModulesForConfigSPR() {
        List<String> modules = FileUtils.deviceModules(configSPR);

        assertTrue(modules.contains(ModuleSPR.SN.toString()));
        assertTrue(modules.contains(ModuleSPR.SZ.toString()));
        assertTrue(modules.contains(ModuleSPR.KP.toString()));
    }



    @Test
    void testDeviceModulesForConfigZPR() {
        List<String> modules = FileUtils.deviceModules(configZPR);

        assertTrue(modules.contains(configZPR.getType()));
    }

    @Test
    void testDeviceModulesForConfigNZZ() {
        List<String> modules = FileUtils.deviceModules(configNZZ);

        assertTrue(modules.contains(configNZZ.getType()));
    }

    @Test
    void testDeviceModulesForConfigRDA() {
        List<String> modules = FileUtils.deviceModules(configRDA);

        assertTrue(modules.contains(configRDA.getType()));
    }

    @Test
    void testDeviceModulesForConfigRTSP() {
        List<String> modules = FileUtils.deviceModules(configRTSP);

        assertTrue(modules.contains(configRTSP.getType()));
    }
}

