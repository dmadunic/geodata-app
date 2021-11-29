package com.ag04.geodata.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ag04.geodata.IntegrationTest;
import com.ag04.geodata.domain.Country;
import com.ag04.geodata.repository.CountryRepository;
import com.ag04.geodata.service.criteria.CountryCriteria;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CountryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CountryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_A_2 = "AA";
    private static final String UPDATED_CODE_A_2 = "BB";

    private static final String DEFAULT_CODE_A_3 = "AAA";
    private static final String UPDATED_CODE_A_3 = "BBB";

    private static final String DEFAULT_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_FLAG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/countries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountryMockMvc;

    private Country country;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createEntity(EntityManager em) {
        Country country = new Country()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .codeA2(DEFAULT_CODE_A_2)
            .codeA3(DEFAULT_CODE_A_3)
            .flag(DEFAULT_FLAG)
            .active(DEFAULT_ACTIVE);
        return country;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createUpdatedEntity(EntityManager em) {
        Country country = new Country()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .codeA2(UPDATED_CODE_A_2)
            .codeA3(UPDATED_CODE_A_3)
            .flag(UPDATED_FLAG)
            .active(UPDATED_ACTIVE);
        return country;
    }

    @BeforeEach
    public void initTest() {
        country = createEntity(em);
    }

    @Test
    @Transactional
    void createCountry() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();
        // Create the Country
        restCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isCreated());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate + 1);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCountry.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCountry.getCodeA2()).isEqualTo(DEFAULT_CODE_A_2);
        assertThat(testCountry.getCodeA3()).isEqualTo(DEFAULT_CODE_A_3);
        assertThat(testCountry.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testCountry.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createCountryWithExistingId() throws Exception {
        // Create the Country with an existing ID
        country.setId(1L);

        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setName(null);

        // Create the Country, which fails.

        restCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setCode(null);

        // Create the Country, which fails.

        restCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeA2IsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setCodeA2(null);

        // Create the Country, which fails.

        restCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeA3IsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setCodeA3(null);

        // Create the Country, which fails.

        restCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setActive(null);

        // Create the Country, which fails.

        restCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCountries() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].codeA2").value(hasItem(DEFAULT_CODE_A_2)))
            .andExpect(jsonPath("$.[*].codeA3").value(hasItem(DEFAULT_CODE_A_3)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get the country
        restCountryMockMvc
            .perform(get(ENTITY_API_URL_ID, country.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(country.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.codeA2").value(DEFAULT_CODE_A_2))
            .andExpect(jsonPath("$.codeA3").value(DEFAULT_CODE_A_3))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getCountriesByIdFiltering() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        Long id = country.getId();

        defaultCountryShouldBeFound("id.equals=" + id);
        defaultCountryShouldNotBeFound("id.notEquals=" + id);

        defaultCountryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCountryShouldNotBeFound("id.greaterThan=" + id);

        defaultCountryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCountryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCountriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name equals to DEFAULT_NAME
        defaultCountryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the countryList where name equals to UPDATED_NAME
        defaultCountryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name not equals to DEFAULT_NAME
        defaultCountryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the countryList where name not equals to UPDATED_NAME
        defaultCountryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCountryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the countryList where name equals to UPDATED_NAME
        defaultCountryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name is not null
        defaultCountryShouldBeFound("name.specified=true");

        // Get all the countryList where name is null
        defaultCountryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByNameContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name contains DEFAULT_NAME
        defaultCountryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the countryList where name contains UPDATED_NAME
        defaultCountryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name does not contain DEFAULT_NAME
        defaultCountryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the countryList where name does not contain UPDATED_NAME
        defaultCountryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where code equals to DEFAULT_CODE
        defaultCountryShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the countryList where code equals to UPDATED_CODE
        defaultCountryShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where code not equals to DEFAULT_CODE
        defaultCountryShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the countryList where code not equals to UPDATED_CODE
        defaultCountryShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCountryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the countryList where code equals to UPDATED_CODE
        defaultCountryShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where code is not null
        defaultCountryShouldBeFound("code.specified=true");

        // Get all the countryList where code is null
        defaultCountryShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCodeContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where code contains DEFAULT_CODE
        defaultCountryShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the countryList where code contains UPDATED_CODE
        defaultCountryShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where code does not contain DEFAULT_CODE
        defaultCountryShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the countryList where code does not contain UPDATED_CODE
        defaultCountryShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeA2IsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where codeA2 equals to DEFAULT_CODE_A_2
        defaultCountryShouldBeFound("codeA2.equals=" + DEFAULT_CODE_A_2);

        // Get all the countryList where codeA2 equals to UPDATED_CODE_A_2
        defaultCountryShouldNotBeFound("codeA2.equals=" + UPDATED_CODE_A_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeA2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where codeA2 not equals to DEFAULT_CODE_A_2
        defaultCountryShouldNotBeFound("codeA2.notEquals=" + DEFAULT_CODE_A_2);

        // Get all the countryList where codeA2 not equals to UPDATED_CODE_A_2
        defaultCountryShouldBeFound("codeA2.notEquals=" + UPDATED_CODE_A_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeA2IsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where codeA2 in DEFAULT_CODE_A_2 or UPDATED_CODE_A_2
        defaultCountryShouldBeFound("codeA2.in=" + DEFAULT_CODE_A_2 + "," + UPDATED_CODE_A_2);

        // Get all the countryList where codeA2 equals to UPDATED_CODE_A_2
        defaultCountryShouldNotBeFound("codeA2.in=" + UPDATED_CODE_A_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeA2IsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where codeA2 is not null
        defaultCountryShouldBeFound("codeA2.specified=true");

        // Get all the countryList where codeA2 is null
        defaultCountryShouldNotBeFound("codeA2.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCodeA2ContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where codeA2 contains DEFAULT_CODE_A_2
        defaultCountryShouldBeFound("codeA2.contains=" + DEFAULT_CODE_A_2);

        // Get all the countryList where codeA2 contains UPDATED_CODE_A_2
        defaultCountryShouldNotBeFound("codeA2.contains=" + UPDATED_CODE_A_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeA2NotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where codeA2 does not contain DEFAULT_CODE_A_2
        defaultCountryShouldNotBeFound("codeA2.doesNotContain=" + DEFAULT_CODE_A_2);

        // Get all the countryList where codeA2 does not contain UPDATED_CODE_A_2
        defaultCountryShouldBeFound("codeA2.doesNotContain=" + UPDATED_CODE_A_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeA3IsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where codeA3 equals to DEFAULT_CODE_A_3
        defaultCountryShouldBeFound("codeA3.equals=" + DEFAULT_CODE_A_3);

        // Get all the countryList where codeA3 equals to UPDATED_CODE_A_3
        defaultCountryShouldNotBeFound("codeA3.equals=" + UPDATED_CODE_A_3);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeA3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where codeA3 not equals to DEFAULT_CODE_A_3
        defaultCountryShouldNotBeFound("codeA3.notEquals=" + DEFAULT_CODE_A_3);

        // Get all the countryList where codeA3 not equals to UPDATED_CODE_A_3
        defaultCountryShouldBeFound("codeA3.notEquals=" + UPDATED_CODE_A_3);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeA3IsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where codeA3 in DEFAULT_CODE_A_3 or UPDATED_CODE_A_3
        defaultCountryShouldBeFound("codeA3.in=" + DEFAULT_CODE_A_3 + "," + UPDATED_CODE_A_3);

        // Get all the countryList where codeA3 equals to UPDATED_CODE_A_3
        defaultCountryShouldNotBeFound("codeA3.in=" + UPDATED_CODE_A_3);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeA3IsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where codeA3 is not null
        defaultCountryShouldBeFound("codeA3.specified=true");

        // Get all the countryList where codeA3 is null
        defaultCountryShouldNotBeFound("codeA3.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCodeA3ContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where codeA3 contains DEFAULT_CODE_A_3
        defaultCountryShouldBeFound("codeA3.contains=" + DEFAULT_CODE_A_3);

        // Get all the countryList where codeA3 contains UPDATED_CODE_A_3
        defaultCountryShouldNotBeFound("codeA3.contains=" + UPDATED_CODE_A_3);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeA3NotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where codeA3 does not contain DEFAULT_CODE_A_3
        defaultCountryShouldNotBeFound("codeA3.doesNotContain=" + DEFAULT_CODE_A_3);

        // Get all the countryList where codeA3 does not contain UPDATED_CODE_A_3
        defaultCountryShouldBeFound("codeA3.doesNotContain=" + UPDATED_CODE_A_3);
    }

    @Test
    @Transactional
    void getAllCountriesByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where flag equals to DEFAULT_FLAG
        defaultCountryShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the countryList where flag equals to UPDATED_FLAG
        defaultCountryShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCountriesByFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where flag not equals to DEFAULT_FLAG
        defaultCountryShouldNotBeFound("flag.notEquals=" + DEFAULT_FLAG);

        // Get all the countryList where flag not equals to UPDATED_FLAG
        defaultCountryShouldBeFound("flag.notEquals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCountriesByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultCountryShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the countryList where flag equals to UPDATED_FLAG
        defaultCountryShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCountriesByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where flag is not null
        defaultCountryShouldBeFound("flag.specified=true");

        // Get all the countryList where flag is null
        defaultCountryShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByFlagContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where flag contains DEFAULT_FLAG
        defaultCountryShouldBeFound("flag.contains=" + DEFAULT_FLAG);

        // Get all the countryList where flag contains UPDATED_FLAG
        defaultCountryShouldNotBeFound("flag.contains=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCountriesByFlagNotContainsSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where flag does not contain DEFAULT_FLAG
        defaultCountryShouldNotBeFound("flag.doesNotContain=" + DEFAULT_FLAG);

        // Get all the countryList where flag does not contain UPDATED_FLAG
        defaultCountryShouldBeFound("flag.doesNotContain=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCountriesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where active equals to DEFAULT_ACTIVE
        defaultCountryShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the countryList where active equals to UPDATED_ACTIVE
        defaultCountryShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllCountriesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where active not equals to DEFAULT_ACTIVE
        defaultCountryShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the countryList where active not equals to UPDATED_ACTIVE
        defaultCountryShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllCountriesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCountryShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the countryList where active equals to UPDATED_ACTIVE
        defaultCountryShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllCountriesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where active is not null
        defaultCountryShouldBeFound("active.specified=true");

        // Get all the countryList where active is null
        defaultCountryShouldNotBeFound("active.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountryShouldBeFound(String filter) throws Exception {
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].codeA2").value(hasItem(DEFAULT_CODE_A_2)))
            .andExpect(jsonPath("$.[*].codeA3").value(hasItem(DEFAULT_CODE_A_3)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountryShouldNotBeFound(String filter) throws Exception {
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCountry() throws Exception {
        // Get the country
        restCountryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country
        Country updatedCountry = countryRepository.findById(country.getId()).get();
        // Disconnect from session so that the updates on updatedCountry are not directly saved in db
        em.detach(updatedCountry);
        updatedCountry
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .codeA2(UPDATED_CODE_A_2)
            .codeA3(UPDATED_CODE_A_3)
            .flag(UPDATED_FLAG)
            .active(UPDATED_ACTIVE);

        restCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCountry.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCountry))
            )
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCountry.getCodeA2()).isEqualTo(UPDATED_CODE_A_2);
        assertThat(testCountry.getCodeA3()).isEqualTo(UPDATED_CODE_A_3);
        assertThat(testCountry.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testCountry.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, country.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(country))
            )
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(country))
            )
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCountryWithPatch() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country using partial update
        Country partialUpdatedCountry = new Country();
        partialUpdatedCountry.setId(country.getId());

        partialUpdatedCountry.name(UPDATED_NAME).code(UPDATED_CODE).codeA2(UPDATED_CODE_A_2).codeA3(UPDATED_CODE_A_3);

        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountry))
            )
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCountry.getCodeA2()).isEqualTo(UPDATED_CODE_A_2);
        assertThat(testCountry.getCodeA3()).isEqualTo(UPDATED_CODE_A_3);
        assertThat(testCountry.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testCountry.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateCountryWithPatch() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country using partial update
        Country partialUpdatedCountry = new Country();
        partialUpdatedCountry.setId(country.getId());

        partialUpdatedCountry
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .codeA2(UPDATED_CODE_A_2)
            .codeA3(UPDATED_CODE_A_3)
            .flag(UPDATED_FLAG)
            .active(UPDATED_ACTIVE);

        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountry))
            )
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCountry.getCodeA2()).isEqualTo(UPDATED_CODE_A_2);
        assertThat(testCountry.getCodeA3()).isEqualTo(UPDATED_CODE_A_3);
        assertThat(testCountry.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testCountry.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, country.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(country))
            )
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(country))
            )
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();
        country.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeDelete = countryRepository.findAll().size();

        // Delete the country
        restCountryMockMvc
            .perform(delete(ENTITY_API_URL_ID, country.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
