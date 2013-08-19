package org.groundres.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import java.lang.Override;
import javax.persistence.OneToOne;
import org.groundres.model.Court;

@Entity
@Table(name = "USERS")
@NamedQueries({@NamedQuery(name = "findUserByUsernameAndPassword", query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password")})
public class User implements Serializable {

    private static final long serialVersionUID = 5465844098830444084L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id = null;
 
    @Version
    @Column(name = "version")
    private int version = 0;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String realName;

    @OneToOne(mappedBy = "host")
    private Court court;

    public User() {
    }
    
    public User(String username) {
        this(username, null, null, null);
    }

    public User(String username, String password, String realName, Court court) {
        this.username = username;
        this.password = password;
        this.realName = realName;
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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(final String realName) {
        this.realName = realName;
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
        if (username != null && !username.trim().isEmpty())
            result += "username: " + username;
        if (password != null && !password.trim().isEmpty())
            result += ", password: " + password;
        if (realName != null && !realName.trim().isEmpty())
            result += ", realName: " + realName;
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        User other = (User) obj;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }
}