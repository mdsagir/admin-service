package com.doctory.web.rest;

import com.doctory.domain.ResponseModel;
import com.doctory.domain.SearchDto;
import com.doctory.domain.branch.dto.BranchDto;
import com.doctory.domain.branch.service.BranchService;
import com.doctory.web.request.BranchRequest;
import com.doctory.web.validator.AddBranchValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequestMapping("/api/branch")
public class BranchController {

    private final BranchService branchService;
    private final AddBranchValidator addBranchValidator;

    /**
     * Binding apply for validation
     *
     * @param webDataBinder spring boot are apply {@link  WebDataBinder} for request body validation
     */
    @InitBinder
    public void bindValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(addBranchValidator);
    }

    public BranchController(BranchService branchService, AddBranchValidator addBranchValidator) {
        this.branchService = branchService;
        this.addBranchValidator = addBranchValidator;
    }

    /**
     * {@code POST /api/branch} API create a new branch
     * <p>
     * <p>
     * Primarily validate all mandatory fields, then checks database unique constraints (given branch name existence)
     * after persist {@link BranchRequest} all property to the database and return with success message with status {@code 201 CREATED}.
     *
     * @param branchRequest payload {@link BranchRequest} Its JSON API request contract send by consumer, It's not be {@literal null}.
     * @return Response entity {@link ResponseEntity} with {@link ResponseModel} status {@code 201 (Created)}
     * @throws IllegalArgumentException              in case the given {@link BranchRequest requestBody} of its property is {@literal empty-string} or {@literal null}.
     * @throws com.doctory.common.SomethingWentWrong when anything went wrong to whole application level like database failure ...
     */
    @PostMapping
    public ResponseEntity<ResponseModel> addBranch(@Valid @RequestBody BranchRequest branchRequest) {
        var responseModel = branchService.addNewBranch(branchRequest);
        return new ResponseEntity<>(responseModel, CREATED);
    }

    /**
     * {@code GET api/branch} get branch info by id
     * <p>
     *
     * @param id input request parameter, It's not be {@literal null}.
     * @return {@link ResponseEntity} with {@link BranchDto} contain all information of particular branch with {@code 200} Success
     * @throws IllegalArgumentException                 in case the given {@literal  id parameter} of its property is {@literal empty-string} or {@literal null}
     *                                                  then validation exception are triggered and response give the {@code 400 Bad request}
     * @throws com.doctory.common.DataNotFoundException in case the given invalid {@literal  id} that no record available and response give the {@code 404 Not found}
     * @throws com.doctory.common.SomethingWentWrong    when anything went wrong to whole application level like database failure and response the {@code 500 Internal server error}
     */

    @GetMapping
    public ResponseEntity<BranchDto> getBranchInfo(@RequestParam(required = false) @NotEmpty(message = "The id must be defined") Long id) {
        var branchInfo = branchService.getBranchInfo(id);
        return new ResponseEntity<>(branchInfo, OK);
    }

    /**
     * {@code PUT api/branch} update branch info by id
     * <p>
     * Finds the existence Branch by given {@literal  id} and update Branch data by {@link BranchRequest}
     *
     * @param id       input request parameter, It's not be {@literal null}, that fetch the existing Branch details
     * @param branchRequest payload {@link BranchRequest} Its JSON API request contract send by consumer, It's not be {@literal null}.
     * @return Response entity {@link ResponseEntity} with {@link ResponseModel} status {@code 200 (SUCCESS)}
     * @throws IllegalArgumentException                 in case the given {@link BranchRequest requestBody} of its property is {@literal empty-string} or {@literal null}
     *                                                  and response give the {@code 400 Bad request}
     * @throws com.doctory.common.DataNotFoundException in case the given invalid {@literal  id} that no record available and response give the {@code 404 Not found}
     * @throws com.doctory.common.SomethingWentWrong    when anything went wrong to whole application level like database failure and response the {@code 500 Internal server error}
     */
    @PutMapping
    public ResponseEntity<ResponseModel> updateBranchInfo(@RequestParam Long id, @Valid @RequestBody BranchRequest branchRequest) {
        var responseModel = branchService.updateBranch(id, branchRequest);
        return new ResponseEntity<>(responseModel, OK);
    }

    /**
     * {@code PUT api/branch/search} search branch by text
     * <p>
     * Perform search by given text with {@literal 50} size pagination, if not match any result return empty array
     *
     * @param search input request parameter, It's not be {@literal null}.
     * @throws com.doctory.common.SomethingWentWrong when anything went wrong to whole application level like database failure and response the {@code 500 Internal server error}
     */
    @GetMapping("search")
    public ResponseEntity<List<SearchDto>> getBranchInfo(@RequestParam String search) {
        var searchBranch = branchService.searchBranch(search);
        return new ResponseEntity<>(searchBranch, OK);
    }
    /**
     * {@code PUT api/branch/all} search branch by text
     *
     * @param pageNo   current page no which data are display
     * @param pageSize array page size default is {@literal 10} size
     * @return {@link ResponseEntity} with {@link List} of {@link BranchDto} branch details based on page nation with {@code 200} Success
     * @throws com.doctory.common.SomethingWentWrong when anything went wrong to whole application level like database failure and response the {@code 500 Internal server error}
     */

    @GetMapping("all")
    public ResponseEntity<List<BranchDto>> getAllBranch(@RequestParam(required = false, defaultValue = "0") Integer pageNo, @RequestParam(required = false, defaultValue = "0") Integer pageSize) {
        var branch = branchService.getAllBranch(pageNo, pageSize);
        return new ResponseEntity<>(branch, OK);
    }
}
