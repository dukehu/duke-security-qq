package com.duke.service;

import com.duke.domain.Demo;
import com.duke.mapper.DemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created duke on 2017/12/30
 */
@Service
@Transactional
public class DemoService {

    @Autowired
    private DemoMapper demoMapper;

    public Demo selectOne(String id) {
        return demoMapper.selectOne(id);
    }

}
