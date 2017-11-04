package com.piles.control.service.impl;

import com.piles.control.entity.RemoteStartRequest;
import com.piles.control.service.IRemoteStartService;
import org.springframework.stereotype.Service;

@Service
public class RemoteStartServiceImpl implements IRemoteStartService {
    @Override
    public boolean remoteStart(RemoteStartRequest remoteStartRequest) {
        return false;
    }
}
