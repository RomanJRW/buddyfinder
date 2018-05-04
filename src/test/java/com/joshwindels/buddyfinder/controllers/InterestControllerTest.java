package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.Date;
import java.util.Optional;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.dos.ExcursionDOBuilder;
import com.joshwindels.buddyfinder.repositories.ExcursionRepository;
import com.joshwindels.buddyfinder.repositories.InterestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InterestControllerTest {

    public static final int EXCURSION_ID = 1;
    public static final int OWNER_ID = 10;
    public static final String EXCURSION_NAME = "new excursion";
    public static final String START_LOCATION = "Belmopan";
    public static final String END_LOCATION = "Belize City";
    public static final Date START_DATE = new Date(2018, 10, 13);
    public static final Date END_DATE = new Date(2018, 10, 15);
    public static final int ESTIMATED_COST = 50;
    public static final int REQUIRED_BUDDIES = 2;
    public static final String DESCRIPTION = "a road trip between Belmopan and Belize City, stopping off at some temples along the way";
    public static final String USERNAME = "username";
    private static final int USER_ID = 10;

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
    public void givenExcursionDoesNotExist_whenUserExpressesInterest_thenInterestIsStoredAndSuccessMessageReturned() {
        when(currentUser.getId()).thenReturn(USER_ID);
        when(excursionRepository.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.empty());

        verifyInterestError("excursion not found");
    }

    @Test(expected = RuntimeException.class)
    public void givenExcursionPostedByCurrentUser_whenCurrentUserExpressesInterest_thenInterestIsStoredAndSuccessMessageReturned() {
        when(currentUser.getId()).thenReturn(USER_ID);
        when(excursionRepository.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getExcursionDO()));

        verifyInterestError("cannot express interest in own excursions");
    }

    private void verifyInterestError(String errorMessage) {
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

}
