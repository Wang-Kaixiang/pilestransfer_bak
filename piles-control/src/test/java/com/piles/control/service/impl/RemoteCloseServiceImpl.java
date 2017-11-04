package com.piles.control.service.impl;

import com.piles.control.entity.RemoteCloseRequest;
import com.piles.control.service.IRemoteCloseService;
import org.springframework.stereotype.Service;

@Service
public class RemoteCloseServiceImpl implements IRemoteCloseService {
    @Override
    public boolean remoteClose(RemoteCloseRequest remoteCloseRequest) {
        return false;
    }
}
