package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import com.joshwindels.buddyfinder.dtos.ExcursionDTO;
import com.joshwindels.buddyfinder.dtos.ExcursionDTOBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExcursionControllerTest {

    ExcursionController excursionController = new ExcursionController();

    @Test
    public void givenANewExcursion_whenCreatingExcursion_thenExcursionIsStoredAndSuccessMessageReturned() {
        ExcursionDTO excursionDTO = new ExcursionDTOBuilder()
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

    }

}
