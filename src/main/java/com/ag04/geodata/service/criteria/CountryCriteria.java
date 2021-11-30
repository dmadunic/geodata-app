package com.ag04.geodata.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.ag04.geodata.domain.Country} entity. This class is used
 * in {@link com.ag04.geodata.web.rest.CountryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /countries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CountryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private StringFilter codeA2;

    private StringFilter codeA3;

    private StringFilter flag;

    private BooleanFilter active;

    private Boolean distinct;

    public CountryCriteria() {}

    public CountryCriteria(CountryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.codeA2 = other.codeA2 == null ? null : other.codeA2.copy();
        this.codeA3 = other.codeA3 == null ? null : other.codeA3.copy();
        this.flag = other.flag == null ? null : other.flag.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CountryCriteria copy() {
        return new CountryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getCodeA2() {
        return codeA2;
    }

    public StringFilter codeA2() {
        if (codeA2 == null) {
            codeA2 = new StringFilter();
        }
        return codeA2;
    }

    public void setCodeA2(StringFilter codeA2) {
        this.codeA2 = codeA2;
    }

    public StringFilter getCodeA3() {
        return codeA3;
    }

    public StringFilter codeA3() {
        if (codeA3 == null) {
            codeA3 = new StringFilter();
        }
        return codeA3;
    }

    public void setCodeA3(StringFilter codeA3) {
        this.codeA3 = codeA3;
    }

    public StringFilter getFlag() {
        return flag;
    }

    public StringFilter flag() {
        if (flag == null) {
            flag = new StringFilter();
        }
        return flag;
    }

    public void setFlag(StringFilter flag) {
        this.flag = flag;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public BooleanFilter active() {
        if (active == null) {
            active = new BooleanFilter();
        }
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CountryCriteria that = (CountryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(codeA2, that.codeA2) &&
            Objects.equals(codeA3, that.codeA3) &&
            Objects.equals(flag, that.flag) &&
            Objects.equals(active, that.active) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, codeA2, codeA3, flag, active, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (codeA2 != null ? "codeA2=" + codeA2 + ", " : "") +
            (codeA3 != null ? "codeA3=" + codeA3 + ", " : "") +
            (flag != null ? "flag=" + flag + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
