package org.jmisb.api.klv.st0601.dto;

/**
 * Weapon Store data.
 *
 * <p>This class supports the ST0601 WeaponsStores implementation.
 */
public class WeaponStore {
    private int stationId;
    private int hardpointId;
    private int carriageId;
    private int storeId;
    private WeaponStoreStatus status;
    private boolean fuzeEnabled;
    private boolean laserEnabled;
    private boolean targetEnabled;
    private boolean weaponArmed;
    private String storeType;

    /**
     * Get the station identifier.
     *
     * <p>In this case, this is not a "weapon station" - see hardpoint. Instead it is the "payload
     * station" in the sense used by STANAG 4586 (see AEP-84) for the stores management system
     * (SMS).
     *
     * @return integer identifier for the station identifier.
     */
    public int getStationId() {
        return stationId;
    }

    /**
     * Set the station identifier.
     *
     * <p>In this case, this is not a "weapon station" - see hardpoint. Instead it is the "payload
     * station" in the sense used by STANAG 4586 (see AEP-84) for the stores management system
     * (SMS).
     *
     * @param stationId integer identifier for the station identifier.
     */
    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    /**
     * Get the hardpoint identifier.
     *
     * <p>This corresponds to the aircraft hardpoint where the stores suspension equipment starts
     * (e.g. a wing pylon, or bay location). Some platforms would call this a weapons station.
     *
     * @return the hardpoint identifier.
     */
    public int getHardpointId() {
        return hardpointId;
    }

    /**
     * Set the hardpoint identifier.
     *
     * <p>This corresponds to the aircraft hardpoint where the stores suspension equipment starts
     * (e.g. a wing pylon, or bay location). Some platforms would call this a weapons station.
     *
     * @param hardpointId the hardpoint identifier.
     */
    public void setHardpointId(int hardpointId) {
        this.hardpointId = hardpointId;
    }

    /**
     * Get the carriage identifier.
     *
     * <p>This corresponds to something between the hardpoint and the store-specific suspension
     * equipment, such as a dual-position rack (e.g. BRU-55/A).
     *
     * @return the carrriage identifier.
     */
    public int getCarriageId() {
        return carriageId;
    }

    /**
     * Set the carriage identifier.
     *
     * <p>This corresponds to something between the hardpoint and the store-specific suspension
     * equipment, such as a dual-position rack (e.g. BRU-55/A).
     *
     * @param carriageId the carrriage identifier.
     */
    public void setCarriageId(int carriageId) {
        this.carriageId = carriageId;
    }

    /**
     * Get the store identifier.
     *
     * <p>This corresponds to a position on store-specific suspension equipment, such as a launcher
     * or miniature store rack.
     *
     * @return the store identifier.
     */
    public int getStoreId() {
        return storeId;
    }

    /**
     * Set the store identifier.
     *
     * <p>This corresponds to a position on store-specific suspension equipment, such as a launcher
     * or miniature store rack.
     *
     * @param storeId the store identifier.
     */
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    /**
     * Get the general status of the store.
     *
     * @return store status (enumerated value).
     */
    public WeaponStoreStatus getStatus() {
        return status;
    }

    /**
     * Set the general status of the store.
     *
     * @param status store status (enumerated value).
     */
    public void setStatus(WeaponStoreStatus status) {
        this.status = status;
    }

    /**
     * Check if fuze engagement status is set.
     *
     * @return true if Fuze functions are set, otherwise false.
     */
    public boolean isFuzeEnabled() {
        return fuzeEnabled;
    }

    /**
     * Set fuze engagement status.
     *
     * @param fuzeEnabled true if Fuze functions are set, otherwise false.
     */
    public void setFuzeEnabled(boolean fuzeEnabled) {
        this.fuzeEnabled = fuzeEnabled;
    }

    /**
     * Check if laser engagement status is set.
     *
     * @return true if Laser functions are set, otherwise false.
     */
    public boolean isLaserEnabled() {
        return laserEnabled;
    }

    /**
     * Set laser engagement status.
     *
     * @param laserEnabled true if Laser functions are set, otherwise false.
     */
    public void setLaserEnabled(boolean laserEnabled) {
        this.laserEnabled = laserEnabled;
    }

    /**
     * Check if target engagement status is set.
     *
     * @return true if target functions are set, otherwise false.
     */
    public boolean isTargetEnabled() {
        return targetEnabled;
    }

    /**
     * Set target engagement status.
     *
     * @param targetEnabled true if target functions are set, otherwise false.
     */
    public void setTargetEnabled(boolean targetEnabled) {
        this.targetEnabled = targetEnabled;
    }

    /**
     * Check if weapon is armed in engagement status.
     *
     * <p>ST0601 suggests this is "master arm", but it probably is intended to be weapon specific.
     *
     * @return true if weapon is armed, otherwise false.
     */
    public boolean isWeaponArmed() {
        return weaponArmed;
    }

    /**
     * Set weapon arm status in engagement status.
     *
     * <p>ST0601 suggests this is "master arm", but it probably is intended to be weapon specific.
     *
     * @param weaponArmed true if weapon is armed, otherwise false.
     */
    public void setWeaponArmed(boolean weaponArmed) {
        this.weaponArmed = weaponArmed;
    }

    /**
     * Get the type of store.
     *
     * <p>This is the "name" of the store or weapon type (e.g. "Harpoon", or "GBU-15").
     *
     * @return string representing the store type.
     */
    public String getStoreType() {
        return storeType;
    }

    /**
     * Set the type of store.
     *
     * <p>This is the "name" of the store or weapon type (e.g. "Harpoon", or "GBU-15").
     *
     * @param storeType string representing the store type.
     */
    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }
}
