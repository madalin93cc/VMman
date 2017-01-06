package ro.upb.dai.mcc.vmman.web.rest;

import ro.upb.dai.mcc.vmman.VMmanApp;

import ro.upb.dai.mcc.vmman.domain.OperatingSystem;
import ro.upb.dai.mcc.vmman.repository.OperatingSystemRepository;
import ro.upb.dai.mcc.vmman.service.OperatingSystemService;

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
 * Test class for the OperatingSystemResource REST controller.
 *
 * @see OperatingSystemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VMmanApp.class)
public class OperatingSystemResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private OperatingSystemRepository operatingSystemRepository;

    @Inject
    private OperatingSystemService operatingSystemService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restOperatingSystemMockMvc;

    private OperatingSystem operatingSystem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OperatingSystemResource operatingSystemResource = new OperatingSystemResource();
        ReflectionTestUtils.setField(operatingSystemResource, "operatingSystemService", operatingSystemService);
        this.restOperatingSystemMockMvc = MockMvcBuilders.standaloneSetup(operatingSystemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperatingSystem createEntity(EntityManager em) {
        OperatingSystem operatingSystem = new OperatingSystem()
                .name(DEFAULT_NAME);
        return operatingSystem;
    }

    @Before
    public void initTest() {
        operatingSystem = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperatingSystem() throws Exception {
        int databaseSizeBeforeCreate = operatingSystemRepository.findAll().size();

        // Create the OperatingSystem

        restOperatingSystemMockMvc.perform(post("/api/operating-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operatingSystem)))
            .andExpect(status().isCreated());

        // Validate the OperatingSystem in the database
        List<OperatingSystem> operatingSystemList = operatingSystemRepository.findAll();
        assertThat(operatingSystemList).hasSize(databaseSizeBeforeCreate + 1);
        OperatingSystem testOperatingSystem = operatingSystemList.get(operatingSystemList.size() - 1);
        assertThat(testOperatingSystem.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createOperatingSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operatingSystemRepository.findAll().size();

        // Create the OperatingSystem with an existing ID
        OperatingSystem existingOperatingSystem = new OperatingSystem();
        existingOperatingSystem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperatingSystemMockMvc.perform(post("/api/operating-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingOperatingSystem)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OperatingSystem> operatingSystemList = operatingSystemRepository.findAll();
        assertThat(operatingSystemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = operatingSystemRepository.findAll().size();
        // set the field null
        operatingSystem.setName(null);

        // Create the OperatingSystem, which fails.

        restOperatingSystemMockMvc.perform(post("/api/operating-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operatingSystem)))
            .andExpect(status().isBadRequest());

        List<OperatingSystem> operatingSystemList = operatingSystemRepository.findAll();
        assertThat(operatingSystemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperatingSystems() throws Exception {
        // Initialize the database
        operatingSystemRepository.saveAndFlush(operatingSystem);

        // Get all the operatingSystemList
        restOperatingSystemMockMvc.perform(get("/api/operating-systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operatingSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getOperatingSystem() throws Exception {
        // Initialize the database
        operatingSystemRepository.saveAndFlush(operatingSystem);

        // Get the operatingSystem
        restOperatingSystemMockMvc.perform(get("/api/operating-systems/{id}", operatingSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operatingSystem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOperatingSystem() throws Exception {
        // Get the operatingSystem
        restOperatingSystemMockMvc.perform(get("/api/operating-systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperatingSystem() throws Exception {
        // Initialize the database
        operatingSystemService.save(operatingSystem);

        int databaseSizeBeforeUpdate = operatingSystemRepository.findAll().size();

        // Update the operatingSystem
        OperatingSystem updatedOperatingSystem = operatingSystemRepository.findOne(operatingSystem.getId());
        updatedOperatingSystem
                .name(UPDATED_NAME);

        restOperatingSystemMockMvc.perform(put("/api/operating-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperatingSystem)))
            .andExpect(status().isOk());

        // Validate the OperatingSystem in the database
        List<OperatingSystem> operatingSystemList = operatingSystemRepository.findAll();
        assertThat(operatingSystemList).hasSize(databaseSizeBeforeUpdate);
        OperatingSystem testOperatingSystem = operatingSystemList.get(operatingSystemList.size() - 1);
        assertThat(testOperatingSystem.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingOperatingSystem() throws Exception {
        int databaseSizeBeforeUpdate = operatingSystemRepository.findAll().size();

        // Create the OperatingSystem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOperatingSystemMockMvc.perform(put("/api/operating-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operatingSystem)))
            .andExpect(status().isCreated());

        // Validate the OperatingSystem in the database
        List<OperatingSystem> operatingSystemList = operatingSystemRepository.findAll();
        assertThat(operatingSystemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOperatingSystem() throws Exception {
        // Initialize the database
        operatingSystemService.save(operatingSystem);

        int databaseSizeBeforeDelete = operatingSystemRepository.findAll().size();

        // Get the operatingSystem
        restOperatingSystemMockMvc.perform(delete("/api/operating-systems/{id}", operatingSystem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OperatingSystem> operatingSystemList = operatingSystemRepository.findAll();
        assertThat(operatingSystemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
