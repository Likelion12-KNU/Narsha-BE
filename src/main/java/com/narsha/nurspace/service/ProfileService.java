package com.narsha.nurspace.service;

import com.narsha.nurspace.dto.ProfileCreateRequest;
import com.narsha.nurspace.dto.ProfileEntity;
import com.narsha.nurspace.dto.ProfileUpdateRequest;
import com.narsha.nurspace.model.Profile;
import com.narsha.nurspace.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    /**
     * 새로운 프로필을 생성하고 저장
     *
     * @param request 프로필 생성 요청 객체
     * @return 생성된 프로필의 이름과 내용을 포함하는 ProfileCreateRequest 객체
     */
    public ProfileCreateRequest createProfile(ProfileCreateRequest request) {
        Profile profile = new Profile();
        profile.setName(request.getName());
        profile.setContents(request.getContents());

        // 프로필 저장
        Profile savedProfile = profileRepository.save(profile);
        // 생성된 프로필 정보를 반환
        return new ProfileCreateRequest(savedProfile.getName(), savedProfile.getContents());
    }

    /**
     * 주어진 ID에 해당하는 프로필 조회
     *
     * @param id 프로필 ID
     * @return 조회된 프로필의 정보를 포함하는 ProfileEntity 객체
     */
    public ProfileEntity getProfile(long id) {
        Optional<Profile> profileOpt = profileRepository.findById(id);
        if (profileOpt.isPresent()) {
            Profile profile = profileOpt.get();
            return new ProfileEntity(profile.getProfile_id(), profile.getName(), profile.getContents(), profile.getLikes());
        }
        return null;
    }

    /**
     * 주어진 ID에 해당하는 프로필 업데이트
     *
     * @param id      프로필 ID
     * @param request 프로필 업데이트 요청 객체
     * @return 업데이트된 프로필의 이름과 내용을 포함하는 ProfileUpdateRequest 객체
     */
    public ProfileUpdateRequest update(Long id, ProfileUpdateRequest request){
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        profile.setName((request.getName()));
        profile.setContents(request.getContents());

        // 프로필 저장
        Profile savedProfile = profileRepository.save(profile);

        // 업데이트된 프로필 정보를 반환
        return new ProfileUpdateRequest(savedProfile.getName(), savedProfile.getContents());
    }

    /**
     * 주어진 ID에 해당하는 프로필 삭제
     *
     * @param id 프로필 ID
     */
    public void deleteProfile(long id) {
        profileRepository.deleteById(id);
    }

    /**
     * 주어진 ID에 해당하는 프로필에 좋아요 추가
     *
     * @param id 프로필 ID
     */
    public void addLike(long id) {
        Optional<Profile> profileOpt = profileRepository.findById(id);
        if (profileOpt.isPresent()) {
            Profile profile = profileOpt.get();
            profile.addLikes();
            profileRepository.save(profile);
        }
    }

    /**
     * 주어진 ID에 해당하는 프로필 삭제
     *
     * @param id 프로필 ID
     */
    public void removeLike(long id) {
        Optional<Profile> profileOpt = profileRepository.findById(id);
        if (profileOpt.isPresent()) {
            Profile profile = profileOpt.get();
            profile.minusLikes();
            profileRepository.save(profile);
        }
    }
}
