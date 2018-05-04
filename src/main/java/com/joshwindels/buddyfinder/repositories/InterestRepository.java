package com.joshwindels.buddyfinder.repositories;

import java.util.List;

import com.joshwindels.buddyfinder.dos.InterestedUser;
import org.springframework.stereotype.Repository;

@Repository
public class InterestRepository {

    public void expressUserInterestInExcursion(Integer id, int excursionId) {
    }

    public List<InterestedUser> getUsersInterestedInExcursion(int excursionId) {
        return null;
    }
}
