package com.kk.TeamUp.service;

import com.kk.TeamUp.domain.Matching;
import com.kk.TeamUp.domain.User;
import com.kk.TeamUp.domain.UserMatching;
import com.kk.TeamUp.dto.AddMatchingRequest;
import com.kk.TeamUp.dto.UpdateMatchingRequest;
import com.kk.TeamUp.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final UserMatchingService userMatchingService;

    @Transactional
    public Matching saveMatching(User user, AddMatchingRequest request) {
        Matching matching = Matching.builder()
                        .category(request.getCategory())
                        .place(request.getPlace())
                        .detail(request.getDetail())
                        .title(request.getTitle())
                        .build();

        matchingRepository.save(matching);
        userMatchingService.save(user,matching);

        return matching;
    }

    public List<Matching> findAllMatching() {
        return matchingRepository.findAll();
    }

    public Matching findOneMatching(long id) {
        return matchingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no matching with this ID"));
    }

    public void deleteMatching(long id) {
        matchingRepository.deleteById(id);
    }

    @Transactional
    public Matching updateMatching(long id, UpdateMatchingRequest request) {
        Matching matching = matchingRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("no matching with this ID"));

        matching.setGameTime(request.getGameTime());
        matching.setDetail(request.getDetail());
        matching.setTitle(request.getTitle());
        matching.setPlace(request.getPlace());
        matching.setCategory(request.getCategory());

        return matching;
    }

}
