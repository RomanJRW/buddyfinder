package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.repositories.InterestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InterestControllerTest {

    private static final int EXCURSION_ID = 1;
    private static final int USER_ID = 10;

    @Mock
    InterestRepository interestRepository;

    @Mock
    CurrentUser currentUser;

    @InjectMocks
    InterestController interestController;

    @Test
    public void givenExistingExcursion_whenUserExpressesInterest_thenInterestIsStoredAndSuccessMessageReturned() {
        when(currentUser.getId()).thenReturn(USER_ID);
        assertEquals("interest expressed", interestController.expressInterest(EXCURSION_ID));
        verify(interestRepository, times(1)).expressUserInterestInExcursion(anyInt(), anyInt());
    }

}
