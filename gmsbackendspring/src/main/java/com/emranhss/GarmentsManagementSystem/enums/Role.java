package com.emranhss.GarmentsManagementSystem.enums;

public enum Role {
    ADMIN,
    MERCHANDISER,
    STORE_MANAGER,
    PURCHASE_MANAGER,
    CUTTING_MANAGER,
    SEWING_MANAGER,
    FINISHING_MANAGER,
    PACKING_MANAGER,
    PRODUCTION_MANAGER,
    SHIPMENT_EXECUTIVE;

    // Returns Spring Security compatible authority string
    public String getAuthority() {
        return "ROLE_" + this.name();
    }

}
