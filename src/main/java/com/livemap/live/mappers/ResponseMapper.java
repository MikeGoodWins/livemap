package com.livemap.live.mappers;

import com.livemap.live.domain.entity.LiveMap;
import com.livemap.live.dto.ResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

    @Mapping(target = "dataMap", ignore = true)
    default ResponseModel toResponse(LiveMap liveMap) {
        ResponseModel rp = new ResponseModel();
        rp.setId(liveMap.getId());
        rp.setBirthday(liveMap.getBirthday());
        rp.setPhone(liveMap.getPhone());
        rp.setCreated(liveMap.getCreated());
        return rp;
    }

    ;
}
