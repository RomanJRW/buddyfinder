package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.dos.ExcursionDOBuilder;
import com.joshwindels.buddyfinder.dos.InterestedUser;
import com.joshwindels.buddyfinder.repositories.ExcursionRepository;
import com.joshwindels.buddyfinder.repositories.InterestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InterestControllerTest {

    private static final int EXCURSION_ID = 1;
    private static final int OWNER_ID = 10;
    private static final String EXCURSION_NAME = "new excursion";
    private static final String START_LOCATION = "Belmopan";
    private static final String END_LOCATION = "Belize City";
    private static final Date START_DATE = new Date(2018, 10, 13);
    private static final Date END_DATE = new Date(2018, 10, 15);
    private static final int ESTIMATED_COST = 50;
    private static final int REQUIRED_BUDDIES = 2;
    private static final String DESCRIPTION = "a road trip between Belmopan and Belize City, stopping off at some temples along the way";
    private static final int USER_ID = 10;
    private static final String USERNAME = "username";
    private static final String EMAIL_ADDRESS = "email@email.com";
    private static final String TELEPHONE_NUMBER = "+447700 012345";

    @Mock
    InterestRepository interestRepository;

    @Mock
    ExcursionRepository excursionRepository;

    @Mock
    CurrentUser currentUser;

    @InjectMocks
    InterestController interestController;

    @Test
    public void givenExistingExcursion_whenUserExpressesInterest_thenInterestIsStoredAndSuccessMessageReturned() {
        when(currentUser.getId()).thenReturn(USER_ID);
        when(excursionRepository.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getExcursionDO()));

        assertEquals("interest expressed", interestController.expressInterest(EXCURSION_ID));
        verify(interestRepository, times(1)).expressUserInterestInExcursion(anyInt(), anyInt());
    }

    @Test(expected = RuntimeException.class)
    public void givenExcursionDoesNotExist_whenUserExpressesInterest_thenInterestIsNotStoredAndErrorMessageReturned() {
        when(currentUser.getId()).thenReturn(USER_ID);
        when(excursionRepository.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.empty());

        verifyInterestExpressError("excursion not found");
    }

    @Test(expected = RuntimeException.class)
    public void givenExcursionPostedByCurrentUser_whenCurrentUserExpressesInterest_thenInterestIsNotStoredAndErrorMessageReturned() {
        when(currentUser.getId()).thenReturn(USER_ID);
        when(excursionRepository.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getExcursionDO()));

        verifyInterestExpressError("cannot express interest in own excursions");
    }

    @Test(expected = RuntimeException.class)
    public void givenUnauthenticatedUser_whenUserExpressesInterest_thenInterestIsNotStoredAndErrorMessageReturned() {
        when(currentUser.getId()).thenReturn(null);

        verifyInterestExpressError("not authenticated");
    }

    @Test
    public void givenExistingExcursion_whenRequestingInterestedUsers_thenUsersWithExpressedInterestAreReturned() {
        when(interestRepository.getUsersInterestedInExcursion(anyInt())).thenReturn(Arrays.asList(getInterestedUser(), getInterestedUser()));

        List<InterestedUser> interestedUserList = interestController.getInterestedUsersForExcursion(EXCURSION_ID);
        verify(interestRepository, times(1)).getUsersInterestedInExcursion(EXCURSION_ID);
        assertEquals(interestedUserList, Arrays.asList(getInterestedUser(), getInterestedUser()));
    }

    private void verifyInterestExpressError(String errorMessage) {
        try {
            interestController.expressInterest(EXCURSION_ID);
        } catch (RuntimeException ex) {
            assertEquals(errorMessage, ex.getMessage());
            verify(interestRepository, never()).expressUserInterestInExcursion(anyInt(), anyInt());
            throw ex;
        }
        fail("expected Exception wasn't thrown");
    }

    private ExcursionDO getExcursionDO() {
        return new ExcursionDOBuilder()
                .id(EXCURSION_ID)
                .ownerId(OWNER_ID)
                .name(EXCURSION_NAME)
                .startLocation(START_LOCATION)
                .endLocation(END_LOCATION)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .estimatedCost(ESTIMATED_COST)
                .requiredBuddies(REQUIRED_BUDDIES)
                .description(DESCRIPTION)
                .build();
    }

    private InterestedUser getInterestedUser() {
        InterestedUser interestedUser = new InterestedUser();
        interestedUser.setUsername(USERNAME);
        interestedUser.setEmailAddress(EMAIL_ADDRESS);
        interestedUser.setTelephoneNumber(TELEPHONE_NUMBER);
        return interestedUser;
    }

}
