package ro.upb.dai.mcc.vmman.web.rest;

import ro.upb.dai.mcc.vmman.VMmanApp;

import ro.upb.dai.mcc.vmman.domain.GenericVm;
import ro.upb.dai.mcc.vmman.repository.GenericVmRepository;
import ro.upb.dai.mcc.vmman.service.GenericVmService;
import ro.upb.dai.mcc.vmman.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static ro.upb.dai.mcc.vmman.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GenericVmResource REST controller.
 *
 * @see GenericVmResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VMmanApp.class)
public class GenericVmResourceIntTest {

    private static final String DEFAULT_HDD = "AAAAAAAAAA";
    private static final String UPDATED_HDD = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESSOR = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSOR = "BBBBBBBBBB";

    private static final String DEFAULT_RAM = "AAAAAAAAAA";
    private static final String UPDATED_RAM = "BBBBBBBBBB";

    @Autowired
    private GenericVmRepository genericVmRepository;

    @Autowired
    private GenericVmService genericVmService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGenericVmMockMvc;

    private GenericVm genericVm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GenericVmResource genericVmResource = new GenericVmResource(genericVmService);
        this.restGenericVmMockMvc = MockMvcBuilders.standaloneSetup(genericVmResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GenericVm createEntity(EntityManager em) {
        GenericVm genericVm = new GenericVm()
            .hdd(DEFAULT_HDD)
            .processor(DEFAULT_PROCESSOR)
            .ram(DEFAULT_RAM);
        return genericVm;
    }

    @Before
    public void initTest() {
        genericVm = createEntity(em);
    }

    @Test
    @Transactional
    public void createGenericVm() throws Exception {
        int databaseSizeBeforeCreate = genericVmRepository.findAll().size();

        // Create the GenericVm
        restGenericVmMockMvc.perform(post("/api/generic-vms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericVm)))
            .andExpect(status().isCreated());

        // Validate the GenericVm in the database
        List<GenericVm> genericVmList = genericVmRepository.findAll();
        assertThat(genericVmList).hasSize(databaseSizeBeforeCreate + 1);
        GenericVm testGenericVm = genericVmList.get(genericVmList.size() - 1);
        assertThat(testGenericVm.getHdd()).isEqualTo(DEFAULT_HDD);
        assertThat(testGenericVm.getProcessor()).isEqualTo(DEFAULT_PROCESSOR);
        assertThat(testGenericVm.getRam()).isEqualTo(DEFAULT_RAM);
    }

    @Test
    @Transactional
    public void createGenericVmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = genericVmRepository.findAll().size();

        // Create the GenericVm with an existing ID
        genericVm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenericVmMockMvc.perform(post("/api/generic-vms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericVm)))
            .andExpect(status().isBadRequest());

        // Validate the GenericVm in the database
        List<GenericVm> genericVmList = genericVmRepository.findAll();
        assertThat(genericVmList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGenericVms() throws Exception {
        // Initialize the database
        genericVmRepository.saveAndFlush(genericVm);

        // Get all the genericVmList
        restGenericVmMockMvc.perform(get("/api/generic-vms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genericVm.getId().intValue())))
            .andExpect(jsonPath("$.[*].hdd").value(hasItem(DEFAULT_HDD.toString())))
            .andExpect(jsonPath("$.[*].processor").value(hasItem(DEFAULT_PROCESSOR.toString())))
            .andExpect(jsonPath("$.[*].ram").value(hasItem(DEFAULT_RAM.toString())));
    }

    @Test
    @Transactional
    public void getGenericVm() throws Exception {
        // Initialize the database
        genericVmRepository.saveAndFlush(genericVm);

        // Get the genericVm
        restGenericVmMockMvc.perform(get("/api/generic-vms/{id}", genericVm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(genericVm.getId().intValue()))
            .andExpect(jsonPath("$.hdd").value(DEFAULT_HDD.toString()))
            .andExpect(jsonPath("$.processor").value(DEFAULT_PROCESSOR.toString()))
            .andExpect(jsonPath("$.ram").value(DEFAULT_RAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGenericVm() throws Exception {
        // Get the genericVm
        restGenericVmMockMvc.perform(get("/api/generic-vms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGenericVm() throws Exception {
        // Initialize the database
        genericVmService.save(genericVm);

        int databaseSizeBeforeUpdate = genericVmRepository.findAll().size();

        // Update the genericVm
        GenericVm updatedGenericVm = genericVmRepository.findOne(genericVm.getId());
        // Disconnect from session so that the updates on updatedGenericVm are not directly saved in db
        em.detach(updatedGenericVm);
        updatedGenericVm
            .hdd(UPDATED_HDD)
            .processor(UPDATED_PROCESSOR)
            .ram(UPDATED_RAM);

        restGenericVmMockMvc.perform(put("/api/generic-vms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGenericVm)))
            .andExpect(status().isOk());

        // Validate the GenericVm in the database
        List<GenericVm> genericVmList = genericVmRepository.findAll();
        assertThat(genericVmList).hasSize(databaseSizeBeforeUpdate);
        GenericVm testGenericVm = genericVmList.get(genericVmList.size() - 1);
        assertThat(testGenericVm.getHdd()).isEqualTo(UPDATED_HDD);
        assertThat(testGenericVm.getProcessor()).isEqualTo(UPDATED_PROCESSOR);
        assertThat(testGenericVm.getRam()).isEqualTo(UPDATED_RAM);
    }

    @Test
    @Transactional
    public void updateNonExistingGenericVm() throws Exception {
        int databaseSizeBeforeUpdate = genericVmRepository.findAll().size();

        // Create the GenericVm

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGenericVmMockMvc.perform(put("/api/generic-vms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericVm)))
            .andExpect(status().isCreated());

        // Validate the GenericVm in the database
        List<GenericVm> genericVmList = genericVmRepository.findAll();
        assertThat(genericVmList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGenericVm() throws Exception {
        // Initialize the database
        genericVmService.save(genericVm);

        int databaseSizeBeforeDelete = genericVmRepository.findAll().size();

        // Get the genericVm
        restGenericVmMockMvc.perform(delete("/api/generic-vms/{id}", genericVm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GenericVm> genericVmList = genericVmRepository.findAll();
        assertThat(genericVmList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenericVm.class);
        GenericVm genericVm1 = new GenericVm();
        genericVm1.setId(1L);
        GenericVm genericVm2 = new GenericVm();
        genericVm2.setId(genericVm1.getId());
        assertThat(genericVm1).isEqualTo(genericVm2);
        genericVm2.setId(2L);
        assertThat(genericVm1).isNotEqualTo(genericVm2);
        genericVm1.setId(null);
        assertThat(genericVm1).isNotEqualTo(genericVm2);
    }
}
