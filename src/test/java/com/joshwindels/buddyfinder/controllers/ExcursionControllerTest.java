package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;

import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.dtos.ExcursionDTO;
import com.joshwindels.buddyfinder.dtos.ExcursionDTOBuilder;
import com.joshwindels.buddyfinder.repositories.ExcursionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExcursionControllerTest {

    @Mock
    ExcursionRepository excursionRepository;

    @InjectMocks
    ExcursionController excursionController;

    @Test
    public void givenANewExcursion_whenCreatingExcursion_thenExcursionIsStoredAndSuccessMessageReturned() {
        ExcursionDTO excursionDTO = new ExcursionDTOBuilder()
                .id(1)
                .ownerId(10)
                .name("new excursion")
                .startLocation("Belmopan")
                .endLocation("Belize City")
                .startDate(new Date(2018, 10, 13))
                .endDate(new Date(2018, 10, 15))
                .estimatedCost(50)
                .requiredBuddies(2)
                .description("a road trip between Belmopan and Belize City, stopping off at some temples along the way")
                .build();

        assertEquals("excursion created", excursionController.createExcursion(excursionDTO));
        verify(excursionRepository, times(1)).storeExcursion(any(ExcursionDO.class));
    }

}
