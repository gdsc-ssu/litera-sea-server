package com.server.literasea.service;

import com.server.literasea.dto.BadgeInfoDto;
import com.server.literasea.dto.BoatInfoDto;
import com.server.literasea.dto.ResponseMainPageDto;
import com.server.literasea.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HomeService {
    public ResponseMainPageDto getResponseMainPageDto(Users users){
        return Users.to(users);
    }

    public List<BadgeInfoDto> getBadgeInfoDtoList(Users users){
        return users.getBadges(users);
    }

    public BoatInfoDto getBoatInfoDto(Users users){
        return users.getBoatInfoDto();
    }
}
