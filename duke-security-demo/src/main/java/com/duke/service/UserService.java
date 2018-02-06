package com.duke.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duke on 2017/12/25
 */
@Service
@Transactional
public class UserService {

    public boolean exist(String id) {
        return false;
    }
}
