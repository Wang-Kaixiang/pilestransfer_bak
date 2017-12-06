package com.piles.test;

import com.piles.record.entity.UploadRecordRequest;
import com.piles.record.service.IUploadRecordService;
import org.springframework.stereotype.Service;

@Service
public class UploadRecordService implements IUploadRecordService {
    @Override
    public boolean uploadRecord(UploadRecordRequest uploadRecordRequest) {
        return true;
    }
}
