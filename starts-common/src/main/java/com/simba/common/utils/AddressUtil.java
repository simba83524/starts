package com.simba.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @author yong
 * @date 2019/6/12
 * @description 根据IP地址获取城市
 */
@Slf4j
public class AddressUtil {

    private static final String IPDB_FILE = "ip2region.db";

    public static String getCityInfo(String ip) {
        DbSearcher searcher = null;
        try {
            Resource resource = new ClassPathResource(IPDB_FILE);
            File file = resource.getFile();
            if (file.exists()) {
                log.debug("成功读取到ip2region.db");
                DbConfig config = new DbConfig();
                searcher = new DbSearcher(config, file.getPath());
                Method method = searcher.getClass().getMethod("btreeSearch", String.class);
                if (!Util.isIpAddress(ip)) {
                    log.debug("地址识别信息:  Invalid ip address！");
                }
                DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
                return dataBlock.getRegion();
            }
        } catch (Exception e) {
            log.error("获取地址信息异常：{}", e.getLocalizedMessage());
        }
        return "XX.XX.XX.XX";
    }

    public static void main(String[] args) {

        System.out.println(getCityInfo("115.218.10.198"));
    }
}
