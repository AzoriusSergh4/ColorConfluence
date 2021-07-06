package com.cc.web.set;

import com.cc.web.entity.SetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://azoriussergh4.github.io"})
@RestController
@RequestMapping("/api/sets")
public class SetController {

    @Autowired
    private SetInfoService setInfoService;

    @GetMapping("")
    public List<SetInfo> getAllSets() {
        return setInfoService.getAllSets();
    }
}
