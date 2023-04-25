package com.doctory.web.rest;

import com.doctory.web.request.BranchRequest;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/branch")
public class BranchController {


    @PostMapping
    public void addBranch(@Valid @RequestBody BranchRequest branchRequest) {

    }
}
