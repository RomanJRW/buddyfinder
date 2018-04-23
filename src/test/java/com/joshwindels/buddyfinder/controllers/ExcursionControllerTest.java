package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import com.joshwindels.buddyfinder.dos.CurrentUser;
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

    @Mock CurrentUser currentUserMock;
    @Mock
    ExcursionRepository excursionRepositoryMock;

    @InjectMocks
    ExcursionController excursionController;

    @Test
    public void givenANewExcursion_whenCreatingExcursion_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();

        assertEquals("excursion created", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, times(1)).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoName_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setName("");

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    private ExcursionDTO getValidNewExcursionDTO() {
        return new ExcursionDTOBuilder().id(1)
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
    }

}
