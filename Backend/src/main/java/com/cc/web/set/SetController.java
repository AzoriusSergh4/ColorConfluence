package com.cc.web.set;

import com.cc.web.entity.SetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/set")
public class SetController {

    @Autowired
    private SetInfoService setInfoService;

    @GetMapping("/all")
    public List<SetInfo> getAllSets(){
        return setInfoService.getAllSets();
    }
}
