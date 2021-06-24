package com.cc.web.set;

import com.cc.web.entity.SetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetInfoService {

    @Autowired
    private SetInfoRepository setInfoRepository;

    /**
     * Check if the set exists in the database
     * @param code the set code
     * @return true if the set exists, false in other case
     */
    public boolean existsByCode(String code) {
        return setInfoRepository.existsByCode(code);
    }

    /**
     * Stores the set in the database
     * @param set the set to save
     */
    public void save(SetInfo set) {
        setInfoRepository.save(set);
    }

    /**
     * Get all sets
     * @return the list of sets
     */
    public List<SetInfo> getAllSets() {
        return setInfoRepository.findAll();
    }
}
