package com.server.literasea.entity;

import com.server.literasea.dto.BadgeInfoDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Entity
@Builder
@Getter
public class Badge {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="badge_id")
    Long id;



    @Column
    private String name;
    @Column
    private String image;

    public void setInventory(final Inventory inventory){  //TODO:Setter안쓰고 싶었는데...이것만 쓰자
        this.setInventory(inventory);
    }
}
