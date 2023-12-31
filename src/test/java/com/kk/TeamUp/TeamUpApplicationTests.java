package com.kk.TeamUp;

import com.kk.TeamUp.domain.Matching;
import com.kk.TeamUp.domain.User;
import com.kk.TeamUp.domain.UserMatching;
import com.kk.TeamUp.repository.MatchingRepository;
import com.kk.TeamUp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeamUpApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MatchingRepository matchingRepository;

		@Test
		void idontknow() {
			System.out.println("hello here");
			User user = userRepository.findById(1L).get();
			Matching matching = matchingRepository.findById(1L).get();

			UserMatching userMatching = new UserMatching();

			//matching.addUserMatching(userMatching);
		}

}
