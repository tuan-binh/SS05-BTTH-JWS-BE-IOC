package org.example.session05.controllers;

import lombok.RequiredArgsConstructor;
import org.example.session05.models.dto.res.UserInfo;
import org.example.session05.models.dto.res.UserRes;
import org.example.session05.models.entities.User;
import org.example.session05.models.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAllByName(@RequestParam(name = "search", defaultValue = "") String search) {
        return ResponseEntity.ok(userService.findAllByName(search));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<User>> findAllWithPagination(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "2") int size,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ) {
        return ResponseEntity.ok(userService.findAll(page, size, sort, direction));
    }

    @GetMapping("/slice")
    public ResponseEntity<Slice<User>> sliceUser(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "2") int size
    ) {
        return ResponseEntity.ok(userService.sliceContent(page, size));
    }

    @GetMapping("/pageable-default")
    public ResponseEntity<Page<User>> pageableDefault(
            @PageableDefault(page = 0, size = 2, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(userService.paginationWithPageableDefault(pageable));
    }

    @GetMapping("/top-3-age")
    public ResponseEntity<List<User>> getTop3UserLessthenAge(){
        return ResponseEntity.ok(userService.getTop3LessthenAge());
    }

    @GetMapping("/interface-base")
    public ResponseEntity<List<UserInfo>> getAllDTOProjection() {
        return ResponseEntity.ok(userService.getAllDTOWithAge());
    }

    @GetMapping("/class-based")
    public ResponseEntity<List<UserRes>> getAll() {
        return ResponseEntity.ok(userService.getAllDTO());
    }

}
