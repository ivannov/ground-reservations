package org.groundres.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import java.lang.Override;
import javax.persistence.OneToOne;
import org.groundres.model.User;
import org.groundres.model.Offer;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@NamedQueries(
        { @NamedQuery(name = "findCourtByName", query = "SELECT c FROM Court c WHERE c.name = :name"),
        @NamedQuery(name = "findCourtByHost", query = "SELECT c FROM Court c WHERE c.host.username = :hostUsername"),
        @NamedQuery(name = "findAllCourts", query = "SELECT c FROM Court c")})
public class Court implements Serializable {

    private static final long serialVersionUID = -6294720529431245686L;

    public static final int DEFAULT_START_HOUR = 11;
    public static final int DEFAULT_END_HOUR = 16;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id = null;

    @Version
    @Column(name = "version")
    private int version = 0;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    private Integer defaultPrice;

    @OneToOne
    private User host;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Offer> offers = new HashSet<Offer>();

    public Court() {
    }

    public Court(String name, String address) {
        this(name, null, null, address, null, null, null);
    }
    
    public Court(String name, String description, String phone, String address, Integer defaultPrice,
            User host, Set<Offer> offers) {
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.address = address;
        this.defaultPrice = defaultPrice;
        this.host = host;
        this.offers = offers;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public Integer getDefaultPrice() {
        return this.defaultPrice;
    }

    public void setDefaultPrice(final Integer defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public User getHost() {
        return this.host;
    }

    public void setHost(final User host) {
        this.host = host;
    }

    public Set<Offer> getOffers() {
        return this.offers;
    }

    public void setOffers(final Set<Offer> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (name != null && !name.trim().isEmpty())
            result += "name: " + name;
        if (description != null && !description.trim().isEmpty())
            result += ", description: " + description;
        if (phone != null && !phone.trim().isEmpty())
            result += ", phone: " + phone;
        if (address != null && !address.trim().isEmpty())
            result += ", address: " + address;
        if (defaultPrice != null)
            result += ", defaultPrice: " + defaultPrice;
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Court other = (Court) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}