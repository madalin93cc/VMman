package ro.upb.dai.mcc.vmman.web.rest;

import ro.upb.dai.mcc.vmman.VMmanApp;

import ro.upb.dai.mcc.vmman.domain.VirtualMachine;
import ro.upb.dai.mcc.vmman.repository.VirtualMachineRepository;
import ro.upb.dai.mcc.vmman.service.VirtualMachineService;

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

import ro.upb.dai.mcc.vmman.domain.enumeration.Environment;
/**
 * Test class for the VirtualMachineResource REST controller.
 *
 * @see VirtualMachineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VMmanApp.class)
public class VirtualMachineResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Environment DEFAULT_ENVIRONMENT = Environment.DEV;
    private static final Environment UPDATED_ENVIRONMENT = Environment.TEST;

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final String DEFAULT_HDD = "AAAAAAAAAA";
    private static final String UPDATED_HDD = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESSOR = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSOR = "BBBBBBBBBB";

    private static final String DEFAULT_RAM = "AAAAAAAAAA";
    private static final String UPDATED_RAM = "BBBBBBBBBB";

    @Inject
    private VirtualMachineRepository virtualMachineRepository;

    @Inject
    private VirtualMachineService virtualMachineService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restVirtualMachineMockMvc;

    private VirtualMachine virtualMachine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VirtualMachineResource virtualMachineResource = new VirtualMachineResource();
        ReflectionTestUtils.setField(virtualMachineResource, "virtualMachineService", virtualMachineService);
        this.restVirtualMachineMockMvc = MockMvcBuilders.standaloneSetup(virtualMachineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VirtualMachine createEntity(EntityManager em) {
        VirtualMachine virtualMachine = new VirtualMachine()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .environment(DEFAULT_ENVIRONMENT)
                .ip(DEFAULT_IP)
                .hdd(DEFAULT_HDD)
                .processor(DEFAULT_PROCESSOR)
                .ram(DEFAULT_RAM);
        return virtualMachine;
    }

    @Before
    public void initTest() {
        virtualMachine = createEntity(em);
    }

    @Test
    @Transactional
    public void createVirtualMachine() throws Exception {
        int databaseSizeBeforeCreate = virtualMachineRepository.findAll().size();

        // Create the VirtualMachine

        restVirtualMachineMockMvc.perform(post("/api/virtual-machines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virtualMachine)))
            .andExpect(status().isCreated());

        // Validate the VirtualMachine in the database
        List<VirtualMachine> virtualMachineList = virtualMachineRepository.findAll();
        assertThat(virtualMachineList).hasSize(databaseSizeBeforeCreate + 1);
        VirtualMachine testVirtualMachine = virtualMachineList.get(virtualMachineList.size() - 1);
        assertThat(testVirtualMachine.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVirtualMachine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVirtualMachine.getEnvironment()).isEqualTo(DEFAULT_ENVIRONMENT);
        assertThat(testVirtualMachine.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testVirtualMachine.getHdd()).isEqualTo(DEFAULT_HDD);
        assertThat(testVirtualMachine.getProcessor()).isEqualTo(DEFAULT_PROCESSOR);
        assertThat(testVirtualMachine.getRam()).isEqualTo(DEFAULT_RAM);
    }

    @Test
    @Transactional
    public void createVirtualMachineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = virtualMachineRepository.findAll().size();

        // Create the VirtualMachine with an existing ID
        VirtualMachine existingVirtualMachine = new VirtualMachine();
        existingVirtualMachine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVirtualMachineMockMvc.perform(post("/api/virtual-machines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingVirtualMachine)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VirtualMachine> virtualMachineList = virtualMachineRepository.findAll();
        assertThat(virtualMachineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = virtualMachineRepository.findAll().size();
        // set the field null
        virtualMachine.setName(null);

        // Create the VirtualMachine, which fails.

        restVirtualMachineMockMvc.perform(post("/api/virtual-machines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virtualMachine)))
            .andExpect(status().isBadRequest());

        List<VirtualMachine> virtualMachineList = virtualMachineRepository.findAll();
        assertThat(virtualMachineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVirtualMachines() throws Exception {
        // Initialize the database
        virtualMachineRepository.saveAndFlush(virtualMachine);

        // Get all the virtualMachineList
        restVirtualMachineMockMvc.perform(get("/api/virtual-machines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(virtualMachine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].environment").value(hasItem(DEFAULT_ENVIRONMENT.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].hdd").value(hasItem(DEFAULT_HDD.toString())))
            .andExpect(jsonPath("$.[*].processor").value(hasItem(DEFAULT_PROCESSOR.toString())))
            .andExpect(jsonPath("$.[*].ram").value(hasItem(DEFAULT_RAM.toString())));
    }

    @Test
    @Transactional
    public void getVirtualMachine() throws Exception {
        // Initialize the database
        virtualMachineRepository.saveAndFlush(virtualMachine);

        // Get the virtualMachine
        restVirtualMachineMockMvc.perform(get("/api/virtual-machines/{id}", virtualMachine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(virtualMachine.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.environment").value(DEFAULT_ENVIRONMENT.toString()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()))
            .andExpect(jsonPath("$.hdd").value(DEFAULT_HDD.toString()))
            .andExpect(jsonPath("$.processor").value(DEFAULT_PROCESSOR.toString()))
            .andExpect(jsonPath("$.ram").value(DEFAULT_RAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVirtualMachine() throws Exception {
        // Get the virtualMachine
        restVirtualMachineMockMvc.perform(get("/api/virtual-machines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVirtualMachine() throws Exception {
        // Initialize the database
        virtualMachineService.save(virtualMachine);

        int databaseSizeBeforeUpdate = virtualMachineRepository.findAll().size();

        // Update the virtualMachine
        VirtualMachine updatedVirtualMachine = virtualMachineRepository.findOne(virtualMachine.getId());
        updatedVirtualMachine
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .environment(UPDATED_ENVIRONMENT)
                .ip(UPDATED_IP)
                .hdd(UPDATED_HDD)
                .processor(UPDATED_PROCESSOR)
                .ram(UPDATED_RAM);

        restVirtualMachineMockMvc.perform(put("/api/virtual-machines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVirtualMachine)))
            .andExpect(status().isOk());

        // Validate the VirtualMachine in the database
        List<VirtualMachine> virtualMachineList = virtualMachineRepository.findAll();
        assertThat(virtualMachineList).hasSize(databaseSizeBeforeUpdate);
        VirtualMachine testVirtualMachine = virtualMachineList.get(virtualMachineList.size() - 1);
        assertThat(testVirtualMachine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVirtualMachine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVirtualMachine.getEnvironment()).isEqualTo(UPDATED_ENVIRONMENT);
        assertThat(testVirtualMachine.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testVirtualMachine.getHdd()).isEqualTo(UPDATED_HDD);
        assertThat(testVirtualMachine.getProcessor()).isEqualTo(UPDATED_PROCESSOR);
        assertThat(testVirtualMachine.getRam()).isEqualTo(UPDATED_RAM);
    }

    @Test
    @Transactional
    public void updateNonExistingVirtualMachine() throws Exception {
        int databaseSizeBeforeUpdate = virtualMachineRepository.findAll().size();

        // Create the VirtualMachine

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVirtualMachineMockMvc.perform(put("/api/virtual-machines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virtualMachine)))
            .andExpect(status().isCreated());

        // Validate the VirtualMachine in the database
        List<VirtualMachine> virtualMachineList = virtualMachineRepository.findAll();
        assertThat(virtualMachineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVirtualMachine() throws Exception {
        // Initialize the database
        virtualMachineService.save(virtualMachine);

        int databaseSizeBeforeDelete = virtualMachineRepository.findAll().size();

        // Get the virtualMachine
        restVirtualMachineMockMvc.perform(delete("/api/virtual-machines/{id}", virtualMachine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VirtualMachine> virtualMachineList = virtualMachineRepository.findAll();
        assertThat(virtualMachineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
