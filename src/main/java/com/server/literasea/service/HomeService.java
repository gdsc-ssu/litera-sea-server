package com.server.literasea.service;

import com.server.literasea.dto.BadgeInfoDto;
import com.server.literasea.dto.BoatInfoDto;
import com.server.literasea.dto.ResponseMainPageDto;
import com.server.literasea.entity.Badge;
import com.server.literasea.entity.Inventory;
import com.server.literasea.entity.Users;
import com.server.literasea.exception.UsersException;
import com.server.literasea.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.server.literasea.exception.UsersExceptionType.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class HomeService {
    //repository-----------------------------------------------------------------
    private final UserRepository userRepository;

    //공용------------------------------------------------------------------------
    //TODO: 토큰에서 유저 아이디 빼기
    private Long getUserIdByToken(String token) {
        return 1l;
    }

    ;

    private Users findUsersById(Long usersId) {
        return userRepository.findById(usersId)
                .orElseThrow(() -> new UsersException(NOT_FOUND));
    }

    private Inventory getInventoryByUsers(Users users) {
        return users.getInventory();
    }

    //badgeInfo----------------------------------------------------------
    @Transactional(readOnly = true)
    public List<BadgeInfoDto> getBadgeInfoDtoListByUsers(Users users) {
        Inventory usersInventory = getInventoryByUsers(users);
        List<Badge> usersBadges = getBadgeListByInventory(usersInventory);
        return getBadgeDtoListByBadgeList(usersBadges);
    }

    private List<BadgeInfoDto> getBadgeDtoListByBadgeList(List<Badge> badgeList) {
        List<BadgeInfoDto> badgeInfoDtoList = new ArrayList<>();
        for (Badge badge : badgeList) {
            badgeInfoDtoList.add(BadgeInfoDto.badgeToDto(badge));
        }
        return badgeInfoDtoList;
    }

    private List<Badge> getBadgeListByInventory(Inventory inventory) {
        return inventory.getBadges();
    }


    //main-----------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public ResponseMainPageDto getMainPageDtoByUsers(Users users) {
        return Users.usersToDto(users);
    }

    //boatInfo-------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public BoatInfoDto getBoatInfoDto(Users users) {
        Inventory usersInventory = getInventoryByUsers(users);
        return getBoatInfoDtoByInventory(usersInventory);
    }

    private BoatInfoDto getBoatInfoDtoByInventory(Inventory inventory) {
        return BoatInfoDto.InventoryToBoatDto(inventory);
    }
    //-------------------------------------------------------------------------
}
