package com.livemap.live.services;

import com.livemap.live.domain.entity.LiveMap;
import com.livemap.live.domain.repository.LiveMapRepository;
import com.livemap.live.dto.RequestModel;
import org.springframework.stereotype.Component;

@Component
public class LiveMapService {

    private final LiveMapRepository liveMapRepository;

    public LiveMapService(LiveMapRepository liveMapRepository) {
        this.liveMapRepository = liveMapRepository;
    }

    public LiveMap create(RequestModel request){;
        LiveMap liveMap = LiveMap.create(request);
        return liveMap;
//        return liveMapRepository.save(liveMap);
    }

//    public LiveMap update(LiveMap liveMap){;
//        return liveMapRepository.save(liveMap);
//    }
}
