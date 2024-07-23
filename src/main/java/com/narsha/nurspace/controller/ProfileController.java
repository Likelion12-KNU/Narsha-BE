package com.narsha.nurspace.controller;


import com.narsha.nurspace.dto.ProfileCreateRequest;
import com.narsha.nurspace.dto.ProfileEntity;
import com.narsha.nurspace.dto.ProfileUpdateRequest;
import com.narsha.nurspace.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@CrossOrigin(origins = "*")
@Tag(name = "프로필 API Controller", description = "프로필 정보를 제공하는 메인 컨트롤러")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    @Operation(summary = "프로필 생성")
    public ResponseEntity<ProfileCreateRequest> createProfile(
            @Parameter(description = "프로필 생성 요청 객체", required = true)
            @RequestBody ProfileCreateRequest request) {
        if (request == null || request.getName() == null || request.getContents() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProfileCreateRequest response = profileService.createProfile(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "상세 프로필 조회")
    public ResponseEntity<ProfileEntity> getProfile(
            @Parameter(description = "검색할 프로필 ID", required = true)
            @PathVariable long id) {
        ProfileEntity profile = profileService.getProfile(id);
        if (profile != null) {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "프로필 변경")
    public ResponseEntity<ProfileUpdateRequest> updateProfile(
            @Parameter(description = "변경할 프로필 ID", required = true)
            @PathVariable long id,
            @Parameter(description = "프로필 변경 요청 객체", required = true)
            @RequestBody ProfileUpdateRequest request) {
        try {
            ProfileUpdateRequest updatedProfile = profileService.update(id, request);
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "프로필 삭제")
    public ResponseEntity<Void> deleteProfile(
            @Parameter(description = "삭제할 프로필 ID", required = true)
            @PathVariable long id) {
        profileService.deleteProfile(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/like")
    @Operation(summary = "프로필 좋아요 추가")
    public ResponseEntity<Void> addLike(
            @Parameter(description = "좋아요 추가할 프로필 ID", required = true)
            @PathVariable long id) {
        profileService.addLike(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/unlike")
    @Operation(summary = "프로필 좋아요 제거")
    public ResponseEntity<Void> removeLike(
            @Parameter(description = "좋아요 제거할 프로필 ID", required = true)
            @PathVariable long id) {
        profileService.removeLike(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}