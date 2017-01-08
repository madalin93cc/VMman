package ro.upb.dai.mcc.vmman.web.rest;

import ro.upb.dai.mcc.vmman.VMmanApp;

import ro.upb.dai.mcc.vmman.domain.VmRequest;
import ro.upb.dai.mcc.vmman.repository.VmRequestRepository;
import ro.upb.dai.mcc.vmman.service.VmRequestService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VmRequestResource REST controller.
 *
 * @see VmRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VMmanApp.class)
public class VmRequestResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HDD = "AAAAAAAAAA";
    private static final String UPDATED_HDD = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESSOR = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSOR = "BBBBBBBBBB";

    private static final String DEFAULT_RAM = "AAAAAAAAAA";
    private static final String UPDATED_RAM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    @Inject
    private VmRequestRepository vmRequestRepository;

    @Inject
    private VmRequestService vmRequestService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restVmRequestMockMvc;

    private VmRequest vmRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VmRequestResource vmRequestResource = new VmRequestResource();
        ReflectionTestUtils.setField(vmRequestResource, "vmRequestService", vmRequestService);
        this.restVmRequestMockMvc = MockMvcBuilders.standaloneSetup(vmRequestResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VmRequest createEntity(EntityManager em) {
        VmRequest vmRequest = new VmRequest()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .hdd(DEFAULT_HDD)
                .processor(DEFAULT_PROCESSOR)
                .ram(DEFAULT_RAM)
                .approved(DEFAULT_APPROVED);
        return vmRequest;
    }

    @Before
    public void initTest() {
        vmRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createVmRequest() throws Exception {
        int databaseSizeBeforeCreate = vmRequestRepository.findAll().size();

        // Create the VmRequest

        restVmRequestMockMvc.perform(post("/api/vm-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vmRequest)))
            .andExpect(status().isCreated());

        // Validate the VmRequest in the database
        List<VmRequest> vmRequestList = vmRequestRepository.findAll();
        assertThat(vmRequestList).hasSize(databaseSizeBeforeCreate + 1);
        VmRequest testVmRequest = vmRequestList.get(vmRequestList.size() - 1);
        assertThat(testVmRequest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVmRequest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVmRequest.getHdd()).isEqualTo(DEFAULT_HDD);
        assertThat(testVmRequest.getProcessor()).isEqualTo(DEFAULT_PROCESSOR);
        assertThat(testVmRequest.getRam()).isEqualTo(DEFAULT_RAM);
        assertThat(testVmRequest.isApproved()).isEqualTo(DEFAULT_APPROVED);
    }

    @Test
    @Transactional
    public void createVmRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vmRequestRepository.findAll().size();

        // Create the VmRequest with an existing ID
        VmRequest existingVmRequest = new VmRequest();
        existingVmRequest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVmRequestMockMvc.perform(post("/api/vm-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingVmRequest)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VmRequest> vmRequestList = vmRequestRepository.findAll();
        assertThat(vmRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vmRequestRepository.findAll().size();
        // set the field null
        vmRequest.setName(null);

        // Create the VmRequest, which fails.

        restVmRequestMockMvc.perform(post("/api/vm-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vmRequest)))
            .andExpect(status().isBadRequest());

        List<VmRequest> vmRequestList = vmRequestRepository.findAll();
        assertThat(vmRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHddIsRequired() throws Exception {
        int databaseSizeBeforeTest = vmRequestRepository.findAll().size();
        // set the field null
        vmRequest.setHdd(null);

        // Create the VmRequest, which fails.

        restVmRequestMockMvc.perform(post("/api/vm-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vmRequest)))
            .andExpect(status().isBadRequest());

        List<VmRequest> vmRequestList = vmRequestRepository.findAll();
        assertThat(vmRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProcessorIsRequired() throws Exception {
        int databaseSizeBeforeTest = vmRequestRepository.findAll().size();
        // set the field null
        vmRequest.setProcessor(null);

        // Create the VmRequest, which fails.

        restVmRequestMockMvc.perform(post("/api/vm-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vmRequest)))
            .andExpect(status().isBadRequest());

        List<VmRequest> vmRequestList = vmRequestRepository.findAll();
        assertThat(vmRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRamIsRequired() throws Exception {
        int databaseSizeBeforeTest = vmRequestRepository.findAll().size();
        // set the field null
        vmRequest.setRam(null);

        // Create the VmRequest, which fails.

        restVmRequestMockMvc.perform(post("/api/vm-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vmRequest)))
            .andExpect(status().isBadRequest());

        List<VmRequest> vmRequestList = vmRequestRepository.findAll();
        assertThat(vmRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVmRequests() throws Exception {
        // Initialize the database
        vmRequestRepository.saveAndFlush(vmRequest);

        // Get all the vmRequestList
        restVmRequestMockMvc.perform(get("/api/vm-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vmRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].hdd").value(hasItem(DEFAULT_HDD.toString())))
            .andExpect(jsonPath("$.[*].processor").value(hasItem(DEFAULT_PROCESSOR.toString())))
            .andExpect(jsonPath("$.[*].ram").value(hasItem(DEFAULT_RAM.toString())))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())));
    }

    @Test
    @Transactional
    public void getVmRequest() throws Exception {
        // Initialize the database
        vmRequestRepository.saveAndFlush(vmRequest);

        // Get the vmRequest
        restVmRequestMockMvc.perform(get("/api/vm-requests/{id}", vmRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vmRequest.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.hdd").value(DEFAULT_HDD.toString()))
            .andExpect(jsonPath("$.processor").value(DEFAULT_PROCESSOR.toString()))
            .andExpect(jsonPath("$.ram").value(DEFAULT_RAM.toString()))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVmRequest() throws Exception {
        // Get the vmRequest
        restVmRequestMockMvc.perform(get("/api/vm-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVmRequest() throws Exception {
        // Initialize the database
        vmRequestService.save(vmRequest);

        int databaseSizeBeforeUpdate = vmRequestRepository.findAll().size();

        // Update the vmRequest
        VmRequest updatedVmRequest = vmRequestRepository.findOne(vmRequest.getId());
        updatedVmRequest
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .hdd(UPDATED_HDD)
                .processor(UPDATED_PROCESSOR)
                .ram(UPDATED_RAM)
                .approved(UPDATED_APPROVED);

        restVmRequestMockMvc.perform(put("/api/vm-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVmRequest)))
            .andExpect(status().isOk());

        // Validate the VmRequest in the database
        List<VmRequest> vmRequestList = vmRequestRepository.findAll();
        assertThat(vmRequestList).hasSize(databaseSizeBeforeUpdate);
        VmRequest testVmRequest = vmRequestList.get(vmRequestList.size() - 1);
        assertThat(testVmRequest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVmRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVmRequest.getHdd()).isEqualTo(UPDATED_HDD);
        assertThat(testVmRequest.getProcessor()).isEqualTo(UPDATED_PROCESSOR);
        assertThat(testVmRequest.getRam()).isEqualTo(UPDATED_RAM);
        assertThat(testVmRequest.isApproved()).isEqualTo(UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void updateNonExistingVmRequest() throws Exception {
        int databaseSizeBeforeUpdate = vmRequestRepository.findAll().size();

        // Create the VmRequest

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVmRequestMockMvc.perform(put("/api/vm-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vmRequest)))
            .andExpect(status().isCreated());

        // Validate the VmRequest in the database
        List<VmRequest> vmRequestList = vmRequestRepository.findAll();
        assertThat(vmRequestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVmRequest() throws Exception {
        // Initialize the database
        vmRequestService.save(vmRequest);

        int databaseSizeBeforeDelete = vmRequestRepository.findAll().size();

        // Get the vmRequest
        restVmRequestMockMvc.perform(delete("/api/vm-requests/{id}", vmRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VmRequest> vmRequestList = vmRequestRepository.findAll();
        assertThat(vmRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
