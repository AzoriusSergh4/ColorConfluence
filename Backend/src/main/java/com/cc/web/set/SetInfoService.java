package com.cc.web.set;

import com.cc.web.entity.SetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetInfoService {

    @Autowired
    private SetInfoRepository setInfoRepository;

    public boolean existsByCode(String code){
        return setInfoRepository.existsByCode(code);
    }

    public void save(SetInfo set){
        setInfoRepository.save(set);
    }

    public List<SetInfo> getAllSets() {
        return setInfoRepository.findAll();
    }
}
