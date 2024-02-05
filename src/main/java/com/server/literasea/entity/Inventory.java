package com.server.literasea.entity;

import com.server.literasea.dto.BadgeInfoDto;
import com.server.literasea.dto.BoatInfoDto;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
public class Inventory {
    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<Badge> badges;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="inventory_id")
    Long id;
    @Column
    private int boatA;  //돗단배 조각

    @Column
    private int boatB;  //종이배 조각

    public void addBadge(final Badge badge){  //인벤토리에 뱃지 추가하고, 해당 뱃지에 인벤토리 할당
        this.badges.add(badge);
        badge.setInventory(this);
    }

    public List<BadgeInfoDto> getBadgeInfoDtos(){  //이거때문에 DTO써야하는건가봐
        List<BadgeInfoDto> dtos=new ArrayList<>();
        for(int i=0;i<this.badges.size();i++){
            dtos.add(Badge.to(this.badges.get(i)));
        }
        return dtos;
    }

    public static BoatInfoDto to(Inventory inventory){
        return BoatInfoDto.builder()
                .boatA(inventory.boatA)
                .boatB(inventory.boatB)
                .build();
    }
}
