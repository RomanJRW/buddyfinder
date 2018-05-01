package com.joshwindels.buddyfinder.controllers;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.dos.ExcursionDOBuilder;
import com.joshwindels.buddyfinder.dtos.ExcursionDTO;
import com.joshwindels.buddyfinder.dtos.ExcursionDTOBuilder;
import com.joshwindels.buddyfinder.filters.ExcursionFilter;
import com.joshwindels.buddyfinder.filters.ExcursionFilterBuilder;
import com.joshwindels.buddyfinder.helpers.ExcursionHelper;
import com.joshwindels.buddyfinder.helpers.FilterTypes;
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
        when(excursionHelperMock.extractFilterParametersFromFilter(any(ExcursionFilter.class))).thenCallRealMethod();
    }

    @Test
    public void givenANewExcursion_whenCreatingExcursion_thenExcursionIsStoredAndSuccessMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();

        assertEquals("excursion created", excursionController.createExcursion(excursionDTO));
        verify(excursionRepositoryMock, times(1)).storeExcursion(any(ExcursionDO.class));
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithEmptyName_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setName("");

        verifyCreateError(excursionDTO, "all fields must be provided");
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithNoName_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setName(null);

        verifyCreateError(excursionDTO, "all fields must be provided");
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithEmptyStartLocation_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartLocation("");

        verifyCreateError(excursionDTO, "all fields must be provided");
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithNoStartLocation_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartLocation(null);

        verifyCreateError(excursionDTO, "all fields must be provided");
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithEmptyEndLocation_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndLocation("");

        verifyCreateError(excursionDTO, "all fields must be provided");
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithNoEndLocation_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndLocation(null);

        verifyCreateError(excursionDTO, "all fields must be provided");
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithNoStartDate_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartDate(null);

        verifyCreateError(excursionDTO, "all fields must be provided");
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithNoEndDate_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndDate(null);

        verifyCreateError(excursionDTO, "all fields must be provided");
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithNegativeEstimatedCost_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEstimatedCost(-1);

        verifyCreateError(excursionDTO, "all fields must be provided");
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithNoRequiredBuddies_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setRequiredBuddies(0);

        verifyCreateError(excursionDTO, "all fields must be provided");
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithEmptyDescription_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setDescription("");

        verifyCreateError(excursionDTO, "all fields must be provided");
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithNoDescription_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setDescription(null);

        verifyCreateError(excursionDTO, "all fields must be provided");
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithMultipleMissingFields_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setName(null);
        excursionDTO.setRequiredBuddies(0);
        excursionDTO.setDescription(null);

        verifyCreateError(excursionDTO, "all fields must be provided");
    }

    @Test(expected = RuntimeException.class)
    public void givenANewExcursionWithEndDateBeforeStartDate_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndDate(new Date(2017, 6, 6));

        verifyCreateError(excursionDTO, "end date must be after start date");
    }

    @Test(expected = RuntimeException.class)
    public void givenUnauthenticatedUser_whenCreatingExcursion_thenExcursionIsNotStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(null);
        ExcursionDTO excursionDTO = getValidExcursionDTO();

        verifyCreateError(excursionDTO, "not authenticated");
    }

    @Test
    public void givenAnExistingExcursion_whenUpdatingExcursion_thenExcursionIsStoredAndSuccessMessageReturned() {
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));
        ExcursionDTO excursionDTO = getValidExcursionDTO();

        assertEquals("excursion updated", excursionController.updateExcursion(excursionDTO));
        verify(excursionRepositoryMock, times(1)).updateExcursion(any(ExcursionDO.class));
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEmptyName_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setName("");

        verifyUpdateError(excursionDTO, "all fields must be valid");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoName_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setName(null);

        verifyUpdateError(excursionDTO, "all fields must be valid");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEmptyStartLocation_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartLocation("");

        verifyUpdateError(excursionDTO, "all fields must be valid");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoStartLocation_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartLocation(null);

        verifyUpdateError(excursionDTO, "all fields must be valid");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEmptyEndLocation_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndLocation("");

        verifyUpdateError(excursionDTO, "all fields must be valid");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoEndLocation_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndLocation(null);

        verifyUpdateError(excursionDTO, "all fields must be valid");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoStartDate_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartDate(null);

        verifyUpdateError(excursionDTO, "all fields must be valid");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoEndDate_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndDate(null);

        verifyUpdateError(excursionDTO, "all fields must be valid");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNegativeEstimatedCost_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEstimatedCost(-1);

        verifyUpdateError(excursionDTO, "all fields must be valid");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNegativeRequiredBuddies_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setRequiredBuddies(-1);

        verifyUpdateError(excursionDTO, "all fields must be valid");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEmptyDescription_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setDescription("");

        verifyUpdateError(excursionDTO, "all fields must be valid");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithNoDescription_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setDescription(null);

        verifyUpdateError(excursionDTO, "all fields must be valid");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithStartDateAfterEndDate_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setStartDate(new Date(2018, 10, 20));

        verifyUpdateError(excursionDTO, "end date must be after start date");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursion_whenUpdatingExcursionWithEndDateBeforeStartDate_thenExcursionIsStoredAndErrorMessageReturned() {
        ExcursionDTO excursionDTO = getValidExcursionDTO();
        excursionDTO.setEndDate(new Date(2018, 10, 10));

        verifyUpdateError(excursionDTO, "end date must be after start date");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExcursionUpdate_whenExcursionDoesNotExist_thenNoInformationIsStoredAndErrorMessageReturned() {
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.empty());

        verifyUpdateError(getValidExcursionDTO(), "excursion does not exist");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExcursionUpdate_whenExcursionWasPostedByDifferentUser_thenNoInformationIsStoredAndErrorMessageReturned() {
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        ExcursionDO storedExcursion = getValidExcursionDO();
        storedExcursion.setOwnerId(100);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(storedExcursion));
        ExcursionDTO excursionDTO = getValidExcursionDTO();

        verifyUpdateError(excursionDTO, "user does not have permission to update this excursion");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnUnauthenticatedUser_whenUpdatingExcursion_thenNoInformationIsStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(null);
        ExcursionDTO excursionDTO = getValidExcursionDTO();

        verifyUpdateError(excursionDTO, "not authenticated");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnUnauthenticatedUser_whenUpdatingNonExistentExcursion_thenNoInformationIsStoredAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(null);
        ExcursionDTO excursionDTO = getValidExcursionDTO();

        verifyUpdateError(excursionDTO, "not authenticated");
    }

    @Test
    public void givenAnExistingExcursionPostedByUser_whenDeletingExcursion_thenInformationIsDeletedFromStorageAndSuccessMessageReturned() {
        when(currentUserMock.getId()).thenReturn(OWNER_ID);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));

        assertEquals("excursion deleted", excursionController.deleteExcursion(EXCURSION_ID));
        verify(excursionRepositoryMock, times(1)).deleteExcursion(EXCURSION_ID);
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExistingExcursionPostedByDifferentUser_whenDeletingExcursion_thenNoInformationIsDeletedFromStorageAndErrorMessageReturned() {
        when(currentUserMock.getId()).thenReturn(11);
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.of(getValidExcursionDO()));

        verifyDeleteError(EXCURSION_ID, "user doesn't have permission to delete excursion");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExcursionNotInStorage_whenDeletingExcursion_thenNoInformationIsDeletedFromStorageAndErrorMessageReturned() {
        when(excursionRepositoryMock.getExcursionForId(EXCURSION_ID)).thenReturn(Optional.empty());

        verifyDeleteError(EXCURSION_ID, "excursion does not exist");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnUnauthenticatedUser_whenDeletingExcursion_thenNoInformationIsDeletedFromStorageAndErrorMessageReturned() {
        when(currentUserMock.getUsername()).thenReturn(null);

        verifyDeleteError(EXCURSION_ID, "not authenticated");
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithNoFilter_thenAllResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilter();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().isEmpty());
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithNameContainsFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder().nameContains("fun").build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 1);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.NAME_CONTAINS).equals("fun"));
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithStartLocationContainsFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder().startLocationContains("city").build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 1);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.START_LOCATION_CONTAINS).equals("city"));
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithEndLocationContainsFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder().endLocationContains("city").build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 1);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.END_LOCATION_CONTAINS).equals("city"));
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithStartDateFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder().startDate(new Date(2018, 2, 10)).build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 1);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.START_DATE).equals(new Date(2018, 2, 10)));
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithEndDateFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder().endDate(new Date(2018, 9, 15)).build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 1);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.END_DATE).equals(new Date(2018, 9, 15)));
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithStartDateAndEndDateFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder()
                .startDate(new Date(2018, 2, 10))
                .endDate(new Date(2018, 9, 15))
                .build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 2);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.START_DATE).equals(new Date(2018, 2, 10)));
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.END_DATE).equals(new Date(2018, 9, 15)));
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithMinEstimatedCostFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder().minEstimatedCost(10.00).build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 1);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.MIN_ESTIMATED_COST).equals(10.00));
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithMaxEstimatedCostFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder().maxEstimatedCost(50.00).build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 1);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.MAX_ESTIMATED_COST).equals(50.00));
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithMinAndMaxEstimatedCostFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder()
                .minEstimatedCost(10.00)
                .maxEstimatedCost(50.00)
                .build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 2);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.MIN_ESTIMATED_COST).equals(10.00));
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.MAX_ESTIMATED_COST).equals(50.00));
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithMinRequiredBuddiesFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder().minRequiredBuddies(1).build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 1);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.MIN_REQUIRED_BUDDIES).equals(1));
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithMaxRequiredBuddiesFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder().maxRequiredBuddies(4).build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 1);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.MAX_REQUIRED_BUDDIES).equals(4));
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithMinAndMaxRequiredBuddiesFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder()
                .minRequiredBuddies(1)
                .maxRequiredBuddies(4)
                .build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 2);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.MIN_REQUIRED_BUDDIES).equals(1));
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.MAX_REQUIRED_BUDDIES).equals(4));
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithDescriptionContainsFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder().descriptonContains("waterfall").build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 1);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.DESCRIPTION_CONTAINS).equals("waterfall"));
    }

    @Test
    public void givenAnExcursionRequest_whenProvidedWithComplexFilter_thenFilteredResultsAreReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder()
                .nameContains("test name")
                .startDate(new Date(2018, 2, 10))
                .endDate(new Date(2018, 9, 15))
                .descriptonContains("waterfall")
                .build();

        excursionController.getExcursions(filter);
        verify(excursionRepositoryMock, times(1)).getExcursionsMatchingFilterParameters(filterParamsCaptor.capture());
        assertTrue(filterParamsCaptor.getValue().size() == 4);
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.NAME_CONTAINS).equals("test name"));
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.START_DATE).equals(new Date(2018, 2, 10)));
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.END_DATE).equals(new Date(2018, 9, 15)));
        assertTrue(filterParamsCaptor.getValue().get(FilterTypes.DESCRIPTION_CONTAINS).equals("waterfall"));
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExcursionRequest_whenFilterStartDateIsAfterStartDate_thenErrorMessageIsReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder()
                .startDate(new Date(2018, 2, 10))
                .endDate(new Date(2018, 2, 5))
                .build();

        verifyFilterError(filter, "end date must be after start date");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExcursionRequest_whenFilterMinCostIsGreaterThanMaxCost_thenErrorMessageIsReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder()
                .minEstimatedCost(20)
                .maxEstimatedCost(10)
                .build();

        verifyFilterError(filter, "maximum estimated cost must be greater or equal to minimum estimated cost");
    }

    @Test(expected = RuntimeException.class)
    public void givenAnExcursionRequest_whenFilterMinBuddiesIsGreaterThanMaxBuddies_thenErrorMessageIsReturned() {
        ExcursionFilter filter = new ExcursionFilterBuilder()
                .minRequiredBuddies(2)
                .maxRequiredBuddies(1)
                .build();

        verifyFilterError(filter, "maximum required buddies must be greater or equal to minimum required buddies");
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

    private void verifyCreateError(ExcursionDTO excursionDTO, String errorMessage) {
        try {
            excursionController.createExcursion(excursionDTO);
        } catch (RuntimeException ex) {
            assertEquals(errorMessage, ex.getMessage());
            verify(excursionRepositoryMock, never()).storeExcursion(any(ExcursionDO.class));
            throw ex;
        }
        fail("expected Exception wasn't thrown");
    }

    private void verifyUpdateError(ExcursionDTO excursionDTO, String errorMessage) {
        try {
            excursionController.updateExcursion(excursionDTO);
        } catch (RuntimeException ex) {
            assertEquals(errorMessage, ex.getMessage());
            verify(excursionRepositoryMock, never()).updateExcursion(any(ExcursionDO.class));
            throw ex;
        }
        fail("expected Exception wasn't thrown");
    }

    private void verifyDeleteError(int excursionId, String errorMessage) {
        try {
            excursionController.deleteExcursion(excursionId);
        } catch (RuntimeException ex) {
            assertEquals(errorMessage, ex.getMessage());
            verify(excursionRepositoryMock, never()).deleteExcursion(anyInt());
            throw ex;
        }
        fail("expected Exception wasn't thrown");
    }

    private void verifyFilterError(ExcursionFilter filter, String errorMessage) {
        try {
            excursionController.getExcursions(filter);
        } catch (RuntimeException ex) {
            assertEquals(errorMessage, ex.getMessage());
            verify(excursionRepositoryMock, never()).deleteExcursion(anyInt());
            throw ex;
        }
        fail("expected Exception wasn't thrown");
    }
}
