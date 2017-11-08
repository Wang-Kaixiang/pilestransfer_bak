package com.piles.record.service.impl;

import com.piles.record.entity.HeartBeatRequest;
import com.piles.record.service.IHeartBeatService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HeartBeatService implements IHeartBeatService {
    @Override
    public Date heartBeat(HeartBeatRequest heartBeatRequest) {

        return new Date();
    }
}
