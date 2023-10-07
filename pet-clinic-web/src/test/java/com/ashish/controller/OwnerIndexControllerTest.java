package com.ashish.controller;

import com.ashish.model.Owner;
import com.ashish.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerIndexControllerTest {

    public static final long OWNER_ID = 1L;
    public static final long ID = 1L;
    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerIndexController ownerIndexController;

    MockMvc mockMvc;
    Set<Owner> owners ;
    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        Owner owner1 = Owner.builder().id(1L).build();
        Owner owner2 = Owner.builder().id(2L).build();
        owners.add(owner1);
        owners.add(owner2);

        mockMvc = MockMvcBuilders.
                standaloneSetup(ownerIndexController)
                .build();
    }

    @Test
    void findOwners() throws Exception{
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect( model().attributeExists("owner"))
                .andExpect(view().name("owners/findOwners"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception{
        List<Owner> ownerList = new ArrayList<>();
        ownerList.add( Owner.builder().id(ID).build());
        ownerList.add( Owner.builder().id(2L).build());

        when( ownerService.findAllByLastNameLike(any())).thenReturn(ownerList);

        mockMvc.perform( get("/owners"))
                .andExpect( status().isOk())
                .andExpect( view().name("owners/ownersList"))
                .andExpect( model().attribute("selections", hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception{
        List<Owner> ownerList = new ArrayList<>();
        ownerList.add( Owner.builder().id(ID).build());
        when( ownerService.findAllByLastNameLike(any())).thenReturn(ownerList);

        mockMvc.perform(get("/owners"))
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name("redirect:/owners/"+ ID ));
    }

    @Test
    void showOwner() throws Exception{
        when( ownerService.findById(anyLong())).thenReturn(Owner.builder().id(OWNER_ID).build());

        mockMvc.perform(get("/owners/" + OWNER_ID ))
                .andExpect( view().name("owners/ownerDetails"))
                .andExpect( model().attributeExists("owner"))
                .andExpect( model().attribute("owner", hasProperty("id", is(OWNER_ID))))
                .andExpect( status().isOk() );

        verify( ownerService, times(1)).findById( anyLong() );
    }

    @Test
    void initCreateForm() throws Exception{
        //this will be called on /new and return an empty owner object and view will be createOrUpdate

        mockMvc.perform( get("/owners/new"))
                .andExpect( status().isOk() )
                .andExpect( model().attributeExists("owner"))
                .andExpect( view().name("owners/createOrUpdateOwnerForm"));

        verifyNoInteractions( ownerService );
    }

    @Test
    void processCreateForm() throws Exception {
        //this will be called on response of initCreateForm, it will save data and redirect to detail page
        Owner owner1 = Owner.builder().id(1L).build();
        when( ownerService.save( any()) ).thenReturn( owner1 );

        mockMvc.perform( post("/owners/new") )
                .andExpect( status().is3xxRedirection() )
               //.andExpect( model().attributeExists("owner") )
                .andExpect( view().name("redirect:/owners/1"));

        verify( ownerService, times(1)).save( any() );

    }

    @Test
    void initUpdateForm() throws Exception{
        //this will be called on /ownerId/edit, get the owner object and view will be returned createOrUpdate
        Owner owner1 = Owner.builder().id(1L).build();
        when( ownerService.findById( any()) ).thenReturn( owner1 );

        mockMvc.perform( get("/owners/1/edit"))
                .andExpect( status().isOk() )
                .andExpect( model().attributeExists("owner"))
                .andExpect( view().name(OWNERS_CREATE_OR_UPDATE_OWNER_FORM));

        verify( ownerService, times(1)).findById(anyLong());
    }

    @Test
    void processUpdateForm() throws Exception{
        //this will be called on response of initUpdateForm, it will save data and redirect to detail page
        Owner owner1 = Owner.builder().id(1L).build();
        when( ownerService.save( any()) ).thenReturn( owner1 );

        mockMvc.perform( post("/owners/1/edit") )
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name("redirect:/owners/1"));
               // .andExpect( model().attributeExists("owner"));

        verify( ownerService, times(1)).save(any());
    }
}