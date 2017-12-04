package com.piles.test;

import com.piles.Util;
import com.piles.setting.entity.UpdatePackageRequest;
import com.piles.setting.entity.UpdatePackageResponse;
import com.piles.setting.service.IRequestUpdatePackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class RequestUpdatePackageServiceImpl implements IRequestUpdatePackageService {
    @Override
    public UpdatePackageResponse getUpdatePackage(UpdatePackageRequest updatePackageRequest) {
        log.info( "进入请求升级包service" );
        int index = updatePackageRequest.getIndex();
        log.info( "请求升级包当前索引:{}", index );

        String pileNo = updatePackageRequest.getPileNo();
        log.info( "请求升级包当前桩号:{}", pileNo );
        UpdatePackageResponse response = new UpdatePackageResponse();
        Integer limit= Util.map.get( pileNo );
        File file=new File( "/piletransfer/soft/AcOneV2.12.bin" );
        if (file.exists()){
            response.setResult( 0 );
            response.setCurrentIndex( index );
            response.setTotalIndex( 5 );
            response.setCurrentSegmentLen( 10 );
            response.setActualContent( new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0} );
            long total=file.getTotalSpace();
            total/limit
        }


        log.info( "请求升级包请求数据:{}", response.toString() );
        return response;
    }
}
