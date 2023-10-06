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

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerIndexControllerTest {

    public static final long OWNER_ID = 1L;
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
    void getListOfOwners() throws Exception {
        when(ownerService.findAll()).thenReturn(owners);
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners",hasSize(2)))
        ;
    }

    @Test
    void getListOfOwnersByIndex() throws Exception {
        when(ownerService.findAll()).thenReturn(owners);
        mockMvc.perform(get("/owners/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners",hasSize(2)))
        ;
    }

    @Test
    void findOwners() throws Exception{
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("notImplemented"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void showOwner() throws Exception{
        when( ownerService.findById(anyLong())).thenReturn(Owner.builder().id(OWNER_ID).build());

        mockMvc.perform(get("/owners/" + OWNER_ID + "/show"))
                .andExpect( view().name("owners/ownerDetails"))
                .andExpect( model().attributeExists("owner"))
                .andExpect( model().attribute("owner", hasProperty("id", is(OWNER_ID))))
                .andExpect( status().isOk() );

        verify( ownerService, times(1)).findById( anyLong() );
    }
}