package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.dos.ExcursionDOBuilder;
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
    @Mock
    CurrentUser currentUserMock;

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
    public void givenANewExcursionWithEmptyName_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setName("");

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoName_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setName(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithEmptyStartLocation_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setStartLocation("");

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoStartLocation_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setStartLocation(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithEmptyEndLocation_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setEndLocation("");

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoEndLocation_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setEndLocation(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoStartDate_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setStartDate(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoEndDate_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setEndDate(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNegativeEstimatedCost_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setEstimatedCost(-1);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoRequiredBuddies_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setRequiredBuddies(0);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithEmptyDescription_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setDescription("");

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoDescription_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setDescription(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithMultipleMissingFields_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setName(null);
        excursionDTO.setRequiredBuddies(0);
        excursionDTO.setDescription(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithEndDateBeforeStartDate_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();
        excursionDTO.setEndDate(new Date(2017, 6, 6));

        assertEquals("end date must be after start date", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenUnauthenticatedUser_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(null);
        ExcursionDTO excursionDTO = getValidNewExcursionDTO();

        assertEquals("not authenticated", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursion_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();

        assertEquals("excursion updated", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, times(1)).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEmptyName_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setName("");

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoName_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setName(null);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEmptyStartLocation_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setStartLocation("");

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoStartLocation_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setStartLocation(null);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEmptyEndLocation_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setEndLocation("");

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoEndLocation_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setEndLocation(null);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoStartDate_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setStartDate(null);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoEndDate_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setEndDate(null);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNegativeEstimatedCost_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setEstimatedCost(-1);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNegativeRequiredBuddies_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setRequiredBuddies(-1);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEmptyDescription_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setDescription("");

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoDescription_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setDescription(null);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithStartDateAfterEndDate_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setStartDate(new Date(2018, 10, 20));

        assertEquals("end date must be after start date", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEndDateBeforeStartDate_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidUpdateExcursionDTO();
        excursionDTO.setEndDate(new Date(2018, 10, 10));

        assertEquals("end date must be after start date", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    private ExcursionDTO getValidNewExcursionDTO() {
        return new ExcursionDTOBuilder()
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

    private ExcursionDTO getValidUpdateExcursionDTO() {
        return new ExcursionDTOBuilder()
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

    private ExcursionDO getValidExcursionDO() {
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
