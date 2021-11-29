package com.ag04.geodata.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(max = 20)
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @NotNull
    @Size(max = 2)
    @Column(name = "code_a_2", length = 2, nullable = false)
    private String codeA2;

    @NotNull
    @Size(max = 3)
    @Column(name = "code_a_3", length = 3, nullable = false)
    private String codeA3;

    @Size(max = 255)
    @Column(name = "flag", length = 255)
    private String flag;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Country id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Country name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public Country code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeA2() {
        return this.codeA2;
    }

    public Country codeA2(String codeA2) {
        this.codeA2 = codeA2;
        return this;
    }

    public void setCodeA2(String codeA2) {
        this.codeA2 = codeA2;
    }

    public String getCodeA3() {
        return this.codeA3;
    }

    public Country codeA3(String codeA3) {
        this.codeA3 = codeA3;
        return this;
    }

    public void setCodeA3(String codeA3) {
        this.codeA3 = codeA3;
    }

    public String getFlag() {
        return this.flag;
    }

    public Country flag(String flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Country active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return id != null && id.equals(((Country) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", codeA2='" + getCodeA2() + "'" +
            ", codeA3='" + getCodeA3() + "'" +
            ", flag='" + getFlag() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
