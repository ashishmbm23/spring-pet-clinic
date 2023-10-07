package com.ashish.controller;

import com.ashish.model.Owner;
import com.ashish.model.Pet;
import com.ashish.model.PetType;
import com.ashish.services.OwnerService;
import com.ashish.services.PetService;
import com.ashish.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    public static final long OWNER_ID = 1L;
    @Mock
    private OwnerService ownerService;
    @Mock
    private PetTypeService petTypeService;
    @Mock
    private PetService petService;
    @InjectMocks
    private PetController petController;

    MockMvc mockMvc;

    private Owner getOwner(){
        Owner owner = new Owner();
        owner.setId(OWNER_ID);
        return owner;
    }

    private Collection<PetType> getPetTypes(){
        Set<PetType> petTypes = new HashSet<>();
        petTypes.add( PetType.builder().id(11L).name("Cat").build());
        petTypes.add( PetType.builder().id(12L).name("Dog").build());
        return petTypes;
    }

    private void mockModelCreateForm(){
        mockModelAttributes();
    }

    private void mockModelUpdateForm(){
        mockModelAttributes();
        Pet pet = Pet.builder().id(2L).build();
        when( petService.findById(anyLong())).thenReturn(pet);
    }
    private void mockModelAttributes() {
        when( ownerService.findById(anyLong()) ).thenReturn(getOwner());
        when( petTypeService.findAll() ).thenReturn((Set<PetType>) getPetTypes());
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void findOwner() throws Exception {
//        when( ownerService.findById(anyLong()) ).thenReturn(getOwner());
//
//        mockMvc.perform( get("/owners/1"))
//                .andExpect( model().attributeExists("owner"));
//
//        verify( ownerService, times(1)).findById(anyLong());
    }

    @Test
    void findPetTypes() throws Exception{
//        when( petTypeService.findAll() ).thenReturn((Set<PetType>) getPetTypes());
//
//        mockMvc.perform( get("/owners/1"))
//                .andExpect( model().attributeExists("types"));
//
//        verify( petTypeService, times(1)).findAll();
    }

    @Test
    void initOwnerBinder() {

    }

    @Test
    public void initCreationForm() throws Exception{
        //the request will come from /owners/ownerId/pets/new
        mockModelCreateForm();
        mockMvc.perform( get("/owners/1/pets/new"))
                .andExpect( status().isOk() )
                .andExpect( model().attributeExists("owner"))
                .andExpect( model().attributeExists("pet"))
                .andExpect( view().name("pets/createOrUpdatePetForm"));

        verify( ownerService, times(1)).findById(anyLong());
        verify( petTypeService, times(1)).findAll();
        verifyNoInteractions( petService);
    }

    @Test
    public void processCreationForm() throws Exception{
        mockModelCreateForm();
        mockMvc.perform( post("/owners/1/pets/new"))
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name("redirect:/owners/1"));

        verify( petService, times(1) ).save(any());
    }

    @Test
    public void initUpdateForm() throws Exception{
        mockModelUpdateForm();
        mockMvc.perform( get("/owners/1/pets/2/edit"))
                .andExpect( status().isOk() )
                .andExpect( model().attributeExists("owner"))
                .andExpect( model().attributeExists("pet"))
                .andExpect( view().name("pets/createOrUpdatePetForm"));

        verify( ownerService, times(1)).findById(anyLong());
        verify( petTypeService, times(1)).findAll();
        verify( petService, times(1)).findById(anyLong());

    }

    @Test
    public void processUpdateForm() throws Exception{
        mockModelCreateForm();
        mockMvc.perform( post("/owners/1/pets/2/edit"))
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name("redirect:/owners/1"));
        verify( petService, times(1) ).save(any());
    }
}