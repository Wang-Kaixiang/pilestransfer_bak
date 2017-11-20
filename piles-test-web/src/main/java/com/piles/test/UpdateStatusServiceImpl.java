package com.piles.test;

import com.piles.setting.entity.UpdateStatusRequest;
import com.piles.setting.service.IUpdateStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UpdateStatusServiceImpl implements IUpdateStatusService {


    @Override
    public void updateStatus(UpdateStatusRequest updateStatusRequest) {
        log.info("进入升级状态汇报接口" + updateStatusRequest.toString());

    }
}
