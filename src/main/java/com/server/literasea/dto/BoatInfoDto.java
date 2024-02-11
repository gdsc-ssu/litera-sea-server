package com.server.literasea.dto;

import com.server.literasea.entity.Inventory;
import lombok.Builder;

@Builder
public class BoatInfoDto {
    private int boatA;
    private int boatB;

    public static BoatInfoDto InventoryToBoatDto(Inventory inventory){
        return BoatInfoDto.builder()
                .boatA(inventory.getBoatA())
                .boatB(inventory.getBoatB())
                .build();
    }
}
