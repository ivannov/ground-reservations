package org.groundres.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@NamedQueries({@NamedQuery(name = "findAllOffersForTimeframe", query = "SELECT o FROM Offer o WHERE o.timeSlot > :fromTime and o.timeSlot < :toTime"),
               @NamedQuery(name = "findAllOffersForCourt", query = "SELECT o FROM Offer o WHERE o.court.name = :courtName")})
public class Offer implements Serializable {

    private static final long serialVersionUID = -4255469185314727357L;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id = null;
 
    @Version
    @Column(name = "version")
    private int version = 0;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeSlot;

    @Column
    private Integer price;

    @ManyToOne
    private Court court;

    public Offer() {
    }

    public Offer(Date timeSlot, Integer price, Court court) {
        this.timeSlot = timeSlot;
        this.price = price;
        this.court = court;
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

    public Date getTimeSlot() {
        return this.timeSlot;
    }

    public void setTimeSlot(final Date timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(final Integer price) {
        this.price = price;
    }

    public Court getCourt() {
        return this.court;
    }

    public void setCourt(final Court court) {
        this.court = court;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (price != null)
            result += "price: " + price;
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((court == null) ? 0 : court.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((timeSlot == null) ? 0 : timeSlot.hashCode());
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
        Offer other = (Offer) obj;
        if (court == null) {
            if (other.court != null)
                return false;
        } else if (!court.equals(other.court))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (timeSlot == null) {
            if (other.timeSlot != null)
                return false;
        } else if (!timeSlot.equals(other.timeSlot))
            return false;
        return true;
    }
}