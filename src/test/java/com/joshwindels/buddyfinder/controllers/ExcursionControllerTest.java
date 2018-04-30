package com.joshwindels.buddyfinder.controllers;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.dos.ExcursionDOBuilder;
import com.joshwindels.buddyfinder.dtos.ExcursionDTO;
import com.joshwindels.buddyfinder.dtos.ExcursionDTOBuilder;
import com.joshwindels.buddyfinder.filters.ExcursionFilter;
import com.joshwindels.buddyfinder.helpers.ExcursionHelper;
import com.joshwindels.buddyfinder.repositories.ExcursionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @Mock
    ExcursionHelper excursionHelperMock;

    @InjectMocks
    ExcursionController excursionController;

    @Captor
    private ArgumentCaptor<HashMap> filterParamsCaptor;


    @Before
    public void setup() {
        when(currentUserMock.getUsername()).thenReturn(USERNAME);
        when(excursionHelperMock.convertToDO(any(ExcursionDTO.class))).thenCallRealMethod();
        when(excursionHelperMock.convertToDTO(any(ExcursionDO.class))).thenCallRealMethod();
    }

    @Test
    public void givenANewExcursion_whenCreatingExcursion_thenExcursionIsStoredAndSuccessMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();

        assertEquals("excursion created", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, times(1)).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithEmptyName_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setName("");

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoName_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setName(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithEmptyStartLocation_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartLocation("");

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoStartLocation_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartLocation(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithEmptyEndLocation_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndLocation("");

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoEndLocation_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndLocation(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoStartDate_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartDate(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoEndDate_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndDate(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNegativeEstimatedCost_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEstimatedCost(-1);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoRequiredBuddies_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setRequiredBuddies(0);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithEmptyDescription_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setDescription("");

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithNoDescription_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setDescription(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithMultipleMissingFields_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setName(null);
        excursionDTO.setRequiredBuddies(0);
        excursionDTO.setDescription(null);

        assertEquals("all fields must be provided", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenANewExcursionWithEndDateBeforeStartDate_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndDate(new Date(2017, 6, 6));

        assertEquals("end date must be after start date", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenUnauthenticatedUser_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(null);
        ExcursionDTO excursionDTO = getValidExcursionDTO();

        assertEquals("not authenticated", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursion_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidExcursionDTO();

        assertEquals("excursion updated", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, times(1)).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEmptyName_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setName("");

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoName_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setName(null);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEmptyStartLocation_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartLocation("");

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoStartLocation_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartLocation(null);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEmptyEndLocation_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndLocation("");

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoEndLocation_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndLocation(null);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoStartDate_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartDate(null);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoEndDate_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndDate(null);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNegativeEstimatedCost_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEstimatedCost(-1);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNegativeRequiredBuddies_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setRequiredBuddies(-1);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEmptyDescription_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setDescription("");

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoDescription_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setDescription(null);

        assertEquals("all fields must be valid", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithStartDateAfterEndDate_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartDate(new Date(2018, 10, 20));

        assertEquals("end date must be after start date", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEndDateBeforeStartDate_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndDate(new Date(2018, 10, 10));

        assertEquals("end date must be after start date", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExcursionUpdate_whenExcursionDoesNotExist_thenNoInformationIsStoredAndErrorMessageReturned() {
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.empty());

        assertEquals("excursion does not exist", excursionController.updateExcursion(
                getValidExcursionDTO()));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExcursionUpdate_whenExcursionWasPostedByDifferentUser_thenNoInformationIsStoredAndErrorMessageReturned() {
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        ExcursionDO storedExcursion = getValidExcursionDO();
        storedExcursion.setOwnerId(100);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(storedExcursion));
        ExcursionDTO excursionDTO = getValidExcursionDTO();

        assertEquals("user does not have permission to update this excursion", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnUnauthenticatedUser_whenUpdatingExcursion_thenNoInformationIsStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(null);
        ExcursionDTO excursionDTO = getValidExcursionDTO();

        assertEquals("not authenticated", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnUnauthenticatedUser_whenUpdatingNonExistentExcursion_thenNoInformationIsStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(null);
        ExcursionDTO excursionDTO = getValidExcursionDTO();

        assertEquals("not authenticated", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
    }

    @Test
    public void givenAnExistingExcursionPostedByUser_whenDeletingExcursion_thenInformationIsDeletedFromStorageAndSuccessMessageReturned() {
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));

        assertEquals("excursion deleted", excursionController.deleteExcursion(EXCURSION_ID));
        verify(excursionRepositoryMock, times(1)).deleteExcursion(EXCURSION_ID);
    }

    @Test
    public void givenAnExistingExcursionPostedByDifferentUser_whenDeletingExcursion_thenNoInformationIsDeletedFromStorageAndErrorMessageReturned() {
        when(currentUserMock.getId()).thenReturn(11);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));

        assertEquals("user doesn't have permission to delete excursion", excursionController.deleteExcursion(EXCURSION_ID));
        verify(excursionRepositoryMock, never()).deleteExcursion(EXCURSION_ID);
    }

    @Test
    public void givenAnExcursionNotInStorage_whenDeletingExcursion_thenNoInformationIsDeletedFromStorageAndErrorMessageReturned() {
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.empty());

        assertEquals("excursion does not exist", excursionController.deleteExcursion(EXCURSION_ID));
        verify(excursionRepositoryMock, never()).deleteExcursion(EXCURSION_ID);
    }

    @Test
    public void givenAnUnauthenticatedUser_whenDeletingExcursion_thenNoInformationIsDeletedFromStorageAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(null);

        assertEquals("not authenticated", excursionController.deleteExcursion(EXCURSION_ID));
        verify(excursionRepositoryMock, never()).deleteExcursion(EXCURSION_ID);
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithNoFilter_thenAllResultsAreReturned() {
        when(excursionRepositoryMock.getExcursionsMatchingFilterParameters(any(HashMap.class)))
                .thenReturn(Arrays.asList(getValidExcursionDO(), getValidExcursionDO(), getValidExcursionDO()));
        ExcursionFilter filter = new ExcursionFilter();

        List<ExcursionDTO> matchedExcursions = excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(matchedExcursions.size() == 3);
        assertTrue(matchedExcursions.containsAll(Arrays.asList(getValidExcursionDTO(), getValidExcursionDTO(), getValidExcursionDTO())));
        assertTrue(filterParamsCaptor.getValue().isEmpty());
    }

    private ExcursionDTO getValidExcursionDTO() {
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
