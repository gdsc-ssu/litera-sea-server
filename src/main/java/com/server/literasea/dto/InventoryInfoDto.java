package com.server.literasea.dto;

import com.server.literasea.entity.Inventory;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InventoryInfoDto {
    private int boatA;
    private int boatB;

    public static InventoryInfoDto ToDto(Inventory inventory){
        return InventoryInfoDto.builder()
                .boatA(inventory.getBoatA())
                .boatB(inventory.getBoatB())
                .build();
    }
}
