package thaumic.tinkerer.common.compat;

import crazypants.enderio.machine.farm.farmers.FarmersCommune;

/**
 * Created by katsw on 02/02/2016.
 */
public class EnderIO {

    public static void registerPlanters() {
        FarmersCommune.joinCommune(new InfusedSeedsPlanter());
    }
}
